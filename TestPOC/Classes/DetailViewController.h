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
	IBOutlet UIView *pageView;
    
    id detailItem;
    UILabel *detailDescriptionLabel;
	
}

@property(nonatomic,retain)UIView *pageView;
@property(nonatomic,retain)UIButton *listPopOver;
@property (nonatomic, retain) IBOutlet UIToolbar *toolbar;

@property (nonatomic, retain) id detailItem;
@property (nonatomic, retain) IBOutlet UILabel *detailDescriptionLabel;

-(IBAction)popoverList:(id)sender;

@end
