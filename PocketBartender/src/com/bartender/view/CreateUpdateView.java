package com.bartender.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.bartender.R;

public class CreateUpdateView extends Activity implements OnClickListener {

	private Intent intent;
	private Button btnIng, btnCat;
	private EditText etDrinkTitle, etDirections;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update);
		initComponents();
	}

	public void onClick(View view) {
		
		if(view==btnIng)
		{
			intent = new Intent(this, IngredientsListView.class);
			startActivity(intent);
		}
		else if(view==btnCat) 
		{
			intent = new Intent(this, CategoryListView.class);
			startActivity(intent);
		}
		
	}
	
	private void initComponents() {
		
		btnIng = (Button) findViewById(R.id.btnNewIng);
		btnIng.setOnClickListener(this);
			
		btnCat = (Button) findViewById(R.id.btnAddNewCat);
		btnCat.setOnClickListener(this);
	
		etDirections = (EditText) findViewById(R.id.etDirections);
		etDirections.setOnClickListener(this);
	
		etDrinkTitle = (EditText) findViewById(R.id.etNewDrinkNm); 
		etDrinkTitle.setOnClickListener(this);

	}
	
}
