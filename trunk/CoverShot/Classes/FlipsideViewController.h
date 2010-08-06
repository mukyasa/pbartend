//
//  FlipViewController.h
//  CoverShot
//
//  Created by Darren Mason on 8/5/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import <UIKit/UIKit.h>


@protocol FlipsideViewControllerDelegate;


@interface FlipsideViewController : UIViewController {
	id <FlipsideViewControllerDelegate> delegate;
	IBOutlet UIWebView *webView;
}

@property (nonatomic, retain) UIWebView *webView;
-(IBAction) callWebsite:(id)sender;

@property (nonatomic, assign) id <FlipsideViewControllerDelegate> delegate;
- (IBAction)done:(id)sender;
@end


@protocol FlipsideViewControllerDelegate
- (void)flipsideViewControllerDidFinish:(FlipsideViewController *)controller;
@end
