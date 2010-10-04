//
//  MD5_keycodeViewController.m
//  MD5_keycode
//
//  Created by Darren Mason on 10/4/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import "MD5_keycodeViewController.h"
#import <CommonCrypto/CommonDigest.h>  
#import <CommonCrypto/CommonCryptor.h>

@implementation MD5_keycodeViewController
@synthesize keybutton,email,resultlbl;



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


/*
// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
}
*/

-(IBAction)makekey{

	[self showEmailComposer:[self uniqueIDFromString:@"flashcard101"] toRecipient:email.text];
}

- (void) showEmailComposer:(NSString *)body toRecipient:(NSString*)toRecipientsString
{	

	NSString* subject = @"Flashcard 101 Registration Code";

	NSString* isHTML = NO;
	
    MFMailComposeViewController *picker = [[MFMailComposeViewController alloc] init];
    picker.mailComposeDelegate = self;
    
	// Set subject
	if(subject != nil)
		[picker setSubject:subject];
	// set body
	if(body != nil)
	{
		if(isHTML != nil && [isHTML boolValue])
		{
			[picker setMessageBody:body isHTML:YES];
		}
		else
		{
			[picker setMessageBody:body isHTML:NO];
		}
	}


	[picker setToRecipients:[ toRecipientsString componentsSeparatedByString:@","]];

    // Attach an image to the email
	// NSString *path = [[NSBundle mainBundle] pathForResource:@"rainy" ofType:@"png"];
	//  NSData *myData = [NSData dataWithContentsOfFile:path];
	//  [picker addAttachmentData:myData mimeType:@"image/png" fileName:@"rainy"];
    
    
    [self presentModalViewController:picker animated:YES];
    [picker release];
}
- (NSString *)uniqueIDFromString:(NSString *)source
{
	CC_MD5_CTX md5;
	CC_MD5_Init(&md5);
	
	const char *src = [[source lowercaseString] UTF8String];
	const char *salt = [[email.text lowercaseString] UTF8String];
	
	
	CC_MD5_Update(&md5,salt, strlen(salt));
	CC_MD5_Update(&md5, src,strlen(src));
	
	/*

	 extern int CC_MD5_Update(CC_MD5_CTX *c, const void *data, CC_LONG len);

	 extern unsigned char *CC_MD5(const void *data, CC_LONG len, unsigned char *md);
	 */
	
	unsigned char digest[CC_MD5_DIGEST_LENGTH];
	CC_MD5_Final(digest, &md5);
	
    NSString* s = [NSString stringWithFormat: @"%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x",
				   digest[0], digest[1], 
				   digest[2], digest[3],
				   digest[4], digest[5],
				   digest[6], digest[7],
				   digest[8], digest[9],
				   digest[10], digest[11],
				   digest[12], digest[13],
				   digest[14], digest[15]];

	
	NSString *emailfiller = [NSString stringWithFormat:@"Thank you for your purchase.\n\nHere is the registration code you will need to register your product.\n\nhttp://www.mypocket-technologies.com/android/apps/\n\n********* COPY BELOW ********\n\t%@\n******** COPY ABOVE ********\nYou will also need to use the email address (%@) you purchased the software with.\n\nDarren Mason\nmyPocket technologies\nwww.mypocket-technolgies.com",[s substringWithRange: NSMakeRange (0, 10)],email.text];
    
    return emailfiller;
	

}

- (void)mailComposeController:(MFMailComposeViewController *)controller didFinishWithResult:(MFMailComposeResult)result error:(NSError *)error
{
	[self dismissModalViewControllerAnimated:YES];
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
	// Release any retained subviews of the main view.
	// e.g. self.myOutlet = nil;
}


- (void)dealloc {
	[resultlbl release];
	[keybutton release];
	[email release];
    [super dealloc];
}

@end
