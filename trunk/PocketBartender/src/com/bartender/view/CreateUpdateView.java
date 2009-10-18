package com.bartender.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bartender.R;
import com.bartender.domain.NewDrinkDomain;
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
		initComponents();
	}

	
	private void initComponents() {
		
		if("".equalsIgnoreCase(NewDrinkDomain.getInstance().getCategoryName()))
		{
			TextView newCatNm = (TextView)findViewById(R.id.tvNewCategory);
			newCatNm.setText(NewDrinkDomain.getInstance().getCategoryName());
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
			intent = new Intent(this, IngredientsHomeView.class);
			startActivity(intent);
		}
		else if(view==btnCat) 
		{
			intent = new Intent(this, CategoryAndGlassListView.class);
			startActivity(intent);
		}
		else if(view == btnSave)
		{
			
		}
		
	}
	
	OnFocusChangeListener onFocusListener = new OnFocusChangeListener(){

		public void onFocusChange(View v, boolean hasFocus) {
			
			CharSequence text = ((TextView)v).getText();
			text.toString();
			
			
			((TextView)v).setText("");
			((TextView)v).setTextColor(Color.BLACK);
			
		}
		
		
	};
	
}
