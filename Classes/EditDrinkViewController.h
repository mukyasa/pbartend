//
//  EditDrinkViewController.h
//  TestPOC
//
//  Created by Darren Mason on 8/22/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import <UIKit/UIKit.h>


@protocol EditDrinkViewControllerDelegate;


@interface EditDrinkViewController : UIViewController {
	id <EditDrinkViewControllerDelegate> delegate;
	IBOutlet UIButton *saveDrink;
	
}

@property(nonatomic,retain) UIButton *saveDrink;
@property (nonatomic, assign) id <EditDrinkViewControllerDelegate> delegate;
- (IBAction)saveDrink:(id)sender;

@end


@protocol EditDrinkViewControllerDelegate
- (void)flipsideViewControllerDidFinish:(EditDrinkViewController *)controller;
@end
