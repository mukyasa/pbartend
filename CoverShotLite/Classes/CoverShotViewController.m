//
//  CoverShotViewController.m
//  CoverShot
//
//  Created by Darren Mason on 7/21/10.
//  Copyright TGen 2010. All rights reserved.
//

#import "CoverShotViewController.h"
#import "CoverShotAppDelegate.h"

@implementation CoverShotViewController

@synthesize magizineScrollView,pickedCover,bannerView,coverShotEditorViewController,fullversion,bannerButton;

const CGFloat kScrollObjWidth	= 240.0;
const CGFloat kScrollObjHeight	= 360;
const NSUInteger kNumImages		= 10;
const int limited = 10;


-(IBAction)getGame:(id)sender{
    
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"http://itunes.apple.com/us/app/terminal-velocity-lite-the/id491548703?ls=1&mt=8"]];	

}
-(IBAction)hideGameBanner:(id)sender{
    
    CGFloat viewHeight = self.view.frame.size.height;
	CGRect newBannerview = self.bannerButton.frame;
	newBannerview.origin.y = -viewHeight;

    [UIView beginAnimations:@"BannerViewIntro" context:NULL];
	self.bannerButton.frame = newBannerview;
	[UIView commitAnimations];
}


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

- (void)coverShotEditorViewControllerDidFinish:(CoverShotEditorViewController *)controller{
	[controller dismissModalViewControllerAnimated:YES];

}

- (void)rateApp {
    
    int launchCount = [[NSUserDefaults standardUserDefaults] integerForKey:@"launchCount"];
    launchCount++;
    [[NSUserDefaults standardUserDefaults] setInteger:launchCount forKey:@"launchCount"];
    
    BOOL neverRate = [[NSUserDefaults standardUserDefaults] boolForKey:@"neverRate"];
    
    if ((neverRate != YES) && (launchCount > 2)) {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Please rate Cover Shot Lite!"
                                                        message:@"If you enjoyed using Cover Shot Lite and you would like others to enjoy it, please rate it!" 
                                                       delegate:self 
                                              cancelButtonTitle:nil 
                                              otherButtonTitles:@"Rate now", @"Never ask again", @"Remind me later", nil];
        alert.delegate = self;
        [alert show];
        [alert release];
    }
}

- (void)alertView:(UIAlertView *)alertView didDismissWithButtonIndex:(NSInteger)buttonIndex {
    if (buttonIndex == 0) {
        [[NSUserDefaults standardUserDefaults] setBool:YES forKey:@"neverRate"];
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"http://itunes.apple.com/us/app/cover-shot-lite-fake-magazine/id387311431?ls=1&mt=8"]];
    }
    
    else if (buttonIndex == 1) {
        [[NSUserDefaults standardUserDefaults] setBool:YES forKey:@"neverRate"];
    }
    
    else if (buttonIndex == 2) {
        // Do nothing
    }
}

// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    
    [self rateApp];
    [bannerButton setBackgroundColor: UIColor.clearColor];
	[self moveBannerViewOffscreen];//hide banner
	
	pickedCover = [UIImage imageNamed:@"clearcover1.png"];
	
	// 1. setup the scrollview for multiple images and add it to the view controller
	//
	// note: the following can be done in Interface Builder, but we show this in code for clarity
	
	[magizineScrollView setCanCancelContentTouches:NO];
	magizineScrollView.indicatorStyle = UIScrollViewIndicatorStyleWhite;
	magizineScrollView.showsHorizontalScrollIndicator = NO;
	magizineScrollView.clipsToBounds = YES;		// default is NO, we want to restrict drawing within our scrollview
	magizineScrollView.scrollEnabled = YES;
	
	// pagingEnabled property default is NO, if set the scroller will stop or snap at each photo
	// if you want free-flowing scroll, don't set this property.
	magizineScrollView.pagingEnabled = YES;
	
	// load all the images from our bundle and add them to the scroll view
	NSUInteger i;
	for (i = 1; i <= kNumImages; i++)
	{
		NSString *imageName = [NSString stringWithFormat:@"cover%d.png", i];
		UIImage *image = [UIImage imageNamed:imageName];
		UIImageView *imageView = [[UIImageView alloc] initWithImage:image];
		
		// setup each frame to a default height and width, it will be properly placed when we call "updateScrollList"
		CGRect rect = imageView.frame;
		rect.size.height = kScrollObjHeight;
		rect.size.width = kScrollObjWidth;
		imageView.frame = rect;
		imageView.tag = i;	// tag our images for later use when we place them in serial fashion
		[magizineScrollView addSubview:imageView];
		
		[imageView release];
		image=nil;
		imageName=nil;
	}
	
	[self layoutScrollImages];	// now place the photos in serial layout within the scrollview
	
	
	// do one time set-up of gesture recognizers
	UIGestureRecognizer *recognizer = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(handleDoubleTapFrom:)];
	recognizer.delegate = self;
	((UITapGestureRecognizer*)recognizer).numberOfTapsRequired=2;
	[magizineScrollView addGestureRecognizer:recognizer];
	[recognizer release];
	
    [super viewDidLoad];
}

