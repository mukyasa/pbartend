package com.juggler.view;

import com.juggler.utils.Constants;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CreateActivity extends BaseActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.create_frame);
		initialize();
		
		super.onCreate(savedInstanceState);
	}

	private void initialize() {

		//set title
		Intent selectedIntent = getIntent();
		CharSequence text =  selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_SELECTED_TEXT);
		TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText(text);
		
	}
	
}
