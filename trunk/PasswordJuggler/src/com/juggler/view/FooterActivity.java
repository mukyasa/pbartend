package com.juggler.view;

import android.app.Activity;
import android.os.Bundle;

import com.juggler.utils.FooterUtil;

public class FooterActivity extends Activity{
	private FooterUtil footutil;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        footutil = new FooterUtil(this);
        
        footutil.initialize(this);
        
    }
	
}
