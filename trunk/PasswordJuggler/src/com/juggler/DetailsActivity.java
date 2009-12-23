package com.juggler;

import android.os.Bundle;

public class DetailsActivity extends BaseActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.detail_frame);
		// Intent intent = new Intent(this, FlashCardTest.class);
		initialize();
		
		super.onCreate(savedInstanceState);
	}

	private void initialize() {

	}
	
}
