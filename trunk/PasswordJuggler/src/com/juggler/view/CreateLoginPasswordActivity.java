/**
 * Date: Jan 4, 2010
 * Project: PasswordJuggler
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.juggler.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.juggler.dao.PasswordDAO;
import com.juggler.dao.PasswordDbHelper;
import com.juggler.utils.LoginAuthHandler;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class CreateLoginPasswordActivity extends Activity implements OnClickListener {
	
	private Button bNext;
	private PasswordDAO passDao;
	private PasswordDbHelper myDatabaseAdapter;
	private EditText pwdconfirm,pwd;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.create_password_frame);
	    
	    Button butPrev = (Button)findViewById(R.id.butPrev);
	    butPrev.setVisibility(View.GONE);
	    
	    //set up database for use
		passDao = new PasswordDAO();
		myDatabaseAdapter = PasswordDbHelper.getInstance(this);
		passDao.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
	    
	}
	
	/* (non-Javadoc)
	 * @see com.juggler.view.FooterActivity#onResume()
	 */
	@Override
	protected void onResume() {
		initialize();
	    super.onResume();
	}
	
	private void initialize()
	{
		TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText(getString(R.string.setuppassword));
		
		bNext = (Button)findViewById(R.id.butNext);
	    bNext.setText(getString(R.string.next));
	    bNext.setOnClickListener(this);
	    
	    pwd = (EditText)findViewById(R.id.etNewPassword1);
	    pwdconfirm = (EditText)findViewById(R.id.etNewPassword2);
	    
	}

	
	/* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v) {
    	
    	if(v == bNext)
 	    {
    		if(!pwd.getText().toString().trim().equals("") &&  pwd.getText().toString().trim().equals(pwdconfirm.getText().toString().trim()))
    		{
    			//save password
        		passDao.setRootLogin(pwd.getText().toString());
        		LoginAuthHandler handler = LoginAuthHandler.getInstance(this);
        		handler.setDidLogin(true);
        		handler.setLoginRequired(false);
     	    	finish();
    		}
    		else
    			showDialog(0);
 	    	
 	    }
	    
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreateDialog(int)
     */
    @Override
    protected Dialog onCreateDialog(int id) {
    	
    	return new AlertDialog.Builder(CreateLoginPasswordActivity.this)
        .setIcon(R.drawable.error)
        .setMessage("I'm sorry your password does not match.")
        .setTitle("Error")
        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
               dismissDialog(0);
            }
        })      
       .create();
    }

}
