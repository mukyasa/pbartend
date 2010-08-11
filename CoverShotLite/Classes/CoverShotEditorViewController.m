//
//  CoverShotEditorViewController.m
//  CoverShot
//
//  Created by Darren Mason on 7/21/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import "CoverShotEditorViewController.h"
#import "QuartzBlending.h"


@implementation CoverShotEditorViewController

static NSString *blendModes[] = {
	@"Normal",
	@"Multiply",
	@"Screen",
	@"Overlay",
	@"Darken",
	@"Lighten",
	@"ColorDodge",
	@"ColorBurn",
	@"SoftLight",
	@"HardLight",
	@"Difference",
	@"Exclusion",
	@"Hue",
	@"Saturation",
	@"Color",
	@"Luminosity"
};
static NSInteger blendModeCount = sizeof(blendModes) / sizeof(blendModes[0]);

@synthesize previewImageView,parentPreviewView,parentPreviewImageView,delegate;
@synthesize imageCopy,mainToolBar,photoButton,saveButton,blendButton,gradientPicker,pickerView;


-(IBAction)showPictureControls:(id)sender  {
	isSaving=NO;
    [self movePickerOffScreen];//hide picker
	//determine if there is a camera
	
	BOOL isCamera = [UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerCameraCaptureModePhoto];

	UIActionSheet *actionSheet;
	
	if(isCamera)
		actionSheet = [[UIActionSheet alloc] initWithTitle:@"Choose your photo source." delegate:self cancelButtonTitle:@"Cancel" destructiveButtonTitle:nil otherButtonTitles:@"Camera",@"Camera w/Overlay", @"Photo Library", nil];
	else
		actionSheet = [[UIActionSheet alloc] initWithTitle:@"Choose your photo source." delegate:self cancelButtonTitle:@"Cancel" destructiveButtonTitle:nil otherButtonTitles:@"Photo Library", nil];
		
    actionSheet.actionSheetStyle = UIBarStyleBlackTranslucent;
	
	[actionSheet showInView:self.parentPreviewView];	
	
	[actionSheet release];
	
	
}



- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex
{
	if(isSaving)
	{
		if (buttonIndex == 0) //camera
			[self doSave];		
		
	}else{
		// the user clicked one of the OK/Cancel buttons
		if (buttonIndex == 0) //camera
		{
	
			UIImagePickerController *camera = [[UIImagePickerController alloc] init];
			camera.sourceType = UIImagePickerControllerSourceTypeCamera;	
			camera.delegate = self;
			
			[self presentModalViewController:camera animated:YES];		
			
		}
		if (buttonIndex == 1) //camera with over lay
		{
			
			UIImage *image = parentPreviewImageView.image;
			UIImageView *imgView = [[UIImageView alloc] initWithImage:image] ;
			imgView.contentMode = UIViewContentModeScaleAspectFill;
			imgView.alpha=.5;
			imgView.frame = CGRectMake(0,0,320,480);
			
			UIImagePickerController *camera = [[UIImagePickerController alloc] init];
			camera.sourceType = UIImagePickerControllerSourceTypeCamera;	
			camera.delegate = self;
			camera.cameraOverlayView = imgView;
			
			[self presentModalViewController:camera animated:YES];
			[imgView release];			
			
		}
		else if(buttonIndex ==2) {
			UIImagePickerController *imagePicker = [[UIImagePickerController alloc] init];
			imagePicker = [[UIImagePickerController alloc] init];
			imagePicker.delegate = self;	
			imagePicker.sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
			[self presentModalViewController:imagePicker animated:YES];
			
			
		}		
	}
	
	
	
}

/* the image after taken or picked */
- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info {
	[picker dismissModalViewControllerAnimated:YES];
	previewImageView.image = [info objectForKey:@"UIImagePickerControllerOriginalImage"];
	
	
	[self applyDefaults];
	
	QuartzBlendingView *qbv = (QuartzBlendingView*)self.quartzView;
	qbv.clearsContextBeforeDrawing=YES;
	
	qbv.choosenImage=nil;
	qbv.sourceColor= [UIColor clearColor];
	qbv.blendMode = kCGBlendModeNormal;
	quartzView.hidden=YES;
	
	[picker release];
	

}


