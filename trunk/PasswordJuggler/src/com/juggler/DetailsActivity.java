package com.juggler;

import android.app.Activity;
import android.os.Bundle;

public class DetailsActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_frame);
		// Intent intent = new Intent(this, FlashCardTest.class);
		initialize();
	}

	private void initialize() {

	}
}
