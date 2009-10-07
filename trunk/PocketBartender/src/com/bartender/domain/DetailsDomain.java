package com.bartender.domain;

import java.util.ArrayList;
import java.util.List;

public class DetailsDomain {
	
	private String drinkName;
	private String drinkType;
	private String glass;
	private String ing1;
	private String ing2;
	private String ing3;
	private String ingredients;
	private String instructions;
	private String instructions2;
	private int id;
	private String favorites;
	private List<String> drinkNames = new ArrayList<String>();
	
	
	public String getFavorites() {
		return favorites;
	}
	public void setFavorites(String favorites) {
		this.favorites = favorites;
	}
	public String getIng1() {
		return ing1;
	}
	public void setIng1(String ing1) {
		this.ing1 = ing1;
	}
	public String getIng2() {
		return ing2;
	}
	public void setIng2(String ing2) {
		this.ing2 = ing2;
	}
	public String getIng3() {
		return ing3;
	}
	public void setIng3(String ing3) {
		this.ing3 = ing3;
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
	public String getInstructions2() {
		return instructions2;
	}
	public void setInstructions2(String instructions2) {
		this.instructions2 = instructions2;
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
