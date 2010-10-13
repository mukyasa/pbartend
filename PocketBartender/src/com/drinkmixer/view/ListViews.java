package com.drinkmixer.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.drinkmixer.domain.NewDrinkDomain;
import com.drinkmixer.domain.ScreenType;
import com.drinkmixer.utils.Constants;

public abstract class ListViews extends ListActivity implements TextWatcher{
	
	protected static final String INTENT_EXTRA_SELECTED_ROW = "SELECTED_ROW";
	protected static final int INTENT_NEXT_SCREEN = 0;
	protected final int MENU_CREATE_LIQUOR=0;
	protected final int MENU_CREATE_MIXER=2;
	protected final int MENU_CREATE_GARNISH=3;
	private final int MENU_HOME=1;
	private final int DIALOG_DEMO=9;
	protected final int DIALOG_WHOA_ERROR=0;
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

	 protected Dialog onCreateDialog(int id) {
	    	
		 if(id==DIALOG_DEMO)
	    	{
		    	return new AlertDialog.Builder(ListViews.this)
		        .setIcon(R.drawable.info)
		        .setMessage("Sorry this feature is only available in the full version. You also get 4000 more drinks and no ads in the full version.")
		        .setTitle("Demo Version")
		        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int whichButton) {
		
		               dismissDialog(DIALOG_DEMO);
		            }
		        })      
		       .create();
	    	}else if(id==DIALOG_WHOA_ERROR)
	    	{
			 
		    	return new AlertDialog.Builder(ListViews.this)
		        .setIcon(R.drawable.info)
		        .setMessage(Constants.WHOA_ERROR)
		        .setTitle("Database Error.")
		        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int whichButton) {
		               dismissDialog(DIALOG_WHOA_ERROR);
		            }
		        })      
		       .create();
	    	}
		 return null;
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
		searchbox.addTextChangedListener(this);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_HOME, 0, "Home").setIcon(R.drawable.home);
	    return true;
	}
	
	//home menu option
	public boolean onOptionsItemSelected(MenuItem item) {
	    	
	    	 switch (item.getItemId()) {
				 case MENU_CREATE_LIQUOR:
					 	if(Constants.showAds)
					 	{
					 		showDialog(DIALOG_DEMO);
					 		return true;
					 	}
					 	NewDrinkDomain.getInstance().clearDomain();
						startActivity(new Intent(this, AddNewLiquor.class));
				    	return true;
				  
				 case MENU_CREATE_MIXER:
					 	if(Constants.showAds)
					 	{
					 		showDialog(DIALOG_DEMO);
					 		return true;
					 	}
					 	NewDrinkDomain.getInstance().clearDomain();
						startActivity(new Intent(this, AddNewMixer.class));
				    	return true;
				 case MENU_CREATE_GARNISH:
					 	if(Constants.showAds)
					 	{
					 		showDialog(DIALOG_DEMO);
					 		return true;
					 	}
					 	NewDrinkDomain.getInstance().clearDomain();
						startActivity(new Intent(this, AddNewGarnish.class));
				    	return true;
				    	
				 case MENU_HOME:
					 	NewDrinkDomain.getInstance().clearDomain();
						startActivity(new Intent(this, HomeScreenView.class));
				    	return true;
				    	
			 }
			 return false;
	}
	
	
	/* (non-Javadoc)
     * @see android.text.TextWatcher#afterTextChanged(android.text.Editable)
     */
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub
        
    }
	/* (non-Javadoc)
     * @see android.text.TextWatcher#beforeTextChanged(java.lang.CharSequence, int, int, int)
     */
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // TODO Auto-generated method stub
        
    }
	/* (non-Javadoc)
     * @see android.text.TextWatcher#onTextChanged(java.lang.CharSequence, int, int, int)
     */
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    	
    	try{
    	
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
	    	
    	} catch (Exception e) {
        	showDialog(DIALOG_WHOA_ERROR);
        };
    }
	

}
