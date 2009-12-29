package com.juggler.view;

import android.app.Activity;
import android.os.Bundle;

import com.juggler.utils.FooterUtil;

public class FooterActivity extends Activity{
	private FooterUtil footutil;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        
    }
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		footutil = new FooterUtil(this);
        
        footutil.initialize(this);
	    super.onResume();
	}
	
}
