//
//  MainMagPicViewController.m
//  MagPicMe
//
//  Created by Darren Mason on 7/15/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import "MainMagPicViewController.h"
#import "MainViewController.h"

@implementation MainMagPicViewController

@synthesize saveNavBar,delegate,swipeLeftRecognizer, tapRecognizer,parentPreviewView,parentPreviewImageView;

/*
 // The designated initializer.  Override if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if ((self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil])) {
        // Custom initialization
    }
    return self;
}
*/
- (void)viewDidAppear:(BOOL)animated{
[self moveNavViewOnscreen];
}

- (void)viewWillAppear:(BOOL)animated{
	//parentPreviewView
	NSArray *viewControllers = [self.tabBarController viewControllers];
	MainViewController *mainViewController = [viewControllers objectAtIndex:0];
	parentPreviewImageView.image = mainViewController.pickedCover;

}

// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
	
	[self moveNavViewOffscreen];
	
	// clip sub-layer contents
	parentPreviewView.layer.masksToBounds = YES;
	
	// do one time set-up of gesture recognizers
	UIGestureRecognizer *recognizer;
	
	recognizer = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(handleSingleTapFrom:)];
	recognizer.delegate = self;
	[parentPreviewView addGestureRecognizer:recognizer];
	[recognizer release];
	
	recognizer = [[UIPinchGestureRecognizer alloc] initWithTarget:self action:@selector(handlePinchFrom:)];
	recognizer.delegate = self;
	[parentPreviewView addGestureRecognizer:recognizer];
	[recognizer release];
	
	recognizer = [[UIRotationGestureRecognizer alloc] initWithTarget:self action:@selector(handleRotationFrom:)];
	recognizer.delegate = self;
	[parentPreviewView addGestureRecognizer:recognizer];
	[recognizer release];
	
	
	recognizer = [[UIPanGestureRecognizer alloc] initWithTarget:self action:@selector(handleDragFrom:)];
	recognizer.delegate = self;
	((UIPanGestureRecognizer *)recognizer).maximumNumberOfTouches = 1;
	[parentPreviewView addGestureRecognizer:recognizer];
	[recognizer release];
	
    [super viewDidLoad];
}

- (void)handleSingleTapFrom:(UITapGestureRecognizer *)recognizer
{
	//NSLog(@"Single Tap");
	[self moveNavViewOnscreen];
}

- (void)handlePinchFrom:(UIPinchGestureRecognizer *)recognizer
{
	//NSLog(@"Pinch");
	[self moveNavViewOffscreen];

	
	if (recognizer.state == UIGestureRecognizerStateBegan) {
		beginGestureScale = 1;
	}
	parentPreviewView.transform = CGAffineTransformScale(parentPreviewView.transform, (recognizer.scale / beginGestureScale), (recognizer.scale / beginGestureScale));
	beginGestureScale = recognizer.scale;
	
}

- (void)handleDragFrom:(UIPanGestureRecognizer *)recognizer
{
	//NSLog(@"Pan");
	[self moveNavViewOffscreen];
	
	if (recognizer.state == UIGestureRecognizerStateBegan) {
		CGPoint startPoint = [recognizer locationOfTouch:0 inView:parentPreviewView];
		inImage = [self point:startPoint inView:parentPreviewView];
		oldX = 0;
		oldY = 0;
	}
	if (inImage) {
		CGPoint translate = [recognizer translationInView: parentPreviewView];
		parentPreviewView.transform = CGAffineTransformTranslate(parentPreviewView.transform, translate.x-oldX, translate.y-oldY);
		oldX = translate.x;
		oldY = translate.y;
	}
	 
	 
	
}


-(BOOL)point:(CGPoint)p inView:(UIView *)view {
	return p.x > 0 && p.x < view.bounds.size.width && p.y > 0 && p.y < view.bounds.size.height;
}


- (void)handleRotationFrom:(UIRotationGestureRecognizer *)recognizer
{
	//NSLog(@"Rotate");
	[self moveNavViewOffscreen];
	
	if (recognizer.state == UIGestureRecognizerStateBegan) {
		beginGestureRotationRadians	= 0;
	}
	parentPreviewView.transform = CGAffineTransformRotate(parentPreviewView.transform, (recognizer.rotation - beginGestureRotationRadians));
	beginGestureRotationRadians = recognizer.rotation;
	
}

/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

-(IBAction)showPictureControls:(id)sender  {
	
	UIActionSheet *actionSheet = [[UIActionSheet alloc] initWithTitle:@"Choose your photo source." delegate:self cancelButtonTitle:@"Cancel" destructiveButtonTitle:nil otherButtonTitles:@"Camera", @"Photo Library", nil];
    actionSheet.actionSheetStyle = UIActionSheetStyleDefault;
	
	[actionSheet showInView:self.parentPreviewView];	
	
	[actionSheet release];


}

