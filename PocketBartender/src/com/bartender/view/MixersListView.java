package com.bartender.view;

import android.os.Bundle;

public class MixersListView extends IngredientsListView {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setType(TYPE_MIXERS);
    }
  
    
}
