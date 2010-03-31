/**
 * 
 */
package com.drinkmixer.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

import com.drinkmixer.R;
import com.drinkmixer.dao.CategoryDAO;
import com.drinkmixer.dao.DrinkListDAO;
import com.drinkmixer.domain.ScreenType;


/**
 * @author Darren
 *
 */
public class CategoryListView extends ListViews {

	protected CategoryDAO dataDAO = new CategoryDAO();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentListActivity(this);
        
        intent = new Intent(this, DrinkListView.class);
        
        try {
	        initComponents();
        } catch (Exception e) {
        	showDialog(0);//Log.e("", "Whoa! some error trying to open your db.", e);
        }
        ScreenType.getInstance().screenType=(ScreenType.SCREEN_TYPE_CAT);
    }
    
    /**
     * init screen list
     */
    private void initComponents() throws Exception {
    	dataDAO.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
    	Cursor recordscCursor = dataDAO.retrieveAllDrinktypes();
    	startManagingCursor(recordscCursor);
    	String[] from = new String[] { DrinkListDAO.COL_NAME };
		int[] to = new int[] { R.id.tfName};
    	SimpleCursorAdapter records = new SimpleCursorAdapter(this,
				R.layout.item_row, recordscCursor, from, to);
    	
		setListAdapter(records);
	}
    
}
