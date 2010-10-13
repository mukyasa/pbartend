package com.drinkmixerdemo.view;

import com.drinkmixerdemo.R;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

import com.drinkmixerdemo.dao.DrinkListDAO;
import com.drinkmixerdemo.dao.FavoritesListDAO;

public class FavoriteListView extends ListViews {
	
	protected FavoritesListDAO dataDAO = new FavoritesListDAO();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentListActivity(this);
        intent = new Intent(this, DetailsView.class);
        try {
	        initComponents();
        } catch (Exception e) {
        	showDialog(DIALOG_WHOA_ERROR);//Log.e("", "Whoa! some error trying to open your db.", e);
        }
    }
    
    
    /**
     * init screen list
     */
    private void initComponents() throws Exception {
    	
    	dataDAO.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
    	Cursor recordscCursor = dataDAO.retrieveAllFavorites();
    	startManagingCursor(recordscCursor);
    	String[] from = new String[] { DrinkListDAO.COL_NAME };
		int[] to = new int[] { R.id.tfName};
    	SimpleCursorAdapter records = new SimpleCursorAdapter(this,
				R.layout.fav_item_row, recordscCursor, from, to);
    	
		setListAdapter(records);
	}
}
