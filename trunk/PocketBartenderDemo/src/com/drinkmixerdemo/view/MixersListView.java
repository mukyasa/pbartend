package com.drinkmixerdemo.view;

import android.os.Bundle;
import android.view.Menu;

public class MixersListView extends IngredientsListView {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ingtype.type=(TYPE_MIXERS);
        try {
	        initComponents();
        } catch (Exception e) {
        	showDialog(DIALOG_WHOA_ERROR);//Log.e("", "Whoa! some error trying to open your db.", e);
        }
    }
	
	
	public boolean onCreateOptionsMenu(Menu menu) {
	  	super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_CREATE_MIXER, 0, "Add New Mixer").setIcon(android.R.drawable.ic_menu_add);
	    return true;
	}
    
}
