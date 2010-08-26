//
//  BrightHandViewController.h
//  TorchTest
//
//  Created by Darren Mason on 7/8/10.
//  Copyright TGen 2010. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <AVFoundation/AVFoundation.h>
#import <iAd/iAd.h>
#import "FlipsideViewController.h"

@class AVCamDemoCaptureManager;

@interface BrightHandViewController : UIViewController<FlipsideViewControllerDelegate> {
	
	IBOutlet UIImageView *sliderview;
	IBOutlet UIButton *mainbutton;

	IBOutlet UILabel *timeLabel;
	AVCamDemoCaptureManager *captureManager;
	int toggle;
	float interval; 
	BOOL isLightOn;
	BOOL isStrobing;
	IBOutlet UIScrollView *strobeScroller;
	NSTimer *myTimer;
	IBOutlet UIImageView *background;
	IBOutlet ADBannerView *bannerView;
	UIFont *myCustomFont;
	
	
}

@property(nonatomic,retain)	UIButton *mainbutton;
@property(nonatomic,retain)	UIImageView *sliderview;
@property(nonatomic,retain) UIFont *myCustomFont;
@property(nonatomic,retain) ADBannerView *bannerView;
@property(nonatomic,retain) UIImageView *background;
@property(nonatomic,retain) NSTimer *myTimer;
@property(nonatomic,retain) UIScrollView *strobeScroller;
@property(nonatomic,retain) UILabel *timeLabel;
@property (nonatomic,retain) AVCamDemoCaptureManager *captureManager;

-(IBAction) toggleTorch:(id)sender;

- (void)stopTimer;
- (void)updateCounter:(NSTimer *)theTimer;
-(void) moveBannerViewOffscreen;
-(void) moveBannerViewOnscreen;
-(IBAction)showInfo:(id)sender;

@end

