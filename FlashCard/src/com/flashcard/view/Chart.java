/**
 * Date: Nov 18, 2009
 * Project: FlashCard
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.flashcard.view;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.flashcard.R;
import com.flashcard.domain.CardSet;
import com.flashcard.domain.FlashCard;
import com.flashcard.domain.ResultsBean;
import com.flashcard.handler.ApplicationHandler;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class Chart extends View {

	private final float x;
    private final float y;
    private final int r;
    private final int START_ANGLE = -90;
    private final int CIRCLE = 360;
    private final int SCREEN_OFFSET = 12; 
  //sweepangle will be set by score
    private long SWEEP_ANGLE = 0;
    
    private int screenwidth;
    ResultsBean rbean;
    
    private float lft;
    private float rgt;
    private float top;
    private float bot;
    
    private float percentWrong;
    private float percentRight;
    
    
    private final Paint mPaintWrong = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mPaintCorrect = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mPaintKey = new Paint(Paint.ANTI_ALIAS_FLAG);
    
   
    
    public Chart(Context context, float x, float y, int r,int screenwidth,int screenheight,ResultsBean rbean) {
        super(context);
        mPaintWrong.setColor(0xFFd56b32);
        mPaintCorrect.setColor(0xFFdfb861);
        this.x = x;
        this.y = y; 
        this.r = r;
        
        this.rbean = rbean;
        this.screenwidth = screenwidth;
        
        initCards();
    }
    
    private void initCards() {
	    
    	ApplicationHandler handler = ApplicationHandler.instance();
        CardSet cardsets = handler.currentlyUsedSet;
        if(cardsets!= null)
        {
	        ArrayList<FlashCard> cards = cardsets.flashcards;
	        
	        rbean.totalCards = cards.size();
	        Iterator<FlashCard> iter = cards.iterator();
	        if(iter != null)
	        {
		        while(iter.hasNext())
		        {
		        	FlashCard card = iter.next();
		        	if(!card.isCorrect)
		        		rbean.wrongcardcount++;
		        		
		        	if(card.wasSeen)
		        		rbean.countseen++;
		        	
		        	//assume that if you saw it and didnt' mark it wrong it was correct
		        	rbean.correctcardcount = (rbean.countseen - rbean.wrongcardcount);
		        }
	        }
	        
	        percentRight = (((rbean.countseen - rbean.wrongcardcount) / rbean.countseen) * 100);
            percentWrong = ((rbean.wrongcardcount / rbean.countseen) * 100);
	        
	        //set chart
	        float degree = CIRCLE / rbean.countseen;
	        SWEEP_ANGLE = Math.round(rbean.correctcardcount * degree);
        }
        
    	
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x, y, r, mPaintWrong);
        
        Bitmap bitmapCorrect = BitmapFactory.decodeResource(getResources(),
     		   R.drawable.correctresult);
        Bitmap bitmapInCorrect = BitmapFactory.decodeResource(getResources(),
     		   R.drawable.incorrectresult);
        
        this.lft =  this.screenwidth/2 - this.r-SCREEN_OFFSET;
        this.rgt =  this.screenwidth/2 + this.r-SCREEN_OFFSET;
        this.top = this.y - r;
        this.bot = this.top + (r*2);
        
        canvas.drawLine(10, 0, this.lft+this.rgt-10, 0, mPaintKey);
        
        RectF clockRect = new RectF(this.lft, this.top, this.rgt, this.bot); 
        canvas.drawArc(clockRect, START_ANGLE, SWEEP_ANGLE, true, mPaintCorrect);
        
        //draw images
        canvas.drawBitmap(bitmapCorrect, 10,10, mPaintKey);
        canvas.drawBitmap(bitmapInCorrect,10,this.bot-31, mPaintKey); 
        
        mPaintKey.setTextSize(15);
        canvas.drawText(Math.round(percentRight)+"%", 28, 22, mPaintKey);
        canvas.drawText("Correct", 28, 38, mPaintKey);
        
        canvas.drawText(Math.round(percentWrong)+"%", 28, this.bot-19, mPaintKey);
        canvas.drawText("Incorrect", 28, this.bot-3, mPaintKey);
        
    }
	
}
