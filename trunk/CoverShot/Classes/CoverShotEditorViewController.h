//
//  CoverShotEditorViewController.h
//  CoverShot
//
//  Created by Darren Mason on 7/21/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <AVFoundation/AVFoundation.h>

@protocol CoverShotEditorViewControllerDelegate;

@interface CoverShotEditorViewController : UIViewController <UIGestureRecognizerDelegate>{

	IBOutlet UIImageView  *previewImageView;
	IBOutlet UIView *parentPreviewView;
	IBOutlet UIToolbar *mainToolBar;
	IBOutlet UIImageView *parentPreviewImageView;
	id <CoverShotEditorViewControllerDelegate> delegate;

	BOOL isSaving;
	
	// for dragging
    CGPoint effectiveTranslation;
	
    // for scaling
	CGFloat beginGestureScale;
	
    // for rotating
	CGFloat beginGestureRotationRadians;
	CGFloat oldX,oldY;
	
	BOOL inImage;
	
}

@property(nonatomic,retain) UIToolbar *mainToolBar;
@property(nonatomic,retain) UIImageView  *previewImageView;
@property(nonatomic,retain) UIView *parentPreviewView;
@property(nonatomic,retain) UIImageView *parentPreviewImageView;

-(void) moveNavViewOnscreen;
-(void) moveNavViewOffscreen;
-(BOOL)point:(CGPoint)p inView:(UIView *)view;


@property (nonatomic, assign) id <CoverShotEditorViewControllerDelegate> delegate;
	- (IBAction)done:(id)sender;
@end


@protocol CoverShotEditorViewControllerDelegate
	- (void)coverShotEditorViewControllerDidFinish:(CoverShotEditorViewController *)controller;
@end