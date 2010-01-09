/***
	Copyright (c) 2008-2009 CommonsWare, LLC
	Portions (c) 2009 Google, Inc.
	
	Licensed under the Apache License, Version 2.0 (the "License"); you may
	not use this file except in compliance with the License. You may obtain
	a copy of the License at
		http://www.apache.org/licenses/LICENSE-2.0
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */

package com.flashcard.handler;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.flashcard.R;
import com.flashcard.domain.CardSet;
import com.flashcard.view.OfflineListView;

/**
 * Adapter that delegates to a wrapped LisAdapter, much as CursorWrapper
 * delegates to a wrapped Cursor.
 */
public class AdapterWrapper extends BaseAdapter {
	private ListAdapter wrapped = null;
	private Context context;

	/**
	 * Constructor wrapping a supplied ListAdapter
	 */
	public AdapterWrapper(ListAdapter wrapped) {
		super();

		this.wrapped = wrapped;
	}
	
	public AdapterWrapper(ListAdapter wrapped,Context context) {
		super();
		this.context = context;
		this.wrapped = wrapped;
	}

	/**
	 * Get the data item associated with the specified position in the data set.
	 * 
	 * @param position
	 *            Position of the item whose data we want 
	 */
	public Object getItem(int position) {
		return (wrapped.getItem(position));
	}

	/**
	 * How many items are in the data set represented by this Adapter.
	 */
	public int getCount() {
		return (wrapped.getCount());
	}

	/**
	 * Returns the number of types of Views that will be created by getView().
	 */
	@Override
	public int getViewTypeCount() {
		return (wrapped.getViewTypeCount());
	}

	/**
	 * Get the type of View that will be created by getView() for the specified
	 * item.
	 * 
	 * @param position
	 *            Position of the item whose data we want
	 */
	@Override
	public int getItemViewType(int position) {
		return (wrapped.getItemViewType(position));
	}

	/**
	 * Are all items in this ListAdapter enabled? If yes it means all items are
	 * selectable and clickable.
	 */
	@Override
	public boolean areAllItemsEnabled() {
		return (wrapped.areAllItemsEnabled());
	}

	/**
	 * Returns true if the item at the specified position is something
	 * selectable.
	 * 
	 * @param position
	 *            Position of the item whose data we want
	 */
	@Override
	public boolean isEnabled(int position) {
		return (wrapped.isEnabled(position));
	}

	/**
	 * Get a View that displays the data at the specified position in the data
	 * set.
	 * 
	 * @param position
	 *            Position of the item whose data we want
	 * @param convertView
	 *            View to recycle, if not null
	 * @param parent
	 *            ViewGroup containing the returned View
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		CardSet cardset = (CardSet)wrapped.getItem(position);
		
		Activity activity = (Activity) context;
		LayoutInflater inflater = activity.getLayoutInflater();
		
		String tabs ="\t\t";
        String title;
        
        if(cardset.title.length() > 37)//trunc at 37 char
        	title = cardset.title.substring(0, 32) + "...";
        else
        	title=cardset.title;
        FrameLayout frameRowView;
		if(activity instanceof OfflineListView)
		{
			 // Inflate the views from XML
	        frameRowView = (FrameLayout) inflater.inflate(R.layout.offline_item, null);
	        TextView rowView = (TextView)frameRowView.findViewById(android.R.id.text1);
	        rowView.setText(title);
	        
		}else
		{
		
			 // Inflate the views from XML
	        frameRowView = (FrameLayout) inflater.inflate(R.layout.cardlist_item, null);
	        TextView rowView = (TextView)frameRowView.findViewById(android.R.id.text1);
	        
	        
	        String count = cardset.cardCount.toString();
	        if(count.length() == 1)
	        	count = " " +count;
	        
	        if(count.length() > 2)
	        	tabs = "\t";
	        
	        rowView.setText(count + tabs + title);
		}
		
        return frameRowView;
		//return (wrapped.getView(position, convertView, parent));
	}

	/**
	 * Get the row id associated with the specified position in the list.
	 * 
	 * @param position
	 *            Position of the item whose data we want
	 */
	public long getItemId(int position) {
		return (wrapped.getItemId(position));
	}

	/**
	 * Returns the ListAdapter that is wrapped by the endless logic.
	 */
	protected ListAdapter getWrappedAdapter() {
		return (wrapped);
	}
}