//
//  FlipViewController.m
//  CoverShot
//
//  Created by Darren Mason on 8/5/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import "FlipsideViewController.h"

@implementation FlipsideViewController

@synthesize delegate,webView;


- (void)viewDidLoad {
	CGFloat viewHeight = self.view.frame.size.height;
	CGRect newWebViewFrame = self.webView.frame;
	newWebViewFrame.origin.y = viewHeight;
	
	self.webView.frame =newWebViewFrame;

	theURL = @"http://phobos.apple.com/WebObjects/MZStore.woa/wa/viewSoftware?id=381973252&mt=8";
	
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor viewFlipsideBackgroundColor];      
}


/*share via email*/
-(IBAction)tellAFriend:(id)sender{
	
	//add link to store here via my site
	
	NSString* body = [NSString stringWithFormat:@"Hey I just thought you might want to try this app called Bright Hand looks pretty fun. - %@\n",theURL];
	
	MFMailComposeViewController *picker = [[MFMailComposeViewController alloc] init];
	picker.mailComposeDelegate = self;
	[picker setSubject:@"Bright Hand - iPhone app"];
	
	// [picker setToRecipients:[NSArray arrayWithObject:@"djmason9@yahoo.com"]];
	[picker setMessageBody:body isHTML:NO];
	
	[self presentModalViewController:picker animated:NO];
	[picker release];
	
}

- (void)mailComposeController:(MFMailComposeViewController *)controller didFinishWithResult:(MFMailComposeResult)result error:(NSError *)error
{
	[self dismissModalViewControllerAnimated:YES];
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
	[webView loadRequest:requestObj];
	
	CGRect newBannerview = self.webView.frame;
	newBannerview.origin.y = self.view.frame.size.height-newBannerview.size.height;
	
	[UIView beginAnimations:@"BannerViewIntro" context:NULL];
	[UIView setAnimationDuration:.5];
	[UIView setAnimationCurve:UIViewAnimationCurveEaseOut];
	self.webView.frame = newBannerview;
	[UIView commitAnimations];
	
}

-(IBAction) pickScreen{

	

}

- (IBAction)done:(id)sender {
	[self.delegate flipsideViewControllerDidFinish:self];	
}


- (void)didReceiveMemoryWarning {
	// Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
	
	// Release any cached data, images, etc that aren't in use.
}


- (void)viewDidUnload {
	// Release any retained subviews of the main view.
	// e.g. self.myOutlet = nil;
}


/*
 // Override to allow orientations other than the default portrait orientation.
 - (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
 // Return YES for supported orientations
 return (interfaceOrientation == UIInterfaceOrientationPortrait);
 }
 */


- (void)dealloc {

	[webView release];
    [super dealloc];
}


@end