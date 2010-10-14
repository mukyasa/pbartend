/**
 * Date: Nov 16, 2009
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

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
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

import com.flashcard.R;
import com.flashcard.domain.CardSet;
import com.flashcard.domain.FlashCard;
import com.flashcard.handler.ApplicationHandler;
import com.flashcard.util.AppUtil;
import com.flashcard.util.Constants;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class FlashCardTest extends Activity {
	
	private GestureDetector mGestureDetector;
	private Context context;
	private int maxcount=0;
	private boolean isBack=true;
	private boolean isWrong=false;
	private final String CARD_NUMBER="Card Number: ";
	private final int MENU_RESULTS=0;
	private final int MENU_RETEST=1;
	private final int MENU_SHUFFLE=2;
	private final int MENU_NEW=3;
	private final int MENU_BOOKMARK=4;
	private final int MENU_USER_PREF=6;
	private final int MENU_BOOKMARKS=7;
	private final int MENU_OFFLINECARDS=8;
	private final int MENU_SWAP=9;
	private  TextView cardnumber;
	private TextView cardnumber2;
	private TextView tvFlashCard;
	private boolean cardfinished=false;
	private boolean isSound=false;
	private MediaPlayer mp;
	private final int DIALOG_ERROR=0;
	
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
		AppUtil apput = AppUtil.getInstance();
		if(AppUtil.getSawDirections(this) <=1 && !apput.isCalled)
		{
			apput.isCalled=true;
    		startActivity(new Intent(this,InstructionsActivity.class));
		}
    }
	
    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {
    	//set up cardset called here to avoid back button problems
    	try {
			initalize();
		} catch (Exception e) {
			initalizeError();
		}
        super.onResume();
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
       //save the last card viewed
    	AppUtil.setLastVeiwedCard(this, Constants.count);
    	
        super.onSaveInstanceState(outState);
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	maxcount=0;
    }
    
	private void initalize() throws Exception{
		
		//get user prefs
		isSound = AppUtil.getSound(this);
		mp = MediaPlayer.create(context, R.raw.slide);
		 
        ApplicationHandler handler = ApplicationHandler.instance();
        CardSet cardset = handler.currentlyUsedSet;
        maxcount = cardset.cardCount;
        TextView tvTitle = (TextView)findViewById(R.id.tvSetTitle);
        tvTitle.setText(cardset.title);
        ArrayList<FlashCard> sets = cardset.flashcards;
        
        //save currrent card for reloading
        FlashCard terms = (FlashCard)sets.get(AppUtil.getLastVeiwedCard(this));
        terms.wasSeen=true;
        
        tvFlashCard = (TextView)findViewById(R.id.tvflashCard1);
        tvFlashCard.setText(terms.question);
        tvFlashCard.setTextSize(AppUtil.getFontSize(this,terms.question,terms));
        
        //set card number tag
        cardnumber = (TextView)findViewById(R.id.tvCardNumbr);
        cardnumber2 = (TextView)findViewById(R.id.tvCardNumbr2);
        cardnumber.setText(CARD_NUMBER + (AppUtil.getLastVeiwedCard(this)+1) +" of " + maxcount);
        cardnumber2.setText(CARD_NUMBER + (AppUtil.getLastVeiwedCard(this)+1) +" of " + maxcount);
        
        if(!"".equals(terms.imageURL) && AppUtil.isFlipped)
        	AppUtil.getsizedDrawableFromURL(terms.imageURL,context,tvFlashCard);
        
        //the correct button
        ImageView answered = (ImageView)findViewById(R.id.ivAnswered);
        answered.setOnClickListener(new OnClickListener() {
            
			public void onClick(View v) {
				ApplicationHandler handler = ApplicationHandler.instance();
	            CardSet cardset = handler.currentlyUsedSet;
	            ArrayList<FlashCard> sets = cardset.flashcards;
	            FlashCard card = (FlashCard)sets.get(Constants.count);
				ImageView answered = (ImageView)findViewById(R.id.ivAnswered);
				if(!isWrong)
				{
					answered.setBackgroundResource(R.drawable.wrong);
					card.isCorrect=false;
					isWrong=true;
				}
				else
				{
					answered.setBackgroundResource(R.drawable.correct);
					card.isCorrect=true;
					isWrong=false;
				}
					
            }
        	
        });
		
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_RETEST, 0, "Retest").setIcon(R.drawable.retest);
		menu.add(0, MENU_SHUFFLE, 0, "Shuffle").setIcon(R.drawable.shuffle);
		menu.add(0, MENU_BOOKMARK, 0, "Bookmark").setIcon(R.drawable.bookmark);
		menu.add(0, MENU_RESULTS, 0, getResources().getString(R.string.results)).setIcon(R.drawable.results);
		menu.add(0, MENU_SWAP, 0, "Reverse").setIcon(R.drawable.swap);
		menu.add(0, MENU_NEW, 0, getResources().getString(R.string.home)).setIcon(R.drawable.newcardset);
		menu.add(0, MENU_USER_PREF, 0, "Preferences").setIcon(android.R.drawable.ic_menu_preferences);
		menu.add(0, MENU_BOOKMARKS, 0, "View Bookmarks").setIcon(R.drawable.bookmark);
		menu.add(0, MENU_OFFLINECARDS, 0, "View Offline Cards").setIcon(R.drawable.bookmark);
		
	    return true;
	}

	/* Handles item selections */
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		ApplicationHandler handler = ApplicationHandler.instance();
        CardSet cardset = handler.currentlyUsedSet;
        ViewFlipper vf;
        
	    switch (item.getItemId()) {
    	case MENU_SWAP:
	    	AppUtil.switchQuestionAnswer(context, cardset);
	    	vf = (ViewFlipper) findViewById(R.id.details);
            vf.setDisplayedChild(0);
            vf.getAnimation();
            isBack=true;
	    	try {
				initalize();
			} catch (Exception e1) {
				initalizeError();
			}
	    	return true;
    	case MENU_RESULTS:
	    	intent = new Intent(this, Results.class);
			startActivity(intent);
	    	return true;
	    case MENU_USER_PREF:
 	    	intent = new Intent(this, UserPrefActivity.class);
 			startActivity(intent);
 	    	return true;
	    case MENU_BOOKMARKS:
 	    	intent = new Intent(this, BookmarkList.class);
 			startActivity(intent);
 	    	return true;
	    case MENU_OFFLINECARDS:
 	    	intent = new Intent(this, OfflineCardsList.class);
 			startActivity(intent);
 	    	return true;
	    case MENU_RETEST:
	    	Constants.count=0;
	    	Constants.countlabel=1;
	    	
	        ArrayList<FlashCard> sets = cardset.flashcards;

	        Iterator<FlashCard> iter = sets.iterator();
	        while(iter.hasNext())
	        {
	        	FlashCard card = (FlashCard)iter.next();
	        	card.isCorrect=true;
	        	card.wasSeen=false;
	        }
	        
	    	FlashCard terms = (FlashCard)sets.get(0);
	        terms.wasSeen=true;
	        
	        tvFlashCard.setText(terms.question);
	        if(!"".equals(terms.imageURL) && AppUtil.isFlipped)
	        	AppUtil.getsizedDrawableFromURL(terms.imageURL,context,tvFlashCard);
	        else
	        	tvFlashCard.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
	        
	        //set card number tag
	        cardnumber.setText(CARD_NUMBER + "1 of " + sets.size());
	        cardnumber2.setText(CARD_NUMBER +  "1 of " + sets.size());
	        vf = (ViewFlipper) findViewById(R.id.details);
            vf.setDisplayedChild(0);
            vf.getAnimation();
            isBack=true;
	        return true;
	    case MENU_SHUFFLE:
	    	AppUtil.shuffleCard(cardset);
	    	vf = (ViewFlipper) findViewById(R.id.details);
            vf.setDisplayedChild(0);
            vf.getAnimation();
            isBack=true;
	    	try {
				initalize();
			} catch (Exception e1) {
				initalizeError();
			}
	    	return true;
	    case MENU_NEW:
	    	intent = new Intent(this, Home.class);
			startActivity(intent);
	    	return true;
	    case MENU_BOOKMARK:
	    	try {
	                AppUtil.setBookmarks(this,cardset);
                } catch (JSONException e) {
	                e.printStackTrace();
                }
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
	
	private void initalizeError()
	{
		//alert box here
		showDialog(DIALOG_ERROR);
	}
	
	 protected Dialog onCreateDialog(int id) {
	    	
    	return new AlertDialog.Builder(FlashCardTest.this)
        .setIcon(R.drawable.error)
        .setMessage("Whoa! Some major error happen? If you continue to get this error contact support.")
        .setTitle("Error")
        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
               dismissDialog(DIALOG_ERROR);
            }
        })      
       .create();
	    }
	 
	protected class LearnGestureListener extends GestureDetector.SimpleOnGestureListener{

		
		/* (non-Javadoc)
		 * @see android.view.GestureDetector.SimpleOnGestureListener#onDoubleTap(android.view.MotionEvent)
		 */
		@Override
		public boolean onDoubleTap(MotionEvent e) {

            ApplicationHandler handler = ApplicationHandler.instance();
            CardSet cardsets = handler.currentlyUsedSet;
            ArrayList<FlashCard> sets = cardsets.flashcards;
            ImageView answered = (ImageView)findViewById(R.id.ivAnswered);
            
            if(isBack)//flick over
            {
            	FlashCard terms = (FlashCard)sets.get(Constants.count);
            	terms.wasSeen=true;
                TextView tvFlashCard = (TextView)findViewById(R.id.tvflashCard2);
                tvFlashCard.setText(terms.answer);
                tvFlashCard.setTextSize(AppUtil.getFontSize(context,terms.answer,terms));
                Log.v("",AppUtil.isFlipped+"");
                if(!"".equals(terms.imageURL) && !AppUtil.isFlipped)
                	AppUtil.getsizedDrawableFromURL(terms.imageURL,context,tvFlashCard);
                else
                	tvFlashCard.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                
                if(terms.isCorrect)
                	answered.setBackgroundResource(R.drawable.correct);
                else
                	answered.setBackgroundResource(R.drawable.wrong);
                
            	// Get the ViewFlipper from the layout
                ViewFlipper vf = (ViewFlipper) findViewById(R.id.details);
                vf.setDisplayedChild(1);
                // Set an animation from res/anim: 
                vf.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_top_to_bottom));
                vf.getAnimation(); 
                
                isBack=false;
            }
            else // flick front 
            {
            	FlashCard terms = (FlashCard)sets.get(Constants.count);
            	terms.wasSeen=true;
                TextView tvFlashCard = (TextView)findViewById(R.id.tvflashCard1);
                tvFlashCard.setText(terms.question);
                tvFlashCard.setTextSize(AppUtil.getFontSize(context,terms.question,terms));
                Log.v("",AppUtil.isFlipped+"");
                if(!"".equals(terms.imageURL) && AppUtil.isFlipped)
                	AppUtil.getsizedDrawableFromURL(terms.imageURL,context,tvFlashCard);
                else
                	tvFlashCard.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                
                if(terms.isCorrect)
                	answered.setBackgroundResource(R.drawable.correct);
                else
                	answered.setBackgroundResource(R.drawable.wrong);
                
            	// Get the ViewFlipper from the layout
                ViewFlipper vf = (ViewFlipper) findViewById(R.id.details);
                vf.setDisplayedChild(0);
                // Set an animation from res/anim: 
                vf.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_top_to_bottom));
                vf.getAnimation(); 
                
                isBack=true;
                
                
                //show layover
                
                
                
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
				CardSet cardsets = handler.currentlyUsedSet;
				ArrayList<FlashCard> sets = cardsets.flashcards;
				// Get the ViewFlipper from the layout
				ViewFlipper vf = (ViewFlipper) findViewById(R.id.details);
				vf.setDisplayedChild(0);
				
				if(e1.getX() > e2.getX() && Constants.countlabel < maxcount)//flick next 
				{
					Constants.count++;
					Constants.countlabel++;
					
					if(isSound)
						mp.start();
					
					FlashCard terms = (FlashCard)sets.get(Constants.count);
				    
				    tvFlashCard.setText(terms.question);
				    tvFlashCard.setTextSize(AppUtil.getFontSize(context,terms.question,terms));
				    //check for images
				    if(!"".equals(terms.imageURL) && AppUtil.isFlipped)
				    	AppUtil.getsizedDrawableFromURL(terms.imageURL,context,tvFlashCard);
				    
				    terms.wasSeen=true;
				    // Set an animation from res/anim: 
				    // Get the ViewFlipper from the layout
				    vf.setAnimation(AnimationUtils.loadAnimation(context,  R.anim.slide_left)); 
				    
				}
				else if(Constants.count > 0 && e1.getX() < e2.getX())//flick previous
				{
					Constants.count--;
					Constants.countlabel--;
					
					if(isSound)
						mp.start();
					
					FlashCard terms = (FlashCard)sets.get(Constants.count);
				    tvFlashCard.setText(terms.question);
				    tvFlashCard.setTextSize(AppUtil.getFontSize(context,terms.question,terms));
				    //check for images
				    if(!"".equals(terms.imageURL)  && AppUtil.isFlipped)
				    	AppUtil.getsizedDrawableFromURL(terms.imageURL,context,tvFlashCard);
				    
				    terms.wasSeen=true;
				    // Set an animation from res/anim: 
					// Get the ViewFlipper from the layout
				    vf.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_right));
				}
				
				vf.getAnimation(); 
				
				//set card number tag
				cardnumber.setText(CARD_NUMBER + Constants.countlabel + " of " +  sets.size() );
				cardnumber2.setText(CARD_NUMBER + Constants.countlabel + " of " +  sets.size());
				
				
				if(Constants.countlabel == maxcount && !cardfinished)
				{
					if(isSound)
					{
				    	MediaPlayer mpfinish = MediaPlayer.create(context, R.raw.wind);
				    	mpfinish.start();
					}
				    cardfinished=true;
				}
			} catch (Exception e) {
				showDialog(DIALOG_ERROR);
			} 
	        
	        return true;
	    }

	}
	
}
