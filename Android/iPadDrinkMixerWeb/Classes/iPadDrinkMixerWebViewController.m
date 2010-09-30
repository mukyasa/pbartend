//
//  iPadDrinkMixerWebViewController.m
//  iPadDrinkMixerWeb
//
//  Created by Darren Mason on 9/26/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import "iPadDrinkMixerWebViewController.h"

@implementation iPadDrinkMixerWebViewController
@synthesize webView;


/*
// The designated initializer. Override to perform setup that is required before the view is loaded.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if ((self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil])) {
        // Custom initialization
    }
    return self;
}
*/

/*
// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView { 
}
*/



// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
	NSString *urlAddress = @"file:///Users/dmason/android/NewWorkspace/iPadWebPocketDrinkMixer/WebContent/index.htm";
	//NSString *urlAddress = @"http://mypocket-technologies.com/ipad";
	
	//Create a URL object.
	NSURL *url = [NSURL URLWithString:urlAddress];
	
	//URL Requst Object
	NSURLRequest *requestObj = [NSURLRequest requestWithURL:url];
	
	//Load the request in the UIWebView.
	[webView loadRequest:requestObj];
	
	
	
}


// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    return YES;
}


- (void)didRotateFromInterfaceOrientation:(UIInterfaceOrientation)fromInterfaceOrientation{

	//PORTRAIT
	if(fromInterfaceOrientation == UIInterfaceOrientationLandscapeLeft || fromInterfaceOrientation == UIInterfaceOrientationLandscapeRight)
	{
		[webView stringByEvaluatingJavaScriptFromString:@"changeOrientation(0)"];	
	}
	else {//LANDSCAPE
		[webView stringByEvaluatingJavaScriptFromString:@"changeOrientation(1)"];
	}
	

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


- (void)dealloc {
	[webView release];
    [super dealloc];
}

@end
