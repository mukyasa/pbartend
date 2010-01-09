/**
 * Date: Nov 15, 2009
 * Project: FlashCard
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.flashcard.util;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.flashcard.R;
import com.flashcard.domain.BookmarkDomain;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class SavedCardsArrayAdapter extends ArrayAdapter<BookmarkDomain> {

	private List<BookmarkDomain> bookmarks=null;
	/**
     * Nov 15, 2009
     * @param context
     * @param textViewResourceId
     * @param objects
     * 
     */
    public SavedCardsArrayAdapter(Context context, int textViewResourceId, List<BookmarkDomain> bookmarks) {
	    super(context, textViewResourceId, bookmarks);
	    this.bookmarks = bookmarks;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
		 Activity activity = (Activity) getContext();
	        LayoutInflater inflater = activity.getLayoutInflater();

	        // Inflate the views from XML
	        TextView rowView =(TextView)inflater.inflate(R.layout.offlineview_item, null);
	        
	        BookmarkDomain bookmark = (BookmarkDomain)bookmarks.get(position);
	        
	        rowView.setText(bookmark.title);
	        rowView.setId(Integer.valueOf(bookmark.id));
	        
	        return rowView;
    }

	
}