- (void)imageSavedToPhotosAlbum:(UIImage *)image didFinishSavingWithError:(NSError *)error contextInfo:(void *)contextInfo {
	
	
	NSString *message;
	NSString *title;
	if (!error) {
		title = NSLocalizedString(@"Picture Saved", @"");
		message = NSLocalizedString(@"Your picture was saved in your photo library.", @"");
	} else {
		title = NSLocalizedString(@"Picture Saved", @"");
		message = [error description];
	}
	UIAlertView *alert = [[UIAlertView alloc] initWithTitle:title
													message:message
												   delegate:nil
										  cancelButtonTitle:NSLocalizedString(@"Ok", @"")
										  otherButtonTitles:nil];
	[alert show];
	[alert release];
}

-(IBAction)saveMagCover:(id)sender{
	
	[self moveNavViewOffscreen];
    [self movePickerOffScreen];//hide picker
	
	isSaving=YES;
	UIActionSheet *actionSheet = [[UIActionSheet alloc] initWithTitle:@"Save your photo?" delegate:self cancelButtonTitle:@"Cancel" destructiveButtonTitle:@"Save" otherButtonTitles:nil];
    actionSheet.actionSheetStyle = UIBarStyleBlackTranslucent;
	
	[actionSheet showInView:self.parentPreviewView];	
	
	[actionSheet release];
}

-(void)doSave{
	
	
	UIGraphicsBeginImageContext(parentPreviewImageView.frame.size);
	
	[self.parentPreviewView.layer renderInContext:UIGraphicsGetCurrentContext()];
	
	imageCopy = UIGraphicsGetImageFromCurrentImageContext();
	
	UIGraphicsEndImageContext();
	
	UIImageWriteToSavedPhotosAlbum(imageCopy, self, nil, nil);	
	[self moveNavViewOnscreen];//show main nav
}


-(QuartzView*)quartzView
{
	return quartzView;
}


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {

	didFlip=NO;
	[self moveNavViewOnscreen];
	pickerView.hidden = YES;//hide so we dont see it going off screen
	[self movePickerOffScreen];
	
	// clip sub-layer contents
	parentPreviewView.layer.masksToBounds = YES;
	
	// do one time set-up of gesture recognizers
	UIGestureRecognizer *recognizer;
	
	
	recognizer = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(handleSingleTapFrom:)];
	recognizer.delegate = self;
	[parentPreviewView addGestureRecognizer:recognizer];
	[recognizer release];
	
	UITapGestureRecognizer *doubleTaprecognizer = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(handleDoubleTapFrom:)];
	doubleTaprecognizer.numberOfTapsRequired=2;
	doubleTaprecognizer.delegate = self;
	[parentPreviewView addGestureRecognizer:doubleTaprecognizer];
	[doubleTaprecognizer release];
	
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
	
		//NSLog(@"single FLIPPED: %i",didFlip);
		//[self moveNavViewOnscreen];//show main nav
		[self movePickerOffScreen];//hide picker
		//[self setUpImageState:NO];
	
}

- (void)handleDoubleTapFrom:(UITapGestureRecognizer *)recognizer
{
	
	[self movePickerOffScreen];//hide picker
	
	if (recognizer.state == UIGestureRecognizerStateBegan) 
	{
		quartzView.hidden=YES;
		
	}
	else if(recognizer.state == UIGestureRecognizerStateEnded)
	{
		//flip
		if(!didFlip){
			//NSLog(@"FLIPPED: %i",didFlip);
			previewImageView.transform = CGAffineTransformScale(previewImageView.transform,-1.0, 1.0);
			didFlip=YES;
		}
		else {	
			//NSLog(@"FLIPPED BACK: %i",didFlip);
			previewImageView.transform = CGAffineTransformScale(previewImageView.transform,-1.0, 1.0);
			didFlip=NO;
		}
		
		[self moveNavViewOnscreen];//show main nav
		[self movePickerOffScreen];//hide picker
		[self setUpImageState:NO];
	}
	
}


