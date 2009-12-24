package com.juggler.view;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class WalletSubCatListActivity extends FooterListActivity{
	private Button butPrev;
	private final String[] subComputers = { "Software License", "Database", "WiFi", "Server" };
	private final String[] subFinancial = { "Credit Card", "Bank Account (US)", "Bank Account (CA)" };
	private final String[] subGovernment = { "Passport", "Driver's License", "Social Security Number","Hunting License" };
	private final String[] subInternet = { "Email Account", "Instant Messenger","FTP","iTunes","ISP"};
	private final String[] subMembership = { "Rewards Program","Membership"};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.list_frame);
		initialize();
		super.onCreate(savedInstanceState);
	}
	private void initialize() {
		String[] items={""};
		int selectedRow = getIntent().getIntExtra(WalletCatListActivity.INTENT_EXTRA_SELECTED_ROW, 0);
		switch(selectedRow)
		{
			case 0:
				items = subComputers;
				break;
		}
		
		
		// hide next button
		Button next = (Button) findViewById(R.id.butNext);
		next.setVisibility(View.GONE);
		// hide title
		TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvTitle.setVisibility(View.GONE);
		butPrev = (Button) findViewById(R.id.butPrev);
		butPrev.setOnClickListener(this);
		butPrev.setOnTouchListener(this);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, items));
		getListView().setTextFilterEnabled(true);
	}
	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View,
	 * android.view.MotionEvent)
	 */
	public boolean onTouch(View v, MotionEvent event) {
		super.onTouch(v, event);
		if (v instanceof Button) {
			
			if(v == butPrev)
			{
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					((Button)v).setBackgroundResource(R.drawable.prev_button_on);
					((Button)v).setPadding(20,0,10,0);
				} 
				else{
					((Button)v).setBackgroundResource(R.drawable.prev_button);
					((Button)v).setPadding(20,0,10,0);
				}
			}
		}
		
		
		return false;
	}
	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View v) {
		super.onClick(v);
		if (v == butPrev) {
			finish();
		}
	}
}