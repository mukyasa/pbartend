package com.bartender.domain;

public class IngredientsType {
	
	private static IngredientsType ingType = null;
	
	public static IngredientsType getInstance()
    {
      if (ingType == null)
    	  ingType = new IngredientsType();
      return ingType;
    }

	protected String type;
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
