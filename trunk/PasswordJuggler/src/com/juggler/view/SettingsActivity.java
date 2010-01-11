/**
 * Date: Dec 27, 2009
 * Project: PasswordJuggler
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.juggler.view;

import java.io.File;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.juggler.dao.PasswordDbHelper;
import com.juggler.utils.AppUtils;
import com.juggler.utils.Constants;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class SettingsActivity extends FooterActivity{
	
	private Button bNext,bPrev;
	private TextView tvChangePwd,tvAutoLock;
	private ToggleButton tbClearText,tbDeleteDb;
	private Context context;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    setContentView(R.layout.settings_frame);

	    View details2 = (LinearLayout)findViewById(R.id.vDetails2);
	    details2.setVisibility(View.GONE);
	    View details3 = (LinearLayout)findViewById(R.id.vDetails3);
	    details3.setVisibility(View.GONE);
	    context=this;
	    super.onCreate(savedInstanceState);
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
		tvTitle.setText(getString(R.string.settings));
		
		tbClearText = (ToggleButton)findViewById(R.id.tbClearText);
		String isClear = AppUtils.getClearTextSetting(this);
		if(isClear.equalsIgnoreCase("true"))
			tbClearText.setChecked(true);
		
		tbDeleteDb = (ToggleButton)findViewById(R.id.tbRestDb);
		tbDeleteDb.setOnClickListener(this); 
		
		bPrev = (Button)findViewById(R.id.butPrev);
		bPrev.setOnClickListener(this);
		
		bNext = (Button)findViewById(R.id.butNext);
	    bNext.setText(getString(R.string.save));
	    bNext.setOnClickListener(this);
	    
		//set screen type
		Constants.SCREEN_TYPE=Constants.SETTINGS;
		
		//attach events
		tvChangePwd = (TextView)findViewById(R.id.tvChangePassword);
		tvChangePwd.setOnClickListener(this);
		
		tvAutoLock = (TextView)findViewById(R.id.tvAutoLock);
		tvAutoLock.setOnClickListener(this);
		
	}

	 @Override
	    protected Dialog onCreateDialog(int id) {
	    	
	    	return new AlertDialog.Builder(SettingsActivity.this)
	        .setIcon(R.drawable.error)
	        .setMessage(getString(R.string.confirmrest))
	        .setTitle("Database Reset")
	        .setPositiveButton("Delete it", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					//delete db
					File dbfile = new File(Environment.getExternalStorageDirectory(), PasswordDbHelper.DATABASE_EXTERNAL_FOLDER+"/"+PasswordDbHelper.DATABASE_NAME);
					if(dbfile.exists())
                		dbfile.delete();
					
					//System.exit(0);
					android.os.Process.killProcess(android.os.Process.myPid());
					
				}
			})
	        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	            	tbDeleteDb.setChecked(false);
	               dismissDialog(0);
	            }
	        })      
	       .create();
	 }
	/* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v) {
    	super.onClick(v);
    	
    	if(v == bNext) 
 	    {
    		//clear text button
    		ToggleButton tbclear = (ToggleButton)findViewById(R.id.tbClearText);
    		
    		HashMap<String, String> map = new HashMap<String, String>();
    		map.put(AppUtils.PREFS_CLEAR_TEXT, tbclear.isChecked()+"");
    		//save settings
    		AppUtils.setSettings(this, map);
 	    	finish();
 	    	
 	    }
    	else if(v == bPrev){
    		finish();
    	}
    	else if(v == tvChangePwd)
    		startActivity(new Intent(this,NewPasswordAcivity.class));
    	else if(v==tvAutoLock)
    		startActivity(new Intent(this,AutoLockAcivity.class));	
    	else if(v==tbDeleteDb)
    	{
    		showDialog(0);
    		
    	}
    }
	
}
