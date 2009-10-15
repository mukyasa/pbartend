package com.bartender.domain;

public class ScreenType {
	
	private static ScreenType ingType = null;
	
	public static ScreenType getInstance()
    {
      if (ingType == null)
    	  ingType = new ScreenType();
      return ingType;
    }

	private String type;
	private int screenType=-1;
	
	
	public int getScreenType() {
		return screenType;
	}

	public void setScreenType(int screenType) {
		this.screenType = screenType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
