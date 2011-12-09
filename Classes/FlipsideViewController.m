//
//  FlipsideViewController.m
//  TheScream
//
//  Created by Darren Mason on 6/13/10.
//  Copyright TGen 2010. All rights reserved.
//

#import "FlipsideViewController.h"
#import <AudioToolbox/AudioServices.h>

@implementation FlipsideViewController

@synthesize delegate;


-(IBAction)shutUp:(id)sender{
	
	NSString *path = [NSString stringWithFormat:@"%@%@",[[NSBundle mainBundle]resourcePath],@"/screamshutup.wav"];
	
	SystemSoundID soundId;
	
	NSURL *filePath = [NSURL fileURLWithPath:path isDirectory:NO];
	
	AudioServicesCreateSystemSoundID((CFURLRef) filePath, &soundId);
	
	AudioServicesPlaySystemSound(soundId);
}

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor viewFlipsideBackgroundColor];      
}


- (IBAction)done:(id)sender {
	[self.delegate flipsideViewControllerDidFinish:self];	
}

- (void)aboutViewControllerDidFinish:(AboutViewController *)controller {
    
	[self dismissModalViewControllerAnimated:YES];
}

- (IBAction)showInfo:(id)sender {    
	
	AboutViewController *controller = [[AboutViewController alloc] initWithNibName:@"AboutView" bundle:nil];
	controller.delegate = self;
	
	controller.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;
	[self presentModalViewController:controller animated:YES];
	
	[controller release];
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


/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
	// Return YES for supported orientations
	return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/


- (void)dealloc {
    [super dealloc];
}


@end
