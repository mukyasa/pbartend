package com.juggler.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

import com.juggler.utils.Constants;

public class FooterActivity extends Activity implements OnClickListener,OnTouchListener{
	
	protected Button bHome,bWallet,bLogins,bNotes,bPasswords,bSettings;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        initialize();
    }
	
	 private void initialize(){
	    	
	    	bHome = (Button)findViewById(R.id.bHome);
	    	bHome.setOnClickListener(this);
	    	bHome.setOnTouchListener(this);
	    	bWallet = (Button)findViewById(R.id.bWallet);
	    	bWallet.setOnClickListener(this);
	    	bWallet.setOnTouchListener(this);
	    	bLogins = (Button)findViewById(R.id.bLogins);
	    	bLogins.setOnClickListener(this);
	    	bLogins.setOnTouchListener(this);
	    	bNotes = (Button)findViewById(R.id.bNotes);
	    	bNotes.setOnClickListener(this);
	    	bNotes.setOnTouchListener(this);
	    	bPasswords = (Button)findViewById(R.id.bPasswords);
	    	bPasswords.setOnClickListener(this);
	    	bPasswords.setOnTouchListener(this);
	    	bSettings = (Button)findViewById(R.id.bSettings);
	    	bSettings.setOnClickListener(this);
	    	bSettings.setOnTouchListener(this);
	    	
	    }
	
	 /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v) {
 	   	
 	   if(v == bHome){
 		  if(Constants.SCREEN_TYPE != Constants.HOME){
 		   	Intent intent = new Intent(this,HomeView.class);
	    	startActivity(intent);
 		  }
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
 			Intent intent = new Intent(this,DetailsActivity.class);
	    	startActivity(intent);
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
