//
//  DetailViewController.h
//  iPadDrinkMixer
//
//  Created by Darren Mason on 8/23/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "PageViewController.h"
#import "FilterViewController.h"

@interface DetailViewController : UIViewController <UIPopoverControllerDelegate, UISplitViewControllerDelegate> {
    
    UIPopoverController *popoverController;
    UIToolbar *toolbar;
	IBOutlet UIButton *listPopOver;
	IBOutlet UIButton *listFilterPopOver;
    id detailItem;
    UILabel *detailDescriptionLabel;
	
	UIFont *myCustomFont;
	IBOutlet PageViewController *pageView;
	UIPopoverController *currentPopover;
}
@property(nonatomic,retain) UIPopoverController *currentPopover;
@property (retain,nonatomic)PageViewController *pageView;
@property(nonatomic,retain)UIFont *myCustomFont;
@property (nonatomic, retain) IBOutlet UIToolbar *toolbar;
@property(nonatomic,retain)UIButton *listPopOver;
@property(nonatomic,retain)UIButton *listFilterPopOver;

@property (nonatomic, retain) id detailItem;
@property (nonatomic, retain) IBOutlet UILabel *detailDescriptionLabel;

- (void)setupNewPopoverControllerForViewController:(UIViewController *)vc;
- (void)handleDismissedPopoverController:(UIPopoverController*)popoverController;
-(IBAction)popoverList:(id)sender;
-(IBAction)popoverFilter:(id)sender;

@end
