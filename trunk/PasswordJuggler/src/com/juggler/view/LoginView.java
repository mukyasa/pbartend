/**
 * Date: Dec 14, 2009
 * Project: PasswordJuggler
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.juggler.view;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;

import com.juggler.dao.PasswordDAO;
import com.juggler.dao.PasswordDbHelper;
import com.juggler.dao.QuiresDAO;
import com.juggler.utils.Encrypt;
import com.juggler.utils.LoginAuthHandler;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class LoginView extends Activity implements OnClickListener,OnTouchListener { 
	 
		private PasswordDAO passDao;
		private PasswordDbHelper myDatabaseAdapter;
		private String dbPwd="";
	
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        Window window = getWindow();			  
			window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
			LoginAuthHandler lah = LoginAuthHandler.getInstance(this);
			lah.setLoginRequired(false);
			
			//set up database for use
			passDao = new PasswordDAO();
			myDatabaseAdapter = PasswordDbHelper.getInstance(this);
			passDao.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
			Cursor cursor = passDao.checkForPassword();
			if (cursor != null) 
			{
				cursor.moveToFirst();
				startManagingCursor(cursor);
				dbPwd = Encrypt.decryptA(cursor.getString(cursor.getColumnIndex(QuiresDAO.COL_PASSWORD)));
				LoginAuthHandler logauth = LoginAuthHandler.getInstance(this);
				logauth.setAppPwd(dbPwd);
			}
			 
			 setContentView(R.layout.login);
			 Button loginButton = (Button)findViewById(R.id.butLogin);
			 loginButton.setOnClickListener(this);
	 }

		
	/* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View) 
     */
    public void onClick(View v) {
    	
	   	LoginAuthHandler handler = LoginAuthHandler.getInstance(this);
   		EditText loginpwd = (EditText)findViewById(R.id.etLogin);
   		//Log.v(loginpwd.getText().toString(),dbPwd);
   		//Log.v("LOGIN DID ROTATE: ",handler.isDidRotate()+"");
   		if(dbPwd.equals(loginpwd.getText().toString()))
   		{
   			handler.setDidLogin(true);
   			handler.setLoginRequired(false);
   			finish();
   		}
    }

	/* (non-Javadoc)
     * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
     */
    public boolean onTouch(View v, MotionEvent event) {
	    // TODO Auto-generated method stub
	    return false;
    }
}
