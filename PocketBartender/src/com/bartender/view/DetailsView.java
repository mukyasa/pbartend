package com.bartender.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.bartender.R;
import com.bartender.dao.DatabaseAdapter;
import com.bartender.dao.DetailDAO;

public class DetailsView extends Activity {
	
	private DatabaseAdapter myDatabaseAdapter;
	private long selectedRow;
	private TextView tvDrinkName;
	private TextView tvDrinktype;
	private TextView tvGlass;
	private DetailDAO drinkdetail;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		myDatabaseAdapter = DatabaseAdapter.getInstance(this);
		initComponents();
	}
	
	private void initComponents() {
    	
		selectedRow = getIntent().getLongExtra(ListViews.INTENT_EXTRA_SELECTED_ROW, 0);
		tvDrinkName = (TextView) findViewById(R.id.tvDrinkName);
		tvDrinktype = (TextView) findViewById(R.id.tvDrinkType);
		tvGlass = (TextView) findViewById(R.id.tvGlassType);

		drinkdetail = new DetailDAO();
		drinkdetail.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
		Log.v(getClass().getSimpleName(), "selectedRow=" + selectedRow);
		drinkdetail.setId((int) selectedRow);
		if (selectedRow > 0) {
			drinkdetail.load(this);
		}
		Log.v(getClass().getSimpleName(), "account.getId()=" + drinkdetail.getId());
		if (drinkdetail.getId() > 0) {
			tvDrinkName.setText(drinkdetail.getDrinkName());
			tvDrinktype.setText(drinkdetail.getDrinkType());
			tvGlass.setText(drinkdetail.getGlass());
		} 
		
		Spinner s = (Spinner) findViewById(R.id.spinnerDrinkNames);
		List<String> items = new ArrayList<String>();
		//get names
		
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,items);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    s.setAdapter(adapter);
		    
		    
	}

}
