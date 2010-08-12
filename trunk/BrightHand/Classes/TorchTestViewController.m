//
//  TorchTestViewController.m
//  TorchTest
//
//  Created by Darren Mason on 7/8/10.
//  Copyright TGen 2010. All rights reserved.
//

#import "TorchTestViewController.h"
#import "AVCamDemoCaptureManager.h" 

@implementation TorchTestViewController
#define SEGMENT_BUTTON_OFF 0
#define SEGMENT_BUTTON_ON 1
#define SEGMENT_BUTTON_STROBE 2

@synthesize torchSwitch,captureManager,timeLabel,strobeScroller,myTimer,background,bannerView;


-(IBAction) toggleTorch:(id)sender{
	
	switch ([(UISegmentedControl *)sender selectedSegmentIndex]) {
        case SEGMENT_BUTTON_OFF:
			[self stopTimer];
			isLightOn =NO;
			isStrobing=NO;
			[background setImage:[UIImage imageNamed:@"base_bg_off.png"]];			
            [[self captureManager] setTorchMode:AVCaptureTorchModeOff];
            break;
        case SEGMENT_BUTTON_ON:
			[self stopTimer];
			isLightOn=YES;
			isStrobing=NO;
			[background setImage:[UIImage imageNamed:@"base_bg_on.png"]];
			
            [[self captureManager] setTorchMode:AVCaptureTorchModeOn];
            break;
		case SEGMENT_BUTTON_STROBE:
			isStrobing=YES;
			isLightOn=YES;
			[background setImage:[UIImage imageNamed:@"base_bg_on.png"]];			
			toggle = AVCaptureTorchModeOn;	
			[self stopTimer];
			myTimer = [NSTimer scheduledTimerWithTimeInterval:interval target:self selector:@selector(updateCounter:) userInfo:nil repeats:YES];
			break;

    }	

}
- (void)flipsideViewControllerDidFinish:(FlipsideViewController *)controller {
    
	[self dismissModalViewControllerAnimated:YES];
}
- (IBAction)showInfo:(id)sender {    
	
	FlipsideViewController *controller = [[FlipsideViewController alloc] initWithNibName:@"FlipsideView" bundle:nil];
	controller.delegate = self;
	
	controller.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;
	[self presentModalViewController:controller animated:YES];
	//NSLog(@"FlipsideViewController retain count: %i",[controller retainCount]);
	[controller release];
}

- (void)stopTimer
{
	[myTimer invalidate];
	 myTimer = nil;
	
}


- (void)updateCounter:(NSTimer *)theTimer {
	
	//NSLog(@"Called");
	
	if(isLightOn && isStrobing){
		if(toggle==AVCaptureTorchModeOn)
		{
			toggle = AVCaptureTorchModeOff;
			[[self captureManager] setTorchMode:AVCaptureTorchModeOff];
		}
		else 
		{
			toggle = AVCaptureTorchModeOn;
			[[self captureManager] setTorchMode:AVCaptureTorchModeOn];
		}
				
	}
	else if(!isLightOn)
	{
		toggle = AVCaptureTorchModeOff;
		[[self captureManager] setTorchMode:AVCaptureTorchModeOff];
	}
	else {
		toggle = AVCaptureTorchModeOn;
		[[self captureManager] setTorchMode:AVCaptureTorchModeOn];
	}

}

- (void)scrollViewDidEndDragging:(UIScrollView *)scrollView willDecelerate:(BOOL)decelerate{
	
	[self stopTimer];
	if(torchSwitch.selectedSegmentIndex == SEGMENT_BUTTON_STROBE)
		myTimer = [NSTimer scheduledTimerWithTimeInterval:interval target:self selector:@selector(updateCounter:) userInfo:nil repeats:YES];
	
}
- (void)scrollViewDidScroll:(UIScrollView *)scrollView{

	float screenScroll = scrollView.contentOffset.x/100;
	if(screenScroll < .05)
		screenScroll = .05f;
	if(screenScroll > 5.0)
		screenScroll =5.0f;
	
	[timeLabel setText:[NSString stringWithFormat:@"%.2f second(s)", screenScroll]];
	
	interval = [[[NSString alloc] initWithFormat:@"%.2f",screenScroll] floatValue];
	
	//NSLog(@"View Did Scroll  %.2f",scrollView.contentOffset.x);

}

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
    
	[self moveBannerViewOffscreen];//hide banner
	
	//set up scroller view
	strobeScroller.contentSize = CGSizeMake(792.0f, 30.0f);
	strobeScroller.showsHorizontalScrollIndicator = NO;
	strobeScroller.decelerationRate = UIScrollViewDecelerationRateFast;
	
	//set minimum scroll
	interval=.05f;
	NSError *error;
	
	captureManager = [[AVCamDemoCaptureManager alloc] init];
    if ([captureManager setupSessionWithPreset:AVCaptureSessionPresetHigh error:&error]) {
        [self setCaptureManager:captureManager];
	}
	
	toggle = AVCaptureTorchModeOn;
	isLightOn=YES;			
	[[self captureManager] setTorchMode:AVCaptureTorchModeOn];
	
	[super viewDidLoad];
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



/****banner view *****/
-(void) moveBannerViewOnscreen{
	
	CGRect newBannerview = self.bannerView.frame;
	newBannerview.origin.y = self.view.frame.size.height-newBannerview.size.height;
	
	[UIView beginAnimations:@"BannerViewIntro" context:NULL];
	self.bannerView.frame = newBannerview;
	[UIView commitAnimations];
	
}
-(void) moveBannerViewOffscreen{
	CGFloat viewHeight = self.view.frame.size.height;
	CGRect newBannerview = self.bannerView.frame;
	newBannerview.origin.y = viewHeight;
	
	self.bannerView.frame =newBannerview;
	
}
- (void)bannerView:(ADBannerView *)banner didFailToReceiveAdWithError:(NSError *)error;
{
	[self moveBannerViewOffscreen];
}
- (void)bannerViewDidLoadAd:(ADBannerView *)banner{
	[self moveBannerViewOnscreen];
}
- (void)dealloc {
	bannerView.delegate = nil;
	[bannerView release];
	[self stopTimer];
	[background release];
	[myTimer release];
	[strobeScroller release];
	[timeLabel release];
	[captureManager release];
	[torchSwitch release];
    [super dealloc];
}

@end
