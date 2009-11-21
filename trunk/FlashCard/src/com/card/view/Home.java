package com.card.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.card.R;
import com.card.domain.CardSet;
import com.card.handler.ApplicationHandler;
import com.card.util.Constants;

public class Home extends Activity implements OnTouchListener,Runnable {
	private Button btUserName;
	private Button btTerm;
	private final int TYPE_TERM=0;
	private final int TYPE_USER_NAME=1;
	private int CHOOSEN_TYPE;
	private final String DEV_KEY="f4ndi00uluokcc0c";
	private final String EXTRAS ="&extended=on&sort=most_recent";
	private final String API_VERS ="1.0";
	private Intent intent;
	private ProgressDialog pd;
	private final int MENU_RESULTS=0;
	private final int MENU_USER_PREF=1;
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
    	btUserName = (Button)findViewById(R.id.btnUserNameCardSet);
    	btUserName.setOnTouchListener(this);
    	btTerm = (Button)findViewById(R.id.btnTerm);
    	btTerm.setOnTouchListener(this);
    	
    	EditText etUserName = (EditText)findViewById(R.id.etUserName);
    	etUserName.setOnTouchListener(this);
    	
    	EditText etTerm = (EditText)findViewById(R.id.etTerm);
    	etTerm.setOnTouchListener(this); 
    	
    	ImageView info = (ImageView)findViewById(R.id.ivInfo);
    	info.setOnTouchListener(this);
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0, MENU_RESULTS, 0, "Test Results").setIcon(R.drawable.results);
    	menu.add(0, MENU_USER_PREF, 0, "Preferences").setIcon(android.R.drawable.ic_menu_preferences);
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
	 	    }
	    
	    return false;
	}
    
    private void getCardSets(int type)
    {
    	try {
    		
    		EditText etValue=null;
    		String value = "";
    		if(type==TYPE_USER_NAME)
    		{
    			etValue= (EditText)findViewById(R.id.etUserName);
    			value = "&q=creator:"+ etValue.getText().toString().trim();
    		}
    		else if(type == TYPE_TERM)
    		{
    			etValue = (EditText)findViewById(R.id.etTerm);
    			value = "&q=term:"+etValue.getText().toString().trim();
    		}
    		
	        URL url = new URL("http://quizlet.com/api/"+ API_VERS +"/sets?dev_key="+DEV_KEY + value + EXTRAS);
	        InputStream is = url.openStream();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	        StringBuilder sb = new StringBuilder();
	        
	        String line;
	        while ((line = reader.readLine()) != null) 
	        {
	        	sb.append(line + "\n");
	        }
	        
	        JSONTokener toke = new JSONTokener(sb.toString());
	        JSONObject jsonObj = new JSONObject(toke);
	        if(((String)jsonObj.get("response_type")).equals("ok"))
	        {
		        JSONArray sets = (JSONArray)jsonObj.get("sets");
		        ApplicationHandler.clearHandlerInstance();
		        ApplicationHandler handler = ApplicationHandler.instance();
		        ArrayList<CardSet> cardsets = handler.cardsets;
		        //gets the titles
		        for(int i=0;i<sets.length();i++)
		        {
		        	JSONObject set = (JSONObject)sets.get(i);
		        	CardSet cardset = new CardSet((String)set.get("title"),(JSONArray)set.get("terms"),(Integer)set.get("term_count"));
		        	cardsets.add(cardset);
		        }
		        
		        intent = new Intent(this, CardSetList.class);
		        startActivity(intent);
	        }
	        else
	        	WAS_ERROR=true;
	        
        } catch (MalformedURLException e) {
	        e.printStackTrace();
        } catch (IOException e) {
	        e.printStackTrace();
        } catch (JSONException e) {
	        e.printStackTrace();
        }
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
            .setMessage("Ver:"+Constants.VERSION+"\nsupport@"+Constants.COMPANY_NAME+".com \n\n"+Constants.COMPANY_NAME+".com\ncopyright � 2009")
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
    		
	  		if(event.getAction() == MotionEvent.ACTION_DOWN)
	  		{
	  			v.setBackgroundResource(R.drawable.button_hvr);
	  			v.setPadding(30, 0, 40, 5);
	  		}
	  		
	  		else if(event.getAction()== MotionEvent.ACTION_UP)
	  		{
	  			EditText etValue;
	  			boolean canSearch=true;
	  			
	  			if(v==btTerm)
	  			{
	  				CHOOSEN_TYPE=TYPE_TERM;
	  				etValue = (EditText)findViewById(R.id.etTerm);
	  				Editable ed = etValue.getText();
	  				CharSequence cs = getText(R.string.etTerm);
	  				
	  				if(ed.toString().equals(cs.toString()))
	  					canSearch=false;
	  			}
	  	    	else if(v == btUserName)
	  	    	{
	  	    		CHOOSEN_TYPE=TYPE_USER_NAME;
	  	    		etValue= (EditText)findViewById(R.id.etUserName);
	  	    		Editable ed = etValue.getText();
	  	    		CharSequence cs = getText(R.string.etUserName);
	  	    		if(ed.toString().equals(cs.toString()))
	  					canSearch=false;
	  	    	}
	  			
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