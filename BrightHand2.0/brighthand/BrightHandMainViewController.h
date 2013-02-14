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
#import "BrightHandFlipsideViewController.h"



@interface BrightHandMainViewController : UIViewController <BrightHandFlipsideViewControllerDelegate, UIScrollViewDelegate>{
    
    NSTimer *myTimer;
    int toggle;
	float interval;
	BOOL isLightOn;
    IBOutlet UIScrollView *strobeScroller;
	BOOL isStrobing;
}

@property (weak, nonatomic) IBOutlet ADBannerView *bannerView;
@property(nonatomic,retain) UIFont *myCustomFont;
@property (nonatomic,retain) IBOutlet UIImageView *sliderView;
@property (nonatomic,retain) IBOutlet UIScrollView *strobeScroller;
@property (weak, nonatomic) IBOutlet UIImageView *background;
@property (weak, nonatomic) IBOutlet UIButton *mainButton;
@property(nonatomic,retain) NSTimer *myTimer;


@property(nonatomic,retain) UILabel *timeLabel;

- (void)updateCounter:(NSTimer *)theTimer;
- (void)stopTimer;
-(void)doToggle;

@end
