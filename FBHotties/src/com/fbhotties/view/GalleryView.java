package com.fbhotties.view;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import android.widget.Gallery.LayoutParams;

import com.admob.android.ads.AdView;
import com.admob.android.ads.SimpleAdListener;
import com.fbhotties.handler.EndlessAdapter;
import com.fbhotties.utils.ViewerCountDown;
import com.fbhotties.utils.WebFileLoader;
import com.flashcard.R;
import com.flashcard.domain.CardSet;
import com.flashcard.util.AppUtil;
import com.flashcard.util.Constants;
import com.flashcard.view.CardSetList;

public class GalleryView extends Activity implements
AdapterView.OnItemSelectedListener, ViewSwitcher.ViewFactory, OnTouchListener{
	
	 private ImageSwitcher mSwitcher;
	 //private RatingBar mRatingBar,mSmallRatingBar;
	 private Gallery g;
	 private AdView mAd;  // TODO You will need an AdView (this one is defined in res/layout/lunar_layout.xml).
	 private WebFileLoader imageLoader=null;
	 private Integer[] mImageIds = {};
	 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_view);
        imageLoader =  WebFileLoader.getInstance();

        mSwitcher = (ImageSwitcher) findViewById(R.id.switcher);
        mSwitcher.setFactory(this);
        mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_in));
        mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_out));
        mSwitcher.setOnTouchListener(this);

        g = (Gallery) findViewById(R.id.gallery);
        g.setAdapter(new ImageAdapter(this));
        g.setOnItemSelectedListener(this);
        g.setVisibility(Gallery.INVISIBLE);
        
       /* for testing ads 
        AdManager.setTestDevices( new String[] {                 
        	     AdManager.TEST_EMULATOR            // Android emulator
       } );*/
        mAd = (AdView) findViewById(R.id.ad);
        mAd.setAdListener(new HotGirlAdListener());
        
    }
    

	/***
	 * When the gallery is picked display in the main window
	 */
    public void onItemSelected(AdapterView parent, View v, int position, long id) {
    	ViewerCountDown vcd = ViewerCountDown.getInstance(g,mSwitcher);
    	//mRatingBar.setRating(0f);
       // mSmallRatingBar.setRating(0f);
    	
    	vcd.cancel();
    	vcd.start();
    	
    	mSwitcher.setImageResource(mImageIds[position]);
	    
    }
    
	/**
	 * When the main image is touched show the gallery and ads
	 */
    public boolean onTouch(View v, MotionEvent event) {
    	v.setPadding(0, 46, 0, 0);
    	g.setVisibility(Gallery.VISIBLE);
    	mAd.setVisibility( View.VISIBLE  );
         // The ad will fade in over 0.4 seconds.
 		AlphaAnimation animation = new AlphaAnimation( 0.0f, 1.0f );
 		animation.setDuration( 400 );
 		animation.setFillAfter( true );
 		animation.setInterpolator( new AccelerateInterpolator() );
 		mAd.startAnimation( animation );
    	
    	ViewerCountDown vcd = ViewerCountDown.getInstance(g,v);
    	vcd.cancel();//clear old timer
    	vcd.start();//start timer
    	
	    return false;
    }
    
	/* (non-Javadoc)
     * @see android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(android.widget.AdapterView)
     */
    public void onNothingSelected(AdapterView parent) {
	    // TODO Auto-generated method stub
	    
    }

	/* (non-Javadoc)
     * @see android.widget.ViewSwitcher.ViewFactory#makeView()
     */
    public View makeView() {
        ImageView i = new ImageView(this);
        
        i.setBackgroundColor(0xFF000000);
        i.setScaleType(ImageView.ScaleType.FIT_CENTER);
        i.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
        return i;
    }
    
    public class ImageAdapter extends EndlessAdapter {

    	private RotateAnimation rotate = null;

    	ImageAdapter(ArrayList<CardSet> list) {
			super(new ArrayAdapter<CardSet>(CardSetList.this,R.layout.cardlist_item, android.R.id.text1, list),context);
			
			rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			rotate.setDuration(600);
			rotate.setRepeatMode(Animation.RESTART);
			rotate.setRepeatCount(Animation.INFINITE);
		}

		protected View getPendingView(ViewGroup parent) {
			View row = getLayoutInflater().inflate(R.layout.cardlist_item, null);

			View child = row.findViewById(android.R.id.text1);

			child.setVisibility(View.GONE);

			child = row.findViewById(R.id.throbber);
			View wrapper  = row.findViewById(R.id.throbberWrapper);
			wrapper.setVisibility(View.VISIBLE); 
			child.startAnimation(rotate);

			return (row);
		}

		@SuppressWarnings("unchecked")
		protected void rebindPendingView(int position, View row) {
			if(row != null)
			{
				View child = row.findViewById(android.R.id.text1);
				
				//set up the text here
				ArrayAdapter<CardSet> wrapped = (ArrayAdapter<CardSet>) getWrappedAdapter();
				CardSet cardset = (CardSet)wrapped.getItem(position);
				//get the textview and set the text
		        
		        String tabs ="\t\t";
		        String title;
		        if(cardset.title.length() > 37)//trunc at 37 char
		        	title = cardset.title.substring(0, 32) + "...";
		        else
		        	title=cardset.title;
		        	
		        String count = cardset.cardCount.toString();
		        if(count.length() == 1)
		        	count = " " +count;
		        
		        if(count.length() > 2)
		        	tabs = "\t";
		        
		        child.setVisibility(View.VISIBLE);
		        ((TextView) child).setText(count + tabs + title);

				child = row.findViewById(R.id.throbber);
				View wrapper  = row.findViewById(R.id.throbberWrapper);
				wrapper.setVisibility(View.GONE);
				child.clearAnimation();
			}
		}

		protected boolean appendInBackground() {
			SystemClock.sleep(2000); // pretend to do work
			ArrayAdapter<CardSet> a = (ArrayAdapter<CardSet>) getWrappedAdapter();
			
			int nextCardSet = (a.getCount()/Constants.CARDS_PER_PAGE)+1;
			//Log.v(getClass().getSimpleName(), "nextCardSet=" + nextCardSet);
            JSONArray sets = AppUtil.getQuizletData(AppUtil.searchTerm, sortType,nextCardSet);
            ArrayList<CardSet> cardsets = AppUtil.createNewCardSetArrayList(new ArrayList<CardSet>(),sets);
	    	
			for (CardSet item : cardsets) { 
				a.add(item);
			}
			
			return (a.getCount() <= Constants.TOTAL_RESULTS); //on return true if this is true
		}
    	

    }

    
    
    
    
    
    
    
    
    
    
    
    
    /*******************************************************
     * Ad mob code
     * @author Darren
     *******************************************************
     */
    private class HotGirlAdListener extends SimpleAdListener
    {

		/* (non-Javadoc)
		 * @see com.admob.android.ads.AdView.SimpleAdListener#onFailedToReceiveAd(com.admob.android.ads.AdView)
		 */
		@Override
		public void onFailedToReceiveAd(AdView adView)
		{
			// TODO Auto-generated method stub
			super.onFailedToReceiveAd(adView);
		}

		/* (non-Javadoc)
		 * @see com.admob.android.ads.AdView.SimpleAdListener#onFailedToReceiveRefreshedAd(com.admob.android.ads.AdView)
		 */
		@Override
		public void onFailedToReceiveRefreshedAd(AdView adView)
		{
			// TODO Auto-generated method stub
			super.onFailedToReceiveRefreshedAd(adView);
		}

		/* (non-Javadoc)
		 * @see com.admob.android.ads.AdView.SimpleAdListener#onReceiveAd(com.admob.android.ads.AdView)
		 */
		@Override
		public void onReceiveAd(AdView adView)
		{
			// TODO Auto-generated method stub
			super.onReceiveAd(adView);
		}

		/* (non-Javadoc)
		 * @see com.admob.android.ads.AdView.SimpleAdListener#onReceiveRefreshedAd(com.admob.android.ads.AdView)
		 */
		@Override
		public void onReceiveRefreshedAd(AdView adView)
		{
			// TODO Auto-generated method stub
			super.onReceiveRefreshedAd(adView);
		}
    	
    }

}