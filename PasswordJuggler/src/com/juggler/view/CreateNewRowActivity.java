package com.juggler.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.juggler.domain.NewPassword;
import com.juggler.domain.PasswordDetail;

public class CreateNewRowActivity extends BaseActivity {
	private EditText etTitle,etURL;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.create_frame);
		super.onCreate(savedInstanceState);
		initialize();
	}

	private void initialize() {

		//set title
		TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText(getString(R.string.newrow));
			
		//change text to email and password assume template picked
		etTitle = (EditText)findViewById(R.id.etTitle);
		etTitle.setOnTouchListener(this);
		etURL = (EditText)findViewById(R.id.etURL);
		etURL.setOnTouchListener(this);
		
		etURL.setText(getString(R.string.label));
		etTitle.setText(getString(R.string.value));
		
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View,
	 * android.view.MotionEvent)
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		super.onTouch(v, event);
		
		if (v instanceof EditText) {
			
			if (event.getAction() == MotionEvent.ACTION_UP) {
				String template = ((EditText)v).getText().toString();

				if(template.equals(getString(R.string.label)) || template.equals(getString(R.string.value)))
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
	    	String label = etURL.getText().toString();
	    	String value = etTitle.getText().toString();
	    	
	    	np.addTemplateSaver(label, value);
	    	//set for database
	    	PasswordDetail pd = new PasswordDetail(PasswordDetail.GENERIC, value, "");
	    	
	    	np.addNameValue(label, pd);
	    	
	    	finish();
	    	
	    }
	    
	    
	}
	
}
