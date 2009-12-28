package com.juggler.view;

import java.util.Hashtable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.juggler.dao.PasswordDAO;
import com.juggler.dao.PasswordDbHelper;
import com.juggler.domain.NewPassword;
import com.juggler.utils.Constants;

public class CreateLoginTemplateActivity extends BaseActivity {
	private CharSequence text;
	private EditText etTitle,etURL;
	private PasswordDAO passDao;
	private PasswordDbHelper myDatabaseAdapter;
	private long selectedRow;
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
		Intent selectedIntent = getIntent();
		text =  selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_SELECTED_TEXT);
		selectedRow =  selectedIntent.getLongExtra(Constants.INTENT_EXTRA_SELECTED_ROW, 0);
		TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText(text);
		
		//change text to email and password assume templete picked
		etTitle = (EditText)findViewById(R.id.etTitle);
		etURL = (EditText)findViewById(R.id.etURL);
		
		etURL.setText(selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_STEP2_FIELD1));
		etTitle.setText(selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_STEP2_FIELD2));
		
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
		    
	    	
	    	Hashtable<String, String> nameValue = new Hashtable<String, String>();
	    	nameValue.put(etURL.getText().toString(), etTitle.getText().toString());
	    	
	    	np.nameValue=nameValue;
	    	
	    	passDao.saveLogins();
	    	
	    	//reset data
	    	np.clear();
	    	
	    	Intent intent = new Intent(this, HomeView.class);
	    	startActivity(intent);
	    	
	    }
	    
	    
	    
	    
	}
	
}
