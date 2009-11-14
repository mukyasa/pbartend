package com.mixer.view;

import android.os.Bundle;

import com.mixer.R;
import com.mixer.dao.DatabaseAdapter;
import com.mixer.dao.DetailDAO;
import com.mixer.domain.DetailsDomain;

public class DetailsView extends ActivityView {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details); 
		myDatabaseAdapter = DatabaseAdapter.getInstance(this);
		initComponents();
	}
	
	private void initComponents() {
		drinkdetail = new DetailsDomain();
		drinkdao = new DetailDAO();
		
		selectedRow = getIntent().getLongExtra(ListViews.INTENT_EXTRA_SELECTED_ROW, 0);
		
		findAndSetView();
		
		drinkdao.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
		drinkdetail.id=((int) selectedRow);
		
		if (selectedRow > 0) {
			drinkdao.load(this,drinkdetail);
		}
		
		if (drinkdetail.id > 0) {
			//this one has a drink name 
			setViewItems();
			
		} 
		    
	}

}
;