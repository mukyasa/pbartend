package com.juggler.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.juggler.domain.NewPassword;
import com.juggler.domain.PasswordDetail;
import com.juggler.utils.Constants;

public class CreateNewRowActivity extends BaseActivity {
	private EditText etTitle,etURL;
	private String section="0";
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
		
		section =  (String) getIntent().getCharSequenceExtra(Constants.INTENT_EXTRA_SELECTED_SECTION);
		
		
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
	
	 protected Dialog onCreateDialog(int id) {
	    	
	    	return new AlertDialog.Builder(CreateNewRowActivity.this)
	        .setIcon(R.drawable.error)
	        .setMessage("Please fill in both values.")
	        .setTitle("Error")
	        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	               dismissDialog(0);
	            }
	        })      
	       .create();
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
	    	np.resetTemplateSaver();//clear out any old ones we dont need 
	    	String label = etURL.getText().toString();
	    	String value = etTitle.getText().toString();
	    	
	    	if(label.equals("") || value.equals(""))
	    		showDialog(0);
	    	else
	    	{
		    	np.addTemplateSaver(label, value);
		    	//set for database
		    	PasswordDetail pd = new PasswordDetail(Integer.valueOf(section).intValue(), value, "");
		    	
		    	np.addNameValue(label, pd);
		    	
		    	finish();
	    	}
	    	
	    }
	    
	    
	}
	
}
