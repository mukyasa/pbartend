//
//  BrightHandFlipsideViewController.m
//  brighthand
//
//  Created by Darren Mason on 2/13/13.
//  Copyright (c) 2013 mypocket technologies. All rights reserved.
//

#import "BrightHandFlipsideViewController.h"



@implementation BrightHandFlipsideViewController

@synthesize webView = _webView,navBar=_navBar;

- (void)viewDidLoad {
	CGFloat viewHeight = self.view.frame.size.height;
	CGRect newWebViewFrame = _webView.frame;
	newWebViewFrame.origin.y = viewHeight;
	
	_webView.frame =newWebViewFrame;
    
	theURL = @"http://phobos.apple.com/WebObjects/MZStore.woa/wa/viewSoftware?id=381973252&mt=8";
    self.view.backgroundColor = [UIColor viewFlipsideBackgroundColor];
    [super viewDidLoad];
    
}


/*share via email*/
-(IBAction)tellAFriend:(id)sender{
	
    @try {
        //add link to store here via my site
        
        NSString* body = [NSString stringWithFormat:@"Hey I just thought you might want to try this app called Bright Hand looks pretty cool. - %@\n",theURL];
        
        MFMailComposeViewController *picker = [[MFMailComposeViewController alloc] init];
        picker.mailComposeDelegate = self;
        [picker setSubject:@"Bright Hand - iPhone app"];
        
        // [picker setToRecipients:[NSArray arrayWithObject:@"djmason9@yahoo.com"]];
        [picker setMessageBody:body isHTML:NO];
        
        [self presentViewController:picker animated:YES completion:nil];
    }
    @catch (NSException *exception) {
        //
    }
    @finally {
        //
    }
	
   
	
}

- (void)mailComposeController:(MFMailComposeViewController *)controller didFinishWithResult:(MFMailComposeResult)result error:(NSError *)error
{
    [self dismissViewControllerAnimated:YES completion:nil];
}

-(IBAction) getFullVersion:(id)sender{
	[[UIApplication sharedApplication] openURL:[NSURL URLWithString:theURL]];
}



-(IBAction) callWebsite:(id)sender{
	NSString *urlAddress = @"http://www.mypocket-technologies.com";
	
	//Create a URL object.
	NSURL *url = [NSURL URLWithString:urlAddress];
	
	//URL Requst Object
	NSURLRequest *requestObj = [NSURLRequest requestWithURL:url];
	
	//Load the request in the UIWebView.
	[_webView loadRequest:requestObj];
	
	CGRect webViewFrame = self.webView.frame;
	webViewFrame.origin.y = _navBar.frame.size.height;
	
	[UIView beginAnimations:@"BannerViewIntro" context:NULL];
	[UIView setAnimationDuration:.5];
	[UIView setAnimationCurve:UIViewAnimationCurveEaseOut];
	_webView.frame = webViewFrame;
	[UIView commitAnimations];
	
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Actions

- (IBAction)done:(id)sender
{
    [self.delegate flipsideViewControllerDidFinish:self];
}

@end
