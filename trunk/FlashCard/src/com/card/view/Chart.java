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
    private final Paint mPaintWrong = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mPaintCorrect = new Paint(Paint.ANTI_ALIAS_FLAG);
    
    public Chart(Context context, float x, float y, int r) {
        super(context);
        mPaintWrong.setColor(0xFFedb419);
        mPaintCorrect.setColor(0xFFbc8f13);
        this.x = x;
        this.y = y; 
        this.r = r;
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x, y, r, mPaintWrong);
        RectF clockRect = new RectF(30, 135, 210, 315); 
        canvas.drawArc(clockRect, 180, 300, true, mPaintCorrect);
    }
	
}
