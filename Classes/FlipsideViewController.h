//
//  FlipsideViewController.h
//  TheScream
//
//  Created by Darren Mason on 6/13/10.
//  Copyright TGen 2010. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AboutViewController.h"

@protocol FlipsideViewControllerDelegate;


@interface FlipsideViewController : UIViewController <AboutViewControllerDelegate> {
	id <FlipsideViewControllerDelegate> delegate;
	IBOutlet UIButton *shutUpButton;
	IBOutlet UIButton *whatNavButton;
}

@property (nonatomic, assign) id <FlipsideViewControllerDelegate> delegate;
- (IBAction)done:(id)sender;
- (IBAction)shutUp:(id)sender;
- (IBAction)showInfo:(id)sender;
@end


@protocol FlipsideViewControllerDelegate
- (void)flipsideViewControllerDidFinish:(FlipsideViewController *)controller;
@end

