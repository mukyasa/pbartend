//
//  AboutViewController.h
//  TheScream
//
//  Created by Darren Mason on 6/13/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import <UIKit/UIKit.h>


@protocol AboutViewControllerDelegate;


@interface AboutViewController : UIViewController <UITableViewDataSource,UITableViewDelegate> {
	id <AboutViewControllerDelegate> delegate;
	IBOutlet UITableView *aboutTable;
	NSArray *arryPhoto;
	NSArray *arryPhotoLabels;
	NSArray *arryCompanyLabels;
	NSArray *arryCompany;

}

@property (nonatomic, assign) id <AboutViewControllerDelegate> delegate;
- (IBAction)done:(id)sender;

@end


@protocol AboutViewControllerDelegate
- (void)aboutViewControllerDidFinish:(AboutViewController *)controller;
@end



