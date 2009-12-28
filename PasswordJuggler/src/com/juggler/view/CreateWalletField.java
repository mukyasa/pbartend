/**
 * Date: Dec 27, 2009
 * Project: PasswordJuggler
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.juggler.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

import com.juggler.domain.NewPassword;
import com.juggler.utils.Constants;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class CreateWalletField extends BaseActivity {
	
	private Button bNext;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    setContentView(R.layout.create_frame);

	    bNext = (Button)findViewById(R.id.butNext);
	    bNext.setText(getString(R.string.save));
	    
	    //hide the url field
	    EditText etURL = (EditText)findViewById(R.id.etURL);
	    etURL.setVisibility(View.GONE);
	    
	    EditText etTitle = (EditText)findViewById(R.id.etTitle);
		//get Intent then set text
		Intent selectedIntent = getIntent();
		CharSequence text =  selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_SELECTED_TEXT);
		String subString = text.toString().substring(0, text.toString().length()-1);
		
		etTitle.setText(subString);
	    
		TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
	    tvTitle.setText(subString);
	    
	    super.onCreate(savedInstanceState);
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
