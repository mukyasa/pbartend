/**
 * Date: Nov 15, 2009
 * Project: FlashCard
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.card.view;

import java.util.ArrayList;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.card.R;
import com.card.domain.CardSet;
import com.card.handler.ApplicationHandler;
import com.card.util.AppUtil;
import com.card.util.CardSetArrayAdapter;
import com.card.util.Constants;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$ 
 */
public class CardSetList extends ListActivity {

	private Intent intent;
	protected static final String INTENT_EXTRA_SELECTED_ROW = "SELECTED_ROW";
	protected static final int INTENT_NEXT_SCREEN = 0;
	private View listview=null;
	private ProgressDialog pd;
	private Context context;
	private boolean firtTimeIn=true;
	
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
			v.setBackgroundResource(R.drawable.list_item_hvr);
			v.setPadding(13, 10, 0, 0);
			listview = v;
			pd = ProgressDialog.show(this, null,"LOADING...");
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
	
    /**
     * init screen list
     */
    private void initComponents() {
    	
    	Spinner filterType = (Spinner)findViewById(R.id.filterType);
    	ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.filterTypeValues, android.R.layout.simple_spinner_item);
    	spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterType.setAdapter(spinnerAdapter);

    	
    	filterType.setOnItemSelectedListener(new OnItemSelectedListener(){
    		String sortType = Constants.SORT_TYPE_DEFALUT;
    		ProgressDialog pd;
    		
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
						//AppUtil.initCardSets(AppUtil.searchTerm, sortType, Constants.DEFAULT_PAGE_NUMBER);
					}
					else
					{
						firtTimeIn=false;
						ApplicationHandler handler = ApplicationHandler.instance();
	 			    	ArrayList<CardSet> cardsets = handler.cardsets;
	 			    	ListAdapter adapter = new CardSetArrayAdapter(context,R.layout.cardlist_item,cardsets);
	 			    	setListAdapter(adapter);
					}
    		 }
    		 
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
  			    	ListAdapter adapter = new CardSetArrayAdapter(context,R.layout.cardlist_item,cardsets);
  			    	setListAdapter(adapter);
                    pd.dismiss();
                 }

             };
    	
    	
    	});

    }
}
