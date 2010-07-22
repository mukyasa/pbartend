//
//  CoverShotEditorViewController.m
//  CoverShot
//
//  Created by Darren Mason on 7/21/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import "CoverShotEditorViewController.h"


@implementation CoverShotEditorViewController

@synthesize previewImageView,parentPreviewView,parentPreviewImageView,delegate,mainToolBar;
/*
 // The designated initializer.  Override if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if ((self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil])) {
        // Custom initialization
    }
    return self;
}
*/


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
	
	// clip sub-layer contents
	parentPreviewView.layer.masksToBounds = YES;
	
	// do one time set-up of gesture recognizers
	UIGestureRecognizer *recognizer;
	
	recognizer = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(handleSingleTapFrom:)];
	recognizer.delegate = self;
	[parentPreviewView addGestureRecognizer:recognizer];
	[recognizer release];
	
	recognizer = [[UIPinchGestureRecognizer alloc] initWithTarget:self action:@selector(handlePinchFrom:)];
	recognizer.delegate = self;
	[parentPreviewView addGestureRecognizer:recognizer];
	[recognizer release];
	
	recognizer = [[UIRotationGestureRecognizer alloc] initWithTarget:self action:@selector(handleRotationFrom:)];
	recognizer.delegate = self;
	[parentPreviewView addGestureRecognizer:recognizer];
	[recognizer release];
	
	
	recognizer = [[UIPanGestureRecognizer alloc] initWithTarget:self action:@selector(handleDragFrom:)];
	recognizer.delegate = self;
	((UIPanGestureRecognizer *)recognizer).maximumNumberOfTouches = 1;
	[parentPreviewView addGestureRecognizer:recognizer];
	[recognizer release];
	
    [super viewDidLoad];
}


- (void)handleSingleTapFrom:(UITapGestureRecognizer *)recognizer
{
	//NSLog(@"Single Tap");
	[self moveNavViewOnscreen];
}

- (void)handlePinchFrom:(UIPinchGestureRecognizer *)recognizer
{
	//NSLog(@"Pinch");
	[self moveNavViewOffscreen];
	
	
	if (recognizer.state == UIGestureRecognizerStateBegan) {
		beginGestureScale = 1;
	}
	previewImageView.transform = CGAffineTransformScale(previewImageView.transform, (recognizer.scale / beginGestureScale), (recognizer.scale / beginGestureScale));
	beginGestureScale = recognizer.scale;
	
	
}

- (void)handleDragFrom:(UIPanGestureRecognizer *)recognizer
{
	//NSLog(@"Pan");
	[self moveNavViewOffscreen];
	
	if (recognizer.state == UIGestureRecognizerStateBegan) {
		CGPoint startPoint = [recognizer locationOfTouch:0 inView:previewImageView];
		inImage = [self point:startPoint inView:previewImageView];
		oldX = 0;
		oldY = 0;
	}
	if (inImage) {
		CGPoint translate = [recognizer translationInView: parentPreviewView];
		previewImageView.transform = CGAffineTransformTranslate(previewImageView.transform, translate.x-oldX, translate.y-oldY);
		oldX = translate.x;
		oldY = translate.y;
	}
	
	
	
}


-(BOOL)point:(CGPoint)p inView:(UIView *)view {
	return p.x > 0 && p.x < view.bounds.size.width && p.y > 0 && p.y < view.bounds.size.height;
}


- (void)handleRotationFrom:(UIRotationGestureRecognizer *)recognizer
{
	//NSLog(@"Rotate");
	[self moveNavViewOffscreen];
	
	if (recognizer.state == UIGestureRecognizerStateBegan) {
		beginGestureRotationRadians	= 0;
	}
	previewImageView.transform = CGAffineTransformRotate(previewImageView.transform, (recognizer.rotation - beginGestureRotationRadians));
	beginGestureRotationRadians = recognizer.rotation;
	
}

/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

-(void) moveNavViewOnscreen{
	
	CGRect toobar = self.mainToolBar.frame;
	toobar.origin.y = self.view.frame.size.height-toobar.size.height;
	
	[UIView beginAnimations:@"navViewShow" context:NULL];
	[UIView setAnimationDuration:.5];
	self.mainToolBar.frame = toobar;
	[UIView commitAnimations];
	
}
-(void) moveNavViewOffscreen{
	
	CGFloat viewHeight = self.view.frame.size.height;
	CGRect toobar = self.mainToolBar.frame;
	toobar.origin.y = viewHeight;
	
	[UIView beginAnimations:@"navViewShow" context:NULL];
	[UIView setAnimationDuration:.3];
	self.mainToolBar.frame = toobar;
	[UIView commitAnimations];
	
	
}


- (IBAction)done:(id)sender {
	[self.delegate coverShotEditorViewControllerDidFinish:self];	
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
	[mainToolBar release];
	[parentPreviewImageView release];
	[parentPreviewView release];
	[previewImageView release];
    [super dealloc];
}


@end
