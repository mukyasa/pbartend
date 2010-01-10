package com.juggler.view;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;

import com.juggler.dao.PasswordDAO;
import com.juggler.dao.PasswordDbHelper;
import com.juggler.utils.FooterUtil;
import com.juggler.utils.LoginAuthHandler;

public class FooterActivity extends Activity{
	private FooterUtil footutil;
	protected PasswordDAO passDao;
	protected PasswordDbHelper myDatabaseAdapter;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //set up database for use
		passDao = new PasswordDAO();
		myDatabaseAdapter = PasswordDbHelper.getInstance(this);
		passDao.setSQLiteDatabase(myDatabaseAdapter.getDatabase());        
    }
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		
		//check for login required again
		LoginAuthHandler lah = LoginAuthHandler.getInstance(this);
		///lah.setLoginRequired(true);
	    super.onStop();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		
		
		//check for login required again
		LoginAuthHandler lah = LoginAuthHandler.getInstance(this);
		
		if(lah.isLoginRequired() || !lah.isDidLogin())
		{
		    //pop login window
			if(passDao.checkForPassword().getCount() > 0)
				startActivity(new Intent(this,LoginView.class));
			else
				startActivity(new Intent(this,CreateLoginPasswordActivity.class));
		} 
		
		footutil = new FooterUtil(this);
        footutil.initialize(this);
	    super.onResume();
	}
	
}
