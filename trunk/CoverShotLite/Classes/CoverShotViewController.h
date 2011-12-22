//
//  CoverShotViewController.h
//  CoverShot
//
//  Created by Darren Mason on 7/21/10.
//  Copyright TGen 2010. All rights reserved.
//


#import "CoverShotEditorViewController.h"
#import "FlipsideViewController.h"
#import <iAd/iAd.h>

@interface CoverShotViewController : UIViewController <UIGestureRecognizerDelegate,CoverShotEditorViewControllerDelegate,FlipsideViewControllerDelegate>{

	IBOutlet UIScrollView *magizineScrollView;
	UIImage *pickedCover;
	IBOutlet ADBannerView *bannerView;
	CoverShotEditorViewController *coverShotEditorViewController;
	int liteCover;
    IBOutlet UIView *bannerButton;
	IBOutlet UIImageView *fullversion;

	
}
@property(nonatomic,retain) IBOutlet UIView *bannerButton;
@property(nonatomic,retain)UIImageView *fullversion;
@property(nonatomic,retain) CoverShotEditorViewController *coverShotEditorViewController;
@property(nonatomic,retain) ADBannerView *bannerView;
@property(nonatomic,retain)UIImage *pickedCover;
@property(nonatomic,retain) UIScrollView *magizineScrollView;
-(void)layoutScrollImages;
-(void) moveBannerViewOffscreen;
-(void) moveBannerViewOnscreen;
-(IBAction)showInfo:(id)sender;
-(IBAction)getGame:(id)sender;
-(IBAction)hideGameBanner:(id)sender;

@end

