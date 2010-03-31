package com.drinkmixer.view;

import android.os.Bundle;

public class MixersListView extends IngredientsListView {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ingtype.type=(TYPE_MIXERS);
        try {
	        initComponents();
        } catch (Exception e) {
        	showDialog(0);//Log.e("", "Whoa! some error trying to open your db.", e);
        }
    }
	
    
}
