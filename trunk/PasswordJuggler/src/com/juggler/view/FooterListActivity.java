package com.juggler.view;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.juggler.dao.PasswordDAO;
import com.juggler.dao.PasswordDbHelper;
import com.juggler.utils.FooterUtil;
import com.juggler.utils.LoginAuthHandler;

public class FooterListActivity extends ListActivity implements OnClickListener{
	
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
	protected void onPause() {
		LoginAuthHandler lah = LoginAuthHandler.getInstance(this);
		
		if(lah.isLoginRequired() || !lah.isDidLogin())
		{
			startActivity(new Intent(this,LoginView.class));
		}
		lah.setLoginRequired(true);
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
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v) {
    	/*this is required to reset boolean on every action if the 
		activty is stoped with out this set the login screen shows*/
		LoginAuthHandler lah = LoginAuthHandler.getInstance(this);
	 	lah.setLoginRequired(false);
	    
    }

	
}
