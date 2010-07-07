package com.drinkmixer.view;

import android.os.Bundle;
import android.view.Menu;

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
	  
	  public boolean onCreateOptionsMenu(Menu menu) {
		  	super.onCreateOptionsMenu(menu);
			menu.add(0, MENU_CREATE_LIQUOR, 0, "Add New Liquor").setIcon(android.R.drawable.ic_menu_add);
		    return true;
		}

	
	  
	    
}
