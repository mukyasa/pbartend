package com.bartender.view;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
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
	protected TextView tvIng1;
	protected TextView tvIng2;
	protected TextView tvIng3;
	protected TextView tvFullIng;
	protected TextView tvInstructions;
	protected TextView tvInstructions2;
	protected Spinner spinnerDrinkNames;
	protected DetailsDomain drinkdetail;
	protected ImageButton favImageButton;
	protected ImageView imgGlassType;
	protected DetailDAO drinkdao;
	private static final int MENU_ADD_FAV=0;
	private static final int MENU_REMOVE_FAV=1;
	private static final int MENU_HOME=2;
	
	public static final String champagne="champagne";
    public static final String cocktail="cocktail";
    public static final String highball="highball";
    public static final String hurricane="hurricane";
    public static final String irish="irish";
    public static final String pint="pint";
    public static final String margarita="margarita";
    public static final String mug="mug";
    public static final String parfait="parfait";
    public static final String pilsner="pilsner";
    public static final String pousse_cafe="pousse_cafe";
    public static final String punch="punch";
    public static final String rocks="rocks";
    public static final String shot="shot";
    public static final String snifter="snifter";
    public static final String sour="sour";
    public static final String wine="wine";
	
	/**
	 * sets shared values to domain object
	 */
	protected void setViewItems()
	{
		tvDrinkName.setText(drinkdetail.getDrinkName());
		tvDrinktype.setText(drinkdetail.getDrinkType());
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
		else
			params.height=36;
		
		//set glass image
		if(champagne.equalsIgnoreCase(drinkdetail.getGlass()))
			imgGlassType.setBackgroundResource(R.drawable.champagne);
		else if(cocktail.equalsIgnoreCase(drinkdetail.getGlass()))
			imgGlassType.setBackgroundResource(R.drawable.cocktail);
		else if(highball.equalsIgnoreCase(drinkdetail.getGlass()))
			imgGlassType.setBackgroundResource(R.drawable.highball);
		else if(hurricane.equalsIgnoreCase(drinkdetail.getGlass()))
			imgGlassType.setBackgroundResource(R.drawable.hurricane);
		else if(irish.equalsIgnoreCase(drinkdetail.getGlass()))
			imgGlassType.setBackgroundResource(R.drawable.irish);
		else if(pint.equalsIgnoreCase(drinkdetail.getGlass()))
			imgGlassType.setBackgroundResource(R.drawable.pint);
		else if(margarita.equalsIgnoreCase(drinkdetail.getGlass()))
			imgGlassType.setBackgroundResource(R.drawable.margarita);
		else if(mug.equalsIgnoreCase(drinkdetail.getGlass()))
			imgGlassType.setBackgroundResource(R.drawable.mug);
		else if(parfait.equalsIgnoreCase(drinkdetail.getGlass()))
			imgGlassType.setBackgroundResource(R.drawable.parfait);
		else if(pilsner.equalsIgnoreCase(drinkdetail.getGlass()))
			imgGlassType.setBackgroundResource(R.drawable.pilsner);
		else if(pousse_cafe.equalsIgnoreCase(drinkdetail.getGlass()))
			imgGlassType.setBackgroundResource(R.drawable.pousse_cafe);
		else if(punch.equalsIgnoreCase(drinkdetail.getGlass()))
			imgGlassType.setBackgroundResource(R.drawable.punch);
		else if(rocks.equalsIgnoreCase(drinkdetail.getGlass()))
			imgGlassType.setBackgroundResource(R.drawable.rocks);
		else if(shot.equalsIgnoreCase(drinkdetail.getGlass()))
			imgGlassType.setBackgroundResource(R.drawable.shot);
		else if(snifter.equalsIgnoreCase(drinkdetail.getGlass()))
			imgGlassType.setBackgroundResource(R.drawable.snifter);
		else if(sour.equalsIgnoreCase(drinkdetail.getGlass()))
			imgGlassType.setBackgroundResource(R.drawable.sour);
		else if(wine.equalsIgnoreCase(drinkdetail.getGlass()))
			imgGlassType.setBackgroundResource(R.drawable.wine);
	}
	
	/**
	 * sets the views to the variables
	 */
	protected void findAndSetView()
	{
		tvDrinkName = (TextView) findViewById(R.id.tvDrinkName);
		tvDrinktype = (TextView) findViewById(R.id.tvDrinkType);
		tvIng1 = (TextView) findViewById(R.id.tvIng1);
		tvIng2 = (TextView) findViewById(R.id.tvIng2);
		tvIng3 = (TextView) findViewById(R.id.tvIng3);
		tvFullIng = (TextView) findViewById(R.id.tvFullIng);
		tvInstructions = (TextView) findViewById(R.id.tvInstructions);
		tvInstructions2  = (TextView) findViewById(R.id.tvInstructions2);
		favImageButton = (ImageButton)findViewById(R.id.imgFav);
		imgGlassType =(ImageView)findViewById(R.id.imgGlassType);
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
	    	drinkdetail.setFavorites(DetailDAO.FAV_YES);
	    	setViewItems();
	        return true;
	    case MENU_REMOVE_FAV:
	    	drinkdao.removeFavorite(drinkdetail.getId());
	    	drinkdetail.setFavorites(DetailDAO.FAV_NO);
	    	setViewItems();
	    	return true;
	    }
	    return false;
	}



}
