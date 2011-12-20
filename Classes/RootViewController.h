//
//  RootViewController.h
//  TestPOC
//
//  Created by Darren Mason on 8/19/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import <UIKit/UIKit.h>

@class DetailViewController;

@interface RootViewController : UITableViewController {
    DetailViewController *detailViewController;
}

@property (nonatomic, retain) IBOutlet DetailViewController *detailViewController;

@end
