/**
 * Date: Dec 27, 2009
 * Project: PasswordJuggler
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.juggler.view;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.juggler.utils.Constants;
import com.juggler.utils.TempletUtil;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class SettingsActivity extends FooterActivity implements OnClickListener{
	
	private Button bNext,bPrev;
	private TextView tvChangePwd,tvAutoLock;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    setContentView(R.layout.settings_frame);

	    View details2 = (LinearLayout)findViewById(R.id.vDetails2);
	    details2.setVisibility(View.GONE);
	    View details3 = (LinearLayout)findViewById(R.id.vDetails3);
	    details3.setVisibility(View.GONE);
	    
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
		tvTitle.setText(getString(R.string.settings));
		
		bPrev = (Button)findViewById(R.id.butPrev);
		bPrev.setOnClickListener(this);
		
		bNext = (Button)findViewById(R.id.butNext);
	    bNext.setText(getString(R.string.save));
	    bNext.setOnClickListener(this);
	    
		//set screen type
		Constants.SCREEN_TYPE=Constants.SETTINGS;
		
		//attach events
		tvChangePwd = (TextView)findViewById(R.id.tvChangePassword);
		tvChangePwd.setOnClickListener(this);
		
		tvAutoLock = (TextView)findViewById(R.id.tvAutoLock);
		tvAutoLock.setOnClickListener(this);
		
	}

	/* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v) {
    	
    	if(v == bNext) 
 	    {
    		//clear text button
    		ToggleButton tbclear = (ToggleButton)findViewById(R.id.tbClearText);
    		
    		HashMap<String, String> map = new HashMap<String, String>();
    		map.put(TempletUtil.PREFS_CLEAR_TEXT, tbclear.isChecked()+"");
    		//save settings
    		TempletUtil.setSettings(this, map);
 	    	finish();
 	    	
 	    }
    	else if(v == bPrev){
    		finish();
    	}
    	else if(v == tvChangePwd)
    		startActivity(new Intent(this,NewPasswordAcivity.class));
    	else if(v==tvAutoLock)
    		startActivity(new Intent(this,AutoLockAcivity.class));	    
    }
	
}
