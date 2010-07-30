

#import "QuartzBlending.h"


@interface QuartzBlendingView()
@end

@implementation QuartzBlendingView

@synthesize sourceColor, blendMode,choosenImage;

static inline double radians (double degrees) {return degrees * M_PI/180;}

-(id)initWithFrame:(CGRect)frame
{
	self = [super initWithFrame:frame];
	if(self != nil)
	{
		sourceColor = [UIColor clearColor];
		blendMode = kCGBlendModeScreen;
	}
	return self;
}

-(void)dealloc
{
	[choosenImage release];
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
	
	CGContextScaleCTM(context, 1, -1);
	CGContextTranslateCTM(context, 0, -choosenImage.size.height);
	CGContextConcatCTM(context, CGAffineTransformIdentity);
	CGContextDrawImage(UIGraphicsGetCurrentContext(), CGRectMake(0, 0, choosenImage.size.width, choosenImage.size.height), choosenImage.CGImage);
	UIImage *imageCopy = UIGraphicsGetImageFromCurrentImageContext();
	
	CGContextDrawImage(context, CGRectMake(0.0, 0.0, choosenImage.size.width, choosenImage.size.height), imageCopy.CGImage);
	// Set up our blend mode
	CGContextSetBlendMode(context, blendMode);
	// And draw a rect with the "foreground" color - this is the "Source" for the blending formulas
	CGContextSetFillColorWithColor(context, sourceColor.CGColor);
	CGContextFillRect(context, CGRectMake(0.0, 0.0, 320.0, 480.0));


}

@end
