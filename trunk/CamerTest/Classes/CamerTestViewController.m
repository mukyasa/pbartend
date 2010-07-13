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



// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
	
	NSError *error;
	
	captureManager = [[AVCamDemoCaptureManager alloc] init];
	if ([captureManager setupSessionWithPreset:AVCaptureSessionPresetHigh error:&error]) {
        [self setCaptureManager:captureManager];
		
        captureVideoPreviewLayer = [[AVCaptureVideoPreviewLayer alloc] initWithSession:[captureManager session]];
        AVCamDemoPreviewView *previewParentView = [self videoPreviewView];
        CALayer *viewLayer = [previewParentView layer];
        [viewLayer setMasksToBounds:YES];
        
        CGRect bounds = [previewParentView bounds];
        
        [captureVideoPreviewLayer setFrame:bounds];
        
        if ([captureVideoPreviewLayer isOrientationSupported]) {
            [captureVideoPreviewLayer setOrientation:AVCaptureVideoOrientationPortrait];
        }
        
        [captureVideoPreviewLayer setVideoGravity:AVLayerVideoGravityResizeAspectFill];
		
		
        
        [self setCaptureVideoPreviewLayer:captureVideoPreviewLayer];
		
		
		//[captureManager setDelegate:self];
		
		[viewLayer insertSublayer:captureVideoPreviewLayer below:[[viewLayer sublayers] objectAtIndex:0]];
		
		
		[captureVideoPreviewLayer release];
	}  
	
	[captureManager release];
    [super viewDidLoad]; 
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
    UIView *flashView = [[UIView alloc] initWithFrame:[[self videoPreviewView] frame]];
    [flashView setBackgroundColor:[UIColor whiteColor]];
    [flashView setAlpha:0.f];
    [[[self view] window] addSubview:flashView];
    
    [UIView animateWithDuration:.4f
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
