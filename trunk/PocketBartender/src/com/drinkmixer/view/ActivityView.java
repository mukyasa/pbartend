package com.drinkmixer.view;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.drinkmixer.R;
import com.drinkmixer.dao.DetailDAO;
import com.drinkmixer.dao.MixerDbHelper;
import com.drinkmixer.domain.DetailsDomain;
import com.drinkmixer.domain.NewDrinkDomain;
import com.drinkmixer.utils.FileParser;

public class ActivityView extends Activity {
	
	protected MixerDbHelper myDatabaseAdapter;
	protected long selectedRow;
	protected TextView tvDrinkName;
	protected TextView tvDrinktype;
	protected TextView tvIng1;
	protected TextView tvInstructions;
	protected DetailsDomain drinkdetail;
	protected ImageButton favImageButton;
	protected ImageView imgGlassType;
	protected DetailDAO drinkdao;
	private static final int MENU_ADD_FAV=0;
	private static final int MENU_REMOVE_FAV=1;
	private static final int MENU_HOME=2;
	private static final int MENU_MODIFY=3;
	
	
	/**
	 * sets shared values to domain object
	 */
	protected void setViewItems()
	{
		tvDrinkName.setText(drinkdetail.drinkName);
		tvDrinktype.setText(drinkdetail.drinkType);
		tvIng1.setText(drinkdetail.ingredients);
		tvInstructions.setText(drinkdetail.instructions);
		
		//only show fav star if is a favorite
		LayoutParams params = favImageButton.getLayoutParams();
		if(DetailDAO.FAV_NO.equalsIgnoreCase(drinkdetail.favorites))
			params.height = 0;
		else
			params.height=36;
		
		FileParser.getGlassImage(imgGlassType, drinkdetail.glass);
		
	}
	
	protected void setDomain(){
		NewDrinkDomain ndd = NewDrinkDomain.getInstance();
		ndd.drinkName = drinkdetail.drinkName;
		ndd.setIngredients(drinkdetail.ings);
		ndd.categoryName = drinkdetail.drinkType;
		ndd.categoryId = drinkdetail.catId;
		ndd.glassId = drinkdetail.glassId;
		ndd.instructions = drinkdetail.instructions;
		ndd.glassType = imgGlassType.getBackground();
		ndd.drink_id = drinkdetail.id;
		
	}
	/**
	 * sets the views to the variables
	 */
	protected void findAndSetView()
	{
		tvDrinkName = (TextView) findViewById(R.id.tvDrinkName);
		tvDrinktype = (TextView) findViewById(R.id.tvDrinkType);
		tvIng1 = (TextView) findViewById(R.id.tvIng1);
		tvInstructions = (TextView) findViewById(R.id.tvInstructions);
		favImageButton = (ImageButton)findViewById(R.id.imgFav);
		imgGlassType =(ImageView)findViewById(R.id.imgGlassType);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_HOME, 0, "Home").setIcon(R.drawable.home);
		menu.add(0, MENU_MODIFY, 0, "Modify Drink").setIcon(android.R.drawable.ic_menu_edit);
		menu.add(0, MENU_ADD_FAV, 0, "Save Favorite").setIcon(R.drawable.fav_menu);
		menu.add(0, MENU_REMOVE_FAV, 0, "Remove Favorite").setIcon(R.drawable.no_fav_menu);
	    return true;
	}

	/* Handles item selections */
	public boolean onOptionsItemSelected(MenuItem item) {
		
	    switch (item.getItemId()) {
	    case MENU_HOME:
			startActivity(new Intent(this, HomeScreenView.class));
	    	return true;
	    case MENU_ADD_FAV:
	    	drinkdao.setFavoritesYes(drinkdetail.id);
	    	drinkdetail.favorites = DetailDAO.FAV_YES;
	    	setViewItems();
	        return true;
	    case MENU_REMOVE_FAV:
	    	drinkdao.removeFavorite(drinkdetail.id);
	    	drinkdetail.favorites = DetailDAO.FAV_NO;
	    	setViewItems();
	    	return true;
	    case MENU_MODIFY:
			startActivity(new Intent(this, CreateUpdateView.class));
	    	return true;
	    }
	    return false;
	}



}
