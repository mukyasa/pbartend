

#import "QuartzView.h"

@implementation QuartzView

-(id)initWithFrame:(CGRect)frame
{
	self = [super initWithFrame:frame];
	if(self != nil)
	{
		self.backgroundColor = [UIColor blackColor];
		self.opaque = YES;
		self.clearsContextBeforeDrawing = YES;
	}
	return self;
}

-(void)drawInContext:(CGContextRef)context
{
	// Default is to do nothing!
	
}

-(void)drawRect:(CGRect)rect
{
	
	//NSLog(@"RECT W:%f H:%f",rect.size.width,rect.size.height);
	// Since we use the CGContextRef a lot, it is convienient for our demonstration classes to do the real work
	// inside of a method that passes the context as a parameter, rather than having to query the context
	// continuously, or setup that parameter for every subclass.

	[self drawInContext:UIGraphicsGetCurrentContext()];

	//NSLog(@"Quartz View W:%f H:%f",self.frame.size.width,self.frame.size.height);
	
}


@end