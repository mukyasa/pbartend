/**
 * Date: Nov 24, 2009 Project: FlashCard User: dmason This software is subject
 * to license of IBBL-TGen http://www.gouvernement.lu/ http://www.tgen.org
 */
package com.flashcard.view;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.flashcard.R;
import com.flashcard.domain.BookmarkDomain;
import com.flashcard.domain.CardSet;
import com.flashcard.handler.ApplicationHandler;
import com.flashcard.util.AppUtil;
import com.flashcard.util.Constants;
import com.flashcard.util.SavedCardsArrayAdapter;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class OfflineCardsList extends Activity implements Runnable{
	private Context context;
	private ProgressDialog pd;
	private String cardvalue;
	private Thread thread;
	private final int MENU_NEW=0;
	private boolean didDelete=false;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.offlinelistview);
		 setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		context=this;
		thread= new Thread(this);
		initComponents();
	}
	
	private ListView initCardList()
	{
		//get list
		String savedcards = AppUtil.getSavedCards(this);
		List<BookmarkDomain> items = new ArrayList<BookmarkDomain>();
		try {
			
	        JSONObject jsonObj=null;
	        JSONArray sets=null;
	        if(!"".equals(savedcards))
	        {
	        	JSONTokener toke = new JSONTokener(savedcards);
	        	jsonObj = new JSONObject(toke);
	        	
	        	if(((String)jsonObj.get("response_type")).equals("ok"))
	 	        {
	 	        	Constants.TOTAL_RESULTS = (Integer)jsonObj.get("total_results");
	 	        	sets = (JSONArray)jsonObj.get("sets");
	 	        	
	 	        	for(int i=0;i<sets.length();i++)
	 		        {
	 	        		JSONObject set = (JSONObject)sets.get(i);
	 		        	
	 		        	BookmarkDomain bookmarkdomain = new BookmarkDomain((Integer)set.get("id"),(String)set.get("title"));
	 		        	items.add(bookmarkdomain);	        	
	 		        }
	 	        }
	        
	        }
	        
	        
        } catch (JSONException e) {
	        e.printStackTrace();
        }
		
		
		ListView savedList = (ListView) findViewById(R.id.savedList);
		savedList.setAdapter(new SavedCardsArrayAdapter(this, R.layout.offlineview_item, items));
		
		return savedList;
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
	
	/**
	 * init screen list
	 */
	private void initComponents() {
		
		ListView savedList = initCardList();
		savedList.setOnItemClickListener(savedCardsListener);
		savedList.setOnItemLongClickListener(longclickListener);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0, MENU_NEW, 0, getResources().getString(R.string.home)).setIcon(R.drawable.newcardset);
	    return true;
	}

	/* Handles item selections */
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
	    switch (item.getItemId()) {
	    case MENU_NEW:
	    	intent = new Intent(this, Home.class);
			startActivity(intent);
	    	return true;
	    }
	    return false;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		thread = new Thread(this);//get new thread
	    super.onStop();
	}
	
	private void getCardSets(String value){
		
		if(AppUtil.initCardSets(value,Constants.SORT_TYPE_DEFALUT,Constants.DEFAULT_PAGE_NUMBER,true,this))
        {
			ApplicationHandler handler = ApplicationHandler.instance();
            ArrayList<CardSet> cardsets = handler.cardsets;
            CardSet cardSetPicked = cardsets.get(0);
            
            //this is the set picked from the list screen it will change when they retest correct
			ApplicationHandler.instance().currentlyUsedSet = cardSetPicked; 
			//this is the set picked from the list screen alway constant
			ApplicationHandler.instance().orignalUsedSet = cardSetPicked;
            
			Intent intent = new Intent(context, FlashCardTest.class);
	        startActivity(intent);
        }
		
	}
	
	AdapterView.OnItemClickListener savedCardsListener = new OnItemClickListener(){
			
	public void onItemClick(AdapterView<?> parent, View v, int position,long id) {

		if(!didDelete)
		{
			BookmarkDomain bookmark = ((BookmarkDomain)parent.getItemAtPosition(position));
			cardvalue = ""+ bookmark.id;
			
			pd = ProgressDialog.show(context, null,"LOADING...");
			
	    	thread.start();
		}
		else
			didDelete=false;
		
		}};
		
	AdapterView.OnItemLongClickListener longclickListener = new OnItemLongClickListener(){

		public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
			//Log.v("","LONG CLICK");
			didDelete=true;
			BookmarkDomain bookmark = ((BookmarkDomain)parent.getItemAtPosition(position));
			
			v.setVisibility(View.GONE);
			
			try {
	            AppUtil.deleteCard(context, bookmark.id);
	            initCardList();
            } catch (JSONException e) {
            }
			
	        return false;
        }
		
		
	};
	
	private Handler handler = new Handler() {
        
        @Override
        public void handleMessage(Message msg) {
        	if(pd!=null)
        		pd.dismiss();
        }
    };
		    
	/* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
	    getCardSets(cardvalue);
	    handler.sendEmptyMessage(0);
	    
    }

}
