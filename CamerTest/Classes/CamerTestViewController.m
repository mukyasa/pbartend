//
//  CamerTestViewController.m
//  CamerTest
//
//  Created by Darren Mason on 7/12/10.
//  Copyright TGen 2010. All rights reserved.
//

#import "CamerTestViewController.h"
#import "AVCamDemoCaptureManager.h"

@implementation CamerTestViewController


@synthesize featureButton,captureManager,videoPreviewView,captureVideoPreviewLayer,cameraToggleButton,stillImageButton,magoverlay;


/*
// The designated initializer. Override to perform setup that is required before the view is loaded.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if ((self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil])) {
        // Custom initialization
    }
    return self;
}
*/

/*
// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView {
}
*/


- (void)viewDidDisappear:(BOOL)animated
{
	[captureVideoPreviewLayer.session stopRunning];
}

- (void)applyDefaults
{
	effectiveScale = 1.0;
	effectiveRotationRadians = 0.0;
	effectiveTranslation = CGPointMake(0.0, 0.0);
	[captureVideoPreviewLayer setAffineTransform:CGAffineTransformIdentity];
	captureVideoPreviewLayer.frame = videoPreviewView.layer.bounds;
}

// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
	
	NSError *error;
	
	captureManager = [[AVCamDemoCaptureManager alloc] init];
	if ([captureManager setupSessionWithPreset:AVCaptureSessionPresetHigh error:&error]) {
        [self setCaptureManager:captureManager];
		
		AVCaptureSession *session = [[[AVCaptureSession alloc] init] autorelease];
		AVCaptureDevice *videoDevice = nil;
        for ( AVCaptureDevice *device in [AVCaptureDevice devicesWithMediaType:AVMediaTypeVideo] ) {
            if ( device.position == AVCaptureDevicePositionFront ) {
                videoDevice = device;
                break;
            }
        }
		
		if ( ! videoDevice )
            videoDevice = [AVCaptureDevice defaultDeviceWithMediaType:AVMediaTypeVideo];
		
        if ( videoDevice ) {
			AVCaptureDeviceInput *input = [AVCaptureDeviceInput deviceInputWithDevice:videoDevice error:nil];
			[session addInput:input];
		}
		
        captureVideoPreviewLayer = [[AVCaptureVideoPreviewLayer alloc] initWithSession:[captureManager session]];
		[self applyDefaults];
        UIView *previewParentView = [self videoPreviewView];
        CALayer *viewLayer = [previewParentView layer];
        [viewLayer setMasksToBounds:YES];
        
        CGRect bounds = [previewParentView bounds];
        
        [captureVideoPreviewLayer setFrame:bounds];
        
        if ([captureVideoPreviewLayer isOrientationSupported]) {
            [captureVideoPreviewLayer setOrientation:AVCaptureVideoOrientationPortrait];
        }
        
        [captureVideoPreviewLayer setVideoGravity:AVLayerVideoGravityResizeAspectFill];
	
		
		/***** manipulation ******/
		// clip sub-layer contents
		previewParentView.layer.masksToBounds = YES;
		// do one time set-up of gesture recognizers
		UIGestureRecognizer *recognizer;
		
		recognizer = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(handleSingleTapFrom:)];
		recognizer.delegate = self;
		[previewParentView addGestureRecognizer:recognizer];
		[recognizer release];
		
		recognizer = [[UIPinchGestureRecognizer alloc] initWithTarget:self action:@selector(handlePinchFrom:)];
		recognizer.delegate = self;
		[previewParentView addGestureRecognizer:recognizer];
		[recognizer release];
		
		recognizer = [[UIPanGestureRecognizer alloc] initWithTarget:self action:@selector(handleDragFrom:)];
		recognizer.delegate = self;
		((UIPanGestureRecognizer *)recognizer).maximumNumberOfTouches = 1;
		[previewParentView addGestureRecognizer:recognizer];
		[recognizer release];
		
		recognizer = [[UIRotationGestureRecognizer alloc] initWithTarget:self action:@selector(handleRotationFrom:)];
		recognizer.delegate = self;
		[previewParentView addGestureRecognizer:recognizer];
		[recognizer release];
		 
		/************/
        
        [self setCaptureVideoPreviewLayer:captureVideoPreviewLayer];
		 
		
		//[captureManager setDelegate:self];
		
		[viewLayer insertSublayer:captureVideoPreviewLayer below:[[viewLayer sublayers] objectAtIndex:0]];
		
		
		[captureVideoPreviewLayer release];
	}  
	
	[captureManager release];
    [super viewDidLoad]; 
}

- (BOOL)gestureRecognizerShouldBegin:(UIGestureRecognizer *)gestureRecognizer
{
	if ( [gestureRecognizer isKindOfClass:[UIPinchGestureRecognizer class]] ) {
		beginGestureScale = effectiveScale;
	}
	else if ( [gestureRecognizer isKindOfClass:[UIRotationGestureRecognizer class]] ) {
		beginGestureRotationRadians = effectiveRotationRadians;
	}
	if ( [gestureRecognizer isKindOfClass:[UIPanGestureRecognizer class]] ) {
        CGPoint location = [gestureRecognizer locationInView:videoPreviewView];
        beginGestureTranslation = CGPointMake(effectiveTranslation.x - location.x, effectiveTranslation.x - location.y);
	}
	return YES;
}

- (BOOL)gestureRecognizer:(UIGestureRecognizer *)gestureRecognizer shouldReceiveTouch:(UITouch *)touch
{
	if ( touch.view == videoPreviewView )
		return YES;
	return NO;
}

