package com.bartender.view;

import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bartender.R;
import com.bartender.dao.CreateUpdateDAO;
import com.bartender.domain.NewDrinkDomain;
import com.bartender.domain.ScreenType;


public class CreateUpdateView extends Activity implements OnClickListener {

	private Intent intent;
	private Button btnIng, btnSave,btnCat;
	long selectedRow=-1;
	CreateUpdateDAO dataDAO = new CreateUpdateDAO();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update);
		ScreenType.getInstance().setScreenType(ScreenType.SCREEN_TYPE_NEW);
		
		initComponents();
	}

	
	private void initComponents() {
		
		if(NewDrinkDomain.getInstance().getCategoryName()!=null)
		{
			TextView newCatNm = (TextView)findViewById(R.id.tvNewCategory);
			ImageView newGlass = (ImageView)findViewById(R.id.imgNewGlass);
			newCatNm.setText(NewDrinkDomain.getInstance().getCategoryName());
			newGlass.setBackgroundDrawable(NewDrinkDomain.getInstance().getGlassType());
		}
		
		if(NewDrinkDomain.getInstance().getIngredients() != null)
		{
			TextView newIngNm = (TextView)findViewById(R.id.tvNewIngredients);
			
			NewDrinkDomain ndd = NewDrinkDomain.getInstance();
			Iterator<String> iter = ndd.getIngredients().iterator();
			StringBuffer ings =new StringBuffer();
			
			while(iter.hasNext())
			{
				ings.append(iter.next()+"\n");
			}
				 
			newIngNm.setText(ings.toString());
		}
		
		btnIng = (Button) findViewById(R.id.btnNewIng);
		btnIng.setOnClickListener(this);
			
		btnCat = (Button) findViewById(R.id.btnNewCat);
		btnCat.setOnClickListener(this);
	
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
			NewDrinkDomain ndd = NewDrinkDomain.getInstance();
			List<String> ingredients = ndd.getIngredients();
			
			if(ingredients != null)
			{
				Iterator<String> iter = ingredients.iterator();
				while (iter.hasNext()) {
					String ing = (String) iter.next();
					
					StringTokenizer toke = new StringTokenizer(ing,",");
					
					
				}
				
				EditText drinkName = (EditText)findViewById(R.id.etNewDrinkNm);
				EditText directions = (EditText)findViewById(R.id.etDirections);
				
				ndd.setDrinkName(drinkName.getText().toString());
				ndd.setCategoryName(directions.getText().toString());
				
				//insert into drink table
				dataDAO.insertNewDrink(ndd.getDrinkName(), ndd.getCategoryId(), ndd.getGlassId(), ndd.getInstructions());
				
			}
			
			ndd.clearDomain();
			
		}
		
	}
		
		
	
}
