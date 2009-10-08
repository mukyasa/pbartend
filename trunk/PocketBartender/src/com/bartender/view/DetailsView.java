package com.bartender.view;

import android.os.Bundle;
import android.widget.TextView;

import com.bartender.R;
import com.bartender.dao.DatabaseAdapter;
import com.bartender.dao.DetailDAO;
import com.bartender.domain.DetailsDomain;

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
		tvDrinkName = (TextView) findViewById(R.id.tvDrinkName);
		tvDrinktype = (TextView) findViewById(R.id.tvDrinkType);
		tvGlass = (TextView) findViewById(R.id.tvGlassType);
		tvIng1 = (TextView) findViewById(R.id.tvIng1);
		tvIng2 = (TextView) findViewById(R.id.tvIng2);
		tvIng3 = (TextView) findViewById(R.id.tvIng3);
		tvFullIng = (TextView) findViewById(R.id.tvFullIng);
		tvInstructions = (TextView) findViewById(R.id.tvInstructions);
		tvInstructions2  = (TextView) findViewById(R.id.tvInstructions2);
		
		drinkdao.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
		drinkdetail.setId((int) selectedRow);
		
		if (selectedRow > 0) {
			drinkdao.load(this,drinkdetail);
		}
		
		if (drinkdetail.getId() > 0) {
			tvDrinktype.setText(drinkdetail.getDrinkType());
			tvDrinkName.setText(drinkdetail.getDrinkName());
			tvGlass.setText(drinkdetail.getGlass());
			tvIng1.setText(drinkdetail.getIng1());
			tvIng2.setText(drinkdetail.getIng2());
			tvIng3.setText(drinkdetail.getIng3());
			tvFullIng.setText(drinkdetail.getIngredients());
			tvInstructions.setText(drinkdetail.getInstructions());
			tvInstructions2.setText(drinkdetail.getInstructions2());
			
		} 
		    
	}

}
;