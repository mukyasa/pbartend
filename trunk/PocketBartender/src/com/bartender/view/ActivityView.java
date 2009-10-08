package com.bartender.view;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.bartender.R;
import com.bartender.dao.DatabaseAdapter;
import com.bartender.dao.DetailDAO;
import com.bartender.domain.DetailsDomain;

public class ActivityView extends Activity {
	
	protected DatabaseAdapter myDatabaseAdapter;
	protected long selectedRow;
	protected TextView tvDrinkName;
	protected TextView tvDrinktype;
	protected TextView tvGlass;
	protected TextView tvIng1;
	protected TextView tvIng2;
	protected TextView tvIng3;
	protected TextView tvFullIng;
	protected TextView tvInstructions;
	protected TextView tvInstructions2;
	protected Spinner spinnerDrinkNames;
	protected DetailsDomain drinkdetail;
	protected ImageButton favImageButton;
	protected DetailDAO drinkdao;
	private static final int MENU_ADD_FAV=0;
	private static final int MENU_REMOVE_FAV=1;
	private static final int MENU_HOME=2;
	
	/**
	 * sets shared values to domain object
	 */
	protected void setViewItems()
	{
		tvDrinktype.setText(drinkdetail.getDrinkType());
		tvGlass.setText(drinkdetail.getGlass());
		tvIng1.setText(drinkdetail.getIng1());
		tvIng2.setText(drinkdetail.getIng2());
		tvIng3.setText(drinkdetail.getIng3());
		tvFullIng.setText(drinkdetail.getIngredients());
		tvInstructions.setText(drinkdetail.getInstructions());
		tvInstructions2.setText(drinkdetail.getInstructions2());
		
		//only show fav star if is a favorite
		LayoutParams params = favImageButton.getLayoutParams();
		if(DetailDAO.FAV_NO.equalsIgnoreCase(drinkdetail.getFavorites()))
			params.height = 0;
	}
	
	/**
	 * sets the views to the variables
	 */
	protected void findAndSetView()
	{
		tvDrinktype = (TextView) findViewById(R.id.tvDrinkType);
		tvGlass = (TextView) findViewById(R.id.tvGlassType);
		tvIng1 = (TextView) findViewById(R.id.tvIng1);
		tvIng2 = (TextView) findViewById(R.id.tvIng2);
		tvIng3 = (TextView) findViewById(R.id.tvIng3);
		tvFullIng = (TextView) findViewById(R.id.tvFullIng);
		tvInstructions = (TextView) findViewById(R.id.tvInstructions);
		tvInstructions2  = (TextView) findViewById(R.id.tvInstructions2);
		favImageButton = (ImageButton)findViewById(R.id.imgFav);
		
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_HOME, 0, "Home").setIcon(R.drawable.home);
		menu.add(0, MENU_ADD_FAV, 0, "Save Favorite").setIcon(R.drawable.fav_menu);
		menu.add(0, MENU_REMOVE_FAV, 0, "Remove Favorite").setIcon(R.drawable.no_fav_menu);
	    return true;
	}

	/* Handles item selections */
	public boolean onOptionsItemSelected(MenuItem item) {
		
	    switch (item.getItemId()) {
	    case MENU_HOME:
	    	Intent intent = new Intent(this, HomeScreenView.class);
			startActivity(intent);
	    	return true;
	    case MENU_ADD_FAV:
	    	drinkdao.setFavoritesYes(drinkdetail.getId());
	        return true;
	    case MENU_REMOVE_FAV:
	    	drinkdao.removeFavorite(drinkdetail.getId());
	    	return true;
	    }
	    return false;
	}



}
