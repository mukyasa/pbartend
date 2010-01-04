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

import android.os.Bundle;
import android.view.View.OnClickListener;
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
