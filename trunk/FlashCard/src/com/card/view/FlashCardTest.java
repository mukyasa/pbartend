/**
 * Date: Nov 16, 2009
 * Project: FlashCard
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.card.view;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.card.R;
import com.card.domain.CardSets;
import com.card.handler.ApplicationHandler;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class FlashCardTest extends Activity {
	
	private GestureDetector mGestureDetector;
	private Context context;
	private int count=0;
	private int maxcount=0;
	
	@SuppressWarnings("unchecked")
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_frame);  
		//force to be landscape
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		mGestureDetector = new GestureDetector(this, new LearnGestureListener());
		context = getBaseContext();
		//set up cardset
		initalize();
		
    }
	
	private void initalize(){
		try {
		        ApplicationHandler handler = ApplicationHandler.instance();
		        CardSets cardsets = handler.pickedSet;
		        maxcount = cardsets.cardCount;
		        TextView tvTitle = (TextView)findViewById(R.id.tvSetTitle);
		        tvTitle.setText(cardsets.title);
		        JSONArray sets = cardsets.flashcards;
	
		        JSONArray terms = (JSONArray)sets.get(0);
		        String question = (String)terms.get(0);
		        String answer = (String)terms.get(1);
		        
		        TextView tvFlashCard = (TextView)findViewById(R.id.tvflashCard1);
		        tvFlashCard.setText(question);
		        
	        
        } catch (JSONException e) {
	        e.printStackTrace();
        }
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    if (mGestureDetector.onTouchEvent(event))
	        return true;
	    else
	        return false;
	}
	
	protected class LearnGestureListener extends GestureDetector.SimpleOnGestureListener{
	    @Override
	    public boolean onSingleTapUp(MotionEvent ev) {
	      //  Log.d("onSingleTapUp",ev.toString());
	        return true;
	    }
	    @Override
	    public void onShowPress(MotionEvent ev) {
	       // Log.d("onShowPress",ev.toString());
	    }
	    @Override
	    public void onLongPress(MotionEvent ev) {
	       // Log.d("onLongPress",ev.toString());
	    }
	    @Override
	    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
	       // Log.d("onScroll",e1.toString());
	        return true;
	    }
	    @Override
	    public boolean onDown(MotionEvent ev) {
	        //Log.d("onDownd",ev.toString());
	        return true;
	    }
	    @Override
	    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	        Log.d("e1",e1.getX()+"");
	        Log.d("e2",e2.getX()+"");
	        
	        if(e1.getX() > e2.getX() && count < maxcount)//flick next 
	        {
		        // Get the ViewFlipper from the layout
	            ViewFlipper vf = (ViewFlipper) findViewById(R.id.details);
	
	            // Set an animation from res/anim: 
	            vf.setAnimation(AnimationUtils.loadAnimation(context,  R.anim.slide_left));
	            vf.showNext();
	            count++;
	        }
	        else if(count > 0 && e1.getX() < e2.getX())//flix previous
	        {
	        	 // Get the ViewFlipper from the layout
                ViewFlipper vf = (ViewFlipper) findViewById(R.id.details);
                // Set an animation from res/anim: 
                vf.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_right));
                vf.showPrevious();	
                count--;
	        }
	        
	        return true;
	    }

	}
	

	
}
