package com.bartender.view;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.bartender.R;
import com.bartender.common.AppCommon;
import com.bartender.dao.DataDAO;
import com.bartender.dao.DatabaseAdapter;
import com.bartender.domain.DetailsDomain;
import com.bartender.domain.ScreenType;

public abstract class ListViews extends ListActivity{
	
	protected static final String INTENT_EXTRA_SELECTED_ROW = "SELECTED_ROW";
	protected static final int INTENT_NEXT_SCREEN = 0;
	protected DatabaseAdapter myDatabaseAdapter;
	protected EditText searchbox;
	protected Intent intent;
	protected ListActivity currentListActivity;
	protected DetailsDomain drinkdetail;
		
	public ListActivity getCurrentListActivity() {
		return currentListActivity;
	}

	public void setCurrentListActivity(ListActivity currentListActivity) {
		this.currentListActivity = currentListActivity;
	}

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
			Log.v(getClass().getSimpleName(), "id=" + id + " type=" + ScreenType.getInstance().type);
			intent.putExtra(INTENT_EXTRA_SELECTED_ROW, id);
			startActivityForResult(intent, INTENT_NEXT_SCREEN);
	}
	
	/**
	 * sets event listener to search field
	 */
	protected void setListenter()
	{
		searchbox = (EditText)findViewById(R.id.etSearch);
		searchbox.setOnKeyListener(edSearchBoxListener);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "Home").setIcon(R.drawable.home);
	    return true;
	}
	
	//home menu option
	public boolean onOptionsItemSelected(MenuItem item) {
		
	    	Intent intent = new Intent(this, HomeScreenView.class);
			startActivity(intent);
	    	return true;
	}
	

	/**
	 * capture key up and filter list
	 */
	private EditText.OnKeyListener edSearchBoxListener =

		new EditText.OnKeyListener() {
		
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				
				if(event.getAction() == KeyEvent.ACTION_UP  || event.getAction() == KeyEvent.KEYCODE_ENTER )
				{
					Editable et = searchbox.getText(); //searchbox text
					Cursor recordscCursor=null;
					ListActivity laType = getCurrentListActivity();
					String[] from = new String[] { DataDAO.COL_NAME };
					
					if(laType instanceof FavoriteListView)//filter FAVORITES
						recordscCursor = ((FavoriteListView) laType).dataDAO.retrieveAllFilteredFavorites(et.toString().trim());
					else if(laType instanceof DrinkListView)//filter ALL DRINKS
						recordscCursor = ((DrinkListView) laType).dataDAO.retrieveAllFilteredDrinks(et.toString().trim());
					else if(laType instanceof CategoryListView)//filter CATEGORIES
						recordscCursor = ((CategoryListView) laType).dataDAO.retrieveAllFilteredDrinktypes(et.toString().trim());
					else if(laType instanceof IngredientsListView)
					{
						from = new String[] { DataDAO.COL_CAT_NAME };
						recordscCursor = ((IngredientsListView) laType).dataDAO.retrieveAllFilteredIngredients(ScreenType.getInstance().type, et.toString().trim());
					}
					
			    	startManagingCursor(recordscCursor);
					int[] to = new int[] { R.id.tfName};
			    	SimpleCursorAdapter records = new SimpleCursorAdapter(getCurrentListActivity(),
							R.layout.item_row, recordscCursor, from, to);
			    	
					setListAdapter(records);
					
				}				
				
				return false;
			}

	};

}
