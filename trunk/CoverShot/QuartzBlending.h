

#import <UIKit/UIKit.h>
#import "QuartzView.h"

@interface QuartzBlendingView : QuartzView
{
	UIColor *sourceColor;
	UIColor *destinationColor;
	CGBlendMode blendMode;
}

-(void)drawInContext:(CGContextRef)context;
@property(nonatomic, readwrite, retain) UIColor *sourceColor;
@property(nonatomic, readwrite, retain) UIColor *destinationColor;
@property(nonatomic, readwrite) CGBlendMode blendMode;

@end
