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
import android.widget.EditText;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class HomeView extends Activity {
	
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.home_screen);
	        
	        initialize();
	    }
	   
	   private void initialize(){
	        
	    	Intent intent = new Intent(this,LoginView.class);
	    	startActivity(intent); 
	    	
	    	
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
