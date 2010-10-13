package com.drinkmixer.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

import com.drinkmixer.R;
import com.drinkmixer.dao.DrinkListDAO;
import com.drinkmixer.domain.DetailsDomain;
import com.drinkmixer.domain.ScreenType;
import com.drinkmixer.utils.Constants;

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