- (void)handleDoubleTapFrom:(UITapGestureRecognizer *)recognizer
{
	if (recognizer.state == UIGestureRecognizerStateEnded) {
		//NSLog(@"Tap");
		if(liteCover<=limited)
		{
			//NSLog(@"Tap");
			coverShotEditorViewController = [[CoverShotEditorViewController alloc] initWithNibName:@"CoverShotEditorViewController" bundle:nil];
			coverShotEditorViewController.delegate = self;
			
			coverShotEditorViewController.modalTransitionStyle = UIModalTransitionStyleCoverVertical;
			[self presentModalViewController:coverShotEditorViewController animated:YES];
			
			coverShotEditorViewController.parentPreviewImageView.image = pickedCover;
			CoverShotAppDelegate *appDelegate = (CoverShotAppDelegate *)[[UIApplication sharedApplication] delegate];
			
			appDelegate.coverHolder =  [[UIImageView alloc] initWithImage:pickedCover] ;
			//NSLog(@"CoverShotEditorViewController retain count: %i",[coverShotEditorViewController retainCount]);
			//[coverShotEditorViewController release];
		}else{
			
			NSString *title = NSLocalizedString(@"Full Version Only", @"");
			NSString *message = NSLocalizedString(@"Sorry you can only use this if you purchase the full version. Please buy it, I spend many hours making it. It's only $.99", @"");
		
			UIAlertView *alert = [[UIAlertView alloc] initWithTitle:title
															message:message
														   delegate:nil
												  cancelButtonTitle:NSLocalizedString(@"Ok", @"")
												  otherButtonTitles:nil];
			[alert show];
			[alert release];
		}
	}

}


- (void)scrollViewDidScroll:(UIScrollView *)scrollView{
	
	CGFloat pageWidth = magizineScrollView.frame.size.width-10; 
	int page = ((scrollView.contentOffset.x / pageWidth) + 1);
	pickedCover = [UIImage imageNamed:[NSString stringWithFormat:@"clearcover%i.png",page] ];
	liteCover =page;
	if(liteCover>limited)
		fullversion.hidden=YES;//this should be NO for demo version
	else 
		fullversion.hidden=YES;
	

	
	//NSLog(@"SCROLL OFFSET: %d",page);
	
	
}

- (void)layoutScrollImages
{
	UIImageView *view = nil;
	NSArray *subviews = [magizineScrollView subviews];
	
	// reposition all image subviews in a horizontal serial fashion
	CGFloat curXLoc = 0;
	for (view in subviews)
	{
		if ([view isKindOfClass:[UIImageView class]] && view.tag > 0)
		{
			CGRect frame = view.frame;
			frame.origin = CGPointMake(curXLoc, 0);
			view.frame = frame;
			
			curXLoc += (kScrollObjWidth);
		}
	}
	
	// set the content size so it can be scrollable
	[magizineScrollView setContentSize:CGSizeMake((kNumImages * kScrollObjWidth), [magizineScrollView bounds].size.height)];
}


/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/



- (void)flipsideViewControllerDidFinish:(FlipsideViewController *)controller {
    
	[self dismissModalViewControllerAnimated:YES];
}


- (IBAction)showInfo:(id)sender {    
	
	FlipsideViewController *controller = [[FlipsideViewController alloc] initWithNibName:@"FlipsideView" bundle:nil];
	controller.delegate = self;
	
	controller.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;
	[self presentModalViewController:controller animated:YES];
	//NSLog(@"FlipsideViewController retain count: %i",[controller retainCount]);
	[controller release];
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
	[coverShotEditorViewController release];
	bannerView.delegate = nil;
	[fullversion release];
	[bannerView release];
	[pickedCover release];
	[magizineScrollView release];
    [super dealloc];
}

@end
