package com.bartender.view;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.bartender.R;
import com.bartender.dao.DataDAO;
import com.bartender.dao.DatabaseAdapter;
import com.bartender.domain.DetailsDomain;
import com.bartender.domain.ScreenType;

public abstract class ListViews extends ListActivity implements OnKeyListener{
	
	protected static final String INTENT_EXTRA_SELECTED_ROW = "SELECTED_ROW";
	protected static final int INTENT_NEXT_SCREEN = 0;
	protected DatabaseAdapter myDatabaseAdapter;
	protected EditText searchbox;
	protected Intent intent;
	protected ListActivity currentListActivity;
	protected DetailsDomain drinkdetail;
	protected ProgressDialog pd;
		
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
		//setListenter();
        searchbox = (EditText)findViewById(R.id.etSearch);
		searchbox.setOnKeyListener(this);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		if(pd!=null)
			pd.dismiss();
		
	    super.onStop();
	}
	
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if((event.getAction() == KeyEvent.ACTION_UP  || event.getAction() == KeyEvent.KEYCODE_ENTER )&& event.getAction()!= KeyEvent.KEYCODE_SOFT_LEFT)
		{
			int row_item =R.layout.item_row;
			Editable et = searchbox.getText(); //searchbox text
			Cursor recordscCursor=null;
			ListActivity laType = getCurrentListActivity();
			String[] from = new String[] { DataDAO.COL_NAME };
			
			if(laType instanceof FavoriteListView)//filter FAVORITES
			{
				row_item = R.layout.fav_item_row;
				recordscCursor = ((FavoriteListView) laType).dataDAO.retrieveAllFilteredFavorites(et.toString().trim());
			}
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
	    	SimpleCursorAdapter records = new ImageAndTextAdapter(getCurrentListActivity(),
	    			row_item, recordscCursor, from, to);
	    	
			setListAdapter(records);
			
		}		
	    return false;
    }

	/**
	 * listens for item clicks
	 */
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
			pd =ProgressDialog.show(this, null,"LOADING...");
			super.onListItemClick(l, v, position, id);
			//Log.v(getClass().getSimpleName(), "id=" + id + " type=" + ScreenType.getInstance().type);
			intent.putExtra(INTENT_EXTRA_SELECTED_ROW, id);
			startActivityForResult(intent, INTENT_NEXT_SCREEN);
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
	
}
