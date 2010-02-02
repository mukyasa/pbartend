package com.flashcard.view;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.flashcard.R;
import com.flashcard.util.AppUtil;
import com.flashcard.util.Authenication;
import com.flashcard.util.Constants;

public class Home extends Activity implements OnTouchListener,Runnable {
	private Button btSearch;
	private RadioButton rbUser,rbSavedCard,rbTerm,rbId;
	private final int TYPE_USER_NAME=0;
	private final int TYPE_TERM=1;
	private final int TYPE_ID=2;
	private final int TYPE_SAVED=3;
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
	private Context context;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_load);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context=this;
        initScreen();
      //for demo
        if(!Authenication.getRegPrefererences(this))
        	showDialog(Authenication.ISDEMO);
        
        if(AppUtil.getSawDirections(this) <=0)
        	startActivity(new Intent(this,HomeInstructionsActivity.class));
    }
    
    
    /* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		Constants.count=0;
		Constants.countlabel=1;
		AppUtil.setLastVeiwedCard(this, 0);
	    super.onResume();
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
    	rbSavedCard = (RadioButton)findViewById(R.id.rbSaved);
    	rbSavedCard.setOnTouchListener(this);
    	
    	
    	ImageView info = (ImageView)findViewById(R.id.ivInfo);
    	info.setOnTouchListener(this);
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0, MENU_RESULTS, 0, getResources().getString(R.string.results)).setIcon(R.drawable.results);
    	menu.add(0, MENU_USER_PREF, 0, "Preferences").setIcon(android.R.drawable.ic_menu_preferences);
    	menu.add(0, MENU_BOOKMARKS, 0, "View Bookmarks").setIcon(R.drawable.bookmark);
	    return true;
	}

	/* Handles item selections */
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    	 case MENU_RESULTS:
	 			startActivity(new Intent(this, Results.class));
	 	    	return true;
	    	 case MENU_USER_PREF:
		 			startActivity(new Intent(this, UserPrefActivity.class));
		 	    	return true;
	    	 case MENU_BOOKMARKS:
		 			startActivity(new Intent(this, BookmarkList.class));
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
    	}else if(id == Authenication.ISDEMO){	
    		LayoutInflater factory = LayoutInflater.from(this); 
            final View textEntryView = factory.inflate(R.layout.alert_dialog_text_entry, null);
            return new AlertDialog.Builder(Home.this)
                .setIcon(R.drawable.info)
                .setTitle(R.string.regdialog) 
                .setView(textEntryView)
                .setCancelable(false)
                .setPositiveButton(R.string.register, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    	EditText email =  (EditText)textEntryView.findViewById(R.id.username_edit);
                    	EditText authCode =  (EditText)textEntryView.findViewById(R.id.password_edit);
                    	
                        if(!Authenication.authenitcate(email.getText().toString().trim().toLowerCase(), authCode.getText().toString().trim().toLowerCase()) )
                        	finish();
                        else
                        	Authenication.setRegPrefererences(context);
                        	
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        finish();
                    }
                })
                .create();
    	}else{
    		Calendar c = Calendar.getInstance();
    		return new AlertDialog.Builder(Home.this)
            .setIcon(R.drawable.info)
            .setMessage("Ver:"+Constants.VERSION+"\nsupport@"+Constants.COMPANY_NAME+".com \n\n"+Constants.COMPANY_NAME+".com\ncopyright © "+c.get(Calendar.YEAR))
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

    	EditText etValue = (EditText)findViewById(R.id.etSearchTerm);
    	
    	if(v instanceof Button )
	  	{
    		if(v instanceof RadioButton)
    	  	{
    			if(event.getAction()== MotionEvent.ACTION_UP)
    			{
    				CHOOSEN_TYPE =  Integer.valueOf((((RadioButton) v).getHint().toString()));
    				
    				if(CHOOSEN_TYPE == TYPE_SAVED)
    				{
    					etValue.setEnabled(false);
    				}else
    					etValue.setEnabled(true);
    			}
    	  	}else
    	  	{
    		
		  		if(event.getAction() == MotionEvent.ACTION_DOWN)
		  		{
		  			v.setBackgroundResource(R.drawable.button_hvr);
		  			v.setPadding(30, 0, 40, 5);
		  		}
		  		
		  		else if(event.getAction()== MotionEvent.ACTION_UP)
		  		{
		  			boolean canSearch=true;
		  			v.setBackgroundResource(R.drawable.button);
		  			v.setPadding(30, 0, 40, 5);
	  				Editable ed = etValue.getText();
	  				
	  				if("".equals(ed.toString()) || CHOOSEN_TYPE == TYPE_SAVED)
	  					canSearch=false;
	  				
	  				
		  			if(canSearch)
		  			{
			  			pd = ProgressDialog.show(this, null,"LOADING...");
	
			  			v.setBackgroundResource(R.drawable.button);
			  			v.setPadding(30, 0, 40, 5); 
			  			
			  			Thread thread = new Thread(this);
			        	thread.start();
			        	
		  			}
		  			else if(CHOOSEN_TYPE != TYPE_SAVED)
		  			{
		  				showDialog(DIALOG_ERROR_EMPTY);
		  			}
		  			else if(CHOOSEN_TYPE == TYPE_SAVED)
		  			{
		  				//call saved list
		  				intent = new Intent(this, OfflineCardsList.class);
			 			startActivity(intent);
		  				
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

}