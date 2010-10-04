//
//  MD5_keycodeViewController.h
//  MD5_keycode
//
//  Created by Darren Mason on 10/4/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <CommonCrypto/CommonDigest.h>  
#import <CommonCrypto/CommonCryptor.h>
#import <MessageUI/MFMailComposeViewController.h>

@interface MD5_keycodeViewController : UIViewController <MFMailComposeViewControllerDelegate> {

	IBOutlet UIButton *keybutton;
	IBOutlet UITextField *email;
	IBOutlet UITextView *resultlbl;
	
}

@property(nonatomic,retain) UITextView *resultlbl;
@property(nonatomic,retain) UIButton *keybutton;
@property(nonatomic,retain) UITextField *email;
-(IBAction)makekey;

- (NSString *)uniqueIDFromString:(NSString *)source;
- (void) showEmailComposer:(NSString *)body toRecipient:(NSString*)toRecipientsString;
@end

