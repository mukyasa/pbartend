/**
 * Date: Jul 6, 2010
 * Project: PocketBartender
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.drinkmixerdemo.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.drinkmixerdemo.R;
import com.drinkmixerdemo.dao.CreateUpdateDAO;
import com.drinkmixerdemo.dao.MixerDbHelper;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class AddNewGarnish extends BaseActivity  implements OnClickListener {
	
	protected MixerDbHelper myDatabaseAdapter;
	private EditText garnishName;
	private Button btnSave,btnCancel;
	private CreateUpdateDAO dataDAO = new CreateUpdateDAO();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_garnish);
		myDatabaseAdapter = MixerDbHelper.getInstance(this);
		
		initComponents();
	}
	
	private void initComponents() {
		
		dataDAO.setSQLiteDatabase(myDatabaseAdapter.getDatabase());	
		
		garnishName = (EditText)findViewById(R.id.etNewGarnishNm); 
		garnishName.setOnTouchListener(this);
		
		btnCancel=(Button)findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);
		btnCancel.setOnTouchListener(this);
	
		btnSave = (Button)findViewById(R.id.btnSave);
		btnSave.setOnClickListener(this);
		btnSave.setOnTouchListener(this);
		
	}

	/* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View view) {
    	if(view==btnSave)
		{
    		if(!"".equals(garnishName.getText().toString()) && !getString(R.string.newGarnishName).equals(garnishName.getText().toString()))
    		{
	    		dataDAO.insertNewGarnish(garnishName.getText().toString().trim());
	    		
	    		garnishName.setText(getString(R.string.newGarnishName));
	    		garnishName.setTextColor(Color.LTGRAY);
	    		showDialog(DIALOG_TYPE_SUCCESS);
    		}
    		else
    			showDialog(DIALOG_TYPE_ERROR);
    		
		}
		else if(view==btnCancel)
		{
			Intent intent = new Intent(this, LiquorListView.class);
			startActivity(intent);
		
		}
	    
    }
    
}
