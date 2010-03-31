package com.drinkmixer.view;

import android.os.Bundle;

public class LiquorListView extends IngredientsListView {

	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        ingtype.type=(TYPE_LIQUOR);
	        try {
		        initComponents();
	        } catch (Exception e) {
	        	showDialog(0);//Log.e("", "Whoa! some error trying to open your db.", e);
	        }
	    }
	  
	    
}
