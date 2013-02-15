//
//  BrightHandMainViewController.m
//  brighthand
//
//  Created by Darren Mason on 2/13/13.
//  Copyright (c) 2013 mypocket technologies. All rights reserved.
//

#import "BrightHandMainViewController.h"



@implementation BrightHandMainViewController
#define SEGMENT_BUTTON_OFF 0
#define SEGMENT_BUTTON_ON 1
#define SEGMENT_BUTTON_STROBE 2

//makes pretty colors
#define UIColorFromRGB(rgbValue) [UIColor \
colorWithRed:((float)((rgbValue & 0xFF0000) >> 16))/255.0 \
green:((float)((rgbValue & 0xFF00) >> 8))/255.0 \
blue:((float)(rgbValue & 0xFF))/255.0 alpha:1.0]

@synthesize  compassArrow=_compassArrow, locationManager=_locationManager,bannerView =_bannerView,mainButton = _mainButton, myTimer = _myTimer, timeLabel= _timeLabel,background =_background, strobeScroller= _strobeScroller,sliderView = _sliderView,myCustomFont=_myCustomFont;


- (IBAction)toggleTorch:(id)sender {
    [self doToggle];
    
    [UIView beginAnimations:@"MoveSlider" context:nil];
	[UIView setAnimationDuration:.8];
	[UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
	
	[_timeLabel setText:@"0.00"];
	
	[UIView commitAnimations];
    
}

-(void)doToggle{
    NSLog(@"tag:%i",_mainButton.tag);
    NSString *backgroundprefix =@".png";
    
    if ([[UIScreen mainScreen] bounds].size.height == 568){ //iphone 5
        backgroundprefix = @"-568h@2x.png";
    }
    
	switch (_mainButton.tag) {
        case SEGMENT_BUTTON_OFF:
			[_mainButton setImage:[UIImage imageNamed:@"button_off.png"] forState:UIControlStateNormal];
			[self stopTimer];
			isLightOn =NO;
			isStrobing=NO;
			_mainButton.tag = SEGMENT_BUTTON_ON;
			[_background setImage:[UIImage imageNamed:[NSString stringWithFormat:@"brighthandbg_off%@",backgroundprefix]]];
            [_compassArrow setImage:[UIImage imageNamed:@"compass_off.png"]];
            [self turnOffLight];
            break;
        case SEGMENT_BUTTON_ON:
			[_mainButton setImage:[UIImage imageNamed:@"button_on.png"] forState:UIControlStateNormal];
			[self stopTimer];
			isLightOn=YES;
			isStrobing=NO;
			[_background setImage:[UIImage imageNamed:[NSString stringWithFormat:@"brighthandbg_on%@",backgroundprefix]]];
			_mainButton.tag = SEGMENT_BUTTON_OFF;
            [_compassArrow setImage:[UIImage imageNamed:@"compass_on.png"]];
            [self  turnOnLight];
            break;
		case SEGMENT_BUTTON_STROBE:
			[_mainButton setImage:[UIImage imageNamed:@"button_off.png"] forState:UIControlStateNormal];
			[self stopTimer];
			isLightOn =NO;
			isStrobing=NO;
			_mainButton.tag = SEGMENT_BUTTON_ON;
			[_background setImage:[UIImage imageNamed:[NSString stringWithFormat:@"brighthandbg_off%@",backgroundprefix]]];
            [_compassArrow setImage:[UIImage imageNamed:@"compass_off.png"]];
            [self turnOffLight ];
			break;
    }
    
    
     [UIView beginAnimations:@"MoveSlider" context:nil];
     [UIView setAnimationDuration:.8];
     [UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
     
     _strobeScroller.contentOffset = CGPointMake(0, 0);
     [_timeLabel setText:@"0.00"];

     

     [UIView commitAnimations];
}

- (void)stopTimer
{
	[_myTimer invalidate];
    _myTimer = nil;
	
}


- (void)scrollViewDidEndDragging:(UIScrollView *)scrollView willDecelerate:(BOOL)decelerate{
	
    NSString *backgroundprefix =@".png";
    
    if ([[UIScreen mainScreen] bounds].size.height == 568){ //iphone 5
        backgroundprefix = @"-568h@2x.png";
    }
    
	[_mainButton setImage:[UIImage imageNamed:@"button_strobe.png"] forState:UIControlStateNormal];
	isStrobing=YES;
	isLightOn=YES;
	_mainButton.tag = SEGMENT_BUTTON_STROBE;
	[_background setImage:[UIImage imageNamed:[NSString stringWithFormat:@"brighthandbg_on%@",backgroundprefix]]];
    [_compassArrow setImage:[UIImage imageNamed:@"compass_on.png"]];
	
	[self stopTimer];
	_myTimer = [NSTimer scheduledTimerWithTimeInterval:interval target:self selector:@selector(updateCounter:) userInfo:nil repeats:YES];
	
}



- (void)scrollViewDidScroll:(UIScrollView *)scrollView{
    
	float screenScroll = scrollView.contentOffset.y/100;
	if(screenScroll < .05)
		screenScroll = .05f;
	if(screenScroll > 5.0)
		screenScroll =5.0f;
	
	[_timeLabel setText:[NSString stringWithFormat:@"%.2f", screenScroll]];
	interval = [[[NSString alloc] initWithFormat:@"%.2f",screenScroll] floatValue];
	
	//NSLog(@"View Did Scroll  %.2f",scrollView.contentOffset.y/100);
    
}

- (void)updateCounter:(NSTimer *)theTimer {
	
	NSLog(@"Called");
	
	if(isLightOn && isStrobing){
        if(toggle == AVCaptureTorchModeOff)
        {
            [self turnOnLight];
            toggle = AVCaptureTorchModeOn;
        }else {
            [self turnOffLight];
            toggle = AVCaptureTorchModeOff;
        }
        
	}
	else if(!isLightOn)
	{
        _mainButton.tag = SEGMENT_BUTTON_ON;
        [self doToggle];
	}
	else {
        _mainButton.tag = SEGMENT_BUTTON_OFF;
        [self doToggle];
	}
    
}

- (void)viewDidLoad
{
    
    CGRect scrollViewFrame;
    
    NSString *backgroundprefix =@".png";
    if ([[UIScreen mainScreen] bounds].size.height == 568){ //iphone 5
        backgroundprefix = @"-568h@2x.png";
        scrollViewFrame = CGRectMake(115, 168, 80, 214);
    } else if([[UIScreen mainScreen] bounds].size.height == 480){ //iphone 4/3
        scrollViewFrame = CGRectMake(115, 76, 80, 214);
    } 
    
    
    //set scroller height
    _sliderView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"slider.png"]];
    [_sliderView setFrame:CGRectMake(0, 0, 80, 320)];
    _sliderView.contentMode = UIViewContentModeScaleAspectFit;
    
    
    _strobeScroller = [[UIScrollView alloc] initWithFrame:scrollViewFrame];
    
    [_strobeScroller addSubview:_sliderView];
    _strobeScroller.delegate = self;
    _strobeScroller.showsVerticalScrollIndicator = NO;
    _strobeScroller.decelerationRate = UIScrollViewDecelerationRateFast;
    _strobeScroller.bounces =NO;

    [self.view addSubview:_strobeScroller];
    
    [_background setImage:[UIImage imageNamed:[NSString stringWithFormat:@"brighthandbg_on%@",backgroundprefix]]];
    [_compassArrow setImage:[UIImage imageNamed:@"compass_on.png"]];
    _mainButton.tag = SEGMENT_BUTTON_ON;
    _myCustomFont = [UIFont fontWithName:@"digital-7" size:22];
	
    _timeLabel=[[UILabel alloc]initWithFrame:CGRectMake(22,118,52,27)];
	[_timeLabel setFont:_myCustomFont];
	[_timeLabel setText:@"0.00"];
	_timeLabel.backgroundColor = [UIColor clearColor];
	[_timeLabel setTextColor:UIColorFromRGB(0xfff6d665)];//this is you hex color last 6 chars];
	[_sliderView addSubview:_timeLabel];
    
    //set minimum scroll
	interval=.05f;
    toggle = AVCaptureTorchModeOn;
    
    [self doToggle];
	isLightOn=YES;
    
    // setup the location manager
	_locationManager = [[CLLocationManager alloc] init];
	
	// check if the hardware has a compass
	if ([CLLocationManager headingAvailable] == NO) {
		// No compass is available. This application cannot function without a compass,
        // so a dialog will be displayed and no magnetic data will be measured.
        self.locationManager = nil;
        UIAlertView *noCompassAlert = [[UIAlertView alloc] initWithTitle:@"No Compass!" message:@"This device does not have the ability to measure magnetic fields." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
        [noCompassAlert show];

	} else {
        // heading service configuration
        _locationManager.headingFilter = kCLHeadingFilterNone;
        
        // setup delegate callbacks
        _locationManager.delegate = self;
        
        // start the compass
        [_locationManager startUpdatingHeading];
    }

    
    
    
    
    [self moveBannerViewOffscreen];//hide banner
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)viewDidAppear:(BOOL)animated
{
     _strobeScroller.contentSize = CGSizeMake(80.0f, 320.0f);
    
    [super viewDidAppear:animated];
}

-(void)turnOnLight{
    AVCaptureDevice *device = [AVCaptureDevice defaultDeviceWithMediaType:AVMediaTypeVideo];
    if ([device hasTorch]) {
        [device lockForConfiguration:nil];
        [device setTorchMode:AVCaptureTorchModeOn];  // use AVCaptureTorchModeOff to turn off
        [device unlockForConfiguration];
    }
}

-(void)turnOffLight{
    AVCaptureDevice *device = [AVCaptureDevice defaultDeviceWithMediaType:AVMediaTypeVideo];
    if ([device hasTorch]) {
        [device lockForConfiguration:nil];
        [device setTorchMode:AVCaptureTorchModeOff];  // use AVCaptureTorchModeOn to turn off
        [device unlockForConfiguration];
    }
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Flipside View

- (void)flipsideViewControllerDidFinish:(BrightHandFlipsideViewController *)controller
{
    [self dismissViewControllerAnimated:YES completion:^{
     //_strobeScroller.contentSize = CGSizeMake(80.0f, 320.0f);
        //NSLog(@"Slider Height: %f",_strobeScroller.frame.size.height);
        //NSLog(@"SliderImage Height: %f",_sliderView.frame.size.height);
    }];
}

- (BOOL)shouldPerformSegueWithIdentifier:(NSString *)identifier sender:(id)sender {

   
    
    [UIView animateWithDuration:0.5 delay:0 options:UIViewAnimationOptionCurveEaseInOut animations:^(void){
    
        NSString *backgroundprefix =@".png";
        if ([[UIScreen mainScreen] bounds].size.height == 568){ //iphone 5
            backgroundprefix = @"-568h@2x.png";
        }
        
        [_mainButton setImage:[UIImage imageNamed:@"button_off.png"] forState:UIControlStateNormal];
        [self stopTimer];
        isLightOn =NO;
        isStrobing=NO;
        _mainButton.tag = SEGMENT_BUTTON_ON;
        [_background setImage:[UIImage imageNamed:[NSString stringWithFormat:@"brighthandbg_off%@",backgroundprefix]]];
        [_compassArrow setImage:[UIImage imageNamed:@"compass_off.png"]];
        
        _strobeScroller.contentOffset = CGPointMake(0, 0);
        [_timeLabel setText:@"0.00"];
        [self turnOffLight];
    
    
    } completion:^(BOOL finished){
        if(finished){
            [self performSegueWithIdentifier:@"showAlternate" sender:sender];
        }
    }];

    return NO;
    

}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    
    if ([[segue identifier] isEqualToString:@"showAlternate"]) {
        [[segue destinationViewController] setDelegate:self];     
    }
}

#pragma mark -
#pragma mark Location 

// This delegate method is invoked when the location manager has heading data.
- (void)locationManager:(CLLocationManager *)manager didUpdateHeading:(CLHeading *)heading {
    // Update the labels with the raw x, y, and z values.
    
    float mHeading = heading.magneticHeading;
    CGFloat radians = -mHeading / 180.0 * M_PI;
//    NSLog(@"*****Radians %f",radians);
    
    _compassArrow.transform = CGAffineTransformMakeRotation(radians);
}

// This delegate method is invoked when the location managed encounters an error condition.
- (void)locationManager:(CLLocationManager *)manager didFailWithError:(NSError *)error {
    if ([error code] == kCLErrorDenied) {
        // This error indicates that the user has denied the application's request to use location services.
        [manager stopUpdatingHeading];
    } else if ([error code] == kCLErrorHeadingFailure) {
        // This error indicates that the heading could not be determined, most likely because of strong magnetic interference.
    }
}

#pragma mark -
#pragma mark iAd Banner
-(void) moveBannerViewOnscreen{
	
	CGRect newBannerview = _bannerView.frame;
	newBannerview.origin.y = self.view.frame.size.height-newBannerview.size.height;
	
	[UIView beginAnimations:@"BannerViewIntro" context:NULL];
	_bannerView.frame = newBannerview;
	[UIView commitAnimations];
	
}
-(void) moveBannerViewOffscreen{
	CGFloat viewHeight = self.view.frame.size.height;
	CGRect newBannerview = _bannerView.frame;
	newBannerview.origin.y = viewHeight;
	
	_bannerView.frame =newBannerview;
	
}
- (void)bannerView:(ADBannerView *)banner didFailToReceiveAdWithError:(NSError *)error;
{
	[self moveBannerViewOffscreen];
}
- (void)bannerViewDidLoadAd:(ADBannerView *)banner{
	[self moveBannerViewOnscreen];
}
#pragma mark -
- (void)viewDidUnload {
    [self setBackground:nil];
    [self setStrobeScroller:nil];
    [self setMyTimer:nil];
    [self setTimeLabel:nil];
    [self setSliderView:nil];
    [self setBannerView:nil];
    [super viewDidUnload];
}
@end
