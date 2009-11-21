/**
 * Date: Nov 20, 2009
 * Project: FlashCard
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.card.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;

import com.card.R;
import com.card.util.AppUtil;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class UserPrefActivity extends Activity implements OnTouchListener {

	private CheckBox sound;
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.userpref);
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        
	        sound = (CheckBox)findViewById(R.id.cbSoundEffects);
	        sound.setChecked( AppUtil.getSound(this));
	        
	        Button done = (Button)findViewById(R.id.butDone);
	        done.setOnTouchListener(this);
	        
	    }
	  @Override
		protected void onStop() {
			super.onStop();
			//set in user pref
			// Save user preferences. We need an Editor object to
		      // make changes. All objects are from android.context.Context
		      SharedPreferences settings = getSharedPreferences(AppUtil.PREFS_NAME, 0);
		      SharedPreferences.Editor editor = settings.edit();
		      editor.putBoolean(AppUtil.PREF_SOUND, sound.isChecked());

		      // Don't forget to commit your edits!!!
		      editor.commit();

		}
	  public boolean onTouch(View v, MotionEvent event) {

	    	if(v instanceof Button )
		  	{
	    		
		  		if(event.getAction() == MotionEvent.ACTION_DOWN)
		  		{
		  			v.setBackgroundResource(R.drawable.button_hvr);
		  			v.setPadding(30, 0, 40, 5);
		  		}
		  		
		  		else if(event.getAction()== MotionEvent.ACTION_UP)
		  		{
		  			v.setBackgroundResource(R.drawable.button);
		  			v.setPadding(30, 0, 40, 5); 
		  			
		  			Intent intent = new Intent(this,Home.class);
		  			startActivity(intent);
		  			
		  		}
		  	}
	    	
	    	return false;
	    }
}
