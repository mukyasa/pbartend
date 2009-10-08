package com.bartender.view;

import android.app.Activity;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.bartender.R;
import com.bartender.dao.DataDAO;
import com.bartender.dao.DatabaseAdapter;
import com.bartender.dao.DetailDAO;
import com.bartender.domain.DetailsDomain;

public class MulitDetailsView extends ActivityView {

	private Activity currentActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multi_details);
		myDatabaseAdapter = DatabaseAdapter.getInstance(this);
		initComponents();
	}
	
	
	/**
	 * initializes all components
	 */
	private void initComponents() {
		drinkdetail = new DetailsDomain();
		drinkdao = new DetailDAO();
		currentActivity=this;
		selectedRow = getIntent().getLongExtra(ListViews.INTENT_EXTRA_SELECTED_ROW, 0);
		
		findAndSetView();
		spinnerDrinkNames = (Spinner) findViewById(R.id.spinnerDrinkNames);
		spinnerDrinkNames.setOnItemSelectedListener(spnDrinkTypesListener);
		
		drinkdao.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
		drinkdetail.setId((int) selectedRow);

		if (drinkdetail.getId() > 0)
		{
			drinkdao.loadDrinkIds(spinnerDrinkNames,drinkdetail,currentActivity);
			drinkdao.loadByDrinkTypeNm(currentActivity,drinkdetail);
			setViewItems();
			
		}
			
	}
	
	
	private Spinner.OnItemSelectedListener spnDrinkTypesListener =

		new Spinner.OnItemSelectedListener() {

		@SuppressWarnings("unchecked")
		public void onItemSelected(AdapterView parent, View v, int position, long id) {
			
			SQLiteCursor listitem = (SQLiteCursor)spinnerDrinkNames.getItemAtPosition(position);
			Log.v(getClass().getSimpleName(), "id=" + listitem.getString(listitem.getColumnIndex(DataDAO.COL_ROW_DRINK_NAME)));
			drinkdetail.setDrinkName(listitem.getString(listitem.getColumnIndex(DataDAO.COL_ROW_DRINK_NAME)));
			drinkdao.loadByDrinkNm(currentActivity, drinkdetail);
			setViewItems();
			
		}
		
		@SuppressWarnings("unchecked")
		public void onNothingSelected(AdapterView parent) {
			

		}

	};
	
}
