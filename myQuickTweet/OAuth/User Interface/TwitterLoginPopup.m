//
//  TwitterLoginPopup.m
//
//  Created by Jaanus Kase on 15.01.10.
//  Copyright 2010. All rights reserved.
//

#import "TwitterLoginPopup.h"
#import "OAuth.h"
#import "TwitterWebViewController.h"

@implementation TwitterLoginPopup

@synthesize delegate, uiDelegate, oAuth;

#pragma mark Button actions

- (IBAction)getPin:(id)sender {
		
	[self.uiDelegate tokenRequestDidStart:self];
	
	
	NSInvocationOperation *operation = [[NSInvocationOperation alloc]
										initWithTarget:oAuth
										selector:@selector(synchronousRequestTwitterToken)
										object:nil];
	
	[queue addOperation:operation];
	[operation release];
}

- (IBAction)savePin:(id)sender {
    [pinField resignFirstResponder];
    
	// delegate authorizationRequestDidStart
	[self.uiDelegate authorizationRequestDidStart:self];

	NSInvocationOperation *operation = [[NSInvocationOperation alloc]
										initWithTarget:oAuth
										selector:@selector(synchronousAuthorizeTwitterTokenWithVerifier:)
										object:pinField.text];
	[queue addOperation:operation];
	[operation release];
}

- (void) seePinAgain {
 	if (pinField.editing) {
		[pinField resignFirstResponder];
	}
	willBeEditingPin = YES;
    [[self navigationController] pushViewController:webViewController animated:YES];
}

- (void)cancel {
    
    [[NSNotificationCenter defaultCenter] removeObserver:self]; // fix?
	[self.delegate twitterLoginPopupDidCancel:self];
}

#pragma mark -
#pragma mark OAuthTwitterCallbacks protocol

// For all of these methods, we invoked oAuth in a background thread, so these are also called
// in background thread. So we first transfer the control back to main thread before doing
// anything else.

- (void) requestTwitterTokenDidSucceed:(OAuth *)_oAuth {
	if (![NSThread isMainThread]) {
		[self performSelectorOnMainThread:@selector(requestTwitterTokenDidSucceed:)
							   withObject:_oAuth
							waitUntilDone:NO];
		return;
	}

	NSURL *myURL = [NSURL URLWithString:[NSString
										 stringWithFormat:@"https://api.twitter.com/oauth/authorize?oauth_token=%@",
										 _oAuth.oauth_token]];
	
	if (!webViewController) {
		webViewController = [[TwitterWebViewController alloc] initWithNibName:nil bundle:nil];
        
        UIBarButtonItem *backButton = [[UIBarButtonItem alloc]
                                       initWithTitle:@"Enter PIN" style:UIBarButtonItemStylePlain target:nil action:nil];
        self.navigationItem.backBarButtonItem = backButton;
        [backButton release];
        webViewController.managingVc = self;
        
	}
	
	if (!webView) {
        CGRect appFrame = [UIScreen mainScreen].applicationFrame;
        
		webView = [[UIWebView alloc] initWithFrame:CGRectMake(0,0,appFrame.size.width,appFrame.size.height)];
        webView.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
		[webViewController.view addSubview:webView];
		webView.dataDetectorTypes = UIDataDetectorTypeNone;
        webView.scalesPageToFit = YES;
        webView.delegate = self;
	}
        
	[[self navigationController] pushViewController:webViewController animated:YES];
	[webView loadRequest:[NSURLRequest requestWithURL:myURL]];
	
	UIBarButtonItem *forward = [[UIBarButtonItem alloc] initWithTitle:@"See PIN >"
																style:UIBarButtonItemStyleBordered
															   target:self
															   action:@selector(seePinAgain)];
	self.navigationItem.rightBarButtonItem = forward;
	[forward release];
	willBeEditingPin = YES;

	[self.uiDelegate tokenRequestDidSucceed:self];

}



- (void) requestTwitterTokenDidFail:(OAuth *)_oAuth {
	if (![NSThread isMainThread]) {
		[self performSelectorOnMainThread:@selector(requestTwitterTokenDidFail:)
							   withObject:_oAuth
							waitUntilDone:NO];
		return;
	}

	[self.uiDelegate tokenRequestDidFail:self];
	
}

