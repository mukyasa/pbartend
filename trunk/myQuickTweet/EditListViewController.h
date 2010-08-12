//
//  EditListViewController.h
//  InstaTwit
//
//  Created by Darren Mason on 6/29/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <MessageUI/MessageUI.h>

extern NSString * const nothingIndicator;


@protocol EditListViewControllerDelegate;

@interface EditListViewController : UIViewController <UIPickerViewDataSource,UIPickerViewDelegate,UIActionSheetDelegate,MFMailComposeViewControllerDelegate> {
	id <EditListViewControllerDelegate> delegate;
	IBOutlet UIButton *verbButton;
	IBOutlet UIButton *nounButton;
	IBOutlet UITextField *newIm;
	IBOutlet UITextField *newFeeling;
	IBOutlet UIPickerView *tweetPicker;
	IBOutlet UIButton *deleteButton;
	NSMutableArray *feelingArray;
	NSMutableArray *imArray;

}

-(IBAction)writeToVerbPlist:(id)sender;
-(IBAction)writeToNounPlist:(id)sender;
-(IBAction)textFieldDoneEditing:(id)sender;
-(NSString *)applicationDocumentsDirectory;
-(IBAction) tellAFriend:(id)sender;

@property(nonatomic,retain) UITextField *newIm;
@property(nonatomic,retain) UITextField *newFeeling;
@property(nonatomic,retain) UIButton *verbButton;
@property(nonatomic,retain) UIButton *nounButton;
@property(nonatomic,retain) UIPickerView *tweetPicker;
@property(nonatomic,retain) UIButton *deleteButton;

- (void) loadArrays;
-(IBAction) deleteFromPicker;

@property (nonatomic, assign) id <EditListViewControllerDelegate> delegate;
- (IBAction)done:(id)sender;
@end


@protocol EditListViewControllerDelegate
- (void) editListViewControllerDidFinish:(EditListViewController *)controller;
@end


