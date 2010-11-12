package com.jersey.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DrinkDetails {
	
	private String drinkName;
	private String drinkType;
	private String glass;
	private int glassId;
	private int catId;
	private String ingredients;
	private String instructions;
	private int id;
	private int favorites;
	private float rating;
	private String editAmount;
	private String uid;
	private ArrayList<String> drinkNames = new ArrayList<String>();
	private ArrayList<String> ings = new ArrayList<String>();
	private String img;
	private boolean isCustom=false;
	private String ipAddress;
	private String version;
	private int drinkId;
	
	
	public int getDrinkId() {
		return drinkId;
	}

	public void setDrinkId(int drinkId) {
		this.drinkId = drinkId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public boolean isCustom() {
		return isCustom;
	}

	public void setCustom(boolean isCustom) {
		this.isCustom = isCustom;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getEditAmount() {
		return editAmount;
	}

	public void setEditAmount(String editAmount) {
		this.editAmount = editAmount;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
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

	public int getGlassId() {
		return glassId;
	}

	public void setGlassId(int glassId) {
		this.glassId = glassId;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFavorites() {
		return favorites;
	}

	public void setFavorites(int favorites) {
		this.favorites = favorites;
	}
	

	public ArrayList<String> getDrinkNames() {
		return drinkNames;
	}

	public void setDrinkNames(ArrayList<String> drinkNames) {
		this.drinkNames = drinkNames;
	}

	public ArrayList<String> getIngs() {
		return ings;
	}

	public void setIngs(ArrayList<String> ings) {
		this.ings = ings;
	}
	
	public void addIng(String ings){
		this.ings.add(ings);		
	}
	
	



}
