/**
 * Date: Nov 20, 2009
 * Project: FlashCard
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.flashcard.view;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.flashcard.R;
import com.flashcard.util.AppUtil;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$ 
 */ 
public class UserPrefActivity extends Activity implements OnTouchListener, OnClickListener {

	private CheckBox sound;
	private String fontsize; 
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.userpref);
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        
	        sound = (CheckBox)findViewById(R.id.cbSoundEffects);
	        sound.setChecked( AppUtil.getSound(this));
	        
	        RadioButton s =(RadioButton)findViewById(R.id.rbSmaller);
	        s.setOnClickListener(this);
	        RadioButton n =(RadioButton)findViewById(R.id.rbNormal);
	        n.setOnClickListener(this);
	        RadioButton b =(RadioButton) findViewById(R.id.rbBigger);
	        b.setOnClickListener(this);
	        RadioButton sup =(RadioButton) findViewById(R.id.rbAutoSize);
	        sup.setOnClickListener(this);
	        
	        //loop this or something
	        if(AppUtil.chooseFontSize(this, s.getText().toString()))
	        {
	        	s.setChecked(true);
	        	fontsize = s.getText().toString();
	        } 
	        else if(AppUtil.chooseFontSize(this, n.getText().toString()))
	        {
	        	n.setChecked(true);
	        	fontsize = n.getText().toString();
	        }
	        else if(AppUtil.chooseFontSize(this, b.getText().toString()))
	        {
	        	b.setChecked(true);
	        	fontsize = b.getText().toString();
	        }
	        else if(AppUtil.chooseFontSize(this, sup.getText().toString()))
	        {
	        	sup.setChecked(true);
	        	fontsize = sup.getText().toString();
	        }
	         
	         
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
		      editor.putString(AppUtil.PREF_FONT_SIZE,fontsize);

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
		  			
		  			finish();
		  		}
		  	}
	    	
	    	return false;
	    }
	public void onClick(View v) {
		
		fontsize = ((RadioButton)v).getText().toString();
		
	}
}
