package com.mixer.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

import com.mixer.R;
import com.mixer.dao.DrinkListDAO;
import com.mixer.domain.DetailsDomain;
import com.mixer.domain.ScreenType;
import com.mixer.utils.Constants;

public class DrinkListView extends ListViews {
	
	protected DrinkListDAO dataDAO = new DrinkListDAO();
	long selectedRow=-1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentListActivity(this);
        intent = new Intent(this, DetailsView.class);
        
        selectedRow = getIntent().getLongExtra(ListViews.INTENT_EXTRA_SELECTED_ROW, 0);
        Constants.selectedCat=selectedRow;
        initComponents();
    }
    
    /**
     * init screen list
     */
    private void initComponents() {
    	drinkdetail = new DetailsDomain();
    	drinkdetail.id=((int) selectedRow);
    	dataDAO.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
    	Cursor recordscCursor;
    	if(ScreenType.getInstance().screenType == ScreenType.SCREEN_TYPE_CAT)
    		recordscCursor = dataDAO.retrieveAllDrinkByTypes(selectedRow);
    	else if(ScreenType.getInstance().screenType == ScreenType.SCREEN_TYPE_ING)
    		recordscCursor = dataDAO.retrieveAllDrinksByIng(ScreenType.getInstance().type,selectedRow+"");
    	else
    		recordscCursor = dataDAO.retrieveAllDrinkAndGlass();
    	
    	
    	//Log.v(getClass().getSimpleName(), "count=" + recordscCursor.getCount());
    	startManagingCursor(recordscCursor);
    	String[] from = new String[] { DrinkListDAO.COL_NAME };
		int[] to = new int[] { R.id.tfName};
		
    	SimpleCursorAdapter records = new ImageAndTextAdapter(this,
				R.layout.item_row, recordscCursor, from, to);
    	
		setListAdapter(records);
	}
}

