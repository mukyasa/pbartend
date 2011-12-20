//
//  DetailViewController.h
//  TestPOC
//
//  Created by Darren Mason on 8/19/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "PageViewController.h"

@interface DetailViewController : UIViewController <UIPopoverControllerDelegate, UISplitViewControllerDelegate> {
    
    UIPopoverController *popoverController;
    UIToolbar *toolbar;
	IBOutlet UIButton *listPopOver;
	IBOutlet UIButton *listFilterPopOver;
	PageViewController *pageView; 

    
    id detailItem;
    UILabel *detailDescriptionLabel;
	UIPopoverController *currentPopover;
	
}


@property(nonatomic,retain) UIPopoverController *currentPopover;
@property(nonatomic,retain) PageViewController *pageView;
@property(nonatomic,retain)UIButton *listPopOver;
@property(nonatomic,retain)UIButton *listFilterPopOver;
@property (nonatomic, retain) IBOutlet UIToolbar *toolbar;

@property (nonatomic, retain) id detailItem;
@property (nonatomic, retain) IBOutlet UILabel *detailDescriptionLabel;

-(void)addLabels;
- (void)setupNewPopoverControllerForViewController:(UIViewController *)vc;
- (void)handleDismissedPopoverController:(UIPopoverController*)popoverController;
-(IBAction)popoverList:(id)sender;
-(IBAction)popoverFilter:(id)sender;


@end
