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
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.card.R;
import com.card.domain.CardSets;
import com.card.handler.ApplicationHandler;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class CardSetList extends ListActivity {

	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_frame); 
        //intent = new Intent(this, DetailsView.class);
        initComponents();
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
