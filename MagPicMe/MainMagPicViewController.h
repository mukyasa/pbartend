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
	
	UITapGestureRecognizer *tapRecognizer;
	UISwipeGestureRecognizer *swipeLeftRecognizer;
	
	id <MainMagPicViewControllerDelegate> delegate;
	IBOutlet UINavigationBar *saveNavBar;
	IBOutlet UIImageView  *previewImageView;
	
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
@property (nonatomic, retain) UITapGestureRecognizer *tapRecognizer;
@property (nonatomic, retain) UISwipeGestureRecognizer *swipeLeftRecognizer;
@property(nonatomic,retain)UINavigationBar *saveNavBar;
-(void) moveNavViewOnscreen;
-(void) moveNavViewOffscreen;


@property (nonatomic, assign) id <MainMagPicViewControllerDelegate> delegate;
	- (IBAction)done:(id)sender;
@end


@protocol MainMagPicViewControllerDelegate
	- (void)mainMagPicViewControllerDidFinish:(MainMagPicViewController *)controller;
@end


