//
//  VertLabel.h
//  TestPOC
//
//  Created by Darren Mason on 8/19/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef enum
{
	VerticalAlignmentTop = 0, // default
	VerticalAlignmentMiddle,
	VerticalAlignmentBottom,
} VerticalAlignment;

@interface VertLabel : UILabel {

@private
	VerticalAlignment _verticalAlignment;
}

@property (nonatomic) VerticalAlignment verticalAlignment;

@end



