//
//  PageViewController.h
//  fliptest
//
//  Created by Darren Mason on 8/23/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "EditDrinkViewController.h"
#import "DrinkViewController.h"

@interface PageViewController : UIViewController {
	DrinkViewController *drinkViewController;
	EditDrinkViewController *editDrinkViewController;
	CGRect viewFrame;
	
	IBOutlet UIButton *editSaveButton;
}


@property (retain, nonatomic) UIButton *editSaveButton;
@property (retain, nonatomic) DrinkViewController *drinkViewController;
@property (retain, nonatomic) EditDrinkViewController *editDrinkViewController;
- (IBAction)switchViews:(id)sender;

@end
