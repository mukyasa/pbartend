//
//  VertLabel.m
//  TestPOC
//
//  Created by Darren Mason on 8/19/10.
//  Copyright 2010 TGen. All rights reserved.
//

#import "VertLabel.h"


@implementation VertLabel


-(id)initWithFrame:(CGRect)frame
{
	self = [super initWithFrame:frame];
	if (!self) return nil;
	
	_verticalAlignment = VerticalAlignmentTop;
	
    return self;
}

-(void)dealloc
{
    [super dealloc];
}

-(VerticalAlignment) verticalAlignment
{
	return _verticalAlignment;
}
-(void) setVerticalAlignment:(VerticalAlignment)value
{
	_verticalAlignment = value;
	[self setNeedsDisplay];
}

// align text block according to vertical alignment settings
-(CGRect)textRectForBounds:(CGRect)bounds limitedToNumberOfLines:(NSInteger)numberOfLines
{
	CGRect rect = [super textRectForBounds:bounds limitedToNumberOfLines:numberOfLines];
	CGRect result;
	switch (_verticalAlignment)
	{
		case VerticalAlignmentTop:
			result = CGRectMake(bounds.origin.x, bounds.origin.y, rect.size.width, rect.size.height);
			break;
		case VerticalAlignmentMiddle:
			result = CGRectMake(bounds.origin.x, bounds.origin.y + (bounds.size.height - rect.size.height) / 2, rect.size.width, rect.size.height);
			break;
		case VerticalAlignmentBottom:
			result = CGRectMake(bounds.origin.x, bounds.origin.y + (bounds.size.height - rect.size.height), rect.size.width, rect.size.height);
			break;
		default:
			result = bounds;
			break;
	}
	return result;
}

-(void)drawTextInRect:(CGRect)rect
{
	CGRect r = [self textRectForBounds:rect limitedToNumberOfLines:self.numberOfLines];
	[super drawTextInRect:r];
}

@end