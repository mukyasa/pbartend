//
//  CoverShotViewController.h
//  CoverShot
//
//  Created by Darren Mason on 7/21/10.
//  Copyright TGen 2010. All rights reserved.
//


#import "CoverShotEditorViewController.h"
#import "FlipsideViewController.h"


@interface CoverShotViewController : UIViewController <UIGestureRecognizerDelegate,CoverShotEditorViewControllerDelegate,FlipsideViewControllerDelegate,UIPickerViewDelegate, UIPickerViewDataSource>{

	IBOutlet UIScrollView *magizineScrollView;
	UIImage *pickedCover;
	CoverShotEditorViewController *coverShotEditorViewController;
    IBOutlet UIView *categoryPicker;
    IBOutlet UIButton *catButton;
    IBOutlet UIImageView *basicBackground;
    IBOutlet UIButton *hiddenOffButton;
	
}
@property(nonatomic,retain) UIButton *hiddenOffButton;
@property(nonatomic,retain) UIImageView *basicBackground;
@property(nonatomic,retain) UIButton *catButton;
@property(nonatomic,retain) UIView *categoryPicker;
@property(nonatomic,retain) CoverShotEditorViewController *coverShotEditorViewController;
@property(nonatomic,retain) UIImage *pickedCover;
@property(nonatomic,retain) UIScrollView *magizineScrollView;

-(void)layoutScrollImages:(int)imageCount;
-(IBAction)showInfo:(id)sender;
-(IBAction)pickCategory:(id)sender;
-(IBAction)filterCategories:(id)sender;
-(IBAction)moveCatPickerOffScreen:(id)sender;

-(void)moveCatPickerOnScreen;
-(void)moveCatPickerOffScreen;
-(void)loadAll;
-(void)loadKids;
-(void)loadMens;
-(void)loadWomens;
-(void)loadPets;
-(void)loadOther;
@end

