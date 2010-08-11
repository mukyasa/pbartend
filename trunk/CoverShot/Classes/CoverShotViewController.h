//
//  CoverShotViewController.h
//  CoverShot
//
//  Created by Darren Mason on 7/21/10.
//  Copyright TGen 2010. All rights reserved.
//


#import "CoverShotEditorViewController.h"
#import "FlipsideViewController.h"

@interface CoverShotViewController : UIViewController <UIGestureRecognizerDelegate,CoverShotEditorViewControllerDelegate,FlipsideViewControllerDelegate>{

	IBOutlet UIScrollView *magizineScrollView;
	UIImage *pickedCover;
	CoverShotEditorViewController *coverShotEditorViewController;
	
}
@property(nonatomic,retain) CoverShotEditorViewController *coverShotEditorViewController;
@property(nonatomic,retain)UIImage *pickedCover;
@property(nonatomic,retain) UIScrollView *magizineScrollView;
-(void)layoutScrollImages;
-(IBAction)showInfo:(id)sender;

@end

