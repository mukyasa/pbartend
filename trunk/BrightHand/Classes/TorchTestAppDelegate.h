//
//  TorchTestAppDelegate.h
//  TorchTest
//
//  Created by Darren Mason on 7/8/10.
//  Copyright TGen 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@class BrightHandViewController;


@interface TorchTestAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    BrightHandViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet BrightHandViewController *viewController;

@end

