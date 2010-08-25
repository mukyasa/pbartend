//
//  EditDrinkViewController.h
//  iPadDrinkMixer
//
//  Created by Darren Mason on 8/23/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface EditDrinkViewController : UIViewController {
	IBOutlet UIButton *save;
}

@property(nonatomic,retain) UIButton *save;

-(IBAction)save:(id)sender;

@end
