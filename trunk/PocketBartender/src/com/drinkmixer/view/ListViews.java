package com.drinkmixer.view;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.drinkmixer.R;
import com.drinkmixer.dao.DataDAO;
import com.drinkmixer.dao.MixerDbHelper;
import com.drinkmixer.domain.DetailsDomain;
import com.drinkmixer.domain.ScreenType;
import com.drinkmixer.utils.Constants;

public abstract class ListViews extends ListActivity{
	
	protected static final String INTENT_EXTRA_SELECTED_ROW = "SELECTED_ROW";
	protected static final int INTENT_NEXT_SCREEN = 0;
	protected MixerDbHelper myDatabaseAdapter;
	protected EditText searchbox;
	protected Intent intent;
	protected ListActivity currentListActivity;
	protected DetailsDomain drinkdetail;
	private ProgressDialog pd;
		
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
        myDatabaseAdapter = MixerDbHelper.getInstance(this);
		//set search listener
		setListenter();
		
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
	    if(pd != null)
	    	pd.dismiss();
	    
	    super.onStop();
	}
	
	/**
	 * listens for item clicks
	 */
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
			v.setBackgroundResource(R.drawable.clickbg);
			v.setPadding(18, 2, 0, 2);
			pd = ProgressDialog.show(this, null,"LOADING...");
			super.onListItemClick(l, v, position, id);
			//Log.v(getClass().getSimpleName(), "id=" + id + " type=" + ScreenType.getInstance().type);
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
				
				
				try {
	                if((event.getAction() == KeyEvent.ACTION_UP  || 
	                		event.getAction() == KeyEvent.KEYCODE_ENTER) 
	                		&& (event.getAction() != KeyEvent.KEYCODE_SOFT_LEFT || event.getAction() != KeyEvent.KEYCODE_SOFT_RIGHT)) //dont call on back button
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
	                	{
	                		if(ScreenType.getInstance().screenType == ScreenType.SCREEN_TYPE_CAT)
	                			recordscCursor = ((DrinkListView) laType).dataDAO.retrieveAllFilteredDrinksByTypes(et.toString().trim(),Constants.selectedCat);
	                		else
	                			recordscCursor = ((DrinkListView) laType).dataDAO.retrieveAllFilteredDrinks(et.toString().trim());
	                	}
	                	else if(laType instanceof CategoryListView)//filter CATEGORIES
	                		recordscCursor = ((CategoryListView) laType).dataDAO.retrieveAllFilteredDrinktypes(et.toString().trim());
	                	else if(laType instanceof IngredientsListView)
	                	{
	                		from = new String[] { DataDAO.COL_CAT_NAME };
	                		recordscCursor = ((IngredientsListView) laType).dataDAO.retrieveAllFilteredIngredients(ScreenType.getInstance().type, et.toString().trim());
	                	}
	                	
	                	startManagingCursor(recordscCursor);
	                	int[] to = new int[] { R.id.tfName};
	                	SimpleCursorAdapter records;
	                	if(laType instanceof IngredientsListView || laType instanceof CategoryListView || laType instanceof FavoriteListView)
	                		records = new SimpleCursorAdapter(getCurrentListActivity(),	row_item, recordscCursor, from, to);
	                	else
	                		records = new ImageAndTextAdapter(getCurrentListActivity(),	row_item, recordscCursor, from, to);
	                	
	                	setListAdapter(records);
	                	
	                }
                } catch (Exception e) {
                	//Log.v("", e.getMessage());
                }				
				
				return false;
			}

	};

}
