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
import android.view.View;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class Chart extends View {

	private final float x;
    private final float y;
    private final int r;
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    
    public Chart(Context context, float x, float y, int r) {
        super(context);
        mPaint.setColor(0xFFFF0000);
        this.x = x;
        this.y = y;
        this.r = r;
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x, y, r, mPaint);
    }
	
}
