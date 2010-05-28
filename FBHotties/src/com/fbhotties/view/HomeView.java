package com.fbhotties.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ViewSwitcher;
import android.widget.Gallery.LayoutParams;
import android.widget.RatingBar.OnRatingBarChangeListener;

import com.fbhotties.utils.ViewerCountDown;

public class HomeView extends Activity implements
AdapterView.OnItemSelectedListener, ViewSwitcher.ViewFactory, OnTouchListener,OnRatingBarChangeListener{
	
	 private ImageSwitcher mSwitcher;
	 private RatingBar mRatingBar,mSmallRatingBar;
	 private Gallery g;
	 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_view);

        mRatingBar = (RatingBar)findViewById(R.id.rating_bar);
        mRatingBar.setOnRatingBarChangeListener(this);
        
        mSmallRatingBar = (RatingBar) findViewById(R.id.small_ratingbar);
        
        mSwitcher = (ImageSwitcher) findViewById(R.id.switcher);
        mSwitcher.setFactory(this);
        mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_in));
        mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_out));
        mSwitcher.setOnTouchListener(this);

        g = (Gallery) findViewById(R.id.gallery);
        g.setAdapter(new ImageAdapter(this));
        g.setOnItemSelectedListener(this);
        g.setVisibility(Gallery.INVISIBLE);
    }

	/* (non-Javadoc)
     * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android.widget.AdapterView, android.view.View, int, long)
     */
    public void onItemSelected(AdapterView parent, View v, int position, long id) {
    	ViewerCountDown vcd = ViewerCountDown.getInstance(g,mSwitcher);
    	mRatingBar.setRating(0f);
        mSmallRatingBar.setRating(0f);
    	
    	vcd.cancel();
    	vcd.start();
    	mSwitcher.setImageResource(mImageIds[position]);
	    
    }

    /* (non-Javadoc)
     * @see android.widget.RatingBar.OnRatingBarChangeListener#onRatingChanged(android.widget.RatingBar, float, boolean)
     */
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
    	final float ratingBarStepSize = ratingBar.getStepSize();
    	
    	if(rating>0)
    		mSmallRatingBar.setRating(rating);
	    
    }
    
	/* (non-Javadoc)
     * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
     */
    public boolean onTouch(View v, MotionEvent event) {
    	v.setPadding(0, 70, 0, 0);
    	g.setVisibility(Gallery.VISIBLE);
    	
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
    
    public class ImageAdapter extends BaseAdapter {
        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView i = new ImageView(mContext);
            i.setImageResource(mThumbIds[position]);
            i.setAdjustViewBounds(true);
            i.setLayoutParams(new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            return i;
        }

        private Context mContext;

    }
    
    private Integer[] mThumbIds = {R.drawable.p00006,R.drawable.p00008,R.drawable.p00387,R.drawable.p00388
    		,R.drawable.p00389,R.drawable.p00393,R.drawable.p00395,R.drawable.p00396,R.drawable.p00398
    		,R.drawable.p00402,R.drawable.p00404,R.drawable.p00406,R.drawable.p00411};

    private Integer[] mImageIds = {R.drawable.p00006,R.drawable.p00008,R.drawable.p00387,R.drawable.p00388
    		,R.drawable.p00389,R.drawable.p00393,R.drawable.p00395,R.drawable.p00396,R.drawable.p00398
    		,R.drawable.p00402,R.drawable.p00404,R.drawable.p00406,R.drawable.p00411};



}