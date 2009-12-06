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
import com.flashcard.util.BookMarkArrayAdapter;
import com.flashcard.util.Constants;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class BookmarkList extends Activity implements Runnable{
	private Context context;
	private ProgressDialog pd;
	private String bookmarkvalue;
	private Thread thread;
	private final int MENU_NEW=0;
	private boolean didDelete=false;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bookmarks);
		 setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		context=this;
		thread= new Thread(this);
		initComponents();
	}
	
	private ListView initCardList()
	{
		//get list
		String oldbookmarks = AppUtil.getBookmarks(this);
		List<BookmarkDomain> items = new ArrayList<BookmarkDomain>();
		
		
		try {
	        JSONObject bookmarks=null;
	        JSONArray bookmarkwrapper=null;
	        if(!"".equals(oldbookmarks))
	        {
	        	JSONTokener toke = new JSONTokener(oldbookmarks);
	        	bookmarks = new JSONObject(toke);
	        	bookmarkwrapper = new JSONArray(bookmarks.getString(AppUtil.BOOKMARKS));
	        
	        
		        for(int i=0;i<bookmarkwrapper.length();i++)
		        {
		        	JSONArray bookmark = (JSONArray)bookmarkwrapper.get(i);
		        	BookmarkDomain bookmarkdomain = new BookmarkDomain((Integer)bookmark.get(0),(String)bookmark.get(1));
		        	items.add(bookmarkdomain);	        	
		        }
	        }
	        
	        
        } catch (JSONException e) {
	        e.printStackTrace();
        }
		
		
		ListView bookmarkslist = (ListView) findViewById(R.id.bookmarkList);
		bookmarkslist.setAdapter(new BookMarkArrayAdapter(this, R.layout.bookmark_item, items));
		return bookmarkslist;
	}
	/**
	 * init screen list
	 */
	private void initComponents() {
		
		ListView bookmarkslist = initCardList();
		bookmarkslist.setOnItemClickListener(bookmarkListener);
		bookmarkslist.setOnItemLongClickListener(longclickListener);
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
		
		if(AppUtil.initCardSets(value,Constants.SORT_TYPE_DEFALUT,Constants.DEFAULT_PAGE_NUMBER))
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
	
	   AdapterView.OnItemClickListener bookmarkListener = new OnItemClickListener(){
			
		public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
	
			if(!didDelete)
			{
				BookmarkDomain bookmark = ((BookmarkDomain)parent.getItemAtPosition(position));
				bookmarkvalue = "&q=ids:"+bookmark.id;
				
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
			            AppUtil.deleteBookmarks(context, bookmark.id);
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
	    getCardSets(bookmarkvalue);
	    handler.sendEmptyMessage(0);
	    
    }

}
