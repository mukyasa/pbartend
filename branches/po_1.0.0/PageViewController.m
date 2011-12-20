    //
//  PageViewController.m
//  TestPOC
//
//  Created by Darren Mason on 8/22/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import "PageViewController.h"


@implementation PageViewController
@synthesize edit;

/*
 // The designated initializer.  Override if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if ((self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil])) {
        // Custom initialization
    }
    return self;
}
*/

/*
// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
}
*/

- (void)flipsideViewControllerDidFinish:(EditDrinkViewController *)controller {
    
	[self dismissModalViewControllerAnimated:YES];
	//landscape
	if(controller.interfaceOrientation == UIInterfaceOrientationLandscapeLeft || controller.interfaceOrientation == UIInterfaceOrientationLandscapeRight)
	{
		self.view.frame= CGRectMake(200, 90,500, 860);
	}
	else//portrait
	{
		self.view.frame= CGRectMake(130, 90, 600,830);	
	}
}

-(IBAction)editDrink:(id)sender{
	
	
	EditDrinkViewController *controller = [[EditDrinkViewController alloc] initWithNibName:@"EditDrinkViewController" bundle:nil];
	controller.delegate = self;
	
	controller.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;
	[self presentModalViewController:controller animated:YES];
	

	//rotate -90 degrees if turned	
	if(controller.interfaceOrientation == UIInterfaceOrientationLandscapeLeft || controller.interfaceOrientation == UIInterfaceOrientationLandscapeRight)
	{
		controller.view.frame= CGRectMake(200, 90,500, 860);
		
		controller.view.transform=CGAffineTransformMakeRotation(M_PI);
		controller.view.transform = CGAffineTransformScale(controller.view.transform,1.0, -1.0);
		controller.view.transform = CGAffineTransformScale(controller.view.transform,-1.0, 1.0);
	}	
	else {
		controller.view.frame= CGRectMake(130, 90, 600,830);
		controller.view.transform=CGAffineTransformMakeRotation(M_PI);
		controller.view.transform = CGAffineTransformScale(controller.view.transform,1.0, -1.0);
		controller.view.transform = CGAffineTransformScale(controller.view.transform,-1.0, 1.0);
	}
	
	[controller release];
	 
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Overriden to allow any orientation.
    return YES;
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
	[edit release];
    [super dealloc];
}


@end
