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

import com.bartender.R;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

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
		  
		    return false;
	    }
}
