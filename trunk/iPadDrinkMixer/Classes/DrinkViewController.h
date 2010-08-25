//
//  DrinkViewController.h
//  iPadDrinkMixer
//
//  Created by Darren Mason on 8/23/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface DrinkViewController : UIViewController {

	IBOutlet UIButton *edit;
}
@property(nonatomic,retain)UIButton *edit;
-(IBAction)edit:(id)sender;

@end
