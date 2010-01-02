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
import android.widget.TextView;

import com.juggler.dao.PasswordDAO;
import com.juggler.dao.PasswordDbHelper;
import com.juggler.dao.QuiresDAO;
import com.juggler.utils.Constants;
import com.juggler.utils.CustomCursorAdapter;

public class LoginsListActivity extends FooterListActivity implements OnClickListener,OnTouchListener{
	
	private Intent intent;
	private static final int INTENT_NEXT_SCREEN = 0;
	private Button butPrev;
	private int STANDARD_LOGIN=1;
	private PasswordDAO passDao;
	private PasswordDbHelper myDatabaseAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.list_frame);
		
		//set up database for use
		passDao = new PasswordDAO();
		myDatabaseAdapter = PasswordDbHelper.getInstance(this);
		passDao.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
		
		super.onCreate(savedInstanceState);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
	    super.onResume();
	    initialize();
	}
	
	private void initialize() {
		
		//set title
		Intent selectedIntent = getIntent();
		CharSequence text =  selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_SELECTED_TEXT);
		TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText(text);
		
		// hide next button
		Button next = (Button) findViewById(R.id.butNext);
		next.setVisibility(View.GONE);
		butPrev = (Button) findViewById(R.id.butPrev);
		butPrev.setOnClickListener(this);
		butPrev.setOnTouchListener(this);
		
		Cursor recordscCursor = passDao.getLoginTemplates();
		
		String[] from = new String[] { QuiresDAO.COL_NAME,QuiresDAO.COL_URL };
		int[] to = new int[] { R.id.list_row};
    	CustomCursorAdapter records = new CustomCursorAdapter(this,
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

	    //set next intent for template
		intent = new Intent(this, CreateLoginTemplateActivity.class);
		
	    v.setBackgroundResource(R.drawable.sm_item_spacer_arw_slct);
	    v.setPadding(15, 10,10,10);
	    
	    if(id==STANDARD_LOGIN) //set intent for none template
	    	intent = new Intent(this, Step1CreateActivity.class);
	    else
	    {//pass the titles along
	    	intent.putExtra(Constants.INTENT_EXTRA_STEP2_FIELD1, getString(R.string.email));
	    	intent.putExtra(Constants.INTENT_EXTRA_STEP2_FIELD2, getString(R.string.password));
	    }
	    
	    intent.putExtra(Constants.INTENT_EXTRA_SELECTED_ROW, id);
	    intent.putExtra(Constants.INTENT_EXTRA_SELECTED_TEXT,((TextView)v).getText());
	    intent.putExtra(Constants.INTENT_EXTRA_SELECTED_URL,((TextView)v).getHint());
		startActivityForResult(intent, INTENT_NEXT_SCREEN);
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