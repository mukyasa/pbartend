/**
 * Date: Oct 29, 2009
 * Project: PocketBartender
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.bartender.view;

import android.app.Activity;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;

import com.bartender.R;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class BaseActivity extends Activity implements OnTouchListener {
	
	 public boolean onTouch(View v, MotionEvent event) {
		    
		  	if(v instanceof Button )
		  	{
		  		if(event.getAction() == MotionEvent.ACTION_DOWN)
		  		{
		  			v.setBackgroundResource(R.drawable.button_over);
		  			v.setPadding(0, 0, 0, 10);
		  		}
		  		
		  		else if(event.getAction()== MotionEvent.ACTION_UP)
		  		{
		  			v.setBackgroundResource(R.drawable.button_bg);
		  			v.setPadding(0, 0, 0, 10);
		  		}
		  	}
		  	else if(v instanceof EditText)
		  	{
		  		if(event.getAction() == MotionEvent.ACTION_DOWN)
		  		{
		  			//clear base text
		  			if(((EditText)v).getText().toString().equals(this.getString(R.string.create_drink_title))
		  					||((EditText)v).getText().toString().equals(this.getString(R.string.instructionsText))) 
		  			{
		  				((EditText)v).setText("");
		  				((EditText)v).setTextColor(Color.BLACK);
		  			}
		  			
		  		}

		  	}
		  	
		  
		    return false;
	    }
}

