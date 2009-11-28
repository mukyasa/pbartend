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
import com.flashcard.domain.CardSet;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class CardSetArrayAdapter extends ArrayAdapter<CardSet> {

	private List<CardSet> cards=null;
	/**
     * Nov 15, 2009
     * @param context
     * @param textViewResourceId
     * @param objects
     * 
     */
    public CardSetArrayAdapter(Context context, int textViewResourceId, List<CardSet> cards) {
	    super(context, textViewResourceId, cards);
	    this.cards = cards;

    }

	@SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
		 	Activity activity = (Activity) getContext();
	        LayoutInflater inflater = activity.getLayoutInflater();

	        // Inflate the views from XML
	        TextView rowView = (TextView) inflater.inflate(R.layout.cardlist_item, null);
	        
	        String tabs ="\t\t";
	        CardSet cardset = (CardSet)cards.get(position);
	        String title;
	        if(cardset.title.length() > 37)//trunc at 37 char
	        	title = cardset.title.substring(0, 32) + "...";
	        else
	        	title=cardset.title;
	        	
	        String count = cardset.cardCount.toString();
	        if(count.length() == 1)
	        	count = " " +count;
	        
	        if(count.length() > 2)
	        	tabs = "\t";
	        
	        rowView.setText(count + tabs + title);
	        
	        return rowView;
    }

	
}
