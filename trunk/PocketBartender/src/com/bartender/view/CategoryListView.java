/**
 * 
 */
package com.bartender.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

import com.bartender.R;
import com.bartender.dao.CategoryDAO;
import com.bartender.dao.DrinkListDAO;


/**
 * @author Darren
 *
 */
public class CategoryListView extends ListViews {

	CategoryDAO dataDAO = new CategoryDAO();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentListActivity(this);
        intent = new Intent(this, MulitDetailsView.class);
        initComponents();
    }
    
    
    /**
     * init screen list
     */
    private void initComponents() {
    	dataDAO.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
    	Cursor recordscCursor = dataDAO.retrieveAllDrinktypes();
    	startManagingCursor(recordscCursor);
    	String[] from = new String[] { DrinkListDAO.COL_ROW_DRINK_TYPE };
		int[] to = new int[] { R.id.tfName};
    	SimpleCursorAdapter records = new SimpleCursorAdapter(this,
				R.layout.item_row, recordscCursor, from, to);
    	
		setListAdapter(records);
	}

}
