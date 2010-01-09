/**
 * Date: Dec 1, 2009
 * Project: FlashCard
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.flashcard.view;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.flashcard.R;
import com.flashcard.domain.CardSet;
import com.flashcard.domain.MessageHandler;
import com.flashcard.handler.ApplicationHandler;
import com.flashcard.handler.EndlessAdapter;
import com.flashcard.util.AppUtil;
import com.flashcard.util.Constants;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class OfflineListView extends ListActivity {
	
	private Context context;
	private final int MENU_NEW=0;
	private final int MENU_LOCAL=1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.offline_frame);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context=this;
        initComponents();
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

	
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{ 
		TextView child = (TextView)v.findViewById(android.R.id.text1);

        child.setBackgroundResource(R.drawable.offlineclick);
        child.setPadding(43, 10, 0, 0);

        
        CardSet cardSetPicked = (CardSet)l.getItemAtPosition(position);
        //this is the set picked from the list screen it will change when they retest correct
        ApplicationHandler.instance().currentlyUsedSet = cardSetPicked; 
        //this is the set picked from the list screen alway constant
        ApplicationHandler.instance().orignalUsedSet = cardSetPicked;
        
        try {
        	
        	MessageHandler msg = AppUtil.setSavedCards(this, cardSetPicked);
        	
	        if(!msg.didSave)
	        {
	        	 child.setBackgroundResource(R.drawable.offline_off);
	             child.setPadding(43, 10, 0, 0);	
	             TextView mesg = (TextView)findViewById(R.id.tvOfflineMessage);
	             mesg.setText(msg.message);
	             
	        }
	        
        } catch (JSONException e) { 
	        e.printStackTrace();
        }
        
        super.onListItemClick(l, v, position, id);
        //Log.v(getClass().getSimpleName(), "id=" + id + " type=" + ScreenType.getInstance().type);
			
	}
	
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0, MENU_NEW, 0, getResources().getString(R.string.home)).setIcon(R.drawable.newcardset);
    	menu.add(0, MENU_LOCAL, 0, "View Offline Cards").setIcon(android.R.drawable.ic_menu_view);
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
	    	case MENU_LOCAL:
		    	intent = new Intent(this, OfflineCardsList.class);
				startActivity(intent);
		    	return true;
	    }
	    return false;
	}
	
	
	
	 /******************************************/

	 class CardSetAdapter extends EndlessAdapter {
			private RotateAnimation rotate = null;

			CardSetAdapter(ArrayList<CardSet> list) {
				super(new ArrayAdapter<CardSet>(OfflineListView.this,R.layout.offline_item, android.R.id.text1, list),context);
				
				rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				rotate.setDuration(600);
				rotate.setRepeatMode(Animation.RESTART);
				rotate.setRepeatCount(Animation.INFINITE);
			}

			protected View getPendingView(ViewGroup parent) {
				View row = getLayoutInflater().inflate(R.layout.offline_item, null);

				View child = row.findViewById(android.R.id.text1);

				child.setVisibility(View.GONE);

				child = row.findViewById(R.id.throbber);
				View wrapper  = row.findViewById(R.id.throbberWrapper);
				wrapper.setVisibility(View.VISIBLE); 
				child.startAnimation(rotate);

				return (row);
			}

			@SuppressWarnings("unchecked")
			protected void rebindPendingView(int position, View row) {
				if(row != null)
				{
					View child = row.findViewById(android.R.id.text1);
					
					//set up the text here 
					ArrayAdapter<CardSet> wrapped = (ArrayAdapter<CardSet>) getWrappedAdapter();
					CardSet cardset = (CardSet)wrapped.getItem(position);
					//get the textview and set the text
			        
			        String title;
			        if(cardset.title.length() > 37)//trunc at 37 char
			        	title = cardset.title.substring(0, 32) + "...";
			        else
			        	title=cardset.title;
			        	
			        child.setVisibility(View.VISIBLE);
			        ((TextView) child).setText(title);
	
					child = row.findViewById(R.id.throbber);
					View wrapper  = row.findViewById(R.id.throbberWrapper);
					wrapper.setVisibility(View.GONE);
					child.clearAnimation(); 
				}
			}

			@SuppressWarnings("unchecked")
            protected boolean appendInBackground() {
				SystemClock.sleep(2000); // pretend to do work
				ArrayAdapter<CardSet> a = (ArrayAdapter<CardSet>) getWrappedAdapter();
				
				int nextCardSet = (a.getCount()/Constants.CARDS_PER_PAGE)+1;
				//Log.v(getClass().getSimpleName(), "nextCardSet=" + nextCardSet);
	            JSONArray sets = AppUtil.getQuizletData(AppUtil.searchTerm, Constants.SORT_TYPE_DEFALUT,nextCardSet);
	            ArrayList<CardSet> cardsets = AppUtil.createNewCardSetArrayList(new ArrayList<CardSet>(),sets);
		    	
				for (CardSet item : cardsets) { 
					a.add(item);
				}
				
				return (a.getCount() <= Constants.TOTAL_RESULTS); //on return true if this is true
			}
		}
	 
	 /******************************************/	
	 
    /**
     * init screen list
     */
    private void initComponents() {
    	
    	ApplicationHandler handler = ApplicationHandler.instance();
    	ArrayList<CardSet> cardsets = handler.cardsets;
    	setListAdapter(new CardSetAdapter(cardsets));

    }

}
