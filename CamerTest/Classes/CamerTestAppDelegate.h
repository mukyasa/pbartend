//
//  CamerTestAppDelegate.h
//  CamerTest
//
//  Created by Darren Mason on 7/12/10.
//  Copyright TGen 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@class CamerTestViewController;

@interface CamerTestAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    CamerTestViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet CamerTestViewController *viewController;

@end

