package com.bartender.view;

import android.app.ListActivity;

import com.bartender.dao.DatabaseAdapter;

public abstract class ListViews extends ListActivity {
	
	protected static final String INTENT_EXTRA_SELECTED_ROW = "SELECTED_ROW";
	protected static final int INTENT_NEXT_SCREEN = 0;
	protected DatabaseAdapter myDatabaseAdapter;

}
