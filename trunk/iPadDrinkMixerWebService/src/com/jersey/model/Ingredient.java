package com.jersey.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Ingredient {
 
	private int id;
	private String name;
	private int category_id;
	private int subcategory_id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int categoryId) {
		category_id = categoryId;
	}
	public int getSubcategory_id() {
		return subcategory_id;
	}
	public void setSubcategory_id(int subcategoryId) {
		subcategory_id = subcategoryId;
	}
	
	
}