- (void)handlePinchFrom:(UIPinchGestureRecognizer *)recognizer
{
	//NSLog(@"Pinch");
	[self moveNavViewOffscreen];
	[self movePickerOffScreen];//hide picker
	
	
	if (recognizer.state == UIGestureRecognizerStateBegan) {
		quartzView.hidden=YES;
		beginGestureScale = 1;
	}
	else if(recognizer.state == UIGestureRecognizerStateEnded)
	{
		[self moveNavViewOnscreen];//show main nav
		[self movePickerOffScreen];//hide picker
		[self setUpImageState:NO];
	}
	
	previewImageView.transform = CGAffineTransformScale(previewImageView.transform, (recognizer.scale / beginGestureScale), (recognizer.scale / beginGestureScale));
	//quartzView.transform = previewImageView.transform;
	beginGestureScale = recognizer.scale;
	
	
}

- (void)handleDragFrom:(UIPanGestureRecognizer *)recognizer
{

	//NSLog(@"Pan");
	[self moveNavViewOffscreen];
	[self movePickerOffScreen];//hide picker
	
	if (recognizer.state == UIGestureRecognizerStateBegan) {
		quartzView.hidden=YES;
		CGPoint startPoint = [recognizer locationOfTouch:0 inView:previewImageView];
		inImage = [self point:startPoint inView:previewImageView];
		oldX = 0;
		oldY = 0;
	}
	else if(recognizer.state == UIGestureRecognizerStateEnded)
	{
		[self moveNavViewOnscreen];//show main nav
		[self movePickerOffScreen];//hide picker
		[self setUpImageState:NO];
	}
	
	if (inImage) {
		CGPoint translate = [recognizer translationInView: parentPreviewView];
		//NSLog(@"X:%f",translate.x-oldX);
		if(didFlip)
			previewImageView.transform = CGAffineTransformTranslate(previewImageView.transform, -(translate.x-oldX), (translate.y-oldY));
		else
			previewImageView.transform = CGAffineTransformTranslate(previewImageView.transform, (translate.x-oldX), (translate.y-oldY));
		//quartzView.transform = previewImageView.transform;
		oldX = translate.x;
		oldY = translate.y;
	}
	

}


-(BOOL)point:(CGPoint)p inView:(UIView *)view {
	return p.x > 0 && p.x < view.bounds.size.width && p.y > 0 && p.y < view.bounds.size.height;
}


- (void)handleRotationFrom:(UIRotationGestureRecognizer *)recognizer
{
	NSLog(@"did flip: %i",didFlip);
	[self moveNavViewOffscreen];
	[self movePickerOffScreen];//hide picker
	
	if (recognizer.state == UIGestureRecognizerStateBegan) {
		quartzView.hidden=YES;
		beginGestureRotationRadians	= 0;
	}
	else if(recognizer.state == UIGestureRecognizerStateEnded)
	{
		[self moveNavViewOnscreen];//show main nav
		[self movePickerOffScreen];//hide picker
		[self setUpImageState:NO];
	}
	if(didFlip)
		previewImageView.transform = CGAffineTransformRotate(previewImageView.transform, -(recognizer.rotation - beginGestureRotationRadians));
	else
		previewImageView.transform = CGAffineTransformRotate(previewImageView.transform, (recognizer.rotation - beginGestureRotationRadians));
	//quartzView.transform = previewImageView.transform;
	beginGestureRotationRadians = recognizer.rotation;
	
	
}

//set up shake listener
- (void)viewDidAppear:(BOOL)animated {
	
    [self becomeFirstResponder];
	
}

- (void)motionEnded:(UIEventSubtype)motion withEvent:(UIEvent *)event
{
		if ( motion == UIEventSubtypeMotionShake ) {
			[CATransaction begin];
			[self applyDefaults];
			[CATransaction commit];
		}
}

// For shake events
- (BOOL)canBecomeFirstResponder
{
    return YES;
}

- (void)handleShake
{
	[CATransaction begin];
	[self applyDefaults];
	[CATransaction commit];
}


