//
//  RootViewController.m
//  Plain2
//
//  Created by Jaanus Kase on 03.05.10.
//  Copyright 2010. All rights reserved.
//

#import "RootViewController.h"
#import "OAuth.h"
#import "OAuth+UserDefaults.h"
#import "OAuthConsumerCredentials.h"
#import "CustomLoginPopup.h"
#import "JSON.h"

@implementation RootViewController

@synthesize textNote,tweetPicker,bannerView;

/*
 // The designated initializer.  Override if you create the controller programmatically and
 // want to perform customization that is not appropriate for viewDidLoad.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if ((self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil])) {
        // Custom initialization
    }
    return self;
}
*/


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
	
    
	[self moveBannerViewOffscreen];//hide banner
	[self loadArrays];
	
	self.title = @"Quick Tweet";

	oAuth = [[OAuth alloc] initWithConsumerKey:OAUTH_CONSUMER_KEY andConsumerSecret:OAUTH_CONSUMER_SECRET];
	[oAuth loadOAuthTwitterContextFromUserDefaults];
	   
	[self resetUi];
	
	[super viewDidLoad];
	
    
}

- (void)resetUi {						   
	
	if (oAuth.oauth_token_authorized) {
        postButton.hidden = NO;
		postButton.enabled = YES; 
        signedInAs.text = [NSString stringWithFormat:@"Logged in as %@.", oAuth.screen_name];
		       
        UIBarButtonItem *logout = [[UIBarButtonItem alloc] initWithTitle:@"Log out"
                                                                   style:UIBarButtonItemStyleBordered
                                                                  target:self
                                                                  action:@selector(logout)];
        self.navigationItem.leftBarButtonItem = logout;
			
		
        [logout release];
        
    } else {
        postButton.hidden = YES;
        postButton.enabled = NO;
        signedInAs.text = @"";
        UIBarButtonItem *login = [[UIBarButtonItem alloc] initWithTitle:@"Log in"
                                                                   style:UIBarButtonItemStyleBordered
                                                                  target:self
                                                                  action:@selector(login)];
        self.navigationItem.leftBarButtonItem = login;
		
        [login release];
        
    }
	
	//add edit button
	UIBarButtonItem *editButton =[[UIBarButtonItem alloc]initWithTitle:@"Edit Lists" style:UIBarButtonItemStyleBordered target:self action:@selector(editList)];
	self.navigationItem.rightBarButtonItem = editButton;
	[editButton release];
}

- (void) loadArrays{
	
	//for the device to read/write we need to put plist in the correct place
	NSFileManager *fileManager = [NSFileManager defaultManager];
	NSString *documentDirectory = [self applicationDocumentsDirectory];
	
	//NOUNS
	NSString *writableNounsPath = [documentDirectory stringByAppendingPathComponent:@"Nouns.plist"];	
	BOOL nexits = [fileManager fileExistsAtPath:writableNounsPath];
	if (!nexits) {
		// The writable database does not exist, so copy the default to the appropriate location.
		NSString *nounsPath = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:@"Nouns.plist"];
		
		NSError *error;
		[fileManager copyItemAtPath:nounsPath toPath:writableNounsPath error:&error];
	}
	
	//VERBS	
	NSString *writableVerbsPath = [documentDirectory stringByAppendingPathComponent:@"Verbs.plist"];	
	BOOL vexits = [fileManager fileExistsAtPath:writableVerbsPath];
	if (!vexits) {
		// The writable database does not exist, so copy the default to the appropriate location.
		NSString *verbsPath = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:@"Verbs.plist"];
		
		NSError *error;
		[fileManager copyItemAtPath:verbsPath toPath:writableVerbsPath error:&error];
	}
	
	
	feelingArray = [[NSArray alloc] initWithContentsOfFile:writableNounsPath];
	imArray = [[NSArray alloc] initWithContentsOfFile:writableVerbsPath];

	//NSLog(@"Loading arrays");
	
}

