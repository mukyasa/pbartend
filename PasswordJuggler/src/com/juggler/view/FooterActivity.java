package com.juggler.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.juggler.dao.PasswordDAO;
import com.juggler.dao.PasswordDbHelper;
import com.juggler.utils.FooterUtil;
import com.juggler.utils.LoginAuthHandler;

public class FooterActivity extends Activity implements OnClickListener {
	private FooterUtil footutil;
	protected PasswordDAO passDao;
	protected PasswordDbHelper myDatabaseAdapter;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
      //set up database for use
		passDao = new PasswordDAO();
		myDatabaseAdapter = PasswordDbHelper.getInstance(this);
		passDao.setSQLiteDatabase(myDatabaseAdapter.getDatabase());        
    }
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onRetainNonConfigurationInstance()
	 */
	@Override
	public Object onRetainNonConfigurationInstance() {
		LoginAuthHandler lah = LoginAuthHandler.getInstance(this);
		lah.setDidRotate(true);
		//Log.v(" ","onRetainNonConfigurationInstance called");
	    return super.onRetainNonConfigurationInstance();
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
    
    
    private void callLoginActivity()
    {
	    	//check for login required again
			LoginAuthHandler lah = LoginAuthHandler.getInstance(this);
			//Log.v("Is Login Required? "+lah.isLoginRequired()," Did Login: "+!lah.isDidLogin());
			//Log.v("DID ROTATE: ",lah.isDidRotate()+"");
			if(lah.showLoginScreen())
			{
				//Log.v("MADE IT IN DID ROTATE: ",lah.isDidRotate()+"");
				lah.setLoginRequired(false);
			    //pop login window
				if(passDao.checkForPassword().getCount() > 0 )
				{
					if(!lah.isDidRotate())
					{
						lah.setDidRotate(false);
						startActivity(new Intent(this,LoginView.class));
					}
					else
						lah.setDidRotate(false);
				}
				else
					startActivity(new Intent(this,CreateLoginPasswordActivity.class));
			} 
			lah.setLoginRequired(true);
    }
    
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		
		callLoginActivity();
		footutil = new FooterUtil(this);
        footutil.initialize(this);
        
		super.onResume();
	   
	}
	
}
