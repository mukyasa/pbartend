

#import <UIKit/UIKit.h>
#import "QuartzView.h"

@interface QuartzBlendingView : QuartzView
{
	UIColor *sourceColor;
	CGBlendMode blendMode;
}

-(void)drawInContext:(CGContextRef)context;
@property(nonatomic, readwrite, retain) UIColor *sourceColor;
@property(nonatomic, readwrite) CGBlendMode blendMode;

@end
