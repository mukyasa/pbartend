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
import com.juggler.view.AllLoginsActivity;
import com.juggler.view.AllNotesActivity;
import com.juggler.view.AllPasswordsActivity;
import com.juggler.view.AllWalletActivity;
import com.juggler.view.HomeView;
import com.juggler.view.R;
import com.juggler.view.SettingsActivity;

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
	    	bHome.setBackgroundResource(R.drawable.home_off);
	    	bHome.setOnClickListener(this);
	    	bHome.setOnTouchListener(this);
	    	bWallet = (Button)activity.findViewById(R.id.bWallet);
	    	bWallet.setBackgroundResource(R.drawable.wallet_off);
	    	bWallet.setOnClickListener(this);
	    	bWallet.setOnTouchListener(this);
	    	bLogins = (Button)activity.findViewById(R.id.bLogins);
	    	bLogins.setBackgroundResource(R.drawable.logins_off);
	    	bLogins.setOnClickListener(this);
	    	bLogins.setOnTouchListener(this);
	    	bNotes = (Button)activity.findViewById(R.id.bNotes);
	    	bNotes.setBackgroundResource(R.drawable.notes_off);
	    	bNotes.setOnClickListener(this);
	    	bNotes.setOnTouchListener(this);
	    	bPasswords = (Button)activity.findViewById(R.id.bPasswords);
	    	bPasswords.setBackgroundResource(R.drawable.passwords_off);
	    	bPasswords.setOnClickListener(this);
	    	bPasswords.setOnTouchListener(this);
	    	bSettings = (Button)activity.findViewById(R.id.bSettings);
	    	bSettings.setBackgroundResource(R.drawable.settings_off);
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
		
		switch(Constants.SCREEN_TYPE){
			case Constants.HOME: 
				bHome.setBackgroundResource(R.drawable.home);
				bHome.setClickable(false);
				break;
			case Constants.LOGINS: 
				bLogins.setBackgroundResource(R.drawable.logins);
				bLogins.setClickable(false);
				break;
			case Constants.NOTES: 
				bNotes.setBackgroundResource(R.drawable.notes);
				bNotes.setClickable(false);
				break;
			case Constants.PASSWORDS: 
				bPasswords.setBackgroundResource(R.drawable.passwords);
				bPasswords.setClickable(false);
				break;
			case Constants.SETTINGS: 
				bSettings.setBackgroundResource(R.drawable.settings);
				bSettings.setClickable(false);
				break;
			case Constants.WALLET: 
				bWallet.setBackgroundResource(R.drawable.wallet);
				bWallet.setClickable(false);
				break;
				
		}
		 
		
	 }
	
	 /* (non-Javadoc)
  * @see android.view.View.OnClickListener#onClick(android.view.View)
  */
 public void onClick(View v) {
	   	
	   if(v == bHome){
	    	context.startActivity(new Intent(context,HomeView.class));
	   }else if(v == bLogins){
		   context.startActivity(new Intent(context,AllLoginsActivity.class));
	   }else if(v == bNotes){
		   context.startActivity(new Intent(context,AllNotesActivity.class));
	   }else if(v== bPasswords){
		   context.startActivity(new Intent(context,AllPasswordsActivity.class));
	   }else if(v == bSettings){
		   context.startActivity(new Intent(context,SettingsActivity.class));
	   }else if(v ==bWallet){
		   context.startActivity(new Intent(context,AllWalletActivity.class));
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
