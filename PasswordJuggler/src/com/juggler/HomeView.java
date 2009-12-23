/**
 * Date: Dec 21, 2009
 * Project: PasswordJuggler
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.juggler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class HomeView extends Activity implements OnClickListener{
	
		private Button bHome,bWallet,bLogins,bNotes,bPasswords,bSettings;
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.base_page_layout);
	        
	        initialize();
	    }
	   
	   private void initialize(){
	        //pop login window
	    	Intent intent = new Intent(this,LoginView.class);
	    	startActivity(intent); 
	    	
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
    			Intent intent = new Intent(this,AllView.class);
   	    		startActivity(intent);
    		   
    	   }
       }
       
	   
	   /* (non-Javadoc)
	     * @see android.app.Activity#onCreateDialog(int)
	     */
	    @Override
	    protected Dialog onCreateDialog(int id) {
	    	
	    	LayoutInflater factory = LayoutInflater.from(this); 
	        final View textEntryView = factory.inflate(R.layout.login, null);
	        return new AlertDialog.Builder(HomeView.this)
	            .setIcon(R.drawable.error)
	            .setTitle(R.string.dialogtitle)  
	            .setView(textEntryView)
	            .setCancelable(false)
	            .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {
	                	EditText authCode =  (EditText)textEntryView.findViewById(R.id.etLogin);
	                	
	                    if(false)
	                    	finish();
	                    //else
	                    	//Authenication.setRegPrefererences(this);
	                    	
	                }
	            })
	            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {

	                    finish();
	                }
	            })
	            .create();
	        
	    }

	   
}
