//
//  iPadDrinkMixerWebAppDelegate.h
//  iPadDrinkMixerWeb
//
//  Created by Darren Mason on 9/26/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import <UIKit/UIKit.h>

@class iPadDrinkMixerWebViewController;

@interface iPadDrinkMixerWebAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    iPadDrinkMixerWebViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet iPadDrinkMixerWebViewController *viewController;

@end

