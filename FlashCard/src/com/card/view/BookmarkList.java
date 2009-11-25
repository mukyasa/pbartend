/**
 * Date: Nov 24, 2009 Project: FlashCard User: dmason This software is subject
 * to license of IBBL-TGen http://www.gouvernement.lu/ http://www.tgen.org
 */
package com.card.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.card.R;
import com.card.domain.BookmarkDomain;
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
		String bookmarks = AppUtil.getBookmarks(this);
		String[] BOOKMARKS = bookmarks.split("|");
		List<BookmarkDomain> items = new ArrayList<BookmarkDomain>();
		
		try {
			BookmarkDomain bookmark = new BookmarkDomain();
	        for(int i=0;i<BOOKMARKS.length;i++)
	        {
	        	if(i==0 || i%1==0)
	        	bookmark.id = BOOKMARKS[i];
	        	else
	        	bookmark.title = BOOKMARKS[i];
	        	
	        	items.add(bookmark);
	        }
	        
        } catch (Exception e) { }
		
		ListView bookmarkslist = (ListView) findViewById(R.id.bookmarkList);
		bookmarkslist.setAdapter(new BookMarkArrayAdapter(this, R.layout.bookmark_item, items));
	}

}
