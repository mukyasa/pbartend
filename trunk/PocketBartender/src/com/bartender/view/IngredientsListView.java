/**
 * 
 */
package com.bartender.view;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

import com.bartender.R;
import com.bartender.dao.CategoryDAO;
import com.bartender.dao.DatabaseAdapter;
import com.bartender.dao.DrinkListDAO;


/**
 * @author Darren
 * GETS A SET LIST OF CATEGORIES
 */
public class IngredientsListView extends ListActivity {

	protected DatabaseAdapter myDatabaseAdapter;
	protected Intent intent;
	CategoryDAO dataDAO = new CategoryDAO();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredients_list); 
        myDatabaseAdapter = DatabaseAdapter.getInstance(this);
        intent = new Intent(this, CreateUpdateView.class);
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