- (void)applyDefaults
{
	//set picker to zero
	[gradientPicker selectRow:0 inComponent:0 animated:YES];
	[gradientPicker selectRow:0 inComponent:1 animated:YES];
	QuartzBlendingView *qbv = (QuartzBlendingView*)self.quartzView;
	
	beginGestureScale = 1.0;
	beginGestureRotationRadians = 0.0;
	effectiveTranslation = CGPointMake(0.0, 0.0);
	[quartzView.layer setAffineTransform:CGAffineTransformIdentity];
	[previewImageView.layer setAffineTransform:CGAffineTransformIdentity];
	previewImageView.frame = parentPreviewView.layer.bounds;
	
	[self setupQuartzBlendingView:qbv];
	
}


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

-(void)movePickerOnScreen{
	pickerView.hidden=NO;
	CGRect thePicker = pickerView.frame;
	thePicker.origin.y = self.view.frame.size.height-(thePicker.size.height + mainToolBar.frame.size.height);
	
	[UIView beginAnimations:@"pickerViewShow" context:NULL];
	[UIView setAnimationDuration:.5];
	self.pickerView.frame = thePicker;
	[UIView commitAnimations];
	
	[self setUpImageState:YES];
	
}

-(void)setUpImageState:(BOOL)isMovePicker{

	if([gradientPicker selectedRowInComponent:1]>0 || isMovePicker)
	{
		parentPreviewImageView.hidden=YES;//hide magazine
		quartzView.hidden=YES;//hide color
		
		//get screen image from screen shot
		UIGraphicsBeginImageContext(parentPreviewView.frame.size);
		
		[self.parentPreviewView.layer renderInContext:UIGraphicsGetCurrentContext()];
		
		imageCopy = UIGraphicsGetImageFromCurrentImageContext();
		
		UIGraphicsEndImageContext();
		//hide words for a sec
		parentPreviewImageView.hidden=NO;//show magazine 
		QuartzBlendingView *qbv = (QuartzBlendingView*)self.quartzView;
		qbv.choosenImage = imageCopy;
		
		[self setupQuartzBlendingView:qbv];//this will show quartz
	}
}


//sets color and visiblity options of quartz
-(void)setupQuartzBlendingView:(QuartzBlendingView*)qbv{
	
	//init color
	//if we pick normal lets clear out the quartzblendview
	if([gradientPicker selectedRowInComponent:1]==0)
		quartzView.hidden=YES;
	else 
	{
		quartzView.hidden=NO;
		qbv.sourceColor = [self.colors objectAtIndex:[gradientPicker selectedRowInComponent:0]];
		qbv.blendMode = [gradientPicker selectedRowInComponent:1];
	}
}

-(void)movePickerOffScreen{
	
	CGFloat viewHeight = self.view.frame.size.height;
	CGRect thePicker = self.pickerView.frame;
	thePicker.origin.y = viewHeight;
	
	[UIView beginAnimations:@"pickerViewShow" context:NULL];
	[UIView setAnimationDuration:.3];
	self.pickerView.frame = thePicker;
	[UIView commitAnimations];
	
	
}

- (IBAction)done:(id)sender {
	[self.delegate coverShotEditorViewControllerDidFinish:self];	
}


#pragma mark UIPickerViewDelegate & UIPickerViewDataSource methods

