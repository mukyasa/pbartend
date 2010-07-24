

#import "QuartzBlending.h"


@interface QuartzBlendingView()
@end

@implementation QuartzBlendingView

@synthesize sourceColor, destinationColor, blendMode;

-(id)initWithFrame:(CGRect)frame
{
	self = [super initWithFrame:frame];
	if(self != nil)
	{
		sourceColor = [UIColor whiteColor];
		destinationColor = [UIColor blackColor];
		blendMode = kCGBlendModeNormal;
	}
	return self;
}

-(void)dealloc
{
	[sourceColor release];
	[destinationColor release];
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

-(void)setDestinationColor:(UIColor*)dest
{
	if(dest != destinationColor)
	{
		[destinationColor release];
		destinationColor = [dest retain];
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
	CGContextFillRect(context, CGRectMake(0.0, 0.0, 320.0, 236.0));
}

@end
