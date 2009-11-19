/**
 * Date: Nov 18, 2009
 * Project: FlashCard
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.card.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class Chart extends View {

	private final float x;
    private final float y;
    private final int r;
    
    private int screenwidth;
    
    private float lft;
    private float rgt;
    private float top;
    private float bot;
    
    
    private final Paint mPaintWrong = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mPaintCorrect = new Paint(Paint.ANTI_ALIAS_FLAG);
    //sweepangle will be set by score
    private int sweepangle = 180;
    private int startangle = 90;
    
    public Chart(Context context, float x, float y, int r,int screenwidth,int screenheight) {
        super(context);
        mPaintWrong.setColor(0xFFedb419);
        mPaintCorrect.setColor(0xFFbc8f13);
        this.x = x;
        this.y = y; 
        this.r = r;
        
        this.screenwidth = screenwidth;
        
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x, y, r, mPaintWrong);
        
        int offset = 12; 
        this.lft =  this.screenwidth/2 - this.r-offset;
        this.rgt =  this.screenwidth/2 + this.r-offset;
        this.top = this.y - r;
        this.bot = this.top + (r*2);
        
        RectF clockRect = new RectF(this.lft, this.top, this.rgt, this.bot); 
        canvas.drawArc(clockRect, startangle, sweepangle, true, mPaintCorrect);
    }
	
}
