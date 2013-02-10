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

@synthesize magizineScrollView,pickedCover,coverShotEditorViewController,categoryPicker,catButton,basicBackground;

const CGFloat kScrollObjWidth	= 240;
const CGFloat kScrollObjHeight	= 360;

//shows the picker
-(IBAction)pickCategory:(id)sender{
    if([catButton.titleLabel.text isEqual: @"Filter"]){
        [self moveCatPickerOnScreen];
    } else {
        [self moveCatPickerOffScreen];
    }
}
//filters the categories
-(IBAction)filterCategories:(id)sender{
    [self moveCatPickerOffScreen];
}

-(void)moveCatPickerOnScreen{
    categoryPicker.hidden=NO;
    [catButton setTitle:@"Close Filter" forState:UIControlStateNormal];
	CGRect thePicker = categoryPicker.frame;
	thePicker.origin.y = self.view.frame.size.height-thePicker.size.height;
	
	[UIView beginAnimations:@"pickerViewShow" context:NULL];
	[UIView setAnimationDuration:.5];
	self.categoryPicker.frame = thePicker;
	[UIView commitAnimations];

}

-(void)moveCatPickerOffScreen{
    
    CGFloat viewHeight = self.view.frame.size.height;
	CGRect thePicker = self.categoryPicker.frame;
	thePicker.origin.y = viewHeight;
	[catButton setTitle:@"Filter" forState:UIControlStateNormal];
	[UIView beginAnimations:@"pickerViewShow" context:NULL];
	[UIView setAnimationDuration:.3];
    self.categoryPicker.frame = thePicker;
	[UIView commitAnimations];
}


- (void)rateApp {
    
    int launchCount = [[NSUserDefaults standardUserDefaults] integerForKey:@"launchCount"];
    launchCount++;
    [[NSUserDefaults standardUserDefaults] setInteger:launchCount forKey:@"launchCount"];
    
    BOOL neverRate = [[NSUserDefaults standardUserDefaults] boolForKey:@"neverRate"];
    
    if ((neverRate != YES) && (launchCount > 2)) {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Please rate Cover Shot!"
                                                        message:@"If you enjoyed using Cover Shot and you would like others to enjoy it, please rate it!" 
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
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"http://itunes.apple.com/us/app/cover-shot-fake-magazine-cover/id387310371?ls=1&mt=8"]];
    }
    
    else if (buttonIndex == 1) {
        [[NSUserDefaults standardUserDefaults] setBool:YES forKey:@"neverRate"];
    }
    
    else if (buttonIndex == 2) {
        // Do nothing
    }
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
	//[controller dismissModalViewControllerAnimated:YES];
    [controller dismissViewControllerAnimated:YES completion:nil];

}

// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
	
    if ([[UIScreen mainScreen] bounds].size.height == 568){ //iphone 5
        [basicBackground setImage:[UIImage imageNamed:@"maghome-568h@2x.png"]];
    } else{
        [basicBackground setImage:[UIImage imageNamed:@"maghome.png"]];
    }
    
    categoryPicker.hidden=YES;
	[self moveCatPickerOffScreen];
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
	
    //load all the covers
    [self loadAll];
	
	
	// do one time set-up of gesture recognizers
	UIGestureRecognizer *recognizer = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(handleDoubleTapFrom:)];
	recognizer.delegate = self;
	((UITapGestureRecognizer*)recognizer).numberOfTapsRequired=2;
	[magizineScrollView addGestureRecognizer:recognizer];
	[recognizer release];
	
    [super viewDidLoad];
}

