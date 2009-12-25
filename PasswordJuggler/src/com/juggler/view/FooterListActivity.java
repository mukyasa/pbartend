package com.juggler.view;

import android.app.ListActivity;
import android.os.Bundle;

import com.juggler.utils.FooterUtil;

public class FooterListActivity extends ListActivity{
	
	private FooterUtil footutil;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        footutil = new FooterUtil(this);
        
        footutil.initialize(this);
        
    }
	
}
