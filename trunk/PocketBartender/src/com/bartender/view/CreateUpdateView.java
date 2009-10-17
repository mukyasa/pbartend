package com.bartender.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bartender.R;
import com.bartender.domain.ScreenType;

public class CreateUpdateView extends Activity implements OnClickListener {

	private Intent intent;
	private Button btnIng, btnSave,btnCat;
	private EditText etDrinkTitle, etDirections;
	long selectedRow=-1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update);
		ScreenType.getInstance().setScreenType(ScreenType.SCREEN_TYPE_NEW);
		
		selectedRow = getIntent().getLongExtra(ListViews.INTENT_EXTRA_SELECTED_ROW, 0);
		
		initComponents();
	}

	
	private void initComponents() {
		
		
		if(selectedRow > 0)
		{
			TextView newCatNm = (TextView)findViewById(R.id.tvNewCategory);
			newCatNm.setText("The new Cat");
		}
		
		btnIng = (Button) findViewById(R.id.btnNewIng);
		btnIng.setOnClickListener(this);
			
		btnCat = (Button) findViewById(R.id.btnNewCat);
		btnCat.setOnClickListener(this);
	
		etDirections = (EditText) findViewById(R.id.etDirections);
		etDirections.setOnFocusChangeListener(onFocusListener);
	
		etDrinkTitle = (EditText) findViewById(R.id.etNewDrinkNm); 
		etDrinkTitle.setOnFocusChangeListener(onFocusListener);
		
		btnSave = (Button)findViewById(R.id.btnSave);
		btnSave.setOnClickListener(this);

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
		else if(view == btnSave)
		{
			
		}
		
	}
	
	OnFocusChangeListener onFocusListener = new OnFocusChangeListener(){

		public void onFocusChange(View v, boolean hasFocus) {
			((TextView)v).setText("");
			
		}
		
		
	};
	
}
