package com.juggler.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.juggler.dao.PasswordDAO;
import com.juggler.dao.PasswordDbHelper;
import com.juggler.dao.QuiresDAO;
import com.juggler.domain.NewPassword;
import com.juggler.utils.Constants;

public class WalletSubCatListActivity extends FooterListActivity implements OnClickListener,OnTouchListener{
	private Button butPrev;
	private PasswordDAO passDao;
	private PasswordDbHelper myDatabaseAdapter;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.list_frame);
		super.onCreate(savedInstanceState);
		
		//set up database for use
		passDao = new PasswordDAO();
		myDatabaseAdapter = PasswordDbHelper.getInstance(this);
		passDao.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
		
		
		initialize();
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
	
	/* (non-Javadoc)
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
	    super.onListItemClick(l, v, position, id);
	    
	    NewPassword np = NewPassword.getInstance();
	    np.subCatId = id;
	    
	    Intent intent = new Intent(this, CreateWalletTemplateActivity.class);
	    intent.putExtra(Constants.INTENT_EXTRA_SELECTED_TEXT,((TextView)v).getText());
	    intent.putExtra(Constants.INTENT_EXTRA_SELECTED_ROW, id);
    	startActivity(intent);
	    
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View,
	 * android.view.MotionEvent)
	 */
	public boolean onTouch(View v, MotionEvent event) {

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

		if (v == butPrev) {
			finish();
		}
	}
}