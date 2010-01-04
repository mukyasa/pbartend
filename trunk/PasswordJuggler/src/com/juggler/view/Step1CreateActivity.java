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
import com.juggler.utils.Constants;

public class Step1CreateActivity extends BaseActivity {
	private EditText etTitle,etURL;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.create_frame);
		initialize();
		
		super.onCreate(savedInstanceState);
	}

	private void initialize() {

		//set title
		TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText("");
			
		Button bNext = (Button)findViewById(R.id.butNext);
		bNext.setText(getString(R.string.next));
		
		etTitle = (EditText)findViewById(R.id.etTitle);
		etTitle.setOnTouchListener(this);
		etURL = (EditText)findViewById(R.id.etURL);
		etURL.setOnTouchListener(this);
		
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
	    	
	    	np.name =etTitle.getText().toString();
	    	np.url = etURL.getText().toString();
	    	
	    	Intent intent = new Intent(this, Step2CreateActivity.class);
	    	intent.putExtra(Constants.INTENT_EXTRA_STEP2_FIELD1, getString(R.string.username));
	    	intent.putExtra(Constants.INTENT_EXTRA_STEP2_FIELD2, getString(R.string.password));
	    	intent.putExtra(Constants.INTENT_EXTRA_IS_GENPASSWORD,  "false" );
	    	startActivity(intent);
	    	
	    }
	    
	    
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

				if(template.equals(getString(R.string.url)) || template.equals(getString(R.string.title)))
				{
					((EditText)v).setText("");
					((EditText)v).setTextColor(Color.BLACK);
				}
			} 
			
		}
		return false;
	
	}
}
