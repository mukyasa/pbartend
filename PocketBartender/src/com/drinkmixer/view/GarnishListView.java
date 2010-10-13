package com.drinkmixer.view;

import android.os.Bundle;
import android.view.Menu;

public class GarnishListView extends IngredientsListView {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ingtype.type=(TYPE_GARNISH);
        try {
	        initComponents();
        } catch (Exception e) {
        	showDialog(DIALOG_WHOA_ERROR);
        };
	}
	
	 public boolean onCreateOptionsMenu(Menu menu) {
		  	super.onCreateOptionsMenu(menu);
			menu.add(0, MENU_CREATE_GARNISH, 0, "Add New Garnish").setIcon(android.R.drawable.ic_menu_add);
		    return true;
		}
	
}
