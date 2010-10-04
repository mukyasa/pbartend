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
	
	resultlbl.text = [self uniqueIDFromString:@"flashcard101"];
}
/*
-(NSString*)fileMD5:(NSString*)path
{
	NSFileHandle *handle = [NSFileHandle fileHandleForReadingAtPath:path];
	if( handle== nil ) return @"ERROR GETTING FILE MD5"; // file didnt exist
	
	CC_MD5_CTX md5;
	
	CC_MD5_Init(&md5);
	
	BOOL done = NO;
	while(!done)
	{
		NSData* fileData = [handle readDataOfLength: CHUNK_SIZE ];
		CC_MD5_Update(&md5, [fileData bytes], [fileData length]);
		if( [fileData length] == 0 ) done = YES;
	}
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
	return s;
}
 */
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
    
    return s;
	
	/*
	 const char *src = [[source lowercaseString] UTF8String];
	 unsigned char result[CC_MD5_DIGEST_LENGTH];
	 CC_MD5(src, strlen(src), result);
	 
	 NSString *ret = [[[NSString alloc] initWithFormat:@"%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X", 
	 result[0], result[1], result[2], result[3],
	 result[4], result[5], result[6], result[7],
	 result[8], result[9], result[10], result[11],
	 result[12], result[13], result[14], result[15]
	 ] autorelease];
	 
	 return ret;
	 */
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
