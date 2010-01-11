/**
 * Date: Jan 4, 2010
 * Project: PasswordJuggler
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.juggler.view;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juggler.utils.Constants;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class AutoLockAcivity extends FooterActivity{
	
	private Button bNext,bPrev;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    setContentView(R.layout.settings_frame);
	    
	    View details = (LinearLayout)findViewById(R.id.vDetails);
	    details.setVisibility(View.GONE);
	    View details2 = (LinearLayout)findViewById(R.id.vDetails2);
	    details2.setVisibility(View.GONE);
	    
	    super.onCreate(savedInstanceState);
	}
	
	/* (non-Javadoc)
	 * @see com.juggler.view.FooterActivity#onResume()
	 */
	@Override
	protected void onResume() {
		initialize();
	    super.onResume();
	}
	
	private void initialize()
	{
		TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText(getString(R.string.autolock));
		
		bPrev = (Button)findViewById(R.id.butPrev);
		bPrev.setOnClickListener(this);
		
		bNext = (Button)findViewById(R.id.butNext);
	    bNext.setText(getString(R.string.save));
	    bNext.setOnClickListener(this);
	    
		//set screen type
		Constants.SCREEN_TYPE=Constants.SETTINGS;
	}

	/* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v) {
    	super.onClick(v);
    	if(v == bNext)
 	    	finish();
    	else if(v == bPrev)
    		finish();
	    
    }
}
