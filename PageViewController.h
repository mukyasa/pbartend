//
//  PageViewController.h
//  TestPOC
//
//  Created by Darren Mason on 8/22/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "EditDrinkViewController.h"

@interface PageViewController : UIViewController<EditDrinkViewControllerDelegate> {

		IBOutlet UIButton *edit;
	
}
@property(nonatomic,retain) UIButton *edit;
-(IBAction)editDrink:(id)sender;

@end
