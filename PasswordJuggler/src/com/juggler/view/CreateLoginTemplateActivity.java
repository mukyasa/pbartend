package com.juggler.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.TextView;

import com.juggler.domain.NewPassword;
import com.juggler.domain.PasswordDetail;
import com.juggler.utils.Constants;

public class CreateLoginTemplateActivity extends BaseActivity implements OnTouchListener {
	private CharSequence text,url;
	private EditText etTitle,etURL;
	private long selectedRow;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.create_frame);
		super.onCreate(savedInstanceState);
		initialize();
	}

	private void initialize() {

		//set title
		Intent selectedIntent = getIntent();
		text =  selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_SELECTED_TEXT);
		url =  selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_SELECTED_URL);
		selectedRow =  selectedIntent.getLongExtra(Constants.INTENT_EXTRA_SELECTED_ROW, 0);
		TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText(text);
		
		//change text to email and password assume templete picked
		etTitle = (EditText)findViewById(R.id.etTitle);
		etTitle.setOnTouchListener(this);
		etURL = (EditText)findViewById(R.id.etURL);
		etURL.setOnTouchListener(this);
		
		etURL.setText(selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_STEP2_FIELD1));
		etTitle.setText(selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_STEP2_FIELD2));
		
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		super.onTouch(v, event);
		
		if (v instanceof EditText) {
			
			if (event.getAction() == MotionEvent.ACTION_UP) {
				String template = ((EditText)v).getText().toString();

				if(template.equals(getString(R.string.email))||template.equals(getString(R.string.password)))
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
	    	np.name =text.toString();
		    np.catId=selectedRow;
	    	np.url=url.toString();
		    
	    	PasswordDetail pd = new PasswordDetail(PasswordDetail.GENERIC, etTitle.getText().toString(), "");
	    	np.addNameValue(getString(R.string.password), pd);
	    	pd = new PasswordDetail(PasswordDetail.GENERIC, etURL.getText().toString(), "");
	    	np.addNameValue(getString(R.string.email),pd);
	    	
	    	passDao.saveLogins();
	    	
	    	//reset data
	    	np.clear();
	    	
	    	Intent intent = new Intent(this, HomeView.class);
	    	startActivity(intent);
	    	
	    }
	    
	    
	    
	    
	}
	
}
