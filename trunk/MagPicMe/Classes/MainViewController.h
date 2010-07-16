//
//  MainViewController.h
//  MagPicMe
//
//  Created by Darren Mason on 7/15/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import "FlipsideViewController.h"

@interface MainViewController : UIViewController <FlipsideViewControllerDelegate> {
	
	IBOutlet UITabBarItem *magPicMe;
	IBOutlet UIScrollView *magizineScrollView;
}

@property(nonatomic,retain)UITabBarItem *magPicMe;
@property(nonatomic,retain)UIScrollView *magizineScrollView;

- (IBAction)showInfo:(id)sender;
- (void)layoutScrollImages;
-(IBAction)showMagPicScreen:(id)sender;

@end
