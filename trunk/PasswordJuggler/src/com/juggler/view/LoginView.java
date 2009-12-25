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
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.juggler.utils.LoginAuthHandler;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class LoginView extends Activity implements OnClickListener {
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
            WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
			
			 setContentView(R.layout.login);
			 Button loginButton = (Button)findViewById(R.id.butLogin);
			 loginButton.setOnClickListener(this);
	 }

	/* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v) {
    	
    	if(true)
    	{
		   	LoginAuthHandler handler = LoginAuthHandler.getInstance(this);
	   		handler.setDidLogin(true);
	   		
	   		finish();
    	}
    }
}
