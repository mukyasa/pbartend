package com.bartender.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;

import com.bartender.R;
import com.bartender.dao.DatabaseAdapter;
import com.bartender.dao.DetailDAO;
import com.bartender.domain.DetailsDomain;

public class MulitDetailsView extends DetailsView {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multi_details);
		myDatabaseAdapter = DatabaseAdapter.getInstance(this);
		initComponents();
	}
	
	
	private void initComponents() {
		drinkdetail = new DetailsDomain();
		drinkdao = new DetailDAO();
		
		selectedRow = getIntent().getLongExtra(ListViews.INTENT_EXTRA_SELECTED_ROW, 0);
		spinnerDrinkNames = (Spinner) findViewById(R.id.spinnerDrinkNames);
		
		drinkdao.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
		Log.v(getClass().getSimpleName(), "selectedRow=" + selectedRow);
		drinkdetail.setId((int) selectedRow);
		Log.v(getClass().getSimpleName(), "drinkdetail.getId()=" + drinkdetail.getId());
		if (drinkdetail.getId() > 0) 
			drinkdao.loadDrinkNames(spinnerDrinkNames,drinkdetail,this);
		    
	}

}
;