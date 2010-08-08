//
//  CoverShotEditorViewController.h
//  CoverShot
//
//  Created by Darren Mason on 7/21/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <AVFoundation/AVFoundation.h>
#import "QuartzView.h"

@protocol CoverShotEditorViewControllerDelegate;

@interface CoverShotEditorViewController : UIViewController <UIGestureRecognizerDelegate, UIActionSheetDelegate,UIImagePickerControllerDelegate,UINavigationControllerDelegate,UIPickerViewDelegate, UIPickerViewDataSource>{

	IBOutlet UIImageView  *previewImageView;
	IBOutlet UIView *parentPreviewView;
	IBOutlet UIToolbar *mainToolBar;
	IBOutlet UIImageView *parentPreviewImageView;
	IBOutlet UIBarButtonItem *photoButton;
	IBOutlet UIBarButtonItem *saveButton;
	IBOutlet UIBarButtonItem *blendButton;
	id <CoverShotEditorViewControllerDelegate> delegate;
	IBOutlet UIPickerView *gradientPicker;
	IBOutlet UIView *pickerView;
	IBOutlet QuartzView *quartzView;
	Class viewClass;
	UIImage *imageCopy;

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

@property(nonatomic,retain)UIImage *imageCopy;
@property(nonatomic, readonly) QuartzView *quartzView;
@property(nonatomic,retain) UIView *pickerView;
@property(nonatomic, readonly) NSArray *colors;
@property(nonatomic,retain) UIPickerView *gradientPicker;
@property(nonatomic,retain) UIBarButtonItem *blendButton;
@property(nonatomic,retain) UIBarButtonItem *saveButton;
@property(nonatomic,retain) UIBarButtonItem *photoButton;
@property(nonatomic,retain) UIToolbar *mainToolBar;
@property(nonatomic,retain) UIImageView  *previewImageView;
@property(nonatomic,retain) UIView *parentPreviewView;
@property(nonatomic,retain) UIImageView *parentPreviewImageView;

-(void)setUpImageState:(BOOL)isMovePicker;
-(void)setupQuartzBlendingView:(QuartzView*)qbv;
-(void)applyDefaults;
-(IBAction)showColorPicker:(id)sender;
-(IBAction)saveMagCover:(id)sender;
-(IBAction)showPictureControls:(id)sender;
-(void)doSave;
-(void)imageSavedToPhotosAlbum:(UIImage *)image didFinishSavingWithError:(NSError *)error contextInfo:(void *)contextInfo;
-(void) moveNavViewOnscreen;
-(void) moveNavViewOffscreen;
-(BOOL)point:(CGPoint)p inView:(UIView *)view;
-(void)movePickerOnScreen;
-(void)movePickerOffScreen;



@property (nonatomic, assign) id <CoverShotEditorViewControllerDelegate> delegate;
	- (IBAction)done:(id)sender;
@end


@protocol CoverShotEditorViewControllerDelegate
	- (void)coverShotEditorViewControllerDidFinish:(CoverShotEditorViewController *)controller;
@end