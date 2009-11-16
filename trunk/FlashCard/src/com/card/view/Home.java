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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.card.R;
import com.card.domain.CardSets;

public class Home extends Activity implements OnClickListener {
	private Button btUserName;
	private Button btTerm;
	private final int TYPE_TERM=0;
	private final int TYPE_USER_NAME=1;
	private final String DEV_KEY="f4ndi00uluokcc0c";
	private final String EXTRAS ="&extended=on&sort=most_recent";
	private final String API_VERS ="1.0";
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_load);
        initScreen();
    }
    
    private void initScreen(){
    	btUserName = (Button)findViewById(R.id.btnUserNameCardSet);
    	btTerm = (Button)findViewById(R.id.btnTerm);
    	
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
	        JSONArray sets = (JSONArray)jsonObj.get("sets");
	        ArrayList<CardSets> cardsets = new ArrayList<CardSets>();
	        //gets the titles
	        for(int i=0;i<sets.length();i++)
	        {
	        	JSONObject set = (JSONObject)sets.get(i);
	        	CardSets cardset = new CardSets((String)set.get("title"),(JSONArray)set.get("terms"));
	        	cardsets.add(cardset);
	        }
	        
        } catch (MalformedURLException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        } catch (JSONException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
    }

	/* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View view) {
    	
    	if(view==btTerm)
		{
    		getCardSets(TYPE_TERM);
		}
    	else if(view == btUserName)
    	{
    		getCardSets(TYPE_USER_NAME);
    	}
	    
    }
}