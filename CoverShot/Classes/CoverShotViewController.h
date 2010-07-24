//
//  CoverShotViewController.h
//  CoverShot
//
//  Created by Darren Mason on 7/21/10.
//  Copyright TGen 2010. All rights reserved.
//


#import "CoverShotEditorViewController.h"

@interface CoverShotViewController : UIViewController <UIGestureRecognizerDelegate,CoverShotEditorViewControllerDelegate>{

	IBOutlet UIScrollView *magizineScrollView;
	UIImage *pickedCover;
	
}
@property(nonatomic,retain)UIImage *pickedCover;
@property(nonatomic,retain) UIScrollView *magizineScrollView;
- (void)layoutScrollImages;

@end