//called in a loop loads each cover
-(void)loadEachCover:(int) i{
    NSString *imageName = [NSString stringWithFormat:@"cover%d.png", i];
    UIImage *image = [UIImage imageNamed:imageName];
    UIImageView *imageView = [[UIImageView alloc] initWithImage:image];
    [imageView setTag:i];
    
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

-(void)loadAll{
    //clean out subviews first
    [magizineScrollView.subviews makeObjectsPerformSelector:@selector(removeFromSuperview)];
    // load all the images from our bundle and add them to the scroll view
	NSUInteger i;
    NSUInteger kNumImages = 33;
	for (i = 1; i <= kNumImages; i++)
	{
		[self loadEachCover:i];
	}
	pickedCover = [UIImage imageNamed:@"clearcover1.png"];
	[self layoutScrollImages:kNumImages];	// now place the photos in serial layout within the scrollview
	
}
-(void)loadKids{
    
    NSArray *coversArray = [NSArray arrayWithObjects:
                         @"10",
                         @"27",
                         nil];
        //clean out subviews first
    [magizineScrollView.subviews makeObjectsPerformSelector:@selector(removeFromSuperview)];
    
    for(NSString *coverId in coversArray) {
        int i = [coverId intValue];
    
       [self loadEachCover:i];
    
    }
    pickedCover = [UIImage imageNamed:@"clearcover10.png"];
    [self layoutScrollImages:coversArray.count];
}
-(void)loadMens{
    //clean out subviews first
    [magizineScrollView.subviews makeObjectsPerformSelector:@selector(removeFromSuperview)];
    NSArray *coversArray = [NSArray arrayWithObjects:
                            @"14",
                            @"15",
                            @"2",
                            @"21",
                            @"3",
                            @"8",
                            nil];
	for(NSString *coverId in coversArray) {
        int i = [coverId intValue];
        
        [self loadEachCover:i];
        
    }
	pickedCover = [UIImage imageNamed:@"clearcover14.png"];
	[self layoutScrollImages:coversArray.count];	// now place the photos in serial layout within the scrollview
	
}
-(void)loadWomens{
    //clean out subviews first
    [magizineScrollView.subviews makeObjectsPerformSelector:@selector(removeFromSuperview)];
    NSArray *coversArray = [NSArray arrayWithObjects:
                            @"1",
                            @"12",
                            @"16",
                            @"17",
                            @"18",
                            @"19",
                            @"22",
                            @"4",
                            @"5",
                            @"7",
                            nil];
	for(NSString *coverId in coversArray) {
        int i = [coverId intValue];
        
        [self loadEachCover:i];
        
    }
	pickedCover = [UIImage imageNamed:@"clearcover1.png"];
	[self layoutScrollImages:coversArray.count];	// now place the photos in serial layout within the scrollview

}
-(void)loadPets{
    //clean out subviews first
    [magizineScrollView.subviews makeObjectsPerformSelector:@selector(removeFromSuperview)];
    NSArray *coversArray = [NSArray arrayWithObjects:
                            @"25",
                            @"26",
                            @"30",
                            @"31",
                            @"34",
                            nil];
	for(NSString *coverId in coversArray) {
        int i = [coverId intValue];
        
        [self loadEachCover:i];
        
    }
	pickedCover = [UIImage imageNamed:@"clearcover25.png"];
	[self layoutScrollImages:coversArray.count];	// now place the photos in serial layout within the scrollview

}
-(void)loadOther{
    //clean out subviews first
    [magizineScrollView.subviews makeObjectsPerformSelector:@selector(removeFromSuperview)];
    NSArray *coversArray = [NSArray arrayWithObjects:
                            @"11",
                            @"13",
                            @"20",
                            @"23",
                            @"24",
                            @"28",
                            @"29",
                            @"32",
                            @"6",
                            @"9",
                            nil];
	for(NSString *coverId in coversArray) {
        int i = [coverId intValue];
        
        [self loadEachCover:i];
        
    }
	pickedCover = [UIImage imageNamed:@"clearcover11.png"];
	[self layoutScrollImages:coversArray.count];	// now place the photos in serial layout within the scrollview

}


- (void)handleDoubleTapFrom:(UITapGestureRecognizer *)recognizer
{
	if (recognizer.state == UIGestureRecognizerStateEnded) {
		//NSLog(@"Tap");
		coverShotEditorViewController = [[CoverShotEditorViewController alloc] initWithNibName:@"CoverShotEditorViewController" bundle:nil];
		coverShotEditorViewController.delegate = self;
		
		coverShotEditorViewController.modalTransitionStyle = UIModalTransitionStyleCoverVertical;
		//[self presentModalViewController:coverShotEditorViewController animated:YES];
        [self presentViewController:coverShotEditorViewController animated:YES completion:nil];
		
		coverShotEditorViewController.parentPreviewImageView.image = pickedCover;
		CoverShotAppDelegate *appDelegate = (CoverShotAppDelegate *)[[UIApplication sharedApplication] delegate];
		
		appDelegate.coverHolder =  [[UIImageView alloc] initWithImage:pickedCover] ;
	
		//NSLog(@"CoverShotEditorViewController retain count: %i",[coverShotEditorViewController retainCount]);
		//[coverShotEditorViewController release];
	}

}


- (void)scrollViewDidScroll:(UIScrollView *)scrollView{
	
	CGFloat pageWidth = magizineScrollView.frame.size.width; 
	int page = ((scrollView.contentOffset.x / pageWidth) +1);
    
    if(page > 0){
        page = (page-1);
    }
    
    //get subview index then get the tag of that subview for the full cover
    UIImageView *coverSelected = [scrollView.subviews objectAtIndex:page];
	pickedCover = [UIImage imageNamed:[NSString stringWithFormat:@"clearcover%i.png",coverSelected.tag] ];
	

	
	//NSLog(@"SCROLL OFFSET: %d",page);
	
	
}
-(NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView
{
	return 1;
}

- (NSString *)pickerView:(UIPickerView *)pickerView
             titleForRow:(NSInteger)row
            forComponent:(NSInteger)component
{
    return [[self categories] objectAtIndex:row];
}

//did select category
- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component
{
   NSString *selected = [[self categories] objectAtIndex:row];
    NSLog(@"Picked Item %@",selected);
    if([selected isEqual: @"All"]){
        [self loadAll];
    }
    else if([selected isEqual: @"Kids"]){
        [self loadKids];
    }
    else if([selected isEqual: @"Mens"]){
        [self loadMens];
    }
    else if([selected isEqual: @"Other"]){
        [self loadOther];
    }
    else if([selected isEqual: @"Pets"]){
        [self loadPets];
    }
    else if([selected isEqual: @"Womens"]){
        [self loadWomens];
    }

}

-(NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component
{
	NSInteger numComps = 0;
	switch(component)
	{
		case 0:
			numComps = [self.categories count];
			break;
	}
	return numComps;
}

-(NSArray*)categories
{

    NSArray *catArray = [NSArray arrayWithObjects:
                            @"All",
								  @"Pets",
                                  @"Kids",
                                  @"Mens",
                                  @"Other",
                                  @"Womens",
                                  nil];

    return catArray;
}

- (void)layoutScrollImages:(int)imageCount
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
	[magizineScrollView setContentSize:CGSizeMake((imageCount * kScrollObjWidth), [magizineScrollView bounds].size.height)];
}


/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/



- (void)flipsideViewControllerDidFinish:(FlipsideViewController *)controller {
    
	//[self dismissModalViewControllerAnimated:YES];
    [self dismissViewControllerAnimated:YES completion:nil];
}


- (IBAction)showInfo:(id)sender {    
	
	FlipsideViewController *controller = [[FlipsideViewController alloc] initWithNibName:@"FlipsideView" bundle:nil];
	controller.delegate = self;
	
	controller.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;
	//[self presentModalViewController:controller animated:YES];
    [self presentViewController:controller animated:YES completion:nil];
	//NSLog(@"FlipsideViewController retain count: %i",[controller retainCount]);
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


- (void)dealloc {
	[coverShotEditorViewController release];
    [basicBackground release];
    [catButton release];
    [categoryPicker release];
	[pickedCover release];
	[magizineScrollView release];
    [super dealloc];
}

@end
