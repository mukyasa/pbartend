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
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.card.R;
import com.card.domain.CardSets;
import com.card.handler.ApplicationHandler;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_frame);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        intent = new Intent(this, FlashCardTest.class);
        initComponents();
    }
	
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{ 
			v.setBackgroundResource(R.drawable.list_item_hvr);
			v.setPadding(13, 10, 0, 0);
			listview = v;
			pd = ProgressDialog.show(this, null,"LOADING...");
			CardSets cardSetPicked = (CardSets)l.getItemAtPosition(position);
			ApplicationHandler.instance().pickedSet = cardSetPicked;
			
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
    @SuppressWarnings("unchecked")
    private void initComponents() {
    	
    	ApplicationHandler handler = ApplicationHandler.instance();
    	ArrayList<CardSets> cardsets = handler.cardsets;
    	ListAdapter adapter = new CardSetArrayAdapter(this,R.layout.cardlist_item,cardsets);
    	
    	setListAdapter(adapter);
    	
	}
}
