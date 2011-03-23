//
//  CoverShotAppDelegate.h
//  CoverShot
//
//  Created by Darren Mason on 7/21/10.
//  Copyright TGen 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@class CoverShotViewController;

@interface CoverShotAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    CoverShotViewController *viewController;
	UIImageView *coverHolder;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) UIImageView *coverHolder;
@property (nonatomic, retain) IBOutlet CoverShotViewController *viewController;

@end

