package com.drinkmixer.domain;

import java.util.ArrayList;

public class DetailsDomain {
	
	public String drinkName;
	public String drinkType;
	public String glass;
	public String ingredients;
	public String instructions;
	public int id;
	public String favorites;
	public ArrayList<String> drinkNames = new ArrayList<String>();
	public ArrayList<String> ings = new ArrayList<String>();
	
	public DetailsDomain(){
		
	}
	
	public void addIng(String ing){
		ings.add(ing);
	}
	
	public DetailsDomain(String drinkName, 
			String drinkType, 
			String glass, 
			String ingredients, 
			String instructions,
            int id, String favorites, 
            ArrayList<String> drinkNames) {
	    super();
	    this.drinkName = drinkName;
	    this.drinkType = drinkType;
	    this.glass = glass;
	    this.ingredients = ingredients;
	    this.instructions = instructions;
	    this.id = id;
	    this.favorites = favorites;
	    this.drinkNames = drinkNames;
    }



}
