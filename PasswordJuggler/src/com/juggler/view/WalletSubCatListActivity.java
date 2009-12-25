package com.juggler.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.juggler.dao.PasswordDAO;
import com.juggler.dao.PasswordDbHelper;
import com.juggler.dao.QuiresDAO;
import com.juggler.utils.Constants;

public class WalletSubCatListActivity extends FooterListActivity{
	private Button butPrev;
	private PasswordDbHelper myDatabaseAdapter;
	private PasswordDAO passDao;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.list_frame);
		//set up database for use
		passDao = new PasswordDAO();
		myDatabaseAdapter = PasswordDbHelper.getInstance(this);
		passDao.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
		initialize();
		super.onCreate(savedInstanceState);
	}
	private void initialize() {
		
		Cursor recordscCursor;
		
		//get Intent then set text
		Intent selectedIntent = getIntent();
		long selectedRow = selectedIntent.getLongExtra(Constants.INTENT_EXTRA_SELECTED_ROW, 0);
		CharSequence text =  selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_SELECTED_TEXT);
		
		//set title 
		TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText(text);
		
		recordscCursor = passDao.getSubCategories(selectedRow+"");
		
		// hide next button
		Button next = (Button) findViewById(R.id.butNext);
		next.setVisibility(View.GONE);
		butPrev = (Button) findViewById(R.id.butPrev);
		butPrev.setOnClickListener(this);
		butPrev.setOnTouchListener(this);
		
		String[] from = new String[] { QuiresDAO.COL_NAME };
		int[] to = new int[] { R.id.list_row};
    	SimpleCursorAdapter records = new SimpleCursorAdapter(this,
				R.layout.list_item, recordscCursor, from, to);
    	setListAdapter(records);
		getListView().setTextFilterEnabled(true);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View,
	 * android.view.MotionEvent)
	 */
	public boolean onTouch(View v, MotionEvent event) {
		super.onTouch(v, event);
		if (v instanceof Button) {
			
			if(v == butPrev)
			{
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					((Button)v).setBackgroundResource(R.drawable.prev_button_on);
					((Button)v).setPadding(20,0,10,0);
				} 
				else{
					((Button)v).setBackgroundResource(R.drawable.prev_button);
					((Button)v).setPadding(20,0,10,0);
				}
			}
		}
		
		
		return false;
	}
	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View v) {
		super.onClick(v);
		if (v == butPrev) {
			finish();
		}
	}
}