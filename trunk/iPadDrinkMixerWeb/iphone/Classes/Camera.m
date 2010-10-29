//
//  Camera.m
//  PhoneGap
//
//  Created by Shazron Abdullah on 15/07/09.
//  Copyright 2009 Nitobi. All rights reserved.
//

#import "Camera.h"
#import "NSData+Base64.h"
#import "Categories.h"

@implementation Camera

- (void) getPicture:(NSMutableArray*)arguments withDict:(NSMutableDictionary*)options
{
	NSUInteger argc = [arguments count];
	NSString* successCallback = nil, *errorCallback = nil;
	
	if (argc > 0) successCallback = [arguments objectAtIndex:0];
	if (argc > 1) errorCallback = [arguments objectAtIndex:1];
	
	if (argc < 1) {
		NSLog(@"Camera.getPicture: Missing 1st parameter.");
		return;
	}
	
	bool hasCamera = [UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera];
	if (!hasCamera) {
		NSLog(@"Camera.getPicture: Camera not available.");
		return;
	}
	
	if (pickerController == nil) {
		pickerController = [[CameraPicker alloc] init];
		pickerController.delegate = self;
		pickerController.sourceType = UIImagePickerControllerSourceTypeCamera;
		pickerController.successCallback = successCallback;
		pickerController.errorCallback = errorCallback;
		pickerController.quality = [options integerValueForKey:@"quality" defaultValue:100 withRange:NSMakeRange(0, 100)];
	}
	
	[[super appViewController] presentModalViewController:pickerController animated:YES];
}
-(void) getPictureFromLibrary:(NSMutableArray*)arguments withDict:(NSMutableDictionary*)options{
	
	NSUInteger argc = [arguments count];
	NSString* successCallback = nil, *errorCallback = nil;
	
	if (argc > 0) successCallback = [arguments objectAtIndex:0];
	if (argc > 1) errorCallback = [arguments objectAtIndex:1];
	
	if (argc < 1) {
		NSLog(@"Camera.getPicture: Missing 1st parameter.");
		return;
	}
	
	if (pickerController == nil) {
		pickerController = [[CameraPicker alloc] init];
		pickerController.delegate = self;
		pickerController.sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
		pickerController.successCallback = successCallback;
		pickerController.errorCallback = errorCallback;
		//pickerController.quality = [options integerValueForKey:@"quality" defaultValue:100 withRange:NSMakeRange(0, 100)];
	}
	
	//[[super appViewController] presentModalViewController:pickerController animated:YES];
	
	popover = [[UIPopoverController alloc]	initWithContentViewController:pickerController];
	[popover presentPopoverFromRect:CGRectMake(200, 200, 150, 200) inView:webView permittedArrowDirections:UIPopoverArrowDirectionLeft	animated:true];
}


- (void)imagePickerController:(UIImagePickerController*)picker didFinishPickingImage:(UIImage*)image editingInfo:(NSDictionary*)editingInfo
{
	UIImage *sizedImg = [self resizeImage:image recSize:CGRectMake(0, 0, 201, 167)];
	
	CameraPicker* cameraPicker = (CameraPicker*)picker;
	CGFloat quality = (double)cameraPicker.quality / 100.0; 
	NSData* data = UIImageJPEGRepresentation(sizedImg, quality);
	
	//[picker dismissModalViewControllerAnimated:YES];
	[popover dismissPopoverAnimated:YES];
	
	if (cameraPicker.successCallback) {
		NSString* jsString = [[NSString alloc] initWithFormat:@"%@(\"%@\");", cameraPicker.successCallback, [data base64EncodedString]];
		[webView stringByEvaluatingJavaScriptFromString:jsString];
		[jsString release];
	}
	
}

