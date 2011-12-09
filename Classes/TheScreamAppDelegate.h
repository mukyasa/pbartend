//
//  TheScreamAppDelegate.h
//  TheScream
//
//  Created by Darren Mason on 6/13/10.
//  Copyright TGen 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@class MainViewController;

@interface TheScreamAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    MainViewController *mainViewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet MainViewController *mainViewController;

@end