- (NSString *)applicationDocumentsDirectory {
	return [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) lastObject];
}
// returns the number of 'columns' to display.
- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView{
	return 2;
}

// returns the # of rows in each component..
- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component{
	
	if(component == 0)
		return [imArray count];
	else 
		return [feelingArray count];
	
}




- (NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component{
	
	postButton.enabled = YES;
	
	switch (component) {
		case 0:
			return [imArray objectAtIndex:row];
			
		case 1:
			return [feelingArray objectAtIndex:row];
	}	
	return nil;
	
	
}

- (void) editListViewControllerDidFinish:(EditListViewController *)controller {
	
	[self loadArrays];
	
	[tweetPicker reloadAllComponents];
	[self dismissModalViewControllerAnimated:YES];
	
}

-(IBAction)textFieldDoneEditing:(id)sender{
	[sender resignFirstResponder];
}
-(void) editList{
	
	EditListViewController *editListView = [[EditListViewController alloc] initWithNibName:@"EditListViewController" bundle:nil];
	editListView.delegate = self;
	
	editListView.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;
	[self presentModalViewController:editListView animated:YES];
	
	[editListView release];
	
}
/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {	
    
    // Supports all but upside-down orientation.
	return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}*/


- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

- (void)viewDidUnload {
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}


#pragma mark -
#pragma mark Button actions

- (void)login {
    loginPopup = [[CustomLoginPopup alloc] initWithNibName:@"TwitterLoginPopup" bundle:nil];
    loginPopup.oAuth = oAuth;
    loginPopup.delegate = self;
    loginPopup.uiDelegate = self;
    UINavigationController *nav = [[UINavigationController alloc] initWithRootViewController:loginPopup];
    [self presentModalViewController:nav animated:YES];
    [nav release];
}

- (void)logout {
    [oAuth forget];
    [oAuth saveOAuthTwitterContextToUserDefaults];
    [self resetUi];
}

- (void)didPressPost:(id)sender {
    
	NSMutableString *textString = [NSMutableString stringWithFormat: @"%@",textNote.text ];
	
	if(![textString hasSuffix:@"."])
	   [textString appendFormat: @". "];
	else 
		[textString appendFormat: @" "];

	
	NSString *themessage = [NSString stringWithFormat:@"%@I'm %@ and feeling %@ about it.",textNote.text.length != 0 ? textString : @"",
							[imArray objectAtIndex:[tweetPicker selectedRowInComponent:0]],
							[feelingArray objectAtIndex:[tweetPicker selectedRowInComponent:1]]];
	
    // We assume that the user is authenticated by this point and we have a valid OAuth context,
    // thus no need to do context checking.
    
    NSString *postUrl = @"https://api.twitter.com/1/statuses/update.json";
    
    ASIFormDataRequest *request = [[ASIFormDataRequest alloc]
                                   initWithURL:[NSURL URLWithString:postUrl]];
    [request setPostValue:themessage forKey:@"status"];
    
    [request addRequestHeader:@"Authorization"
                        value:[oAuth oAuthHeaderForMethod:@"POST"
                                                   andUrl:postUrl
                                                andParams:[NSDictionary dictionaryWithObject:themessage
                                                                                      forKey:@"status"]]];
    
    [request startSynchronous];
    
    NSLog(@"Status posted. HTTP result code: %d", request.responseStatusCode);
    
    textNote.text = @"";
	postButton.enabled = NO;
    
    [request release];
}

- (IBAction)didPressLatestTweets:(id)sender {
    NSString *getUrl = @"http://api.twitter.com/1/statuses/user_timeline.json";
    
    NSDictionary *params = [NSDictionary dictionaryWithObjectsAndKeys:@"5", @"count", nil];
    
    // Note how the URL is without parameters here...
    // (this is how OAuth works, you always give it a "normalized" URL without parameters
    // since you give parameters separately to it, even for GET)
    NSString *oAuthValue = [oAuth oAuthHeaderForMethod:@"GET" andUrl:getUrl andParams:params];
    
    // ... but the actual request URL contains normal GET parameters.
    ASIHTTPRequest *request = [[ASIHTTPRequest alloc]
                               initWithURL:[NSURL URLWithString:[NSString
                                                                 stringWithFormat:@"%@?count=%@",
                                                                 getUrl,
                                                                 [params valueForKey:@"count"]]]];
    [request addRequestHeader:@"Authorization" value:oAuthValue];
    [request startSynchronous];
    
    NSLog(@"Got statuses. HTTP result code: %d", request.responseStatusCode);
    
    tweets.text = @"";
    
    NSArray *gotTweets = [[request responseString] JSONValue];
    
    for (NSDictionary *tweet in gotTweets) {
        tweets.text = [NSString stringWithFormat:@"%@%@\n", tweets.text, [tweet valueForKey:@"text"]];
    } 
    
    [request release];
}


#pragma mark -
#pragma mark TwitterLoginPopupDelegate

- (void)twitterLoginPopupDidCancel:(TwitterLoginPopup *)popup {
    [self dismissModalViewControllerAnimated:YES];
    [loginPopup release]; // was retained as ivar in "login"
}

- (void)twitterLoginPopupDidAuthorize:(TwitterLoginPopup *)popup {
    [self dismissModalViewControllerAnimated:YES];
    [loginPopup release]; // was retained as ivar in "login"
    [oAuth saveOAuthTwitterContextToUserDefaults];
    [self resetUi];
}

#pragma mark -
#pragma mark TwitterLoginUiFeedback

- (void) tokenRequestDidStart:(TwitterLoginPopup *)twitterLogin {
    NSLog(@"token request did start");
    [loginPopup.activityIndicator startAnimating];
}

- (void) tokenRequestDidSucceed:(TwitterLoginPopup *)twitterLogin {
    NSLog(@"token request did succeed");    
    [loginPopup.activityIndicator stopAnimating];
}

- (void) tokenRequestDidFail:(TwitterLoginPopup *)twitterLogin {
    NSLog(@"token request did fail");
    [loginPopup.activityIndicator stopAnimating];
}

- (void) authorizationRequestDidStart:(TwitterLoginPopup *)twitterLogin {
    NSLog(@"authorization request did start");    
    [loginPopup.activityIndicator startAnimating];
}

- (void) authorizationRequestDidSucceed:(TwitterLoginPopup *)twitterLogin {
    NSLog(@"authorization request did succeed");
    [loginPopup.activityIndicator stopAnimating];
}

- (void) authorizationRequestDidFail:(TwitterLoginPopup *)twitterLogin {
    NSLog(@"token request did fail");
    [loginPopup.activityIndicator stopAnimating];
}


/****banner view *****/
-(void) moveBannerViewOnscreen{
	
	CGRect newBannerview = self.bannerView.frame;
	newBannerview.origin.y = self.view.frame.size.height-newBannerview.size.height;

	[UIView beginAnimations:@"BannerViewIntro" context:NULL];
	self.bannerView.frame = newBannerview;
	[UIView commitAnimations];
	
}
-(void) moveBannerViewOffscreen{
	CGFloat viewHeight = self.view.frame.size.height;
	CGRect newBannerview = self.bannerView.frame;
	newBannerview.origin.y = viewHeight;
	
	self.bannerView.frame =newBannerview;
	
}
- (void)bannerView:(ADBannerView *)banner didFailToReceiveAdWithError:(NSError *)error;
{
	[self moveBannerViewOffscreen];
}
- (void)bannerViewDidLoadAd:(ADBannerView *)banner{
	[self moveBannerViewOnscreen];
}


- (void)dealloc {
	bannerView.delegate = nil;
	[tweetPicker release];
	[bannerView release];
	[imArray release];
	[feelingArray release];
    [oAuth release];
    
    [super dealloc];
}

@end
