/**
 * Date: Nov 24, 2009 Project: FlashCard User: dmason This software is subject
 * to license of IBBL-TGen http://www.gouvernement.lu/ http://www.tgen.org
 */
package com.card.view;

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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.card.R;
import com.card.domain.BookmarkDomain;
import com.card.domain.CardSet;
import com.card.handler.ApplicationHandler;
import com.card.util.AppUtil;
import com.card.util.BookMarkArrayAdapter;
import com.card.util.Constants;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class BookmarkList extends Activity implements Runnable{
	private Context context;
	private ProgressDialog pd;
	private String bookmarkvalue;
	private Thread thread;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bookmarks);
		context=this;
		thread= new Thread(this);
		initComponents();
	}
	/**
	 * init screen list
	 */
	private void initComponents() {
		
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
	        }
	        
	        for(int i=0;i<bookmarkwrapper.length();i++)
	        {
	        	JSONArray bookmark = (JSONArray)bookmarkwrapper.get(i);
	        	BookmarkDomain bookmarkdomain = new BookmarkDomain((Integer)bookmark.get(0),(String)bookmark.get(1));
	        	items.add(bookmarkdomain);	        	
	        }
	        
	        
        } catch (JSONException e) {
	        e.printStackTrace();
        }
		
		
		ListView bookmarkslist = (ListView) findViewById(R.id.bookmarkList);
		bookmarkslist.setAdapter(new BookMarkArrayAdapter(this, R.layout.bookmark_item, items));
		bookmarkslist.setOnItemClickListener(bookmarkListener);
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
	
			
			BookmarkDomain bookmark = ((BookmarkDomain)parent.getItemAtPosition(position));
			bookmarkvalue = "&q=ids:"+bookmark.id;
			
			pd = ProgressDialog.show(context, null,"LOADING...");
  			
        	thread.start();
			
			
			}};
	
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
