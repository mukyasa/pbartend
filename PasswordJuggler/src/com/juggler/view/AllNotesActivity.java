/**
 * Date: Dec 29, 2009
 * Project: PasswordJuggler
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.juggler.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.juggler.utils.Constants;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class AllNotesActivity extends AllViewActivity implements OnClickListener {
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	/* (non-Javadoc)
	 * @see com.juggler.view.FooterListActivity#onResume()
	 */
	@Override
	protected void onResume() {
		initialize();
	    super.onResume();
	}
	
	/* (non-Javadoc)
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
	    super.onListItemClick(l, v, position, id);
	    
	    Intent intent = new Intent(this, DetailsActivity.class);
	    intent.putExtra(Constants.INTENT_EXTRA_SELECTED_TEXT,((TextView)v).getText());
	    intent.putExtra(Constants.INTENT_EXTRA_SELECTED_ROW, id);
    	startActivity(intent);
	}
	
	protected void initialize() {
		super.initialize();
		//set title
		TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText(getString(R.string.allnotes));
		
		recordscCursor = passDao.getAllNotes();
		
		//set screen type
		Constants.SCREEN_TYPE=Constants.NOTES;
		
	}
}
