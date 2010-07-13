//
//  CamerTestViewController.h
//  CamerTest
//
//  Created by Darren Mason on 7/12/10.
//  Copyright TGen 2010. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <AVFoundation/AVFoundation.h>

@class AVCamDemoCaptureManager,AVCamDemoPreviewView;

@interface CamerTestViewController : UIViewController <UIGestureRecognizerDelegate>{

	IBOutlet UIBarButtonItem *featureButton;
	AVCamDemoCaptureManager *captureManager;
	UIView *videoPreviewView;
	AVCaptureVideoPreviewLayer *captureVideoPreviewLayer;
	IBOutlet UIBarButtonItem *cameraToggleButton;
	IBOutlet UIBarButtonItem *stillImageButton;
	IBOutlet UIImageView *magoverlay;
	BOOL sessionStarted;
	
	/* for manipulation*/
	// for dragging
    CGPoint beginGestureTranslation; // upper-left corner, unscaled, unrotated
	CGPoint effectiveTranslation;
	
    // for scaling
	CGFloat beginGestureScale;
	CGFloat effectiveScale;
	
    // for rotating
	CGFloat beginGestureRotationRadians;
	CGFloat effectiveRotationRadians;
}

@property (nonatomic,retain) UIImageView *magoverlay;
@property (nonatomic,retain) IBOutlet UIBarButtonItem *stillImageButton;
@property (nonatomic,retain) AVCaptureVideoPreviewLayer *captureVideoPreviewLayer;

@property (nonatomic,retain) IBOutlet UIBarButtonItem *cameraToggleButton;
@property (nonatomic,retain) IBOutlet UIView *videoPreviewView;
@property(nonatomic,retain) UIBarButtonItem *featureButton;
@property (nonatomic,retain) AVCamDemoCaptureManager *captureManager;

- (IBAction)cameraToggle:(id)sender;
- (IBAction)still:(id)sender;
- (void)makeAndApplyAffineTransform;
- (void)_setupPreviewLayer;
- (void)applyDefaults;

@end


