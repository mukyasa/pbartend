package com.mixer.view;

import android.os.Bundle;

public class LiquorListView extends IngredientsListView {

	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        ingtype.type=(TYPE_LIQUOR);
	        initComponents();
	    }
	    
}
