//
//  DetailViewController.h
//  TestPOC
//
//  Created by Darren Mason on 8/19/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface DetailViewController : UIViewController <UIPopoverControllerDelegate, UISplitViewControllerDelegate> {
    
    UIPopoverController *popoverController;
    UIToolbar *toolbar;
	IBOutlet UIButton *listPopOver;
	IBOutlet UIButton *listFilterPopOver;
	IBOutlet UIView *pageView;
    
    id detailItem;
    UILabel *detailDescriptionLabel;
	UIPopoverController *currentPopover;
	
}

@property (retain, nonatomic) UIPopoverController *currentPopover;
@property(nonatomic,retain)UIView *pageView;
@property(nonatomic,retain)UIButton *listPopOver;
@property(nonatomic,retain)UIButton *listFilterPopOver;
@property (nonatomic, retain) IBOutlet UIToolbar *toolbar;

@property (nonatomic, retain) id detailItem;
@property (nonatomic, retain) IBOutlet UILabel *detailDescriptionLabel;

- (void)setupNewPopoverControllerForViewController:(UIViewController *)vc;
- (void)handleDismissedPopoverController:(UIPopoverController*)popoverController;
-(IBAction)popoverList:(id)sender;
-(IBAction)popoverFilter:(id)sender;

@end
