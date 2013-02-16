//
//  BrightHandViewController.h
//  brighthand
//
//  Created by Darren Mason on 2/10/13.
//  Copyright (c) 2013 mypocket technologies. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <iAd/iAd.h>
#import <AVFoundation/AVFoundation.h>
#import <CoreLocation/CoreLocation.h>
#import "BrightHandFlipsideViewController.h"



@interface BrightHandMainViewController : UIViewController <CLLocationManagerDelegate,BrightHandFlipsideViewControllerDelegate, UIScrollViewDelegate>{
    
    NSTimer *myTimer;
    int toggle;
	float interval;
	BOOL isLightOn;
    IBOutlet UIScrollView *strobeScroller;
	BOOL isStrobing;
}

@property (weak, nonatomic) IBOutlet ADBannerView *bannerView;
@property(nonatomic,strong) UIFont *myCustomFont;
@property (nonatomic,strong) IBOutlet UIImageView *sliderView;
@property (nonatomic,strong) IBOutlet UIScrollView *strobeScroller;
@property (weak, nonatomic) IBOutlet UIImageView *background;
@property (strong, nonatomic) IBOutlet UIImageView *compassArrow;
@property (weak, nonatomic) IBOutlet UIButton *mainButton;
@property(nonatomic,retain) NSTimer *myTimer;
@property (nonatomic, retain) CLLocationManager *locationManager;

@property(nonatomic,retain) UILabel *timeLabel;

- (void)updateCounter:(NSTimer *)theTimer;
- (void)stopTimer;
-(void)doToggle;

@end
