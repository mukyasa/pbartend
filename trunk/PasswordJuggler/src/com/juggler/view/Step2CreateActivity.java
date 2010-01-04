package com.juggler.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.juggler.dao.PasswordDAO;
import com.juggler.dao.PasswordDbHelper;
import com.juggler.domain.NewPassword;
import com.juggler.domain.PasswordDetail;
import com.juggler.utils.Constants;

public class Step2CreateActivity extends BaseActivity {
	private EditText etTitle,etURL;
	private String date;
	private PasswordDAO passDao;
	private PasswordDbHelper myDatabaseAdapter;
	private Intent selectedIntent;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.create_frame);
		initialize();
		
		//set up database for use
		passDao = new PasswordDAO();
		myDatabaseAdapter = PasswordDbHelper.getInstance(this);
		passDao.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
		
		super.onCreate(savedInstanceState);
	}

	private void initialize() {

		//set title
		TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText("");
		
			
		//change text to email and password assume template picked
		etTitle = (EditText)findViewById(R.id.etTitle);
		etTitle.setOnTouchListener(this);
		etURL = (EditText)findViewById(R.id.etURL);
		etURL.setOnTouchListener(this);
		
		selectedIntent = getIntent();
		
		date = (String) selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_STEP2_FIELD2);
		
		etURL.setText(selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_STEP2_FIELD1));
		etTitle.setText(date);
		
		
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

				if(template.equals(getString(R.string.username)) ||
						template.equals(getString(R.string.password))||
						template.equals(getString(R.string.usage))||
						template.equals(date))
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
	    	PasswordDetail pd = new PasswordDetail(PasswordDetail.GENERIC, etTitle.getText().toString(), "");
	    	
	    	np.addNameValue(etURL.getText().toString(), pd);
	    	
	    	//save gen password
	    	if(selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_IS_GENPASSWORD) != null && 
	    			selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_IS_GENPASSWORD).equals("true"))
	    	{
	    		np.usage=etURL.getText().toString();
	    		np.name=etTitle.getText().toString();
		    	passDao.saveGenPassword();
	    	}
	    	else //save logins
	    		passDao.saveLogins();
	    	
	    	//reset data
	    	np.clear();
	    	
	    	Intent intent = new Intent(this, HomeView.class);
	    	startActivity(intent);
	    	
	    }
	    
	    
	}
	
}
