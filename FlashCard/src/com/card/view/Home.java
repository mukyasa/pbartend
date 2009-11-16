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
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;

import com.card.R;
import com.card.domain.CardSets;
import com.card.handler.ApplicationHandler;

public class Home extends Activity implements OnTouchListener {
	private Button btUserName;
	private Button btTerm;
	private final int TYPE_TERM=0;
	private final int TYPE_USER_NAME=1;
	private final String DEV_KEY="f4ndi00uluokcc0c";
	private final String EXTRAS ="&extended=on&sort=most_recent";
	private final String API_VERS ="1.0";
	private Intent intent;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_load);
        initScreen();
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
		        ApplicationHandler handler = ApplicationHandler.instance();
		        ArrayList<CardSets> cardsets = handler.cardsets;
		        //gets the titles
		        for(int i=0;i<sets.length();i++)
		        {
		        	JSONObject set = (JSONObject)sets.get(i);
		        	CardSets cardset = new CardSets((String)set.get("title"),(JSONArray)set.get("terms"),(Integer)set.get("term_count"));
		        	cardsets.add(cardset);
		        }
		        
		        intent = new Intent(this, CardSetList.class);
		        startActivity(intent);
	        }
	        else
	        {
	        	//error dialog
	        	showDialog(0);
	        }
	        
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
    	return new AlertDialog.Builder(Home.this)
        .setIcon(R.drawable.error)
        .setMessage("No card sets found, please try again.")
        .setTitle("Error")
        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
               dismissDialog(0);
            }
        })      
       .create();

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
	  			v.setBackgroundResource(R.drawable.button);
	  			v.setPadding(30, 0, 40, 5);
	  			
	  			if(v==btTerm)
	  	    		getCardSets(TYPE_TERM);
	  	    	else if(v == btUserName)
	  	    		getCardSets(TYPE_USER_NAME);
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
	  	}
    	
    	return false;
    }
}