//
//  MainViewController.h
//  TheScream
//
//  Created by Darren Mason on 6/13/10.
//  Copyright TGen 2010. All rights reserved.
//

#import "FlipsideViewController.h"
#import "AboutViewController.h"

@interface MainViewController : UIViewController <FlipsideViewControllerDelegate,AboutViewControllerDelegate> {
	
	IBOutlet UIButton *whatButton;
	IBOutlet UIButton *shutUpNavButton;
	IBOutlet UIButton *aboutButton;
	IBOutlet UILabel *instructions;
	
}

- (IBAction)showShutup:(id)sender;
- (IBAction)showInfo:(id)sender;
- (IBAction)what:(id)sender;

@end
