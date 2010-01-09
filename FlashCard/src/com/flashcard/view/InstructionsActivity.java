/**
 * Date: Jan 7, 2010
 * Project: FlashCard
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.flashcard.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.flashcard.R;
import com.flashcard.util.AppUtil;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class InstructionsActivity extends Activity implements OnTouchListener{
	
	private View screenOne,screenTwo,screenThree;
	private int wizardcount =0;
	/**
	 * This will be transparent and there is a theme involved see values/style
	 * then in the manifest you need to apply this theme to this class
	 */
	  @Override
		protected void onCreate(Bundle savedInstanceState) {  
		  super.onCreate(savedInstanceState);
		  Window window = getWindow();
		  window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		  
		  screenOne = new DemoScreenOne(this);
		  screenOne.setOnTouchListener(this);
		  setContentView(screenOne); 
		  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	    }
	  
	  private static class DemoScreenOne extends View{
	        private AnimateDrawable mDrawable;

	        public DemoScreenOne(Context context) {
	            super(context);
	            setFocusable(true);
	            setFocusableInTouchMode(true);

	            Drawable dr = context.getResources().getDrawable(R.drawable.finger_dwn);
	            dr.setBounds(0, 0, dr.getIntrinsicWidth(), dr.getIntrinsicHeight());
	            
	            Animation an = new TranslateAnimation(100, 100, -20, 0);
	            an.setDuration(800);
	            an.setRepeatCount(-1);
	            an.initialize(10, 10, 10, 10);
	            
	            mDrawable = new AnimateDrawable(dr, an); 
	            an.startNow();
	        }
	        
	        
	        @Override protected void onDraw(Canvas canvas) {
	        	
	        	setBackgroundResource(R.drawable.tap_bg);
	        	
	            mDrawable.draw(canvas); 
	            invalidate();
	        }
	    }
	  
	  private static class DemoScreenTwo extends View{
	        private AnimateDrawable mDrawable;

	        public DemoScreenTwo(Context context) {
	            super(context);
	            setFocusable(true);
	            setFocusableInTouchMode(true);

	            Drawable dr = context.getResources().getDrawable(R.drawable.finger_dwn);
	            dr.setBounds(0, 0, dr.getIntrinsicWidth(), dr.getIntrinsicHeight());
	            
	            Animation an = new TranslateAnimation(45, 330, 0, 0);
	            an.setDuration(1000);
	            an.setRepeatCount(-1);
	            an.setInterpolator(new AccelerateDecelerateInterpolator());
	            an.initialize(10, 10, 10, 10);
	            
	            mDrawable = new AnimateDrawable(dr, an);
	            an.startNow();
	        }
	        
	        @Override 
	        protected void onDraw(Canvas canvas) {
	        	
	        	setBackgroundResource(R.drawable.swipe_bg);
	        	
	            mDrawable.draw(canvas);
	            invalidate();
	        }
	    }
	  
	  private static class DemoScreenThree extends View{
	        private AnimateDrawable mDrawable;

	        public DemoScreenThree(Context context) {
	            super(context);
	            setFocusable(true);
	            setFocusableInTouchMode(true);

	            Drawable dr = context.getResources().getDrawable(R.drawable.score_finger);
	            dr.setBounds(0, 0, dr.getIntrinsicWidth(), dr.getIntrinsicHeight());
	            
	            Animation an = new TranslateAnimation(50, 30, -10, -10);
	            an.setDuration(800);
	            an.setRepeatCount(-1);
	            an.initialize(10, 10, 10, 10);
	            
	            mDrawable = new AnimateDrawable(dr, an);
	            an.startNow();
	        }
	        
	        
	        @Override protected void onDraw(Canvas canvas) {
	        	
	        	setBackgroundResource(R.drawable.score_bg);
	        	
	            mDrawable.draw(canvas); 
	            invalidate();
	        }
	    }
	  
	  

	/* (non-Javadoc)
     * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
     */
    public boolean onTouch(View v, MotionEvent event) {

    	if(wizardcount==0)
    	{
    		screenTwo = new DemoScreenTwo(this);  
    		screenTwo.setOnTouchListener(this);
    		v.clearAnimation();
    		v.setVisibility(View.GONE);
			setContentView(screenTwo);	
    	}
    	else if(wizardcount ==1)
    	{
    		screenThree = new DemoScreenThree(this);
    		screenThree.setOnTouchListener(this);
			setContentView(screenThree);
			
    	}else
    	{
    		AppUtil.setSawDirections(this,2);
    		finish();
    	}
    	
    	wizardcount++;
			
		return false;
    }
}
