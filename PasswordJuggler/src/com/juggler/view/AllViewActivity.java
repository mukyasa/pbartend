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

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.juggler.dao.PasswordDAO;
import com.juggler.dao.PasswordDbHelper;
import com.juggler.dao.QuiresDAO;
import com.juggler.utils.CustomCursorAdapter;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class AllViewActivity extends FooterListActivity implements OnClickListener {
	
	protected Cursor recordscCursor;
	private Button bPrev;
	protected PasswordDAO passDao;
	private PasswordDbHelper myDatabaseAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.list_frame);
		
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
		
		//set up database for use
		passDao = new PasswordDAO();
		myDatabaseAdapter = PasswordDbHelper.getInstance(this);
		passDao.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
		
		// hide next button
		Button next = (Button) findViewById(R.id.butNext);
		next.setVisibility(View.GONE);
		
		bPrev = (Button)findViewById(R.id.butPrev);
		bPrev.setOnClickListener(this);
		
		String[] from = new String[] { QuiresDAO.COL_NAME,QuiresDAO.COL_URL };
		int[] to = new int[] { R.id.list_row};
    	CustomCursorAdapter records = new CustomCursorAdapter(this,
				R.layout.list_item, recordscCursor, from, to);
    	setListAdapter(records);
		getListView().setTextFilterEnabled(true);
		
		
	}
	
	/* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v) {
    	
    	if(v == bPrev){
    		finish();
    	}
	    
    }
}
