package com.juggler.view;

import android.app.ListActivity;
import android.os.Bundle;

import com.juggler.dao.PasswordDAO;
import com.juggler.dao.PasswordDbHelper;
import com.juggler.utils.FooterUtil;
import com.juggler.utils.LoginAuthHandler;

public class FooterListActivity extends ListActivity{
	
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
		//lah.setLoginRequired(true);
	    super.onStop();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		footutil = new FooterUtil(this);
        
        footutil.initialize(this);
	    super.onResume();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
	    // TODO Auto-generated method stub
	    super.onPause();
	}
	
}