- (void) authorizeTwitterTokenDidSucceed:(OAuth *)_oAuth {
	if (![NSThread isMainThread]) {
		[self performSelectorOnMainThread:@selector(authorizeTwitterTokenDidSucceed:)
							   withObject:_oAuth
							waitUntilDone:NO];
		return;
	}

	[self.uiDelegate authorizationRequestDidSucceed:self];
    [self.delegate twitterLoginPopupDidAuthorize:self];
}

- (void) authorizeTwitterTokenDidFail:(OAuth *)_oAuth {
	if (![NSThread isMainThread]) {
		[self performSelectorOnMainThread:@selector(authorizeTwitterTokenDidFail:)
							   withObject:_oAuth
							waitUntilDone:NO];
		return;
	}
    
	[self.uiDelegate authorizationRequestDidFail:self];	
}

#pragma mark -
#pragma mark UIViewController and memory mgmt

#define UIColorFromRGB(rgbValue) [UIColor \
colorWithRed:((float)((rgbValue & 0xFF0000) >> 16))/255.0 \
green:((float)((rgbValue & 0xFF00) >> 8))/255.0 \
blue:((float)(rgbValue & 0xFF))/255.0 alpha:1.0]

// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
    
    [[UIDevice currentDevice] beginGeneratingDeviceOrientationNotifications];
    
	self.title = @"Sign In";
	
	UIBarButtonItem *cancelButton = [[UIBarButtonItem alloc]
										  initWithTitle:@"Cancel"
										  style:UIBarButtonItemStylePlain
										  target:self
										  action:@selector(cancel)];	
	self.navigationItem.leftBarButtonItem = cancelButton;
	
	self.navigationController.navigationBar.tintColor =  UIColorFromRGB(0xff8EC1DA);
	
	
	[cancelButton release];
    
	queue = [[NSOperationQueue alloc] init];
	
	oAuth.delegate = self;
	self.navigationController.delegate = self;    
    
    // disable step#2
    pinField.enabled = NO;
    signInButton.enabled = NO;
    signInButton.alpha = 0.5;
    typePinBelow.alpha = 0.5;
    signInBullet2.alpha = 0.5;
	
	// Listen for keyboard hide/show notifications so we can properly reconfigure the UI
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillShow:)
                                                 name:UIKeyboardWillShowNotification object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillHide:)
                                                 name:UIKeyboardWillHideNotification object:nil];        
    
    // XIB is done with portrait layout. If we were launched in landscape, this fixes the button positioning.
	[self fixSignInButtonPositionWithOrientation:self.interfaceOrientation andAnimationDuration:0];
}

- (void)didReceiveMemoryWarning {
	// Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
	
	// Release any cached data, images, etc that aren't in use.
}

- (void)viewDidUnload {
	// Release any retained subviews of the main view.
	// e.g. self.myOutlet = nil;
	
	self.navigationController.delegate = nil;
	[queue release];
}
/*
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
 // Return YES for supported orientations
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}*/

- (void)willRotateToInterfaceOrientation:(UIInterfaceOrientation)toInterfaceOrientation duration:(NSTimeInterval)duration {
    [self fixSignInButtonPositionWithOrientation:toInterfaceOrientation andAnimationDuration:duration];
}

// Re-layout interface after rotation
- (void)didRotateFromInterfaceOrientation:(UIInterfaceOrientation)fromInterfaceOrientation {
    CGRect signInButtonFrame = signInButton.frame;
    
    CGSize scrollContentSize = scrollView.contentSize;
    // 16px padding in bottom
    scrollContentSize.height = signInButtonFrame.origin.y + signInButtonFrame.size.height + 16;
    scrollContentSize.width = scrollView.frame.size.width;
    scrollView.contentSize = scrollContentSize;
    
    // Assume that we always want to focus on sign-in if this step is enabled
    [self focusPinField];    
}


- (void)dealloc {
    
    [[UIDevice currentDevice] endGeneratingDeviceOrientationNotifications];

    [[NSNotificationCenter defaultCenter] removeObserver:self];
	
	[webView release];
	[webViewController release];
	[oAuth release];
	[pinField release];
    [super dealloc];
}

#pragma mark -
#pragma mark Custom helpers, called from appropriate places in flow



- (void) focusPinField {
    if (signInButton.enabled) {
        CGRect scrollToFrame = signInButton.frame;
        scrollToFrame.origin.y += 16;
        [scrollView scrollRectToVisible:scrollToFrame animated:YES];        
    }
}

