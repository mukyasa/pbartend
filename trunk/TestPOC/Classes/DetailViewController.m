//
//  DetailViewController.m
//  TestPOC
//
//  Created by Darren Mason on 8/19/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import "DetailViewController.h"
#import "RootViewController.h"
#import "FilterViewController.h"
#import "EditDrinkViewController.h"
#import "PageViewController.h"


@interface DetailViewController ()
@property (nonatomic, retain) UIPopoverController *popoverController;
- (void)configureView;
@end



@implementation DetailViewController

@synthesize toolbar, popoverController, detailItem, detailDescriptionLabel;
@synthesize listPopOver,listFilterPopOver,currentPopover,pageView;

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
	self.popoverController.popoverContentSize=CGSizeMake(420, 600);
    self.popoverController = pc;
	//show button
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



#pragma mark -
#pragma mark Rotation support

// Ensure that the view controller supports rotation and that the split view can therefore show in both portrait and landscape.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    return YES;
}


#pragma mark -
#pragma mark View lifecycle

-(void)willRotateToInterfaceOrientation:(UIInterfaceOrientation)toInterfaceOrientation duration:(NSTimeInterval)duration
{
	/*
	for (UIView *view in pageView.subviews) {
		[view removeFromSuperview];
	}
	//Y Starting point
	int posY=65;
	
	//add paper
	UIImageView *pageImage = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 576, 700)];
	pageImage.image = [UIImage imageNamed:@"paper.png"];
	[pageView addSubview:pageImage];
	[pageImage release]; 
	
	//start adding labels
	UIFont *myCustomFont = [UIFont fontWithName:@"Diego" size:24];
	
	//add title
	UILabel *title = [[UILabel alloc]initWithFrame:CGRectMake(100, posY, 200, 50)];
	title.backgroundColor = [UIColor clearColor];
	[title setFont:myCustomFont];
	title.autoresizingMask=UIViewAutoresizingNone;
	[title setText:@"This is a drink name"];
	[pageView addSubview:title];
	
	//add ingredients
	posY+=50;
	UILabel *ing;
	int posYMov=20;
	for(int i=1;i<5;i++)
	{
		
		ing= [[UILabel alloc]initWithFrame:CGRectMake(100, (posY+posYMov), 200, 20)];
		ing.backgroundColor = [UIColor clearColor];
		NSString *ingString = [NSString stringWithFormat:@"%i 1/2 oz 7-up",i];
		[ing setText:ingString];
		[pageView addSubview:ing];
		[ing release];
		posYMov+=50;
	}
		
	[title release];
	*/
	
}

 // Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
		
	pageView = [[PageViewController alloc] initWithNibName:@"PageViewController" bundle:nil];
	pageView.view.bounds = CGRectMake(112, 20, 579, 700);
	pageView.view.center = CGPointMake(512, 400);
	
	[self.view addSubview:pageView.view];

	
    [super viewDidLoad];
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
	[listFilterPopOver release];
	[pageView release];
	[listPopOver release];
    [popoverController release];
    [toolbar release];
    
    [detailItem release];
    [detailDescriptionLabel release];
    [super dealloc];
}

@end
