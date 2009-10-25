package com.bartender.domain;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;

public class NewDrinkDomain {
	
	public String drinkName=null;
	public String instructions=null;
	public long glassId;
	public Drawable glassType;
	public long categoryId;
	public String categoryName=null;
	public int ingredientsCat; //0,1,3
	//Multiples
	public String wholeAmount=null; 
	public String halfAmount=null; 
	public String measurment=null;
	public String ingredientsName=null;
	private List<String> ingredients= new ArrayList<String>();
	
	private static NewDrinkDomain newDrinkDomain = null;
	public static final int SCREEN_TYPE_CAT=0;
	public static final int SCREEN_TYPE_ING=1;
	public static final int SCREEN_TYPE_NEW=2;
	

	public List<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}
	
	public void addIngredients(String ing){
		this.ingredients.add(ing);
		
	}
	
	public void clearIngredients()
	{
		this.measurment=null;
		this.wholeAmount = null;
		this.halfAmount = null;
	}

	public static NewDrinkDomain getInstance()
    {
      if (newDrinkDomain == null)
    	  newDrinkDomain = new NewDrinkDomain();
      return newDrinkDomain;
    }
	
	public void clearDomain(){
		
		newDrinkDomain = null;
	}
	
	
	
	
	
	
	
	

}
