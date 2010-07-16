//
//  MainMagPicViewController.h
//  MagPicMe
//
//  Created by Darren Mason on 7/15/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <AVFoundation/AVFoundation.h>

@protocol MainMagPicViewControllerDelegate;

@interface MainMagPicViewController : UIViewController <UIGestureRecognizerDelegate> {
	
	
	
	id <MainMagPicViewControllerDelegate> delegate;
	IBOutlet UINavigationBar *saveNavBar;
	IBOutlet UIImageView  *previewImageView;
	IBOutlet UIView *parentPreviewImageView;
	
	// for dragging
   CGPoint effectiveTranslation;
	
    // for scaling
	CGFloat beginGestureScale;
	
    // for rotating
	CGFloat beginGestureRotationRadians;
	CGFloat oldX,oldY;
	
	BOOL inImage;

	
}

@property(nonatomic,retain)UIView *parentPreviewImageView;
@property (nonatomic, retain) UITapGestureRecognizer *tapRecognizer;
@property (nonatomic, retain) UISwipeGestureRecognizer *swipeLeftRecognizer;
@property(nonatomic,retain)UINavigationBar *saveNavBar;
-(void) moveNavViewOnscreen;
-(void) moveNavViewOffscreen;
-(BOOL)point:(CGPoint)p inView:(UIView *)view;

@property (nonatomic, assign) id <MainMagPicViewControllerDelegate> delegate;
	- (IBAction)done:(id)sender;
@end


@protocol MainMagPicViewControllerDelegate
	- (void)mainMagPicViewControllerDidFinish:(MainMagPicViewController *)controller;
@end


