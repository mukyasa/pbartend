//
//  MainMagPicViewController.m
//  MagPicMe
//
//  Created by Darren Mason on 7/15/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import "MainMagPicViewController.h"

@implementation MainMagPicViewController

@synthesize saveNavBar,delegate,swipeLeftRecognizer, tapRecognizer;

/*
 // The designated initializer.  Override if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if ((self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil])) {
        // Custom initialization
    }
    return self;
}
*/
- (void)viewDidAppear:(BOOL)animated{
[self moveNavViewOnscreen];
}

// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
	
	[self moveNavViewOffscreen];
	
	/*
     Create and configure the four recognizers. Add each to the view as a gesture recognizer.
     */
	UIGestureRecognizer *recognizer;
	
    /*
     Create a tap recognizer and add it to the view.
     Keep a reference to the recognizer to test in gestureRecognizer:shouldReceiveTouch:.
     */
	recognizer = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(handleTapFrom:)];
	[self.view addGestureRecognizer:recognizer];
    self.tapRecognizer = (UITapGestureRecognizer *)recognizer;
    recognizer.delegate = self;
	[recognizer release];
	
    /*
     Create a swipe gesture recognizer to recognize right swipes (the default).
     We're only interested in receiving messages from this recognizer, and the view will take ownership of it, so we don't need to keep a reference to it.
     */
	recognizer = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(handleSwipeFrom:)];
	[self.view addGestureRecognizer:recognizer];
	[recognizer release];
	
    /*
     Create a swipe gesture recognizer to recognize left swipes.
     Keep a reference to the recognizer so that it can be added to and removed from the view in takeLeftSwipeRecognitionEnabledFrom:.
     Add the recognizer to the view if the segmented control shows that left swipe recognition is allowed.
     */
	recognizer = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(handleSwipeFrom:)];
	self.swipeLeftRecognizer = (UISwipeGestureRecognizer *)recognizer;
    swipeLeftRecognizer.direction = UISwipeGestureRecognizerDirectionLeft;

	[self.view addGestureRecognizer:swipeLeftRecognizer];
    
    self.swipeLeftRecognizer = (UISwipeGestureRecognizer *)recognizer;
	[recognizer release];
	
    /*
     Create a rotation gesture recognizer.
     We're only interested in receiving messages from this recognizer, and the view will take ownership of it, so we don't need to keep a reference to it.
     */
	recognizer = [[UIRotationGestureRecognizer alloc] initWithTarget:self action:@selector(handleRotationFrom:)];
	[self.view addGestureRecognizer:recognizer];
	[recognizer release];
    
	
	
    [super viewDidLoad];
}

-(BOOL)gestureRecognizerShouldBegin:(UIGestureRecognizer *)gestureRecognizer{

	if ( [gestureRecognizer isKindOfClass:[UIPinchGestureRecognizer class]] ) {
		beginGestureScale = effectiveScale;
	}
	else if ( [gestureRecognizer isKindOfClass:[UIRotationGestureRecognizer class]] ) {
		beginGestureRotationRadians = effectiveRotationRadians;
	}
	if ( [gestureRecognizer isKindOfClass:[UIPanGestureRecognizer class]] ) {
        CGPoint location = [gestureRecognizer locationInView:self.view];
        beginGestureTranslation = CGPointMake(effectiveTranslation.x - location.x, effectiveTranslation.x - location.y);
	}
	return YES;
	
}


- (void)handleTapFrom:(UITapGestureRecognizer *)recognizer {
	NSLog(@"TAPPING");
	[self moveNavViewOffscreen];
	
}

- (void)handleSwipeFrom:(UISwipeGestureRecognizer *)recognizer {
	NSLog(@"SWIPPING");
	

	
	
}
- (void)handleRotationFrom:(UIRotationGestureRecognizer *)recognizer {
	NSLog(@"ROTATING");
	[self moveNavViewOffscreen];
    
    CGAffineTransform transform = CGAffineTransformMakeRotation([recognizer rotation]);
    previewImageView.transform = transform;


}

- (void)handleDragFrom:(UIPanGestureRecognizer *)recognizer
{
}

/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

-(void)done:(id)sender{


}

-(void) moveNavViewOnscreen{
	
	CGRect newBannerview = self.saveNavBar.frame;
	newBannerview.origin.y = 0;
	
	[UIView beginAnimations:@"navViewShow" context:NULL];
	[UIView setAnimationDuration:1.0];
	self.saveNavBar.frame = newBannerview;
	[UIView commitAnimations];
	
}
-(void) moveNavViewOffscreen{
	
	CGRect newBannerview = self.saveNavBar.frame;
	newBannerview.origin.y = -44;
	
	[UIView beginAnimations:@"navViewShow" context:NULL];
	[UIView setAnimationDuration:.3];
	self.saveNavBar.frame = newBannerview;
	[UIView commitAnimations];

	
}

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


- (void)dealloc {
	[tapRecognizer release];
	[swipeLeftRecognizer release];

	[saveNavBar release];
    [super dealloc];
}


@end
