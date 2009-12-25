package com.juggler.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.juggler.domain.NewPassword;

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
		etURL = (EditText)findViewById(R.id.etURL);
		
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
	    	np.catId=-1;
	    	
	    	Intent intent = new Intent(this, Step2CreateActivity.class);
	    	startActivity(intent);
	    	
	    }
	    
	    
	}
	
}
