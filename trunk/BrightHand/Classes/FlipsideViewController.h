//
//  FlipViewController.h
//  CoverShot
//
//  Created by Darren Mason on 8/5/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <MessageUI/MessageUI.h>


@protocol FlipsideViewControllerDelegate;


@interface FlipsideViewController : UIViewController <MFMailComposeViewControllerDelegate>{
	id <FlipsideViewControllerDelegate> delegate;
	IBOutlet UIWebView *webView;
	NSString *theURL;

}

@property (nonatomic, retain) UIWebView *webView;
-(IBAction) tellAFriend:(id)sender;
-(IBAction) callWebsite:(id)sender;
-(IBAction) getFullVersion:(id)sender;
-(IBAction) pickScreen;

@property (nonatomic, assign) id <FlipsideViewControllerDelegate> delegate;
- (IBAction)done:(id)sender;
@end


@protocol FlipsideViewControllerDelegate
- (void)flipsideViewControllerDidFinish:(FlipsideViewController *)controller;
@end
