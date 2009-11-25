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
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.card.R;
import com.card.domain.BookmarkDomain;
import com.card.domain.FlashCard;
import com.card.util.AppUtil;
import com.card.util.BookMarkArrayAdapter;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class BookmarkList extends Activity {
	private Intent intent;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bookmarks);
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
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		
		
		ListView bookmarkslist = (ListView) findViewById(R.id.bookmarkList);
		bookmarkslist.setAdapter(new BookMarkArrayAdapter(this, R.layout.bookmark_item, items));
	}

}
