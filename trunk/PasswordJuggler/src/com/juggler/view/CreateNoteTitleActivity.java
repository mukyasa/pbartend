/**
 * Date: Dec 25, 2009
 * Project: PasswordJuggler
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.juggler.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.juggler.domain.NewPassword;
import com.juggler.utils.Constants;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class CreateNoteTitleActivity extends Activity implements OnClickListener,OnTouchListener {

	private Button butNext,butPrev;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_frame);
        
        initialize();
    }
	
	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View,
	 * android.view.MotionEvent)
	 */
	public boolean onTouch(View v, MotionEvent event) {
		
		if (v instanceof EditText) {
			
			if (event.getAction() == MotionEvent.ACTION_UP) {
				String template = ((EditText)v).getText().toString();

				if(template.equals(getString(R.string.title)))
				{
					((EditText)v).setText("");
					((EditText)v).setTextColor(Color.BLACK);
				}
			} 
			
		}
		return false;
	
	}
	
	private void initialize(){
		
		//set title
		TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText("");
		
		//hide url field
		EditText etUrl = (EditText)findViewById(R.id.etURL);
		etUrl.setVisibility(View.GONE);
		
		EditText etTitle = (EditText)findViewById(R.id.etTitle);
		etTitle.setOnTouchListener(this);
		
		butNext= (Button)findViewById(R.id.butNext);
		butNext.setText(getString(R.string.next));
		butNext.setOnClickListener(this);
		
		butPrev= (Button)findViewById(R.id.butPrev);
		butPrev.setOnClickListener(this);
	}

	/* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v) {
    	
    	if(v==butNext)
    	{
    		NewPassword np = NewPassword.getInstance();
    		EditText name =  (EditText)findViewById(R.id.etTitle);
    		np.name = name.getText().toString();
    		
    		Intent intent = new Intent(this,CreateNoteActivity.class);
    		intent.putExtra(Constants.INTENT_EXTRA_SELECTED_TEXT,"");
    		startActivity(intent);
    	}
    	else
    	{
    		finish();
    	}
    	
	    
    }
}

