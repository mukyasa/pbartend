//
//  DetailViewController.m
//  iPadDrinkMixer
//
//  Created by Darren Mason on 8/23/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import "DetailViewController.h"
#import "RootViewController.h"
#import "PageViewController.h"
#import "FilterViewController.h"


@interface DetailViewController ()
@property (nonatomic, retain) UIPopoverController *popoverController;
- (void)configureView;
@end



@implementation DetailViewController

@synthesize toolbar, popoverController, detailItem, detailDescriptionLabel,myCustomFont;
@synthesize pageView;
@synthesize listPopOver,listFilterPopOver,currentPopover;

#pragma mark -
#pragma mark Managing the detail item

/*
 When setting the detail item, update the view and dismiss the popover controller if it's showing.
 */
- (void)setDetailItem:(id)newDetailItem {
    if (detailItem != newDetailItem) {
        [detailItem release];
        detailItem = [newDetailItem retain];
        
        // Update the view.
        [self configureView];
    }

    if (popoverController != nil) {
        [popoverController dismissPopoverAnimated:YES];
    }        
}


- (void)configureView {
    // Update the user interface for the detail item.
    detailDescriptionLabel.text = [detailItem description];   
}


#pragma mark -
#pragma mark Split view support

- (void)splitViewController: (UISplitViewController*)svc willHideViewController:(UIViewController *)aViewController withBarButtonItem:(UIBarButtonItem*)barButtonItem forPopoverController: (UIPopoverController*)pc {
    
    barButtonItem.title = @"Root List";
    NSMutableArray *items = [[toolbar items] mutableCopy];
    [items insertObject:barButtonItem atIndex:0];
    [toolbar setItems:items animated:YES];
    [items release];
    self.popoverController = pc;
	listPopOver.hidden=NO;
}


// Called when the view is shown again in the split view, invalidating the button and popover controller.
- (void)splitViewController: (UISplitViewController*)svc willShowViewController:(UIViewController *)aViewController invalidatingBarButtonItem:(UIBarButtonItem *)barButtonItem {
    
    NSMutableArray *items = [[toolbar items] mutableCopy];
    [items removeObjectAtIndex:0];
    [toolbar setItems:items animated:YES];
    [items release];
    self.popoverController = nil;
	listPopOver.hidden=YES;
}


#pragma mark -
#pragma mark Rotation support

// Ensure that the view controller supports rotation and that the split view can therefore show in both portrait and landscape.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    return YES;
}


#pragma mark -
#pragma mark View lifecycle


 // Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
	//set up font
	myCustomFont = [UIFont fontWithName:@"Diego" size:24];
	
	PageViewController *page = [[PageViewController alloc] initWithNibName:@"PageViewController" bundle:nil];
	
	[page.view setFrame:CGRectMake(150, 90, 550, 800)];
	
	self.pageView = page;
	[self.view insertSubview:page.view atIndex:1];
	[page release];
	
    [super viewDidLoad];
}
 
- (void)setupNewPopoverControllerForViewController:(UIViewController *)vc {
	if (self.currentPopover) {
		[self.currentPopover dismissPopoverAnimated:YES];
		[self handleDismissedPopoverController:self.currentPopover];
	}
	self.currentPopover = [[[UIPopoverController alloc] initWithContentViewController:vc] autorelease];
	self.currentPopover.delegate = self;
}

- (void)handleDismissedPopoverController:(UIPopoverController*)popoverController {
	
	self.currentPopover = nil;
}

-(IBAction)popoverList:(id)sender{
	
	
	[self.popoverController presentPopoverFromRect:listPopOver.frame
											inView:self.view
						  permittedArrowDirections:UIPopoverArrowDirectionAny
										  animated:YES];
	
}

-(IBAction)popoverFilter:(id)sender{
	
	FilterViewController *filter = [[[FilterViewController alloc] initWithNibName:@"FilterViewController" bundle:nil] autorelease];
	[self setupNewPopoverControllerForViewController:filter];
	
	currentPopover.popoverContentSize = CGSizeMake(300.0, 400.0);
	
	[self.currentPopover presentPopoverFromRect:listFilterPopOver.frame
										 inView:self.view
					   permittedArrowDirections:UIPopoverArrowDirectionAny
									   animated:YES];
	
}
-(void)willRotateToInterfaceOrientation:(UIInterfaceOrientation)toInterfaceOrientation duration:(NSTimeInterval)duration{
	[pageView didRotateFromInterfaceOrientation:toInterfaceOrientation];
}

/*
- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
}
*/
/*
- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
}
*/
/*
- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
}
*/
/*
- (void)viewDidDisappear:(BOOL)animated {
    [super viewDidDisappear:animated];
}
*/

- (void)viewDidUnload {
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
    self.popoverController = nil;
}


#pragma mark -
#pragma mark Memory management

/*
- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}
*/

- (void)dealloc {
	[currentPopover release];
	[listPopOver release];
	[listFilterPopOver release];
	[pageView release];
	[myCustomFont release];
    [popoverController release];
    [toolbar release];
    
    [detailItem release];
    [detailDescriptionLabel release];
    [super dealloc];
}

@end
