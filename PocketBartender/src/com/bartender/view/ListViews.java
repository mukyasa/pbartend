package com.bartender.view;

import android.app.ListActivity;
import android.content.Intent;

import com.bartender.dao.DataDAO;
import com.bartender.dao.DatabaseAdapter;

public abstract class ListViews extends ListActivity {
	
	protected static final String INTENT_EXTRA_SELECTED_ROW = "SELECTED_ROW";
	protected static final int INTENT_NEXT_SCREEN = 0;
	protected DatabaseAdapter myDatabaseAdapter;
	protected DataDAO dataDAO;
	protected Intent intent;

}
