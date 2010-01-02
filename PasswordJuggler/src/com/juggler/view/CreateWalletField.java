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

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.juggler.domain.NewPassword;
import com.juggler.domain.PasswordDetail;
import com.juggler.utils.Constants;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class CreateWalletField extends BaseActivity{
	
	private Button bNext;
	private int id;
	private EditText etTitle;
	private Intent selectedIntent;
	private CharSequence label;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    setContentView(R.layout.create_frame);

	    bNext = (Button)findViewById(R.id.butNext);
	    bNext.setText(getString(R.string.save));
	    
	    //hide the url field
	    EditText etURL = (EditText)findViewById(R.id.etURL);
	    etURL.setVisibility(View.GONE);
	    
	    etTitle = (EditText)findViewById(R.id.etTitle);
		//get Intent then set text
		selectedIntent = getIntent();
		CharSequence text =  selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_SELECTED_TEXT);
		id =  selectedIntent.getIntExtra(Constants.INTENT_EXTRA_SELECTED_FIELD_ID,-1);
		label =  selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_SELECTED_LABEL);
		etTitle.setText(text.toString());
		etTitle.setOnTouchListener(this);
		//if the title has been changed make it black
		if(!text.toString().equals(label.toString()))
				etTitle.setTextColor(Color.BLACK);
	    
		TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
	    tvTitle.setText(text.toString());
	    
	    super.onCreate(savedInstanceState);
	}
	
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		super.onTouch(v, event);
		
		if (v instanceof EditText) {
			
			if (event.getAction() == MotionEvent.ACTION_UP) {
				String template = ((EditText)v).getText().toString();

				if(template.equals(label.toString()))
				{
					((EditText)v).setText("");
					((EditText)v).setTextColor(Color.BLACK);
				}
			} 
			
		}
		return false;
		
	}
	
	/* (non-Javadoc)
	 * @see com.juggler.view.BaseActivity#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		super.onClick(v);
	    
	    if(v == bNext)
	    {
	    	NewPassword np = NewPassword.getInstance();
	    	String value = etTitle.getText().toString();
	    	np.addTemplateSaver(id+"", value);
	    	//set for database
	    	
	    	PasswordDetail pd = new PasswordDetail(PasswordDetail.GENERIC, value, "");
	    	
	    	if(label.equals(getString(R.string.title)))
	    		np.name=value;
	    	else
	    		np.addNameValue(label.toString(), pd);
	    	
	    	finish();
	    	
	    }
	}
}
