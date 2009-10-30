package com.bartender.view;

import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bartender.R;
import com.bartender.dao.CreateUpdateDAO;
import com.bartender.dao.DatabaseAdapter;
import com.bartender.domain.NewDrinkDomain;
import com.bartender.domain.ScreenType;


public class CreateUpdateView extends BaseActivity implements OnClickListener {

	private Intent intent;
	private Button btnIng, btnSave,btnCat,btnCancel,btnReset;
	private EditText drinkName,directions;
	long selectedRow=-1;
	protected DatabaseAdapter myDatabaseAdapter;
	CreateUpdateDAO dataDAO = new CreateUpdateDAO();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update);
		myDatabaseAdapter = DatabaseAdapter.getInstance(this);
		ScreenType.getInstance().screenType=(ScreenType.SCREEN_TYPE_NEW);
		
		initComponents();
	}

	
	private void initComponents() {
		
		NewDrinkDomain ndd = NewDrinkDomain.getInstance();
		dataDAO.setSQLiteDatabase(myDatabaseAdapter.getDatabase());	
		
		directions = (EditText)findViewById(R.id.etDirections);
		drinkName = (EditText)findViewById(R.id.etNewDrinkNm);
		directions.setOnTouchListener(this);
		drinkName.setOnTouchListener(this);
		
		if(ndd.categoryName!=null)
		{
			TextView newCatNm = (TextView)findViewById(R.id.tvNewCategory);
			ImageView newGlass = (ImageView)findViewById(R.id.imgNewGlass);
			newCatNm.setText(ndd.categoryName);
			newGlass.setBackgroundDrawable(ndd.glassType);
		}
		
		if(ndd.getIngredients() != null && ndd.getIngredients().size() > 0)
		{
			TextView newIngNm = (TextView)findViewById(R.id.tvNewIngredients);
			
			Iterator<String> iter = ndd.getIngredients().iterator();
			StringBuffer ings =new StringBuffer();
			
			while(iter.hasNext())
			{
				ings.append(iter.next()+"\n");
			}
				 
			newIngNm.setText(ings.toString());
		}
		
		if(ndd.drinkName != null)
		{
			
			drinkName.setText(ndd.drinkName);
		}
		
		if(ndd.instructions != null)
		{
			
			directions.setText(ndd.instructions);
		}
		
		btnCancel=(Button)findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);
		btnCancel.setOnTouchListener(this);
		
		btnIng = (Button) findViewById(R.id.btnNewIng);
		btnIng.setOnClickListener(this);
			
		btnCat = (Button) findViewById(R.id.btnNewCat);
		btnCat.setOnClickListener(this);
	
		btnSave = (Button)findViewById(R.id.btnSave);
		btnSave.setOnClickListener(this);
		btnSave.setOnTouchListener(this);
		
		btnReset = (Button)findViewById(R.id.btnReset);
		btnReset.setOnClickListener(this);
		btnReset.setOnTouchListener(this);

	}
	
	public void onClick(View view) {
		
		if(view==btnIng)
		{
			NewDrinkDomain ndd = NewDrinkDomain.getInstance();
			EditText drinkName = (EditText)findViewById(R.id.etNewDrinkNm);
			EditText directions = (EditText)findViewById(R.id.etDirections);
			
			ndd.drinkName=(drinkName.getText().toString());
			ndd.instructions=(directions.getText().toString());
			
			intent = new Intent(this, IngredientsHomeView.class);
			startActivity(intent);
		}
		else if(view==btnCancel)
		{
			intent = new Intent(this, HomeScreenView.class);
			startActivity(intent);
		}
		else if(view==btnReset)
		{
			 NewDrinkDomain.getInstance().clearDomain();
			 EditText drinkName = (EditText)findViewById(R.id.etNewDrinkNm);
			 drinkName.setText(this.getString(R.string.create_drink_title));
			 drinkName.setTextColor(Color.LTGRAY);
			 EditText directions = (EditText)findViewById(R.id.etDirections);
			 directions.setText(this.getString(R.string.instructionsText));
			 directions.setTextColor(Color.LTGRAY);
			 TextView newIngNm = (TextView)findViewById(R.id.tvNewIngredients);
			 newIngNm.setText("");
			 TextView newCatNm = (TextView)findViewById(R.id.tvNewCategory);
			 ImageView newGlass = (ImageView)findViewById(R.id.imgNewGlass);
			 newCatNm.setText("");
			 newGlass.setBackgroundResource(R.drawable.blank);
			 
		}
		else if(view==btnCat) 
		{
			NewDrinkDomain ndd = NewDrinkDomain.getInstance();
			EditText drinkName = (EditText)findViewById(R.id.etNewDrinkNm);
			EditText directions = (EditText)findViewById(R.id.etDirections);
			
			ndd.drinkName=(drinkName.getText().toString());
			ndd.instructions=(directions.getText().toString());
			
			intent = new Intent(this, CategoryAndGlassPicker.class);
			startActivity(intent);
		}
		else if(view == btnSave)
		{
			NewDrinkDomain ndd = NewDrinkDomain.getInstance();
			List<String> ingredients = ndd.getIngredients();
			
			if(ingredients != null)
			{
				
				EditText drinkName = (EditText)findViewById(R.id.etNewDrinkNm);
				EditText directions = (EditText)findViewById(R.id.etDirections);
				
				ndd.drinkName=(drinkName.getText().toString());
				ndd.instructions=(directions.getText().toString());
				
				//insert into drink table
				dataDAO.insertNewDrink(ndd.drinkName, ndd.categoryId, ndd.glassId, ndd.instructions);
				
				Iterator<String> iter = ingredients.iterator();
				while (iter.hasNext()) {
					String ing = (String) iter.next();
					
					StringTokenizer toke = new StringTokenizer(ing,",");
					//the first one is junk ignore
					String amount = (String)toke.nextElement();
					String ingredientsName = (String)toke.nextElement();
					
					
					dataDAO.getIngredientsId(ingredientsName.trim());
					dataDAO.insertDrinkIng(ndd.newDrinkId, ndd.newing_id, amount);
				}
				
				
			}
			
			ndd.clearDomain();
			intent = new Intent(this, HomeScreenView.class);
			startActivity(intent);
			
		}
		
	}
		
		
	
}

