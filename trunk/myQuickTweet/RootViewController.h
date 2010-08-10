//
//  RootViewController.h
//  Plain2
//
//  Created by Jaanus Kase on 03.05.10.
//  Copyright 2010. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <iAd/iAd.h>

#import "TwitterLoginPopupDelegate.h"
#import "TwitterLoginUiFeedback.h"
#import "EditListViewController.h" 

@class OAuth, CustomLoginPopup;

@interface RootViewController : UIViewController <UIPickerViewDataSource,UIPickerViewDelegate,TwitterLoginPopupDelegate, TwitterLoginUiFeedback,EditListViewControllerDelegate> {
    IBOutlet UIButton *postButton;
    IBOutlet UITextField *statusText;
    IBOutlet UILabel *signedInAs;
    IBOutlet UITextView *tweets;
	/* MY STUFF */
	IBOutlet ADBannerView *bannerView;
	IBOutlet UITextField *textNote;
	IBOutlet UIPickerView *tweetPicker;	
	NSArray *feelingArray;
	NSArray *imArray;
    
    CustomLoginPopup *loginPopup;
	
	OAuth *oAuth;
	
	
	
}
- (void)didPressPost:(id)sender;
- (IBAction)didPressLatestTweets:(id)sender;
-(void) moveBannerViewOffscreen;
-(void) moveBannerViewOnscreen;
- (void)resetUi;

//my stuff
@property(nonatomic,retain) UITextField *textNote;
@property(nonatomic,retain) UIPickerView *tweetPicker;
@property(nonatomic,retain) ADBannerView *bannerView;


-(IBAction)textFieldDoneEditing:(id)sender;
- (void) loadArrays;
- (NSString *)applicationDocumentsDirectory;

@end
