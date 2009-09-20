package com.bartender;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class PocketBartenderList extends ListActivity {
    /** Called when the activity is first created. */
	private DatabaseAdapter myDatabaseAdapter;
	Drink drink = new Drink();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list); //main is my list screen
        myDatabaseAdapter = DatabaseAdapter.getInstance(this);
        initComponents();
    }
    
    /**
     * init screen list
     */
    private void initComponents() {
    	
    	drink.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
    	Cursor recordscCursor = drink.retrieveAll();
    	startManagingCursor(recordscCursor);
    	String[] from = new String[] { Drink.COL_TYPE };
		int[] to = new int[] { R.id.tfDrinkType};
    	SimpleCursorAdapter records = new SimpleCursorAdapter(this,
				R.layout.type_row, recordscCursor, from, to);
    	
		setListAdapter(records);

		
	}
}