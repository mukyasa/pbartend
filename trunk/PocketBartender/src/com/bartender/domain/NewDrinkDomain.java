package com.bartender.domain;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;

public class NewDrinkDomain {
	
	private String drinkName;
	private String instructions;
	private long glassId;
	private Drawable glassType;
	private long categoryId;
	private String categoryName;
	private int ingredientsCat; //0,1,3
	//Multiples
	private String wholeAmount=null; 
	private String halfAmount=null; 
	private String measurment=null;
	private String ingredientsName=null;
	private List<String> ingredients= new ArrayList<String>();
	
	private static NewDrinkDomain newDrinkDomain = null;
	public static final int SCREEN_TYPE_CAT=0;
	public static final int SCREEN_TYPE_ING=1;
	public static final int SCREEN_TYPE_NEW=2;
	
	
	
	public String getMeasurment() {
		return measurment;
	}

	public void setMeasurment(String measurment) {
		this.measurment = measurment;
	}

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

	public Drawable getGlassType() {
		return glassType;
	}

	public void setGlassType(Drawable glassType) {
		this.glassType = glassType;
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
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public String getDrinkName() {
		return drinkName;
	}

	public void setDrinkName(String drinkName) {
		this.drinkName = drinkName;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public long getGlassId() {
		return glassId;
	}

	public void setGlassId(long glassId) {
		this.glassId = glassId;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public int getIngredientsCat() {
		return ingredientsCat;
	}

	public void setIngredientsCat(int ingredientsCat) {
		this.ingredientsCat = ingredientsCat;
	}

	public String getIngredientsName() {
		return ingredientsName;
	}

	public void setIngredientsName(String ingredientsName) {
		this.ingredientsName = ingredientsName;
	}

	public String getWholeAmount() {
		return wholeAmount;
	}

	public void setWholeAmount(String wholeAmount) {
		this.wholeAmount = wholeAmount;
	}

	public String getHalfAmount() {
		return halfAmount;
	}

	public void setHalfAmount(String halfAmount) {
		this.halfAmount = halfAmount;
	}
	
	
	
	
	
	

}
