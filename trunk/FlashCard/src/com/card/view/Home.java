package com.card.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.card.R;
import com.card.util.AppUtil;
import com.card.util.Constants;

public class Home extends Activity implements OnTouchListener,OnKeyListener,Runnable {
	private Button btSearch;
	private RadioButton rbUser;
	private RadioButton rbTerm;
	private RadioButton rbId;
	private EditText etUserName;
	private EditText etTerm;
	private final int TYPE_USER_NAME=0;
	private final int TYPE_TERM=1;
	private final int TYPE_ID=2;
	private int CHOOSEN_TYPE=1;
	private Intent intent;
	private ProgressDialog pd;
	private final int MENU_RESULTS=0;
	private final int MENU_USER_PREF=1;
	private final int MENU_BOOKMARKS=2;
	private boolean WAS_ERROR=false;
	private final int DIALOG_ERROR=0;
	private final int DIALOG_ERROR_EMPTY=2;
	private final int DIALOG_INFO=1;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_load);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initScreen();
    }
    
    private Handler handler = new Handler() {
        
        @Override
        public void handleMessage(Message msg) {
        	if(pd!=null)
        		pd.dismiss();
        	if(WAS_ERROR)
        		showDialog(DIALOG_ERROR);
        }
    };
    
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		 getCardSets(CHOOSEN_TYPE);
	     handler.sendEmptyMessage(0);
	    
	}
 
    private void initScreen(){
    	btSearch = (Button)findViewById(R.id.btnSearch);
    	btSearch.setOnTouchListener(this);
    	
    	rbUser = (RadioButton)findViewById(R.id.rbUserName);
    	rbUser.setOnTouchListener(this);
    	rbTerm = (RadioButton)findViewById(R.id.rbTerm);
    	rbTerm.setOnTouchListener(this);
    	rbId = (RadioButton)findViewById(R.id.rbId);
    	rbId.setOnTouchListener(this);
    	
    	
    	
    	ImageView info = (ImageView)findViewById(R.id.ivInfo);
    	info.setOnTouchListener(this);
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0, MENU_RESULTS, 0, "Test Results").setIcon(R.drawable.results);
    	menu.add(0, MENU_USER_PREF, 0, "Preferences").setIcon(android.R.drawable.ic_menu_preferences);
    	menu.add(0, MENU_BOOKMARKS, 0, "View Bookmarks").setIcon(R.drawable.bookmark);
	    return true;
	}

	/* Handles item selections */
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
	    switch (item.getItemId()) {
	    	 case MENU_RESULTS:
	 	    	intent = new Intent(this, Results.class);
	 			startActivity(intent);
	 	    	return true;
	    	 case MENU_USER_PREF:
		 	    	intent = new Intent(this, UserPrefActivity.class);
		 			startActivity(intent);
		 	    	return true;
	    	 case MENU_BOOKMARKS:
		 	    	intent = new Intent(this, BookmarkList.class);
		 			startActivity(intent);
		 	    	return true;
	 	    }
	    
	    return false;
	}
    
    private void getCardSets(int type)
    {
    		
		String value = "";
		EditText etValue= (EditText)findViewById(R.id.etSearchTerm);
		
		if(type==TYPE_USER_NAME)
			value = "&q=creator:"+ etValue.getText().toString().trim();
		else if(type == TYPE_TERM)
			value = "&q="+etValue.getText().toString().trim();
		else if(type == TYPE_ID)
			value = "&q=ids:"+etValue.getText().toString().trim();
		
		
		if(AppUtil.initCardSets(value,Constants.SORT_TYPE_DEFALUT,Constants.DEFAULT_PAGE_NUMBER))
        {
			//set the original search term
			AppUtil.searchTerm = value;
	        intent = new Intent(this, CardSetList.class);
	        startActivity(intent);
        }
        else
        	WAS_ERROR=true;
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onCreateDialog(int)
     */
    @Override
    protected Dialog onCreateDialog(int id) {
    	
    	if(id==DIALOG_ERROR)
    	{
	    	return new AlertDialog.Builder(Home.this)
	        .setIcon(R.drawable.error)
	        .setMessage("No card sets found, please try again.")
	        .setTitle("Error")
	        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	               dismissDialog(DIALOG_ERROR);
	            }
	        })      
	       .create();
    	}else if(id==DIALOG_ERROR_EMPTY){
    		return new AlertDialog.Builder(Home.this)
	        .setIcon(R.drawable.error)
	        .setMessage("Please enter a value.")
	        .setTitle("Error")
	        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	               dismissDialog(DIALOG_ERROR_EMPTY);
	            }
	        })      
	       .create();
    	}else{
    		return new AlertDialog.Builder(Home.this)
            .setIcon(R.drawable.info)
            .setMessage("Ver:"+Constants.VERSION+"\nsupport@"+Constants.COMPANY_NAME+".com \n\n"+Constants.COMPANY_NAME+".com\ncopyright © 2009")
            .setTitle("Application Information")
            .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                   dismissDialog(DIALOG_INFO);
                }
            })      
           .create();
    	}

    }

    /* (non-Javadoc)
     * @see android.app.Activity#onStop()
     */
    @Override
    protected void onStop() {
    	if(pd!=null)
    		pd.dismiss();
    	
    	super.onStop();
    }
	/* (non-Javadoc)
     * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
     */
    public boolean onTouch(View v, MotionEvent event) {

    	if(v instanceof Button )
	  	{
    		if(v instanceof RadioButton)
    	  	{
    			if(event.getAction()== MotionEvent.ACTION_UP)
    				CHOOSEN_TYPE =  Integer.valueOf((((RadioButton) v).getHint().toString()));
    	  	}else
    	  	{
    		
		  		if(event.getAction() == MotionEvent.ACTION_DOWN)
		  		{
		  			v.setBackgroundResource(R.drawable.button_hvr);
		  			v.setPadding(30, 0, 40, 5);
		  		}
		  		
		  		else if(event.getAction()== MotionEvent.ACTION_UP)
		  		{
		  			EditText etValue;
		  			boolean canSearch=true;
		  			
	  				etValue = (EditText)findViewById(R.id.etSearchTerm);
	  				Editable ed = etValue.getText();
	  				
	  				if("".equals(ed.toString()))
	  					canSearch=false;
	  				
	  				
		  			if(canSearch)
		  			{
			  			pd = ProgressDialog.show(this, null,"LOADING...");
	
			  			v.setBackgroundResource(R.drawable.button);
			  			v.setPadding(30, 0, 40, 5); 
			  			
			  			Thread thread = new Thread(this);
			        	thread.start();
			        	
		  			}
		  			else
		  			{
		  				showDialog(DIALOG_ERROR_EMPTY);
		  			}
	  			}
    	  	}
	  	}
	  	else if(v instanceof EditText)
	  	{
	    	if(event.getAction() == MotionEvent.ACTION_DOWN)
	  		{
	  			//clear base text
	  			if(((EditText)v).getText().toString().equals(this.getString(R.string.etUserName))
	  					||((EditText)v).getText().toString().equals(this.getString(R.string.etTerm))) 
	  			{
	  				((EditText)v).setText("");
	  				((EditText)v).setTextColor(Color.BLACK);
	  			}
	  			
	  		}
	  	}else if(v instanceof ImageView)
	  	{
	  		if(event.getAction() == MotionEvent.ACTION_DOWN)
	  		{
	  			int id = v.getId();
	  			
	  			if(id == R.id.ivInfo)
	  			{
	  				showDialog(DIALOG_INFO);
	  			}
	  		}
	  	}
    	
    	return false;
    }

    /**
     * This is used when they is is pressed and the default text is still in the box we need to clear it.
     */
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		
		if(etUserName.hasFocus())
		{
			if(((EditText)v).getText().toString().equals(this.getString(R.string.etUserName))) 
  			{
  				((EditText)v).setText("");
  				((EditText)v).setTextColor(Color.BLACK);
  			}
		}
		else if(etTerm.hasFocus())
		{
			if(((EditText)v).getText().toString().equals(this.getString(R.string.etTerm))) 
  			{
  				((EditText)v).setText("");
  				((EditText)v).setTextColor(Color.BLACK);
  			}
		}
		return false;
	}
}