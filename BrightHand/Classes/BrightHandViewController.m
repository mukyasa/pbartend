//
//  BrightHandViewController.m
//  TorchTest
//
//  Created by Darren Mason on 7/8/10.
//  Copyright TGen 2010. All rights reserved.
//

#import "BrightHandViewController.h"

#import "AVCamDemoCaptureManager.h" 

@implementation BrightHandViewController
#define SEGMENT_BUTTON_OFF 0
#define SEGMENT_BUTTON_ON 1
#define SEGMENT_BUTTON_STROBE 2
//makes pretty colors
#define UIColorFromRGB(rgbValue) [UIColor \
colorWithRed:((float)((rgbValue & 0xFF0000) >> 16))/255.0 \
green:((float)((rgbValue & 0xFF00) >> 8))/255.0 \
blue:((float)(rgbValue & 0xFF))/255.0 alpha:1.0]

@synthesize captureManager,timeLabel,strobeScroller,myTimer,background,bannerView,myCustomFont,sliderview,mainbutton;


-(IBAction) toggleTorch:(id)sender{
	NSLog(@"tag%i",mainbutton.tag);
	switch (mainbutton.tag) {
        case SEGMENT_BUTTON_OFF:
			[mainbutton setImage:[UIImage imageNamed:@"button_off.png"] forState:UIControlStateNormal];
			[self stopTimer];
			isLightOn =NO;
			isStrobing=NO;
			mainbutton.tag = SEGMENT_BUTTON_ON;
			[background setImage:[UIImage imageNamed:@"brighthandbg_off.png"]];			
            [[self captureManager] setTorchMode:AVCaptureTorchModeOff];
            break;
        case SEGMENT_BUTTON_ON:
			[mainbutton setImage:[UIImage imageNamed:@"button_on.png"] forState:UIControlStateNormal];
			[self stopTimer];
			isLightOn=YES;
			isStrobing=NO;			
			[background setImage:[UIImage imageNamed:@"brighthandbg_on.png"]];
			mainbutton.tag = SEGMENT_BUTTON_OFF;
            [[self captureManager] setTorchMode:AVCaptureTorchModeOn];
            break;
			
		case SEGMENT_BUTTON_STROBE:
			[mainbutton setImage:[UIImage imageNamed:@"button_off.png"] forState:UIControlStateNormal];
			[self stopTimer];
			isLightOn =NO;
			isStrobing=NO;
			mainbutton.tag = SEGMENT_BUTTON_ON;
			[background setImage:[UIImage imageNamed:@"brighthandbg_off.png"]];	
            [[self captureManager] setTorchMode:AVCaptureTorchModeOff];
			break;
			
    }	
	
	[UIView beginAnimations:@"MoveSlider" context:nil];
	[UIView setAnimationDuration:.8];
	[UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
	
	strobeScroller.contentOffset = CGPointMake(0, 0);
	[timeLabel setText:@"0.00"];
	
	[UIView commitAnimations];

}
- (void)flipsideViewControllerDidFinish:(FlipsideViewController *)controller {
    
	[self dismissModalViewControllerAnimated:YES];
}
- (IBAction)showInfo:(id)sender {    
	
	[mainbutton setImage:[UIImage imageNamed:@"button_off.png"] forState:UIControlStateNormal];
	[self stopTimer];
	isLightOn =NO;
	isStrobing=NO;
	mainbutton.tag = SEGMENT_BUTTON_ON;
	[background setImage:[UIImage imageNamed:@"brighthandbg_off.png"]];			
	[[self captureManager] setTorchMode:AVCaptureTorchModeOff];
	
	
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
	
	[mainbutton setImage:[UIImage imageNamed:@"button_strobe.png"] forState:UIControlStateNormal];
	isStrobing=YES;
	isLightOn=YES;
	mainbutton.tag = SEGMENT_BUTTON_STROBE;
	[background setImage:[UIImage imageNamed:@"brighthandbg_on.png"]];		
	
	toggle = AVCaptureTorchModeOn;	
	
	[self stopTimer];
	myTimer = [NSTimer scheduledTimerWithTimeInterval:interval target:self selector:@selector(updateCounter:) userInfo:nil repeats:YES];
	
}
- (void)scrollViewDidScroll:(UIScrollView *)scrollView{

	float screenScroll = scrollView.contentOffset.y/100;
	if(screenScroll < .05)
		screenScroll = .05f;
	if(screenScroll > 5.0)
		screenScroll =5.0f;
	
	[timeLabel setText:[NSString stringWithFormat:@"%.2f", screenScroll]];
	
	interval = [[[NSString alloc] initWithFormat:@"%.2f",screenScroll] floatValue];
	
	//NSLog(@"View Did Scroll  %.2f",scrollView.contentOffset.y/100);

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
    
	myCustomFont = [UIFont fontWithName:@"digital-7" size:22];
	timeLabel=[[UILabel alloc]initWithFrame:CGRectMake(22,118,52,27)];
	[timeLabel setFont:myCustomFont];
	[timeLabel setText:@"0.00"];
	timeLabel.backgroundColor = [UIColor clearColor];
	[timeLabel setTextColor:UIColorFromRGB(0xfff6d665)];//this is you hex color last 6 chars];
	[sliderview addSubview:timeLabel];
	
	[self moveBannerViewOffscreen];//hide banner
	
	//set up scroller view
	strobeScroller.contentSize = CGSizeMake(80.0f, 320.0f);
	strobeScroller.showsVerticalScrollIndicator = NO;
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

	[mainbutton release];
	[sliderview release];
	bannerView.delegate = nil;
	[bannerView release];
	[self stopTimer];
	[background release];
	[myTimer release];
	[strobeScroller release];
	[timeLabel release];
	[captureManager release];
    [super dealloc];
}

@end
