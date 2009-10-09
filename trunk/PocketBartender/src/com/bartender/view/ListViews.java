package com.bartender.view;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.bartender.R;
import com.bartender.dao.DatabaseAdapter;

public abstract class ListViews extends ListActivity{
	
	protected static final String INTENT_EXTRA_SELECTED_ROW = "SELECTED_ROW";
	protected static final int INTENT_NEXT_SCREEN = 0;
	protected DatabaseAdapter myDatabaseAdapter;
	protected EditText searchbox;
	protected Intent intent;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_frame); 
        myDatabaseAdapter = DatabaseAdapter.getInstance(this);
		//set search listener
		setListenter();
		
	}
	
	/**
	 * listens for item clicks
	 */
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
			super.onListItemClick(l, v, position, id);
			Log.v(getClass().getSimpleName(), "id=" + id);
			intent.putExtra(INTENT_EXTRA_SELECTED_ROW, id);
			startActivityForResult(intent, INTENT_NEXT_SCREEN);
	}
	
	protected void setListenter()
	{
		searchbox = (EditText)findViewById(R.id.etSearch);
		searchbox.setOnKeyListener(edSearchBoxListener);
		
	}
	

	private EditText.OnKeyListener edSearchBoxListener =

		new EditText.OnKeyListener() {

			public boolean onKey(View v, int keyCode, KeyEvent event) {
				
				if(event.getAction() == KeyEvent.ACTION_UP)
				{
					Editable et = searchbox.getText();
					et.toString();
					//filter
					
				}				
				
				return false;
			}

		

	};

}
