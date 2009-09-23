package com.bartender;

import com.bartender.dao.DrinkDAO;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class PocketBartenderList extends ListActivity {
    /** Called when the activity is first created. */
	public static final String INTENT_EXTRA_SELECTED_ROW = "SELECTED_ROW";
	private static final int INTENT_NEXT_SCREEN = 0;
	private DatabaseAdapter myDatabaseAdapter;
	private DrinkDAO drink = new DrinkDAO();
	private Intent intent;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list); //main is my list screen
        myDatabaseAdapter = DatabaseAdapter.getInstance(this);
        intent = new Intent(this, Details.class);
        initComponents();
    }
    
    protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.v(getClass().getSimpleName(), "id=" + id);
		intent.putExtra(INTENT_EXTRA_SELECTED_ROW, id);
		startActivityForResult(intent, INTENT_NEXT_SCREEN);
	}
    
    /**
     * init screen list
     */
    private void initComponents() {
    	
    	drink.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
    	//Cursor recordscCursor = drink.retrieveAllDrinktypes();
    	Cursor recordscCursor = drink.retrieveAllDrinks();
    	startManagingCursor(recordscCursor);
    	//String[] from = new String[] { Drink.COL_TYPE };
    	String[] from = new String[] { DrinkDAO.COL_ROW_DRINK_NAME };
		int[] to = new int[] { R.id.tfDrinkType};
    	SimpleCursorAdapter records = new SimpleCursorAdapter(this,
				R.layout.type_row, recordscCursor, from, to);
    	
		setListAdapter(records);

		
	}
}