/**
 * Date: Dec 21, 2009
 * Project: PasswordJuggler
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.juggler.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableRow;

import com.juggler.dao.PasswordDbHelper;
import com.juggler.utils.Constants;
import com.juggler.utils.LoginAuthHandler;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class HomeView extends FooterActivity implements OnClickListener{
	
		private TableRow trWallet,trLogins,trNotes,trGenPassword;
		
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
	        setContentView(R.layout.base_page_frame);
	        super.onCreate(savedInstanceState);
	        
	        LoginAuthHandler handler = LoginAuthHandler.getInstance();
	        if(!handler.isDidLogin())
	        {
		        //pop login window
		    	Intent intent = new Intent(this,LoginView.class);
		    	startActivity(intent);
	        }
	        
	        initialize();
	    }
	   
	   
	   private void initialize(){
		   
		   //init database
		   PasswordDbHelper.getInstance(this);
		   
		   	//set screen type
		   Constants.SCREEN_TYPE=Constants.HOME;
		    
		   	//hide next button
	    	Button next = (Button)findViewById(R.id.butNext);
	    	next.setVisibility(View.GONE);
	    	//hide next button
	    	Button butPrev = (Button)findViewById(R.id.butPrev);
	    	butPrev.setVisibility(View.GONE);
	    	
	    	trGenPassword = (TableRow)findViewById(R.id.trGenPassword);
	    	trGenPassword.setOnClickListener(this);
	    	trWallet = (TableRow)findViewById(R.id.trWallet);
	    	trWallet.setOnClickListener(this);
	    	trLogins = (TableRow)findViewById(R.id.trLogins);
	    	trLogins.setOnClickListener(this);
	    	trNotes = (TableRow)findViewById(R.id.trNotes);
	    	trNotes.setOnClickListener(this);
	    	
	    }
	   
	  
		public void onClick(View v) {
		
			if(v==trGenPassword){
				Intent intent = new Intent(this,WalletCatListActivity.class);
				intent.putExtra(Constants.INTENT_EXTRA_SELECTED_TEXT, getString(R.string.genpasswords));
	    		startActivity(intent);
			}else if(v==trWallet){
				Intent intent = new Intent(this,WalletCatListActivity.class);
				intent.putExtra(Constants.INTENT_EXTRA_SELECTED_TEXT, getString(R.string.wallet));
	    		startActivity(intent);
			}else if(v==trLogins){
				Intent intent = new Intent(this,LoginsListActivity.class);
				intent.putExtra(Constants.INTENT_EXTRA_SELECTED_TEXT, getString(R.string.login));
	    		startActivity(intent);
			}else if(v == trNotes){
				Intent intent = new Intent(this,WalletCatListActivity.class);
				intent.putExtra(Constants.INTENT_EXTRA_SELECTED_TEXT, getString(R.string.notes));
	    		startActivity(intent);
				
			}
			
		
		}
       
	   
}
