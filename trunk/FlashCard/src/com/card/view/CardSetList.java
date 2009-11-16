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
			//v.setBackgroundResource(R.drawable.clickbg);
			//v.setPadding(18, 2, 0, 2);
			//pd = ProgressDialog.show(this, null,"LOADING...");
			super.onListItemClick(l, v, position, id);
			//Log.v(getClass().getSimpleName(), "id=" + id + " type=" + ScreenType.getInstance().type);
			intent.putExtra(INTENT_EXTRA_SELECTED_ROW, id);
			startActivityForResult(intent, INTENT_NEXT_SCREEN);
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