-(UIImage*)addImage:(UIImageView*)imageview1 toImageView:(UIImageView*)imageView2 toImage:(UIImage*)image2{

	UIGraphicsBeginImageContext(imageView2.frame.size);
	// Draw image1
	[image2 drawInRect:CGRectMake(0, 0, imageView2.frame.size.width, imageView2.frame.size.height)];
	
	// Draw image2
	[imageview1.image drawInRect:CGRectMake(0, 0, imageView2.frame.size.width, imageView2.frame.size.height)];

	
	UIImage *resultingImage = UIGraphicsGetImageFromCurrentImageContext();
	
	UIGraphicsEndImageContext();
	
	return resultingImage;
}

-(IBAction)saveMagCover:(id)sender{

	//CGFloat width = previewImageView.frame.size.width;
	//CGFloat height = previewImageView.frame.size.height;
	
	UIGraphicsBeginImageContext(previewImageView.frame.size);
	
	CGContextRef context = UIGraphicsGetCurrentContext();
	
	//CGContextScaleCTM(context, - 1.0f, 1.0f);
	//CGContextTranslateCTM(context, -height, 0.0f);
	CGContextConcatCTM(context, parentPreviewView.transform);
	
//	CGContextDrawImage(context, CGRectMake(0.0f, 0.0f, width, height), previewImageView.image.CGImage);
	CGContextDrawImage(context, parentPreviewView.frame, previewImageView.image.CGImage);
	
	UIImage *imageCopy = UIGraphicsGetImageFromCurrentImageContext();
	
	UIGraphicsEndImageContext();
	
	UIImageWriteToSavedPhotosAlbum([self addImage:parentPreviewImageView toImageView:previewImageView toImage:imageCopy], self, @selector(imageSavedToPhotosAlbum:didFinishSavingWithError:contextInfo:), nil);

}

- (void)imageSavedToPhotosAlbum:(UIImage *)image didFinishSavingWithError:(NSError *)error contextInfo:(void *)contextInfo {
	NSString *message;
	NSString *title;
	if (!error) {
		title = NSLocalizedString(@"Picture Saved", @"");
		message = NSLocalizedString(@"Your picture was saved in your photo library.", @"");
	} else {
		title = NSLocalizedString(@"Picture Saved", @"");
		message = [error description];
	}
	UIAlertView *alert = [[UIAlertView alloc] initWithTitle:title
													message:message
												   delegate:nil
										  cancelButtonTitle:NSLocalizedString(@"Ok", @"")
										  otherButtonTitles:nil];
	[alert show];
	[alert release];
}


- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex
{
    // the user clicked one of the OK/Cancel buttons
    if (buttonIndex == 0) //camera
    {
        //NSLog(@"Camera");		
		imagePicker = [[UIImagePickerController alloc] init];
		imagePicker.sourceType = UIImagePickerControllerSourceTypeCamera;
		imagePicker.videoQuality = UIImagePickerControllerQualityTypeHigh;
		
		
		imagePicker.mediaTypes = [NSArray arrayWithObject:@"public.image"];
		imagePicker.cameraCaptureMode = UIImagePickerControllerCameraCaptureModePhoto;
		
		
		imagePicker.cameraDevice = UIImagePickerControllerCameraDeviceRear;
		imagePicker.cameraFlashMode = UIImagePickerControllerCameraFlashModeAuto;
	    
		imagePicker.delegate = self;
		imagePicker.wantsFullScreenLayout = YES;
		
		[self presentModalViewController:imagePicker animated:YES];
		
    }
	else if(buttonIndex ==1) {
		 //NSLog(@"Libaray");
		imagePicker = [[UIImagePickerController alloc] init];
		imagePicker.delegate = self;		
		imagePicker.sourceType = UIImagePickerControllerSourceTypeSavedPhotosAlbum;
	
		[self presentModalViewController:imagePicker animated:YES];
		
	}


}

/* the image after taken or picked */
- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info {
	[picker dismissModalViewControllerAnimated:YES];
	previewImageView.image = [info objectForKey:@"UIImagePickerControllerOriginalImage"];
}

-(void)done:(id)sender{


}

-(void) moveNavViewOnscreen{
	
	CGRect newBannerview = self.saveNavBar.frame;
	newBannerview.origin.y = 0;
	
	[UIView beginAnimations:@"navViewShow" context:NULL];
	[UIView setAnimationDuration:.5];
	self.saveNavBar.frame = newBannerview;
	[UIView commitAnimations];
	
}
-(void) moveNavViewOffscreen{
	
	CGRect newBannerview = self.saveNavBar.frame;
	newBannerview.origin.y = -44;
	
	[UIView beginAnimations:@"navViewShow" context:NULL];
	[UIView setAnimationDuration:.3];
	self.saveNavBar.frame = newBannerview;
	[UIView commitAnimations];

	
}

- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

- (void)viewDidUnload {
	[super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}


- (void)dealloc {

	[tapRecognizer release];
	[swipeLeftRecognizer release];
	[parentPreviewView release];
	[parentPreviewImageView release];
	[saveNavBar release];
    [super dealloc];
}


@end
