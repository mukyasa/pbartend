package com.bartender.view;

import android.app.Activity;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

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
	
	private void setViewItems(){
		
		tvDrinktype.setText(drinkdetail.getDrinkType());
		tvGlass.setText(drinkdetail.getGlass());
		tvIng1.setText(drinkdetail.getIng1());
		tvIng2.setText(drinkdetail.getIng2());
		tvIng3.setText(drinkdetail.getIng3());
		tvFullIng.setText(drinkdetail.getIngredients());
		tvInstructions.setText(drinkdetail.getInstructions());
		tvInstructions2.setText(drinkdetail.getInstructions2());
	}
	
	private void initComponents() {
		drinkdetail = new DetailsDomain();
		drinkdao = new DetailDAO();
		currentActivity=this;
		selectedRow = getIntent().getLongExtra(ListViews.INTENT_EXTRA_SELECTED_ROW, 0);
		
		tvDrinktype = (TextView) findViewById(R.id.tvDrinkType);
		tvGlass = (TextView) findViewById(R.id.tvGlassType);
		tvIng1 = (TextView) findViewById(R.id.tvIng1);
		tvIng2 = (TextView) findViewById(R.id.tvIng2);
		tvIng3 = (TextView) findViewById(R.id.tvIng3);
		tvFullIng = (TextView) findViewById(R.id.tvFullIng);
		tvInstructions = (TextView) findViewById(R.id.tvInstructions);
		tvInstructions2  = (TextView) findViewById(R.id.tvInstructions2);
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
