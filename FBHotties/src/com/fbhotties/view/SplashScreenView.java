package com.fbhotties.view;

import com.fbhotties.utils.WebFileLoader;

import android.app.Activity;
import android.os.Bundle;

public class SplashScreenView extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.splash_view);
		
		//load some images
		WebFileLoader imageLoader = WebFileLoader.getInstance();
	}

}
