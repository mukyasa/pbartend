

#import <UIKit/UIKit.h>
#import "QuartzView.h"

@interface QuartzBlendingView : QuartzView
{
	UIColor *sourceColor;
	CGBlendMode blendMode;
	UIImage *choosenImage;
}

-(void)drawInContext:(CGContextRef)context;
@property(nonatomic, readwrite, retain) UIColor *sourceColor;
@property(nonatomic, readwrite) CGBlendMode blendMode;
@property(nonatomic,readwrite,retain) UIImage *choosenImage;

@end
