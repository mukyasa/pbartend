package com.drinkmixer.view;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;

import com.admob.android.ads.AdManager;
import com.admob.android.ads.AdView;
import com.admob.android.ads.SimpleAdListener;
import com.drinkmixer.R;
import com.drinkmixer.dao.DetailDAO;
import com.drinkmixer.dao.MixerDbHelper;
import com.drinkmixer.domain.DetailsDomain;
import com.drinkmixer.utils.Constants;

public class DetailsView extends ActivityView {

	private AdView mAd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details); 
		myDatabaseAdapter = MixerDbHelper.getInstance(this);
		initComponents();
		
		if(Constants.showAds)
		{
			AdManager.setAllowUseOfLocation(true);
 		
			mAd = (AdView) findViewById(R.id.ad);
			mAd.setAdListener(new AdMobListener());
			mAd.setVisibility( View.VISIBLE  );
	        // The ad will fade in over 0.4 seconds.
	 		AlphaAnimation animation = new AlphaAnimation( 0.0f, 1.0f );
	 		animation.setDuration( 400 );
	 		animation.setFillAfter( true );
	 		animation.setInterpolator( new AccelerateInterpolator() );
	 		mAd.startAnimation( animation );
		}
	}
	
	 private class AdMobListener extends SimpleAdListener
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
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		setDomain();
	    super.onResume();
	}
	
	private void initComponents() {
		drinkdetail = new DetailsDomain();
		drinkdao = new DetailDAO();
		
		selectedRow = getIntent().getLongExtra(ListViews.INTENT_EXTRA_SELECTED_ROW, 0);
		
		findAndSetView();
		
		drinkdao.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
		drinkdetail.id=((int) selectedRow);
		
		if (selectedRow > 0) {
			drinkdao.load(this,drinkdetail);
		}
		
		if (drinkdetail.id > 0) {
			//this one has a drink name 
			setViewItems();
			
		} 
		    
	}

}
;