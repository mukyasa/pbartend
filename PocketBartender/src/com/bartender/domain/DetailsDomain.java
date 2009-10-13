package com.bartender.domain;

import java.util.ArrayList;
import java.util.List;

public class DetailsDomain {
	
	private String drinkName;
	private String drinkType;
	private String glass;
	private String ingredients;
	private String instructions;
	private int id;
	private String favorites;
	private List<String> drinkNames = new ArrayList<String>();
	
	
	public String getFavorites() {
		return favorites;
	}
	public void setFavorites(String favorites) {
		this.favorites = favorites;
	}
	public String getIngredients() {
		return ingredients;
	}
	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public void setDrinkNames(List<String> drinkNames) {
		this.drinkNames = drinkNames;
	}
	public List<String> getDrinkNames() {
		return drinkNames;
	}
	
	public String getDrinkName() {
		return drinkName;
	}
	public void setDrinkName(String drinkName) {
		this.drinkName = drinkName;
	}
	public String getDrinkType() {
		return drinkType;
	}
	public void setDrinkType(String drinkType) {
		this.drinkType = drinkType;
	}
	public String getGlass() {
		return glass;
	}
	public void setGlass(String glass) {
		this.glass = glass;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
