

#import "QuartzBlending.h"


@interface QuartzBlendingView()
@end

@implementation QuartzBlendingView

@synthesize sourceColor, blendMode;

-(id)initWithFrame:(CGRect)frame
{
	self = [super initWithFrame:frame];
	if(self != nil)
	{
		sourceColor = [UIColor whiteColor];
		blendMode = kCGBlendModeClear;
	}
	return self;
}

-(void)dealloc
{
	[sourceColor release];
	[super dealloc];
}

-(void)setSourceColor:(UIColor*)src
{
	if(src != sourceColor)
	{
		[sourceColor release];
		sourceColor = [src retain];
		[self setNeedsDisplay];
	}
}


-(void)setBlendMode:(CGBlendMode)mode
{
	if(mode != blendMode)
	{
		blendMode = mode;
		[self setNeedsDisplay];
	}
}

//puts the color picked over the drawing
-(void)drawInContext:(CGContextRef)context
{
	// Set up our blend mode
	CGContextSetBlendMode(context, blendMode);
	// And draw a rect with the "foreground" color - this is the "Source" for the blending formulas
	CGContextSetFillColorWithColor(context, sourceColor.CGColor);
	CGContextFillRect(context, CGRectMake(0.0, 0.0, 320.0, 480.0));
}

@end
