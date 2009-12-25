package com.juggler.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.juggler.domain.NewPassword;
import com.juggler.utils.Constants;

public class CreateTemplateActivity extends BaseActivity {
	private CharSequence text;
	private long selectedRow;
	private EditText etTitle,etURL;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.create_frame);
		initialize();
		
		super.onCreate(savedInstanceState);
	}

	private void initialize() {

		//set title
		Intent selectedIntent = getIntent();
		text =  selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_SELECTED_TEXT);
		selectedRow =  selectedIntent.getLongExtra(Constants.INTENT_EXTRA_SELECTED_ROW, 0);
		TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText(text);
		
		Button bNext = (Button)findViewById(R.id.butNext);
		bNext.setText(getString(R.string.next));
		
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
	    	
	    	np.name =text.toString();
		    np.catId=selectedRow;
	    	
	    }
	    
	    
	}
	
}
