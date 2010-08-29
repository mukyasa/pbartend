/**
 * Date: Oct 29, 2009
 * Project: PocketBartender
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.drinkmixer.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;

import com.admob.android.ads.AdManager;
import com.admob.android.ads.AdView;
import com.admob.android.ads.SimpleAdListener;
import com.drinkmixer.R;
import com.drinkmixer.utils.Constants;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class BaseActivity extends Activity implements OnTouchListener {

	protected int DIALOG_TYPE_ERROR = 0;
	protected int DIALOG_TYPE_SUCCESS = 1;
	private AdView mAd; 

	protected Dialog onCreateDialog(int id) {

		if (id == DIALOG_TYPE_ERROR) {
			return new AlertDialog.Builder(BaseActivity.this).setIcon(
					R.drawable.info).setMessage(
					"Looks like you forgot to fill in something.").setTitle(
					"Add New Error").setNegativeButton("Close",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

							dismissDialog(DIALOG_TYPE_ERROR);
						}
					}).create();
		} else {
			return new AlertDialog.Builder(BaseActivity.this).setIcon(
					R.drawable.info).setMessage(
					"You have succesfully added a new item.").setTitle(
					"Add New").setNegativeButton("Close",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

							dismissDialog(DIALOG_TYPE_SUCCESS);
						}
					}).create();
		}
	}

	protected void initialize(){
		
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
	protected class AdMobListener extends SimpleAdListener
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
	
	public boolean onTouch(View v, MotionEvent event) {

		if (v instanceof Button) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				v.setBackgroundResource(R.drawable.button_over);
				v.setPadding(0, 0, 0, 10);
			}

			else if (event.getAction() == MotionEvent.ACTION_UP) {
				v.setBackgroundResource(R.drawable.button_bg);
				v.setPadding(0, 0, 0, 10);
			}
		} else if (v instanceof EditText) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				// clear base text
				if (((EditText) v).getText().toString().equals(
						this.getString(R.string.create_drink_title))
						|| ((EditText) v).getText().toString().equals(
								this.getString(R.string.instructionsText))
						|| ((EditText) v).getText().toString().equals(
								this.getString(R.string.newLiquorName))
						|| ((EditText) v).getText().toString().equals(
								this.getString(R.string.newMixerName))
						|| ((EditText) v).getText().toString().equals(
								this.getString(R.string.newGarnishName))) {
					((EditText) v).setText("");
					((EditText) v).setTextColor(Color.BLACK);
				}

			}

		}

		return false;
	}
}
