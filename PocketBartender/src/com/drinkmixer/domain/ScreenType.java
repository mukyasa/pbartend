package com.drinkmixer.domain;

public class ScreenType {
	
	private static ScreenType ingType = null;
	public String type;
	public int screenType=-1;
	public static final int SCREEN_TYPE_CAT=0;
	public static final int SCREEN_TYPE_ING=1;
	public static final int SCREEN_TYPE_NEW=2;
	
	public static ScreenType getInstance()
    {
      if (ingType == null)
    	  ingType = new ScreenType();
      return ingType;
    }


}