-(UIImage*) resizeImage:(UIImage*)inImage recSize:(CGRect)thumbRect{
	
	CGImageRef			imageRef = [inImage CGImage];
	CGImageAlphaInfo	alphaInfo = CGImageGetAlphaInfo(imageRef);
	
	// There's a wierdness with kCGImageAlphaNone and CGBitmapContextCreate
	// see Supported Pixel Formats in the Quartz 2D Programming Guide
	// Creating a Bitmap Graphics Context section
	// only RGB 8 bit images with alpha of kCGImageAlphaNoneSkipFirst, kCGImageAlphaNoneSkipLast, kCGImageAlphaPremultipliedFirst,
	// and kCGImageAlphaPremultipliedLast, with a few other oddball image kinds are supported
	// The images on input here are likely to be png or jpeg files
	if (alphaInfo == kCGImageAlphaNone)
		alphaInfo = kCGImageAlphaNoneSkipLast;
	
	// Build a bitmap context that's the size of the thumbRect
	CGContextRef bitmap = CGBitmapContextCreate(
												NULL,
												thumbRect.size.width,		// width
												thumbRect.size.height,		// height
												CGImageGetBitsPerComponent(imageRef),	// really needs to always be 8
												4 * thumbRect.size.width,	// rowbytes
												CGImageGetColorSpace(imageRef),
												alphaInfo
												);
	
	// Draw into the context, this scales the image
	CGContextDrawImage(bitmap, thumbRect, imageRef);
	
	// Get an image from the context and a UIImage
	CGImageRef	ref = CGBitmapContextCreateImage(bitmap);
	UIImage*	result = [UIImage imageWithCGImage:ref];
	
	CGContextRelease(bitmap);	// ok if NULL
	CGImageRelease(ref);
	
	return result;
	
}


- (void)imagePickerControllerDidCancel:(UIImagePickerController*)picker
{
	[picker dismissModalViewControllerAnimated:YES];
}




- (void) postImage:(UIImage*)anImage withFilename:(NSString*)filename toUrl:(NSURL*)url 
{
	NSString *boundary = @"----BOUNDARY_IS_I";
	
	NSMutableURLRequest *req = [NSMutableURLRequest requestWithURL:url];
	[req setHTTPMethod:@"POST"];
	
	NSString *contentType = [NSString stringWithFormat:@"multipart/form-data; boundary=%@", boundary];
	[req setValue:contentType forHTTPHeaderField:@"Content-type"];
	
	NSData *imageData = UIImagePNGRepresentation(anImage);
	
	// adding the body
	NSMutableData *postBody = [NSMutableData data];
	
	// first parameter an image
	[postBody appendData:[[NSString stringWithFormat:@"\r\n--%@\r\n", boundary] dataUsingEncoding:NSUTF8StringEncoding]];
	[postBody appendData:[[NSString stringWithFormat:@"Content-Disposition: form-data; name=\"upload\"; filename=\"%@\"\r\n", filename] dataUsingEncoding:NSUTF8StringEncoding]];
	[postBody appendData:[@"Content-Type: image/png\r\n\r\n" dataUsingEncoding:NSUTF8StringEncoding]];
	[postBody appendData:imageData];
	
	//	// second parameter information
	//	[postBody appendData:[[NSString stringWithFormat:@"\r\n--%@\r\n", boundary] dataUsingEncoding:NSUTF8StringEncoding]];
	//	[postBody appendData:[@"Content-Disposition: form-data; name=\"some_other_name\"\r\n\r\n" dataUsingEncoding:NSUTF8StringEncoding]];
	//	[postBody appendData:[@"some_other_value" dataUsingEncoding:NSUTF8StringEncoding]];
	//	[postBody appendData:[[NSString stringWithFormat:@"\r\n--%@--\r \n",boundary] dataUsingEncoding:NSUTF8StringEncoding]];
	
	[req setHTTPBody:postBody];
	
	NSURLResponse* response;
	NSError* error;
	[NSURLConnection sendSynchronousRequest:req returningResponse:&response error:&error];
	
	//  NSData* result = [NSURLConnection sendSynchronousRequest:req returningResponse:&response error:&error];
	//	NSString * resultStr =  [[[NSString alloc] initWithData:result encoding:NSUTF8StringEncoding] autorelease];
}

- (void) dealloc
{
	if (pickerController) {
		[pickerController release];
	}
	
	[super dealloc];
}

@end


@implementation CameraPicker

@synthesize quality;
@synthesize successCallback;
@synthesize errorCallback;

- (void) dealloc
{
	if (successCallback) {
		[successCallback release];
	}
	if (errorCallback) {
		[errorCallback release];
	}
	
	[super dealloc];
}

@end
