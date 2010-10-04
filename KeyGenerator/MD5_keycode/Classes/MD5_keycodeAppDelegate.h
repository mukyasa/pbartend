//
//  MD5_keycodeAppDelegate.h
//  MD5_keycode
//
//  Created by Darren Mason on 10/4/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import <UIKit/UIKit.h>

@class MD5_keycodeViewController;

@interface MD5_keycodeAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    MD5_keycodeViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet MD5_keycodeViewController *viewController;

@end

