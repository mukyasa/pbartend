package com.bartender.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

import com.bartender.R;
import com.bartender.dao.CategoryDAO;
import com.bartender.dao.DrinkListDAO;
import com.bartender.dao.FavoritesListDAO;

public class FavoriteListView extends ListViews {
	
	CategoryDAO cat = new CategoryDAO();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = new Intent(this, DetailsView.class);
        initComponents();
    }
    
    
    /**
     * init screen list
     */
    private void initComponents() {
    	FavoritesListDAO drink = new FavoritesListDAO();
    	drink.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
    	Cursor recordscCursor = drink.retrieveAllFavorites();
    	startManagingCursor(recordscCursor);
    	String[] from = new String[] { DrinkListDAO.COL_ROW_DRINK_NAME };
		int[] to = new int[] { R.id.tfName};
    	SimpleCursorAdapter records = new SimpleCursorAdapter(this,
				R.layout.item_row, recordscCursor, from, to);
    	
		setListAdapter(records);
	}
}
