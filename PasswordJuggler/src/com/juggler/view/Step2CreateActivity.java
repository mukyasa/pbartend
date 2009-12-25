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

public class Step2CreateActivity extends BaseActivity {
	private EditText etTitle,etURL;
	private PasswordDAO passDao;
	private PasswordDbHelper myDatabaseAdapter;
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
		
			
		//change text to email and password assume templete picked
		etTitle = (EditText)findViewById(R.id.etTitle);
		etURL = (EditText)findViewById(R.id.etURL);
		
		etURL.setText(getString(R.string.username));
		etTitle.setText(getString(R.string.password));
		
		
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
	    	np.catId=-1;
	    	
	    	Hashtable<String, String> nameValue = new Hashtable<String, String>();
	    	nameValue.put(etURL.getText().toString(), etTitle.getText().toString());
	    	
	    	np.nameValue=nameValue;
	    	//save
	    	passDao.saveLogins();
	    	
	    	Intent intent = new Intent(this, HomeView.class);
	    	startActivity(intent);
	    	
	    }
	    
	    
	}
	
}
