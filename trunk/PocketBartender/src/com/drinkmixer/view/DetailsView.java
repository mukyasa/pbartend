package com.drinkmixer.view;

import android.os.Bundle;

import com.drinkmixer.R;
import com.drinkmixer.dao.DetailDAO;
import com.drinkmixer.dao.MixerDbHelper;
import com.drinkmixer.domain.DetailsDomain;

public class DetailsView extends ActivityView {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details); 
		myDatabaseAdapter = MixerDbHelper.getInstance(this);
		initComponents();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		setDomain();
	    super.onResume();
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