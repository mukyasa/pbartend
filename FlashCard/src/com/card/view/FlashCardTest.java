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
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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
	private int countlabel=1;
	private int maxcount=0;
	private boolean isBack=true;
	private boolean isWrong=false;
	private final String CARD_NUMBER="Card Number: ";
	private final int MENU_RESULTS=0;
	private final int MENU_RETEST=1;
	private final int MENU_SHUFFLE=2;
	private final int MENU_NEW=3;
	
	@SuppressWarnings("unchecked")
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_frame);  
		//force to be landscape
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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
		        
		        TextView tvFlashCard = (TextView)findViewById(R.id.tvflashCard1);
		        tvFlashCard.setText(question);
		        
		        //set card number tag
		        TextView cardnumber = (TextView)findViewById(R.id.tvCardNumbr);
		        TextView cardnumber2 = (TextView)findViewById(R.id.tvCardNumbr2);
		        cardnumber.setText(CARD_NUMBER + "1");
		        cardnumber2.setText(CARD_NUMBER + "1");
		        
		        //the correct button
		        ImageView answered = (ImageView)findViewById(R.id.ivAnswered);
		        answered.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						ImageView answered = (ImageView)findViewById(R.id.ivAnswered);
						if(!isWrong)
						{
							answered.setBackgroundResource(R.drawable.wrong);
							isWrong=true;
						}
						else
						{
							answered.setBackgroundResource(R.drawable.correct);
							isWrong=false;
						}
							
                    }
		        	
		        });
		        
	        
        } catch (JSONException e) {
	        e.printStackTrace();
        }
		
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_RESULTS, 0, "Test Results").setIcon(R.drawable.results);
		menu.add(0, MENU_RETEST, 0, "Retest").setIcon(R.drawable.retest);
		menu.add(0, MENU_SHUFFLE, 0, "Shuffle").setIcon(R.drawable.shuffle);
		menu.add(0, MENU_NEW, 0, "New Card Set").setIcon(R.drawable.newcardset);
	    return true;
	}

	/* Handles item selections */
	public boolean onOptionsItemSelected(MenuItem item) {
		
	    switch (item.getItemId()) {
	    case MENU_RESULTS:
	    	return true;
	    case MENU_RETEST:
	        return true;
	    case MENU_SHUFFLE:
	    	return true;
	    case MENU_NEW:
	    	Intent intent = new Intent(this, Home.class);
			startActivity(intent);
	    	return true;
	    }
	    return false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    if (mGestureDetector.onTouchEvent(event))
	        return true;
	    else
	        return false;
	}
	
	protected class LearnGestureListener extends GestureDetector.SimpleOnGestureListener{
	    
		/* (non-Javadoc)
		 * @see android.view.GestureDetector.SimpleOnGestureListener#onDoubleTap(android.view.MotionEvent)
		 */
		@Override
		public boolean onDoubleTap(MotionEvent e) {

			try {
	            ApplicationHandler handler = ApplicationHandler.instance();
	            CardSets cardsets = handler.pickedSet;
	            JSONArray sets = cardsets.flashcards;
	            
	            if(isBack)//flick previous
	            {
	            	JSONArray terms = (JSONArray)sets.get(count);
	                String answer = (String)terms.get(1);
	                TextView tvFlashCard = (TextView)findViewById(R.id.tvflashCard2);
	                tvFlashCard.setText(answer);
	                
	            	// Get the ViewFlipper from the layout
	                ViewFlipper vf = (ViewFlipper) findViewById(R.id.details);
	                vf.setDisplayedChild(1);
	                // Set an animation from res/anim: 
	                vf.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_top_to_bottom));
	                vf.getAnimation(); 
	                
	                isBack=false;
	            }
	            else
	            {
	            	JSONArray terms = (JSONArray)sets.get(count);
	                String question = (String)terms.get(0);
	                TextView tvFlashCard = (TextView)findViewById(R.id.tvflashCard1);
	                tvFlashCard.setText(question);
	                
	            	// Get the ViewFlipper from the layout
	                ViewFlipper vf = (ViewFlipper) findViewById(R.id.details);
	                vf.setDisplayedChild(0);
	                // Set an animation from res/anim: 
	                vf.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_top_to_bottom));
	                vf.getAnimation(); 
	                
	                isBack=true;
	            }
	            
            } catch (NotFoundException e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
            } catch (JSONException e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
            }
	        return true;
		}
		
		@Override
	    public boolean onSingleTapUp(MotionEvent ev) {
	    	
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
	        
	    	try {
	    		//reset values on fling
	            isBack=true;
	            isWrong=false;
	            ImageView answered = (ImageView)findViewById(R.id.ivAnswered);
		        answered.setBackgroundResource(R.drawable.correct);
	            ApplicationHandler handler = ApplicationHandler.instance();
                CardSets cardsets = handler.pickedSet;
            	JSONArray sets = cardsets.flashcards;
	            
	            if(e1.getX() > e2.getX() && countlabel < maxcount)//flick next 
	            {
	            	count++;
	            	countlabel++;
	                JSONArray terms = (JSONArray)sets.get(count);
	                String question = (String)terms.get(0);
	                TextView tvFlashCard = (TextView)findViewById(R.id.tvflashCard1);
			        tvFlashCard.setText(question);
	                // Get the ViewFlipper from the layout
	                ViewFlipper vf = (ViewFlipper) findViewById(R.id.details);
	                vf.setDisplayedChild(0);
	                // Set an animation from res/anim: 
	                vf.setAnimation(AnimationUtils.loadAnimation(context,  R.anim.slide_left)); 
	                vf.getAnimation(); 
	            }
	            else if(count > 0 && e1.getX() < e2.getX())//flick previous
	            {
	            	count--;
	            	countlabel--;
	            	
	            	JSONArray terms = (JSONArray)sets.get(count);
	                String question = (String)terms.get(0);
	                TextView tvFlashCard = (TextView)findViewById(R.id.tvflashCard1);
			        tvFlashCard.setText(question);
			        
	            	// Get the ViewFlipper from the layout
	                ViewFlipper vf = (ViewFlipper) findViewById(R.id.details);
	                vf.setDisplayedChild(0);
	                // Set an animation from res/anim: 
	                vf.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_right));
	                vf.getAnimation(); 
	            }
	            
	            //set card number tag
		        TextView cardnumber = (TextView)findViewById(R.id.tvCardNumbr);
		        TextView cardnumber2 = (TextView)findViewById(R.id.tvCardNumbr2);
		        cardnumber.setText(CARD_NUMBER + countlabel);
		        cardnumber2.setText(CARD_NUMBER + countlabel);
	            
            } catch (NotFoundException e) {
	            e.printStackTrace();
            } catch (JSONException e) {
	            e.printStackTrace();
            }
	        
	        return true;
	    }

	}
	

	
}
