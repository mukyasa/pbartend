package com.bartender.domain;

public class NewDrinkDomain {
	
	private String drinkName;
	private String instructions;
	private long glassId;
	private long categoryId;
	private String categoryName;
	private int ingredientsCat; //0,1,3
	//Multiples
	private String amount; 
	private String ingredientsName;
	
	private static NewDrinkDomain newDrinkDomain = null;
	public static final int SCREEN_TYPE_CAT=0;
	public static final int SCREEN_TYPE_ING=1;
	public static final int SCREEN_TYPE_NEW=2;
	
	public static NewDrinkDomain getInstance()
    {
      if (newDrinkDomain == null)
    	  newDrinkDomain = new NewDrinkDomain();
      return newDrinkDomain;
    }
	
	public static void clearDomain(){
		
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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getIngredientsName() {
		return ingredientsName;
	}

	public void setIngredientsName(String ingredientsName) {
		this.ingredientsName = ingredientsName;
	}
	
	
	
	
	
	

}
