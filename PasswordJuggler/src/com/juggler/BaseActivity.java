package com.juggler;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class BaseActivity extends Activity implements OnClickListener,OnTouchListener{

	Button bNext,bPrev;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        initialize();
    }
	
	private void initialize() {
		bNext = (Button)findViewById(R.id.butNext);
		bNext.setOnClickListener(this);
		bNext.setOnTouchListener(this);
		
		bPrev = (Button)findViewById(R.id.butPrev);
		bPrev.setOnClickListener(this);
		bPrev.setOnTouchListener(this);
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View,
	 * android.view.MotionEvent)
	 */
	public boolean onTouch(View v, MotionEvent event) {
		
		if (v instanceof Button) {
			
			if(v==bNext)
			{
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					((Button)v).setBackgroundResource(R.drawable.next_button_on);
					((Button)v).setPadding(10,0,10,0);
				} 
				else{
					((Button)v).setBackgroundResource(R.drawable.next_button);
					((Button)v).setPadding(10,0,10,0);
				}
			}else if(v==bPrev){
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					((Button)v).setBackgroundResource(R.drawable.prev_button_on);
					((Button)v).setPadding(20,0,10,0);
				} 
				else{
					((Button)v).setBackgroundResource(R.drawable.prev_button);
					((Button)v).setPadding(20,0,10,0);
				}
			}
		}
		
		
		return false;
	}
	
	 /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v) {
 	   	
 	   if(v == bNext){
 		   //done or next
 		   
 	   }else if(v == bPrev){
 		   finish();
 	   }
    }
}
