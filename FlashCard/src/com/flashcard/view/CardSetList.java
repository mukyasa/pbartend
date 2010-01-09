/**
 * Date: Nov 15, 2009
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

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.flashcard.R;
import com.flashcard.domain.CardSet;
import com.flashcard.handler.ApplicationHandler;
import com.flashcard.handler.EndlessAdapter;
import com.flashcard.util.AppUtil;
import com.flashcard.util.Constants;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$ 
 */
public class CardSetList extends ListActivity{

	private Intent intent;
	protected static final String INTENT_EXTRA_SELECTED_ROW = "SELECTED_ROW";
	protected static final int INTENT_NEXT_SCREEN = 0;
	private View listview=null;
	private ProgressDialog pd;
	private Context context;
	private boolean firtTimeIn=true;
	private String sortType = Constants.SORT_TYPE_DEFALUT;
	private final int MENU_NEW=0;
	private final int MENU_LOCAL=1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_frame);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        intent = new Intent(this, FlashCardTest.class);
        context=this;
        initComponents();
    }
	

	
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{ 
			TextView child = (TextView)v.findViewById(android.R.id.text1);
		
			child.setBackgroundResource(R.drawable.list_item_hvr);
			child.setPadding(13, 10, 0, 0);
			listview = child;
			pd = ProgressDialog.show(this, null,"LOADING...",false,true);
			CardSet cardSetPicked = (CardSet)l.getItemAtPosition(position);
			//this is the set picked from the list screen it will change when they retest correct
			ApplicationHandler.instance().currentlyUsedSet = cardSetPicked; 
			//this is the set picked from the list screen alway constant
			ApplicationHandler.instance().orignalUsedSet = cardSetPicked;
			
			super.onListItemClick(l, v, position, id);
			//Log.v(getClass().getSimpleName(), "id=" + id + " type=" + ScreenType.getInstance().type);
			intent.putExtra(INTENT_EXTRA_SELECTED_ROW, id);
			startActivityForResult(intent, INTENT_NEXT_SCREEN);
	}
	
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0, MENU_NEW, 0, getResources().getString(R.string.home)).setIcon(R.drawable.newcardset);
    	menu.add(0, MENU_LOCAL, 0, "Save Cards Offline").setIcon(android.R.drawable.ic_menu_save);
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
		    	intent = new Intent(this, OfflineListView.class);
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
		if(listview!=null)
		{
			listview.setBackgroundResource(R.drawable.list_item);
			listview.setPadding(13, 10, 0, 0);
		}
		if(pd!=null)
			pd.dismiss();
		
		super.onStop();
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
	
	 /******************************************/

	 class CardSetAdapter extends EndlessAdapter {
			private RotateAnimation rotate = null;

			CardSetAdapter(ArrayList<CardSet> list) {
				super(new ArrayAdapter<CardSet>(CardSetList.this,R.layout.cardlist_item, android.R.id.text1, list),context);
				
				rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				rotate.setDuration(600);
				rotate.setRepeatMode(Animation.RESTART);
				rotate.setRepeatCount(Animation.INFINITE);
			}

			protected View getPendingView(ViewGroup parent) {
				View row = getLayoutInflater().inflate(R.layout.cardlist_item, null);

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
			        
			        String tabs ="\t\t";
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
			        
			        child.setVisibility(View.VISIBLE);
			        ((TextView) child).setText(count + tabs + title);
	
					child = row.findViewById(R.id.throbber);
					View wrapper  = row.findViewById(R.id.throbberWrapper);
					wrapper.setVisibility(View.GONE);
					child.clearAnimation();
				}
			}

			protected boolean appendInBackground() {
				SystemClock.sleep(2000); // pretend to do work
				ArrayAdapter<CardSet> a = (ArrayAdapter<CardSet>) getWrappedAdapter();
				
				int nextCardSet = (a.getCount()/Constants.CARDS_PER_PAGE)+1;
				//Log.v(getClass().getSimpleName(), "nextCardSet=" + nextCardSet);
	            JSONArray sets = AppUtil.getQuizletData(AppUtil.searchTerm, sortType,nextCardSet);
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
    	
    	//set up spinner
    	Spinner filterType = (Spinner)findViewById(R.id.filterType);
    	ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.filterTypeValues, android.R.layout.simple_spinner_item);
    	spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterType.setAdapter(spinnerAdapter);

    	filterType.setOnItemSelectedListener(new OnItemSelectedListener(){
		ProgressDialog pd;
		
		 @SuppressWarnings("unchecked")
        public void onItemSelected(AdapterView parent, View v,int position, long id) { 
            
				//Log.v(getClass().getSimpleName(), "position=" + position);
				switch(position)
				{
					case 0: sortType = Constants.SORT_TYPE_DEFALUT;break;
					case 1: sortType = Constants.SORT_TYPE_STUDIED;break;
					case 2: sortType = Constants.SORT_TYPE_RECENT;break;
				}
				//Log.v(getClass().getSimpleName(), "firtTimeIn=" + firtTimeIn);
				
				if(!firtTimeIn)//skip the first time in thread this
				{
					pd = ProgressDialog.show(context, null,"RELOADING...");
					startHeavyDutyStuff();
				}
				else
				{
					firtTimeIn=false;
					ApplicationHandler handler = ApplicationHandler.instance();
 			    	ArrayList<CardSet> cardsets = handler.cardsets;
 			    	setListAdapter(new CardSetAdapter(cardsets));
				}
    		 }
		 
		 
    		 
             @SuppressWarnings("unchecked")
            public void onNothingSelected(AdapterView arg0) {
                  
             } 
             
             void startHeavyDutyStuff() {

                 // Here is the heavy-duty thread
                 Thread t = new Thread() {

                     public void run() {
                        	AppUtil.initCardSets(AppUtil.searchTerm, sortType, Constants.DEFAULT_PAGE_NUMBER);   
                             //Send update to the main thread
                             messageHandler.sendEmptyMessage(0); 
                     }
                 };
                 t.start();
             } 

             // Instantiating the Handler associated with the main thread.
             private Handler messageHandler = new Handler() {

                 @Override
                 public void handleMessage(Message msg) { 
                	ApplicationHandler handler = ApplicationHandler.instance();
  			    	ArrayList<CardSet> cardsets = handler.cardsets;
  			    	setListAdapter(new CardSetAdapter(cardsets));
                    pd.dismiss();
                 }

             };
    	
    	
    	});

    }

}
