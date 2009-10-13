package com.bartender.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

import com.bartender.R;
import com.bartender.dao.DrinkListDAO;

public class DrinkListView extends ListViews {
	
	protected DrinkListDAO dataDAO = new DrinkListDAO();
	
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
    	Cursor recordscCursor = dataDAO.retrieveAllDrinks();
    	startManagingCursor(recordscCursor);
    	String[] from = new String[] { DrinkListDAO.COL_NAME };
		int[] to = new int[] { R.id.tfName};
		
    	SimpleCursorAdapter records = new SimpleCursorAdapter(this,
				R.layout.item_row, recordscCursor, from, to);
    	
		setListAdapter(records);	
	}
}
