package com.juggler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BaseActivity extends Activity implements OnClickListener{

	protected Button bHome,bWallet,bLogins,bNotes,bPasswords,bSettings;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        initialize();
    }
	
	 private void initialize(){
	    	
	    	bHome = (Button)findViewById(R.id.bHome);
	    	bHome.setOnClickListener(this);
	    	bWallet = (Button)findViewById(R.id.bWallet);
	    	bWallet.setOnClickListener(this);
	    	bLogins = (Button)findViewById(R.id.bLogins);
	    	bLogins.setOnClickListener(this);
	    	bNotes = (Button)findViewById(R.id.bNotes);
	    	bNotes.setOnClickListener(this);
	    	bPasswords = (Button)findViewById(R.id.bPasswords);
	    	bPasswords.setOnClickListener(this);
	    	bSettings = (Button)findViewById(R.id.bSettings);
	    	bSettings.setOnClickListener(this);
	    	
	    }
	
	 /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v) {
 	   	
 	   if(v == bHome){
 		   	Intent intent = new Intent(this,HomeView.class);
	    		startActivity(intent);
 	   }else if(v == bLogins){
 		   
 	   }else if(v == bNotes){
 		   
 	   }else if(v== bPasswords){
 		   
 	   }else if(v == bSettings){
 		   
 	   }else if(v ==bWallet){
 			Intent intent = new Intent(this,DetailsActivity.class);
	    	startActivity(intent);
 		   
 	   }
    }
}
