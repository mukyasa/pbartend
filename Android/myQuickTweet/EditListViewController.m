//
//  EditListViewController.m
//  InstaTwit
//
//  Created by Darren Mason on 6/29/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import "EditListViewController.h"

NSString * const nothingIndicator = @"-----------";

@implementation EditListViewController
@synthesize delegate,verbButton,nounButton,newIm,newFeeling,deleteButton,tweetPicker;

#pragma mark -
#pragma mark - UIActionSheetDelegate

- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex
{
    // the user clicked one of the OK/Cancel buttons
    if (buttonIndex == 0) //ok
    {
        //delete selected items from picker
		NSString *imString = [imArray objectAtIndex:[tweetPicker selectedRowInComponent:0]];
		NSString *feelingString = [feelingArray objectAtIndex:[tweetPicker selectedRowInComponent:1]];
		
		if(![imString isEqualToString:nothingIndicator])
		{
			//remove the item in the list and reload
			
			[imArray removeObjectAtIndex:[tweetPicker selectedRowInComponent:0]];
			[imArray removeObjectAtIndex:0];//remove that -------- before we save back
			//sort array before saving
			NSArray *sortedArray = [imArray sortedArrayUsingSelector:@selector(caseInsensitiveCompare:)];
			
			NSString *documentDirectory = [self applicationDocumentsDirectory];
			NSString *verbsPath = [documentDirectory stringByAppendingPathComponent:@"Verbs.plist"];
			
			[sortedArray writeToFile:verbsPath atomically:YES];
		}
		
		if(![feelingString isEqualToString:nothingIndicator])
		{
			//remove the item in the list and reload
			[feelingArray removeObjectAtIndex:[tweetPicker selectedRowInComponent:1]];
			[feelingArray removeObjectAtIndex:0];//remove that -------- before we save back
			
			//sort array before saving
			NSArray *sortedArray = [feelingArray sortedArrayUsingSelector:@selector(caseInsensitiveCompare:)];
			
			NSString *documentDirectory = [self applicationDocumentsDirectory];
			NSString *nounsPath = [documentDirectory stringByAppendingPathComponent:@"Nouns.plist"];
			
			[sortedArray writeToFile:nounsPath atomically:YES];
			
		}
		
		[self loadArrays]; //reload from bundles
		[tweetPicker reloadAllComponents];//refresh picker
    }
}

-(void) deleteFromPicker{

	NSMutableString *deleteMessage = [[NSMutableString alloc] initWithString: @"You are deleting"];
	NSString *imString = [imArray objectAtIndex:[tweetPicker selectedRowInComponent:0]];
	NSString *feelingString = [feelingArray objectAtIndex:[tweetPicker selectedRowInComponent:1]];
	
	if(![imString isEqualToString:nothingIndicator])
		[deleteMessage appendFormat:@" %@",imString];
	
	if(![feelingString isEqualToString:nothingIndicator] && ![imString isEqualToString:nothingIndicator])
		[deleteMessage appendFormat:@" and"];
	
	if(![feelingString isEqualToString:nothingIndicator])
		[deleteMessage appendFormat:@" %@",feelingString];
	
	[deleteMessage appendFormat:@"?"];
	
	UIActionSheet *actionSheet = [[UIActionSheet alloc] initWithTitle:deleteMessage delegate:self cancelButtonTitle:@"Cancel" destructiveButtonTitle:@"OK" otherButtonTitles:nil];
    actionSheet.actionSheetStyle = UIActionSheetStyleDefault;

	
	if(![feelingString isEqualToString:nothingIndicator]  ||![imString isEqualToString:nothingIndicator] )
		[actionSheet showInView:self.view];	

    [actionSheet release];

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
	
	
	
	feelingArray = [[NSMutableArray alloc] initWithContentsOfFile:writableNounsPath];
	[feelingArray insertObject:nothingIndicator atIndex:0];
	imArray = [[NSMutableArray alloc] initWithContentsOfFile:writableVerbsPath];
	[imArray insertObject:nothingIndicator atIndex:0];
	
	//NSLog(@"Loading arrays");
	
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
	
	switch (component) {
		case 0:
			return [imArray objectAtIndex:row];
			
		case 1:
			return [feelingArray objectAtIndex:row];
	}	
	return nil;
	
	
}

-(IBAction)textFieldDoneEditing:(id)sender{
	[sender resignFirstResponder];
}

- (IBAction)done:(id)sender {
	[self.delegate editListViewControllerDidFinish:self];	
}

- (IBAction)writeToVerbPlist:(id)sender
{
	
	if(![[newIm text] isEqualToString:@""])
	{
	NSString *documentDirectory = [self applicationDocumentsDirectory];
	NSString *verbsPath = [documentDirectory stringByAppendingPathComponent:@"Verbs.plist"];
	NSMutableArray *tmpVerbs = [[NSMutableArray alloc]initWithContentsOfFile:verbsPath];
	
	
	[tmpVerbs addObject:[newIm text]];
	//sort array before saving
	NSArray *sortedArray = [tmpVerbs sortedArrayUsingSelector:@selector(caseInsensitiveCompare:)];
	
	[sortedArray writeToFile:verbsPath atomically:YES];
	
	newIm.text =@"";
	[self loadArrays]; //reload from bundles
	[tweetPicker reloadAllComponents];//refresh picker
	}
		
}

- (NSString *)applicationDocumentsDirectory {
	return [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) lastObject];
}

- (IBAction)writeToNounPlist:(id)sender
{
	if(![[newFeeling text] isEqualToString:@""])
	{
	
	NSString *documentDirectory = [self applicationDocumentsDirectory];
	NSString *nounsPath = [documentDirectory stringByAppendingPathComponent:@"Nouns.plist"];
	NSMutableArray *tmpNouns = [[NSMutableArray alloc]initWithContentsOfFile:nounsPath];
	
	[tmpNouns addObject:[newFeeling text]];
	//sort array before saving
	NSArray *sortedArray = [tmpNouns sortedArrayUsingSelector:@selector(caseInsensitiveCompare:)];
	
	[sortedArray writeToFile:nounsPath atomically:YES];
	
	
	newFeeling.text =@"";
	[self loadArrays]; //reload from bundles
	[tweetPicker reloadAllComponents];//refresh picker
	}
	
}

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
	[self loadArrays];
}


/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

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
	[tweetPicker release];
	[imArray release];
	[feelingArray release];
	[verbButton release];
	[nounButton release];
    [super dealloc];
}


@end
