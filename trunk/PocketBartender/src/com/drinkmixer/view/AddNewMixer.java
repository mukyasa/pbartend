/**
 * Date: Jul 6, 2010
 * Project: PocketBartender
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.drinkmixer.view;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.drinkmixer.R;
import com.drinkmixer.dao.CreateUpdateDAO;
import com.drinkmixer.dao.DataDAO;
import com.drinkmixer.dao.DrinkListDAO;
import com.drinkmixer.dao.MixerDbHelper;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class AddNewMixer extends BaseActivity  implements OnClickListener {
	
	private ListView listBaseMixers;
	protected MixerDbHelper myDatabaseAdapter;
	private EditText mixerName;
	private TextView mixerType;
	private Button btnSave,btnCancel;
	private CreateUpdateDAO dataDAO = new CreateUpdateDAO();
	String baseCatId;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_mixer);
		myDatabaseAdapter = MixerDbHelper.getInstance(this);
		
		initComponents();
	}
	
	private void initComponents() {
		baseCatId="";
		
		dataDAO.setSQLiteDatabase(myDatabaseAdapter.getDatabase());	
		
		mixerType = (TextView)findViewById(R.id.tvMixerType);
		
		listBaseMixers = (ListView)findViewById(R.id.listBaseMixers); 
		listBaseMixers.setOnItemClickListener(baseLiquorItemListener);
		
		mixerName = (EditText)findViewById(R.id.etNewMixerNm); 
		mixerName.setOnTouchListener(this);
		
		btnCancel=(Button)findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);
		btnCancel.setOnTouchListener(this);
	
		btnSave = (Button)findViewById(R.id.btnSave);
		btnSave.setOnClickListener(this);
		btnSave.setOnTouchListener(this);
		
    	try {
	        Cursor recordscCursor = dataDAO.retrieveAllBaseIngredients();
	        startManagingCursor(recordscCursor);
	        String[] from = new String[] { DrinkListDAO.COL_NAME };
	        int[] to = new int[] { R.id.tvItem};
	        SimpleCursorAdapter records = new SimpleCursorAdapter(this,
	        		R.layout.textviewrow, recordscCursor, from, to);
	        
	        listBaseMixers.setAdapter(records);
        } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
    	
	}

	/* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View view) {
    	if(view==btnSave)
		{
    		if(!baseCatId.equals("") && "".equals(mixerName.getText().toString().trim())
    				&& !getString(R.string.newMixerName).equals(mixerName.getText().toString()))
    		{
	    		dataDAO.insertNewMixer(mixerName.getText().toString(),baseCatId);
	    		
	    		mixerName.setText(getString(R.string.newMixerName));
	    		mixerName.setTextColor(Color.LTGRAY);
	    		mixerType.setText(getString(R.string.newMixerTypeAdded));
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
    
    
 AdapterView.OnItemClickListener baseLiquorItemListener = new OnItemClickListener(){
		
    	public void onItemClick(AdapterView<?> parent, View v, int position,long id) {

    		Cursor cursor = (Cursor) parent.getItemAtPosition(position);
    		
    		mixerType.setText(getString(R.string.newMixerTypeAdded)+" "+ (cursor.getString(cursor.getColumnIndexOrThrow(DataDAO.COL_NAME))));
    		baseCatId = id+"";
    		
			}};
	
}
