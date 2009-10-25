package com.bartender.view;

import android.os.Bundle;

public class GarnishListView extends IngredientsListView {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ingtype.type=(TYPE_GARNISH);
        initComponents();
	}
}