- (void)handleSingleTapFrom:(UITapGestureRecognizer *)recognizer
{
	CGPoint location = [recognizer locationInView:videoPreviewView];
	CGPoint convertedLocation = [captureVideoPreviewLayer convertPoint:location fromLayer:captureVideoPreviewLayer.superlayer];
	if ( [captureVideoPreviewLayer containsPoint:convertedLocation] ) {
		// cycle to next video gravity mode.
		NSString *videoGravity = captureVideoPreviewLayer.videoGravity;
		if ( [videoGravity isEqualToString:AVLayerVideoGravityResizeAspect] )
			captureVideoPreviewLayer.videoGravity = AVLayerVideoGravityResizeAspectFill;
		else if ( [videoGravity isEqualToString:AVLayerVideoGravityResizeAspectFill] )
			captureVideoPreviewLayer.videoGravity = AVLayerVideoGravityResize;
		else if ( [videoGravity isEqualToString:AVLayerVideoGravityResize] )
			captureVideoPreviewLayer.videoGravity = AVLayerVideoGravityResizeAspect;
	}
}

- (void)handleRotationFrom:(UIRotationGestureRecognizer *)recognizer
{
	BOOL allTouchesAreOnThePreviewLayer = YES;
	NSUInteger numTouches = [recognizer numberOfTouches], i;
	for ( i = 0; i < numTouches; ++i ) {
		CGPoint location = [recognizer locationOfTouch:i inView:videoPreviewView];
		CGPoint convertedLocation = [captureVideoPreviewLayer convertPoint:location fromLayer:captureVideoPreviewLayer.superlayer];
		if ( ! [captureVideoPreviewLayer containsPoint:convertedLocation] ) {
			allTouchesAreOnThePreviewLayer = NO;
			break;
		}
	}
	
	if ( allTouchesAreOnThePreviewLayer ) {
		effectiveRotationRadians = beginGestureRotationRadians + recognizer.rotation;
		[self makeAndApplyAffineTransform];
	}
}

- (void)handleDragFrom:(UIPanGestureRecognizer *)recognizer
{
	CGPoint location = [recognizer locationInView:videoPreviewView];
	CGPoint convertedLocation = [captureVideoPreviewLayer convertPoint:location fromLayer:captureVideoPreviewLayer.superlayer];
	
	if ( [captureVideoPreviewLayer containsPoint:convertedLocation] ) {
        effectiveTranslation = CGPointMake(beginGestureTranslation.x + location.x, beginGestureTranslation.y + location.y);
        [self makeAndApplyAffineTransform];
	}
}

- (void)handlePinchFrom:(UIPinchGestureRecognizer *)recognizer
{
	BOOL allTouchesAreOnThePreviewLayer = YES;
	NSUInteger numTouches = [recognizer numberOfTouches], i;
	for ( i = 0; i < numTouches; ++i ) {
		CGPoint location = [recognizer locationOfTouch:i inView:videoPreviewView];
		CGPoint convertedLocation = [captureVideoPreviewLayer convertPoint:location fromLayer:captureVideoPreviewLayer.superlayer];
		if ( ! [captureVideoPreviewLayer containsPoint:convertedLocation] ) {
			allTouchesAreOnThePreviewLayer = NO;
			break;
		}
	}
	
	if ( allTouchesAreOnThePreviewLayer ) {
		effectiveScale = beginGestureScale * recognizer.scale;
		[self makeAndApplyAffineTransform];
	}
}

- (void)makeAndApplyAffineTransform
{
	// translate, then scale, then rotate
	CGAffineTransform affineTransform = CGAffineTransformMakeTranslation(effectiveTranslation.x, effectiveTranslation.y);
	affineTransform = CGAffineTransformScale(affineTransform, effectiveScale, effectiveScale);
	affineTransform = CGAffineTransformRotate(affineTransform, effectiveRotationRadians);
	[CATransaction begin];
	[CATransaction setAnimationDuration:.025];
	[captureVideoPreviewLayer setAffineTransform:affineTransform];
	[CATransaction commit];
}

- (IBAction)cameraToggle:(id)sender
{
    [[self captureManager] cameraToggle];
         
}
- (IBAction)still:(id)sender
{

    [[self captureManager] captureStillImageMerge:[magoverlay image]];
	//[[self captureManager] captureStillImage];
    
	//causes the flash
    UIView *flashView = [[UIView alloc] initWithFrame:[[self view] frame]];
    [flashView setBackgroundColor:[UIColor whiteColor]];
    [flashView setAlpha:0.f];
    [[[self view] window] addSubview:flashView];
    
    [UIView animateWithDuration:.5f
                     animations:^{
                         [flashView setAlpha:1.f];
                         [flashView setAlpha:0.f];
                     }
                     completion:^(BOOL finished){
                         [flashView removeFromSuperview];
                         [flashView release];
                     }
	 ];
}

- (void) captureStillImageFailedWithError:(NSError *)error
{
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Still Image Capture Failure"
                                                        message:[error localizedDescription]
                                                       delegate:nil
                                              cancelButtonTitle:@"Okay"
                                              otherButtonTitles:nil];
    [alertView show];
    [alertView release];
}

/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

- (void)didReceiveMemoryWarning {
	// Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
	
	// Release any cached data, images, etc that aren't in use.
}

- (void)viewDidUnload {
	// Release any retained subviews of the main view.
	// e.g. self.myOutlet = nil;
}


- (void)dealloc {
	[magoverlay release];
	[stillImageButton release];
	[cameraToggleButton release];
	[captureVideoPreviewLayer release];
	[videoPreviewView release];
	[captureManager release];
	[featureButton release];
    [super dealloc];
}

@end
