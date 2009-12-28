/**
 * Date: Dec 24, 2009
 * Project: PasswordJuggler
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.juggler.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

import com.juggler.dao.PasswordDAO;
import com.juggler.dao.PasswordDbHelper;
import com.juggler.view.DetailsActivity;
import com.juggler.view.HomeView;
import com.juggler.view.R;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class FooterUtil implements OnClickListener,OnTouchListener{
	
	protected Button bHome,bWallet,bLogins,bNotes,bPasswords,bSettings;
	private PasswordDbHelper myDatabaseAdapter;
	private PasswordDAO passDao;
	private Context context;
	
	
	
	public FooterUtil(Context context) {
	    this.context = context;
    }

	public void initialize(Activity activity){
	    	
	    	bHome = (Button)activity.findViewById(R.id.bHome);
	    	bHome.setOnClickListener(this);
	    	bHome.setOnTouchListener(this);
	    	bWallet = (Button)activity.findViewById(R.id.bWallet);
	    	bWallet.setOnClickListener(this);
	    	bWallet.setOnTouchListener(this);
	    	bLogins = (Button)activity.findViewById(R.id.bLogins);
	    	bLogins.setOnClickListener(this);
	    	bLogins.setOnTouchListener(this);
	    	bNotes = (Button)activity.findViewById(R.id.bNotes);
	    	bNotes.setOnClickListener(this);
	    	bNotes.setOnTouchListener(this);
	    	bPasswords = (Button)activity.findViewById(R.id.bPasswords);
	    	bPasswords.setOnClickListener(this);
	    	bPasswords.setOnTouchListener(this);
	    	bSettings = (Button)activity.findViewById(R.id.bSettings);
	    	bSettings.setOnClickListener(this);
	    	bSettings.setOnTouchListener(this);
	    	
	    	setUpmenu();
	    	
	    }
	 
	 private void setUpmenu(){
		 
		//set up database for use
		passDao = new PasswordDAO();
		myDatabaseAdapter = PasswordDbHelper.getInstance(context);
		passDao.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
		
		if(passDao.getLoginsCount() <= 0)
			bLogins.setVisibility(View.GONE);
		if(passDao.getWalletCount() <= 0)
			bWallet.setVisibility(View.GONE);
		if(passDao.getPasswordsCount() <= 0)
			bPasswords.setVisibility(View.GONE);
		if(passDao.getNotesCount() <= 0)
			bNotes.setVisibility(View.GONE);
		 
		
	 }
	
	 /* (non-Javadoc)
  * @see android.view.View.OnClickListener#onClick(android.view.View)
  */
 public void onClick(View v) {
	   	
	   if(v == bHome){
		  //if(Constants.SCREEN_TYPE != Constants.HOME){
		   	Intent intent = new Intent(context,HomeView.class);
	    	context.startActivity(intent);
		  //}
	   }else if(v == bLogins){
		  if(Constants.SCREEN_TYPE != Constants.LOGINS){
			  
		  }
	   }else if(v == bNotes){
		  if(Constants.SCREEN_TYPE != Constants.NOTES){
			  
		  }
	   }else if(v== bPasswords){
		  if(Constants.SCREEN_TYPE != Constants.PASSWORDS){
			  
		  }
	   }else if(v == bSettings){
		  if(Constants.SCREEN_TYPE != Constants.SETTINGS){
			  
		  }
	   }else if(v ==bWallet){
		  if(Constants.SCREEN_TYPE != Constants.WALLET){
			Intent intent = new Intent(context,DetailsActivity.class);
			context.startActivity(intent);
		  }
		   
	   }
 }

 /*
	 * (non-Javadoc)
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View,
	 * android.view.MotionEvent)
	 */
	public boolean onTouch(View v, MotionEvent event) {
		
		if (v instanceof Button) {
			
			if(v==bHome)
			{
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					((Button)v).setBackgroundResource(R.drawable.home);
				} 
				else{
					if(Constants.SCREEN_TYPE != Constants.HOME)
						((Button)v).setBackgroundResource(R.drawable.home_off);
				}
			}else if(v==bLogins){
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
						((Button)v).setBackgroundResource(R.drawable.logins);
				} 
				else{
					if(Constants.SCREEN_TYPE != Constants.LOGINS)
						((Button)v).setBackgroundResource(R.drawable.logins_off);
				}
			}
			else if(v==bWallet){
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					((Button)v).setBackgroundResource(R.drawable.wallet);
				} 
				else{
					if(Constants.SCREEN_TYPE != Constants.WALLET)
						((Button)v).setBackgroundResource(R.drawable.wallet_off);
				}
			}
			else if(v==bNotes){
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					((Button)v).setBackgroundResource(R.drawable.notes);
				} 
				else{
					if(Constants.SCREEN_TYPE != Constants.NOTES)
						((Button)v).setBackgroundResource(R.drawable.notes_off);
				}
			}
			else if(v==bPasswords){
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					((Button)v).setBackgroundResource(R.drawable.passwords);
				} 
				else{
					if(Constants.SCREEN_TYPE != Constants.PASSWORDS)
						((Button)v).setBackgroundResource(R.drawable.passwords_off);
				}
			}
			else if(v==bSettings){
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					((Button)v).setBackgroundResource(R.drawable.settings);
				} 
				else{
					if(Constants.SCREEN_TYPE != Constants.SETTINGS)
						((Button)v).setBackgroundResource(R.drawable.settings_off);
				}
			}
		}
		
		
		return false;
	}
	
}
