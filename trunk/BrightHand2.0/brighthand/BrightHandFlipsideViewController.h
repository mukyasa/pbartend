//
//  BrightHandFlipsideViewController.h
//  brighthand
//
//  Created by Darren Mason on 2/13/13.
//  Copyright (c) 2013 mypocket technologies. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <MessageUI/MessageUI.h>

@class BrightHandFlipsideViewController;


@protocol BrightHandFlipsideViewControllerDelegate
- (void)flipsideViewControllerDidFinish:(BrightHandFlipsideViewController *)controller;
@end


@interface BrightHandFlipsideViewController : UIViewController <MFMailComposeViewControllerDelegate>{
    
	IBOutlet UIWebView *webView;
	NSString *theURL;
    
}

@property (nonatomic, retain) UIWebView *webView;
-(IBAction) tellAFriend:(id)sender;
-(IBAction) callWebsite:(id)sender;
-(IBAction) getFullVersion:(id)sender;

@property (weak, nonatomic) id <BrightHandFlipsideViewControllerDelegate> delegate;

- (IBAction)done:(id)sender;

@end
