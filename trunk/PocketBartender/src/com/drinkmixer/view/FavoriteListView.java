package com.drinkmixer.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

import com.drinkmixer.R;
import com.drinkmixer.dao.DrinkListDAO;
import com.drinkmixer.dao.FavoritesListDAO;

public class FavoriteListView extends ListViews {
	
	protected FavoritesListDAO dataDAO = new FavoritesListDAO();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentListActivity(this);
        intent = new Intent(this, DetailsView.class);
        initComponents();
    }
    
    
    /**
     * init screen list
     */
    private void initComponents() {
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
