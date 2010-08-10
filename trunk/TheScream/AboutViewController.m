    //
//  AboutViewController.m
//  TheScream
//
//  Created by Darren Mason on 6/13/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import "AboutViewController.h"



@implementation AboutViewController

@synthesize delegate;



- (IBAction)done:(id)sender {
	[self.delegate aboutViewControllerDidFinish:self];	
}

- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

- (void)viewDidLoad {
	NSString*	version = [[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleVersion"];
	NSString*	appname = [[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleName"];
	
	arryCompany = [[NSArray alloc] initWithObjects:appname,version,@"mypocket technologies",@"mypocket-technologies.com",nil];
	arryCompanyLabels = [[NSArray alloc] initWithObjects:@"Name:",@"Version:",@"Company",@"Website:",nil];
	
	arryPhoto = [[NSArray alloc] initWithObjects:@"sparktography",@"The Scream",nil];
	arryPhotoLabels = [[NSArray alloc] initWithObjects:@"Owner:",@"Name:",nil];
    [super viewDidLoad];
}


- (void)dealloc {
	[arryCompany dealloc];
	[arryCompanyLabels dealloc];
	[arryPhoto dealloc];
	[arryPhotoLabels dealloc];
    [super dealloc];
}


#pragma mark Table view methods

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section{
	if(section == 0)
		return @"Company Information";
	else
		return @"Photo Information";
}


- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 2;
}


// Customize the number of rows in the table view.
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
	if(section ==0)
		return [arryCompany count];
	else
		return [arryPhoto count];
}


// Customize the appearance of table view cells.
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *CellIdentifier = @"Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
		cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue2 reuseIdentifier:CellIdentifier] autorelease];
		

    }
    
    // Set up the cell...
	if(indexPath.section == 0)
	{
		[cell.textLabel setText: [arryCompanyLabels objectAtIndex:indexPath.row]];
		[cell.detailTextLabel setText:[arryCompany objectAtIndex:indexPath.row]];
	}
	else 
	{
		[cell.textLabel setText:[arryPhotoLabels objectAtIndex:indexPath.row]];
		[cell.detailTextLabel setText:[arryPhoto objectAtIndex:indexPath.row]];
	}
	

	
    return cell;
}



- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
	
	NSURL *myURL = [NSURL URLWithString:@"http://www.mypocket-technologies.com"];
	
	if(indexPath.row == 3)
	{
		[[UIApplication sharedApplication] openURL:myURL];
	}

	[tableView deselectRowAtIndexPath:indexPath animated:YES];
	


}



@end
