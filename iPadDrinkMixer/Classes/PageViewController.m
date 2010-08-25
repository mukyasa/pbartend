    //
//  PageViewController.m
//  fliptest
//
//  Created by Darren Mason on 8/23/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import "PageViewController.h"
#import "EditDrinkViewController.h"
#import "DrinkViewController.h"

@implementation PageViewController
@synthesize drinkViewController,editSaveButton;
@synthesize editDrinkViewController;
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
    [super viewDidLoad];
	
	
	DrinkViewController *drinkController = [[DrinkViewController alloc] initWithNibName:@"DrinkViewController" bundle:nil];
	viewFrame = CGRectMake(0, 0, 550, 800);//set up viewframe size by default
	self.drinkViewController = drinkController;
	[self.view insertSubview:drinkController.view atIndex:0];
	
	[drinkController release];
	
	[editSaveButton	setTitle:@"Edit" forState:UIControlStateNormal];
}

-(void)flipview{
		
	[UIView beginAnimations:@"View Flip" context:nil];
    [UIView setAnimationDuration:.5];
    [UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
    
    if (self.editDrinkViewController.view.superview == nil)
    {
		
        if (self.editDrinkViewController == nil)
        {
            EditDrinkViewController *editController = 
            [[EditDrinkViewController alloc] initWithNibName:@"EditDrinkViewController" bundle:nil];
			[editController.view setFrame:viewFrame];
            self.editDrinkViewController = editController;
            [editController release];
			
        }
		
        [UIView setAnimationTransition:
		UIViewAnimationTransitionFlipFromRight forView:self.view cache:YES];
        
        [drinkViewController viewWillAppear:YES];
        [editDrinkViewController viewWillDisappear:YES];
        [drinkViewController.view removeFromSuperview];
        [self.view insertSubview:editDrinkViewController.view atIndex:0];
        [editDrinkViewController viewDidDisappear:YES];
        [drinkViewController viewDidAppear:YES];
    }
    else
    {
        if (self.drinkViewController == nil)
        {
            DrinkViewController *drinkController = 
            [[DrinkViewController alloc] initWithNibName:@"DrinkViewController" bundle:nil];
			[drinkController.view setFrame:viewFrame];
            self.drinkViewController = drinkController;
            [drinkController release];
			
        }

        [UIView setAnimationTransition:
		UIViewAnimationTransitionFlipFromLeft forView:self.view cache:YES];
        
        [editDrinkViewController viewWillAppear:YES];
        [drinkViewController viewWillDisappear:YES];
        [editDrinkViewController.view removeFromSuperview];
        [self.view insertSubview:drinkViewController.view atIndex:0];
        [drinkViewController viewDidDisappear:YES];
        [editDrinkViewController viewDidAppear:YES];
    }
    [UIView commitAnimations];

}

- (void)didRotateFromInterfaceOrientation:(UIInterfaceOrientation)fromInterfaceOrientation{

	NSLog(@"rotated");
	if(fromInterfaceOrientation == UIInterfaceOrientationLandscapeLeft || fromInterfaceOrientation == UIInterfaceOrientationLandscapeRight)
	{
		viewFrame = CGRectMake(0, 0, 450, 630);
		[drinkViewController.view setFrame:viewFrame];
		[editDrinkViewController.view setFrame:viewFrame];
		
	}
	else //portrait
	{
		viewFrame = CGRectMake(0, 0, 550, 800);
		[drinkViewController.view setFrame:viewFrame];
		[editDrinkViewController.view setFrame:viewFrame];
	}
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
	[editSaveButton release];
	[editDrinkViewController release];
	[drinkViewController release];
    [super dealloc];
}


@end
