//
//  AppDelegate_Phone.m
//  Plain2
//
//  Created by Jaanus Kase on 03.05.10.
//  Copyright 2010. All rights reserved.
//

#import "AppDelegate_Phone.h"
#import "RootViewController.h"

@implementation AppDelegate_Phone

@synthesize window;


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {    

	#define UIColorFromRGB(rgbValue) [UIColor \
	colorWithRed:((float)((rgbValue & 0xFF0000) >> 16))/255.0 \
	green:((float)((rgbValue & 0xFF00) >> 8))/255.0 \
	blue:((float)(rgbValue & 0xFF))/255.0 alpha:1.0]
	
	
	
    // Override point for customization after application launch
	sleep(2);//show splash screen default.png longer
    [window makeKeyAndVisible];
	
	RootViewController *root = [[RootViewController alloc] initWithNibName:@"RootViewController" bundle:nil];
	nav = [[UINavigationController alloc] initWithRootViewController:root];
	nav.navigationBar.tintColor = UIColorFromRGB(0xff8EC1DA);
	[window addSubview:nav.view];
	[root release];
	
	return YES;
}


- (void)dealloc {
	[nav release];
    [window release];
    [super dealloc];
}


@end
