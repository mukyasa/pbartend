package com.juggler.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.juggler.domain.NewPassword;
import com.juggler.utils.Constants;
import com.juggler.utils.TempletUtil;

public class CreateWalletTemplateActivity extends BaseActivity {
	private CharSequence text;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.template_frame);
		initialize();
		
		super.onCreate(savedInstanceState);
	}

	private void initialize() {

		//set title
		Intent selectedIntent = getIntent();
		text =  selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_SELECTED_TEXT);
		TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText(text);
		
		TableLayout detailLayout = (TableLayout)findViewById(R.id.tlDetails);
		detailLayout.addView(TempletUtil.getRow(this,"test","test val 1",true));
		detailLayout.addView(TempletUtil.getRow(this,"test","test val 2",false));
		
		
	}
	
	/* (non-Javadoc)
	 * @see com.juggler.view.BaseActivity#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
	    super.onClick(v);
	    
	    if(v == bNext)
	    {
	    	NewPassword np = NewPassword.getInstance();
	    	
	    }
	    
	    
	}
	
}