- (void) fixSignInButtonPositionWithOrientation:(UIDeviceOrientation)orientation andAnimationDuration:(NSTimeInterval)duration {
    CGRect signInButtonFrame = signInButton.frame;
    CGRect pinFieldFrame = pinField.frame;
    
    // If portrait, signin button is centered below the text field
    // If landscape, button is next to textfield
    if (!UIDeviceOrientationIsLandscape(orientation)) {
        signInButtonFrame.origin.y = pinFieldFrame.origin.y + 56;
        signInButtonFrame.origin.x = pinFieldFrame.origin.x + pinFieldFrame.size.width/2 - signInButtonFrame.size.width/2;        
    } else {
        signInButtonFrame.origin.y = pinFieldFrame.origin.y;
        signInButtonFrame.origin.x = pinFieldFrame.origin.x + pinFieldFrame.size.width + 8;
    }
    
    [UIView beginAnimations:@"RepositionSignInButton" context:nil];
    [UIView setAnimationDuration:duration];
    signInButton.frame = signInButtonFrame;
    [UIView commitAnimations];    
}

#pragma mark -
#pragma mark UINavigationController delegate

- (void)navigationController:(UINavigationController *)navigationController
      willShowViewController:(UIViewController *)viewController animated:(BOOL)animated {
    if (viewController == self) {
		if (willBeEditingPin) {
            [self fixSignInButtonPositionWithOrientation:self.interfaceOrientation andAnimationDuration:0];
        }
    }
}

- (void)navigationController:(UINavigationController *)navigationController
       didShowViewController:(UIViewController *)viewController animated:(BOOL)animated {

    // If user was looking at PIN in webview, activate the PIN text field and bring up keyboard.
	if (viewController == self) {
		if (willBeEditingPin) {
            
            pinField.enabled = YES;
            signInButton.enabled = YES;
            signInButton.alpha = 1;
            typePinBelow.alpha = 1;
            signInBullet2.alpha = 1;

			[pinField becomeFirstResponder];
            
            [self focusPinField];
		}
	}
}

#pragma mark -
#pragma mark Keyboard notifications

- (CGFloat)keyboardHeightFromNotification:(NSNotification *)aNotification {
    // http://stackoverflow.com/questions/2807339/uikeyboardboundsuserinfokey-is-deprecated-what-to-use-instead
    CGRect _keyboardEndFrame;
    [[aNotification.userInfo valueForKey:UIKeyboardFrameEndUserInfoKey] getValue:&_keyboardEndFrame];
    CGFloat _keyboardHeight;
    if (UIDeviceOrientationIsPortrait([[UIDevice currentDevice] orientation])) {
        _keyboardHeight = _keyboardEndFrame.size.height;
    }
    else {
        _keyboardHeight = _keyboardEndFrame.size.width;
    }
    
    return _keyboardHeight;
}

- (void)keyboardWillShow:(NSNotification *)aNotification {
    
    NSTimeInterval animationDuration = [[[aNotification userInfo] objectForKey:UIKeyboardAnimationDurationUserInfoKey] doubleValue];
		
    CGRect scrollFrame = scrollView.frame;
    scrollFrame.size.height = self.view.frame.size.height - [self keyboardHeightFromNotification:aNotification];
    	
    [UIView beginAnimations:@"ResizeForKeyboard" context:nil];
    [UIView setAnimationDuration:animationDuration];
    scrollView.frame = scrollFrame;
    [UIView commitAnimations]; 

}

- (void)keyboardWillHide:(NSNotification *)aNotification {
    
    CGRect scrollFrame = scrollView.frame;
    scrollFrame.size.height = self.view.frame.size.height;
    
    NSTimeInterval animationDuration = [[[aNotification userInfo] objectForKey:UIKeyboardAnimationDurationUserInfoKey] doubleValue];
    
    [UIView beginAnimations:@"ResizeForKeyboard" context:nil];
    [UIView setAnimationDuration:animationDuration];
    scrollView.frame = scrollFrame;
    [UIView commitAnimations];
}

#pragma mark -
#pragma mark UIWebViewDelegate

- (void)webViewDidStartLoad:(UIWebView *)webView {
    [[UIApplication sharedApplication] setNetworkActivityIndicatorVisible:YES];
}

- (void)webViewDidFinishLoad:(UIWebView *)webView {
    [[UIApplication sharedApplication] setNetworkActivityIndicatorVisible:NO];

}

@end
