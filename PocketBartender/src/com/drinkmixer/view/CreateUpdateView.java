package com.drinkmixer.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.Path.FillType;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

import com.drinkmixer.R;
import com.drinkmixer.dao.CreateUpdateDAO;
import com.drinkmixer.dao.MixerDbHelper;
import com.drinkmixer.domain.NewDrinkDomain;
import com.drinkmixer.domain.ScreenType;


public class CreateUpdateView extends BaseActivity implements OnClickListener, OnLongClickListener {

	private Intent intent;
	private Button btnIng, btnSave,btnCat,btnCancel,btnReset;
	private EditText drinkName,directions;
	long selectedRow=-1;
	protected MixerDbHelper myDatabaseAdapter;
	private CreateUpdateDAO dataDAO = new CreateUpdateDAO();
	private final int MENU_DRINK_NAME=0;
	private final int MENU_DRINK_ING=1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update);
		myDatabaseAdapter = MixerDbHelper.getInstance(this);
		ScreenType.getInstance().screenType=(ScreenType.SCREEN_TYPE_NEW);
		
		initComponents();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
	    super.onResume();
	    if(!drinkName.getText().toString().equals(this.getString(R.string.create_drink_title)))
	    	drinkName.setTextColor(Color.BLACK);	
	    if(!directions.getText().toString().equals(this.getString(R.string.instructionsText))) 
	    	directions.setTextColor(Color.BLACK);
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
			//ImageView newGlass = (ImageView)findViewById(R.id.imgNewGlass);
			newCatNm.setText(ndd.categoryName);
			newCatNm.setCompoundDrawablesWithIntrinsicBounds(ndd.glassType, null, null, null);
			//newGlass.setBackgroundDrawable(ndd.glassType);
		}
		
		if(ndd.getIngredients() != null && ndd.getIngredients().size() > 0)
		{
			//TextView newIngNm = (TextView)findViewById(R.id.tvNewIngredients);
			
			Iterator<String> iter = ndd.getIngredients().iterator();
			//StringBuffer ings =new StringBuffer();
			TableLayout ll = (TableLayout)findViewById(R.id.lling);
			
			while(iter.hasNext())
			{
				TableRow  tr = new TableRow(this);
				TextView tv = new TextView(this);
				tv.setText(iter.next());
				tv.setTypeface(Typeface.DEFAULT_BOLD);
				tv.setTextColor(Color.WHITE);
				tv.setTextSize(16f); 
				tv.setOnLongClickListener(this);
				tr.setOnTouchListener(this);
				Drawable d = getResources().getDrawable(R.drawable.delete);
				tv.setCompoundDrawablesWithIntrinsicBounds(d, null, null, null);
				tr.addView(tv);
				ll.addView(tr);
			}
			
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
	
	/* (non-Javadoc)
     * @see android.app.Activity#onCreateDialog(int)
     */
    @Override
    protected Dialog onCreateDialog(int id) {

    		if(id== MENU_DRINK_NAME){
		    	return new AlertDialog.Builder(CreateUpdateView.this)
		        .setIcon(R.drawable.error)
		        .setMessage("Don't forget a drink name.")
		        .setTitle("Error")
		        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int whichButton) {
		               dismissDialog(0);
		            }
		        })      
		       .create();
    		}
    		else 
    		{
    			return new AlertDialog.Builder(CreateUpdateView.this)
    	        .setIcon(R.drawable.error)
    	        .setMessage("You need some ingredients.")
    	        .setTitle("Error")
    	        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
    	            public void onClick(DialogInterface dialog, int whichButton) {
    	               dismissDialog(1);
    	            }
    	        })      
    	       .create();
    		}
    }
    
	public void onClick(View view) {
		NewDrinkDomain ndd = NewDrinkDomain.getInstance();
		if(view==btnIng)
		{
			EditText drinkName = (EditText)findViewById(R.id.etNewDrinkNm);
			EditText directions = (EditText)findViewById(R.id.etDirections);
			
			ndd.drinkName=(drinkName.getText().toString());
			ndd.instructions=(directions.getText().toString());
			
			intent = new Intent(this, IngredientsHomeView.class);
			startActivity(intent);
		}
		else if(view==btnCancel)
		{
			TableLayout ll = (TableLayout)findViewById(R.id.lling);
			 ll.removeAllViews();
			 ndd.clearDomain();
			 if(ndd.drink_id > 0)
				 finish();
			 else
				 startActivity(new Intent(this,HomeScreenView.class));
		}
		else if(view==btnReset)
		{
			 ndd.clearDomain();
			 EditText drinkName = (EditText)findViewById(R.id.etNewDrinkNm);
			 drinkName.setText(this.getString(R.string.create_drink_title));
			 drinkName.setTextColor(Color.LTGRAY);
			 EditText directions = (EditText)findViewById(R.id.etDirections);
			 directions.setText(this.getString(R.string.instructionsText));
			 directions.setTextColor(Color.LTGRAY);
			 TableLayout ll = (TableLayout)findViewById(R.id.lling);
			 ll.removeAllViews();
			// TextView newIngNm = (TextView)findViewById(R.id.tvNewIngredients);
			 //newIngNm.setText("");
			 TextView newCatNm = (TextView)findViewById(R.id.tvNewCategory);
			// ImageView newGlass = (ImageView)findViewById(R.id.imgNewGlass);
			 newCatNm.setText("");
			 newCatNm.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
			// newGlass.setBackgroundResource(R.drawable.blank);
			 
		}
		else if(view==btnCat) 
		{
			
			EditText drinkName = (EditText)findViewById(R.id.etNewDrinkNm);
			EditText directions = (EditText)findViewById(R.id.etDirections);
			
			ndd.drinkName=(drinkName.getText().toString());
			ndd.instructions=(directions.getText().toString());
			
			intent = new Intent(this, CategoryAndGlassPicker.class);
			startActivity(intent);
		}
		else if(view == btnSave)
		{
			List<String> ingredients = ndd.getIngredients();
			
			if(ndd.drinkName != null && !"".equals(ndd.drinkName.trim()))
			{
				if(ingredients != null && !"".equals(ndd.drinkName))
				{
					
					EditText drinkName = (EditText)findViewById(R.id.etNewDrinkNm);
					EditText directions = (EditText)findViewById(R.id.etDirections);
					
					ndd.drinkName=(drinkName.getText().toString().trim());
					ndd.instructions=(directions.getText().toString());
					
					//if  update
					if(ndd.drink_id >0)
						dataDAO.updateDrink(ndd);
					else
						dataDAO.insertNewDrink(ndd.drinkName, ndd.categoryId, ndd.glassId, ndd.instructions);
						
						Iterator<String> iter = ingredients.iterator();
						while (iter.hasNext()) {
							String ing = (String) iter.next();
							
							StringTokenizer toke = new StringTokenizer(ing,",");
							String amount="";
							String ingredientsName="";
							//the first one is junk ignore
							String token =toke.nextToken();
							//if there is only one token assume no amount
							if(toke.hasMoreElements())
							{
								if(toke != null)
								{
									 amount = token;
									 token =toke.nextToken();
								}
								if(toke != null)
									 ingredientsName = token;
							}
							else
								ingredientsName = token;
							
							
							dataDAO.getIngredientsId(ingredientsName.trim());
							if(ndd.drink_id >0)
								dataDAO.updateDrinkIngs(ndd,amount);
							else
								dataDAO.insertDrinkIng(ndd.newDrinkId, ndd.newing_id, amount);
						
						}
					
					ndd.clearDomain();
					TableLayout ll = (TableLayout)findViewById(R.id.lling);
					 ll.removeAllViews();
					intent = new Intent(this, HomeScreenView.class);
					startActivity(intent);
				}
				else
					showDialog(MENU_DRINK_ING);
			}
			else
				showDialog(MENU_DRINK_NAME);
			
		}
		
	}

	/* (non-Javadoc)
     * @see android.view.View.OnLongClickListener#onLongClick(android.view.View)
     */
    public boolean onLongClick(View v) {

    	try {
			v.setVisibility(View.GONE);
			//remove item from array
			NewDrinkDomain ndd = NewDrinkDomain.getInstance();
			CharSequence text = ((TextView)v).getText();
			
			Iterator<String> iter = ndd.getIngredients().iterator();
			
			for(int i=0;iter.hasNext();i++)
			{
				String t = (String)iter.next();
				if(t.equals(text.toString()))
				{
					ArrayList<String> items = ndd.getIngredients();
					items.remove(i);
				}
			}
		} catch (Exception e) {
		}
		
	    return false;
    }
    
    @Override
    public boolean onTouch(View v, MotionEvent event) {
	    super.onTouch(v, event);
	    //when touched set tag to background color then set back if touched again
	  	if(v instanceof TableRow )
	  	{
	  		
	  		if(event.getAction()== MotionEvent.ACTION_DOWN)
	  		{
	  			if(v.getTag() != null && ((String)v.getTag()).equals("true"))
	  			{
	  				v.setBackgroundColor(Color.TRANSPARENT);
	  				v.setTag("false");
	  			}
	  			else
	  			{
	  				v.setBackgroundColor(Color.rgb(104, 119, 152)); //104,119,152
	  				v.setTag("true");
	  			}
	  		}
	  	}
	  	
	  	
	  
	    return false;
    }
		
		
	
}

