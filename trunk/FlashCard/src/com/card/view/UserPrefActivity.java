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

import com.card.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class UserPrefActivity extends Activity implements OnTouchListener {
	
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.userpref);
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        
	        Button done = (Button)findViewById(R.id.butDone);
	        done.setOnTouchListener(this);
	        
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