-(NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView
{
	return 2;
}

#define kColorTag 1
#define kLabelTag 2
-(UIView *)pickerView:(UIPickerView *)_pickerView viewForRow:(NSInteger)row forComponent:(NSInteger)component reusingView:(UIView *)view
{	
	switch (component)
	{
		case 0:
			if(view.tag != kColorTag)
			{
				CGRect frame = CGRectZero;
				frame.size = [_pickerView rowSizeForComponent:component];
				frame = CGRectInset(frame, 4.0, 4.0);
				view = [[[UIView alloc] initWithFrame:frame] autorelease];
				view.tag = kColorTag;
				view.userInteractionEnabled = NO;
			}
			view.backgroundColor = [self.colors objectAtIndex:row];
			break;
			
		case 1:
			if(view.tag != kLabelTag)
			{
				CGRect frame = CGRectZero;
				frame.size = [_pickerView rowSizeForComponent:component];
				frame = CGRectInset(frame, 4.0, 4.0);
				view = [[[UILabel alloc] initWithFrame:frame] autorelease];
				view.tag = kLabelTag;
				view.opaque = NO;
				view.backgroundColor = [UIColor clearColor];
				view.userInteractionEnabled = NO;
			}
			UILabel *label = (UILabel*)view;
			label.textColor = [UIColor blackColor];
			label.text = blendModes[row];
			label.font = [UIFont boldSystemFontOfSize:18.0];
			break;
	}
	return view;
}

-(CGFloat)pickerView:(UIPickerView *)pickerView widthForComponent:(NSInteger)component
{
	CGFloat width = 0.0;
	switch (component)
	{
		case 0:
			width = 48.0;
			break;
		case 1:
			width = 250.0;
			break;
	}
	return width;
}

-(NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component
{
	NSInteger numComps = 0;
	switch(component)
	{
		case 0:
			numComps = [self.colors count];
			break;
			
		case 1:
			numComps = blendModeCount;
			break;
	}
	return numComps;
}

-(void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component
{
	QuartzBlendingView *qbv = (QuartzBlendingView*)self.quartzView;
	[self setupQuartzBlendingView:qbv];
}

// Calculate the luminance for an arbitrary UIColor instance
CGFloat luminanceForColor(UIColor *color)
{
	CGColorRef cgColor = color.CGColor;
	const CGFloat *components = CGColorGetComponents(cgColor);
	
	CGFloat luminance = 0.0;
	
	switch(CGColorSpaceGetModel(CGColorGetColorSpace(cgColor)))
	{
		case kCGColorSpaceModelMonochrome:
			// For grayscale colors, the luminance is the color value
			luminance = components[0];
			break;
			
		case kCGColorSpaceModelRGB:
			// For RGB colors, we calculate luminance assuming sRGB Primaries as per
			// http://en.wikipedia.org/wiki/Luminance_(relative)
			luminance = 0.2126 * components[0] + 0.7152 * components[1] + 0.0722 * components[2];
			break;
			
		default:
			// We don't implement support for non-gray, non-rgb colors at this time.
			// Since our only consumer is colorSortByLuminance, we return a larger than normal
			// value to ensure that these types of colors are sorted to the end of the list.
			luminance = 2.0;
	}
	return luminance;
}

// Simple comparison function that sorts the two (presumed) UIColors according to their luminance value.
NSInteger colorSortByLuminance(id color1, id color2, void *context)
{
	CGFloat luminance1 = luminanceForColor(color1);
	CGFloat luminance2 = luminanceForColor(color2);
	if(luminance1 == luminance2)
	{
		return NSOrderedSame;
	}
	else if(luminance1 < luminance2)
	{
		return NSOrderedAscending;
	}
	else
	{
		return NSOrderedDescending;
	}
}

-(NSArray*)colors
{
	static NSArray *colorArray = nil;
	if(colorArray == nil)
	{
		// If you want to add more colors to the demo, here would be the place
		// You can also add patterns if you like, they will simply be sorted
		// to the end of the list.
		NSArray *unsortedArray = [NSArray arrayWithObjects:
								  [UIColor redColor],
								  [UIColor greenColor],
								  [UIColor blueColor],
								  [UIColor yellowColor],
								  [UIColor magentaColor],
								  [UIColor cyanColor],
								  [UIColor orangeColor],
								  [UIColor purpleColor],
								  [UIColor brownColor],
								  [UIColor whiteColor],
								  [UIColor lightGrayColor],
								  [UIColor darkGrayColor],
								 // [UIColor blackColor],
									  nil];
			colorArray = [[unsortedArray sortedArrayUsingFunction:colorSortByLuminance context:nil] retain];
		}
		return colorArray;
}


-(IBAction)showColorPicker:(id)sender{

	if(self.view.frame.size.height <= pickerView.frame.origin.y)
		[self movePickerOnScreen];
	else
		[self movePickerOffScreen];
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
	[imageCopy release];
	[quartzView release]; 
	quartzView = nil;
	[pickerView release];
	[gradientPicker release];
	[blendButton release];
	[saveButton release];
	[photoButton release];
	[mainToolBar release];
	[parentPreviewImageView release];
	[parentPreviewView release];
	[previewImageView release];
    [super dealloc];
}


@end
