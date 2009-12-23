package com.juggler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class FooterActivity extends Activity implements OnClickListener,OnTouchListener{
	protected final int HOME = 0;
	protected final int WALLET = 1;
	protected final int LOGINS = 2;
	protected final int NOTES = 3;
	protected final int PASSWORDS = 4;
	protected final int SETTINGS = 5;
	protected int SCREEN_TYPE=0;
	
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
 		  if(SCREEN_TYPE != HOME){
 		   	Intent intent = new Intent(this,HomeView.class);
	    	startActivity(intent);
 		  }
 	   }else if(v == bLogins){
 		  if(SCREEN_TYPE != LOGINS){
 			  
 		  }
 	   }else if(v == bNotes){
 		  if(SCREEN_TYPE != NOTES){
 			  
 		  }
 	   }else if(v== bPasswords){
 		  if(SCREEN_TYPE != PASSWORDS){
 			  
 		  }
 	   }else if(v == bSettings){
 		  if(SCREEN_TYPE != SETTINGS){
 			  
 		  }
 	   }else if(v ==bWallet){
 		  if(SCREEN_TYPE != WALLET){
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
					if(SCREEN_TYPE != HOME)
						((Button)v).setBackgroundResource(R.drawable.home_off);
				}
			}else if(v==bLogins){
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
						((Button)v).setBackgroundResource(R.drawable.logins);
				} 
				else{
					if(SCREEN_TYPE != LOGINS)
						((Button)v).setBackgroundResource(R.drawable.logins_off);
				}
			}
			else if(v==bWallet){
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					((Button)v).setBackgroundResource(R.drawable.wallet);
				} 
				else{
					if(SCREEN_TYPE != WALLET)
						((Button)v).setBackgroundResource(R.drawable.wallet_off);
				}
			}
			else if(v==bNotes){
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					((Button)v).setBackgroundResource(R.drawable.notes);
				} 
				else{
					if(SCREEN_TYPE != NOTES)
						((Button)v).setBackgroundResource(R.drawable.notes_off);
				}
			}
			else if(v==bPasswords){
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					((Button)v).setBackgroundResource(R.drawable.passwords);
				} 
				else{
					if(SCREEN_TYPE != PASSWORDS)
						((Button)v).setBackgroundResource(R.drawable.passwords_off);
				}
			}
			else if(v==bSettings){
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					((Button)v).setBackgroundResource(R.drawable.settings);
				} 
				else{
					if(SCREEN_TYPE != SETTINGS)
						((Button)v).setBackgroundResource(R.drawable.settings_off);
				}
			}
		}
		
		
		return false;
	}
}
