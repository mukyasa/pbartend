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
import android.util.Log;
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
public class AddNewLiquor extends BaseActivity  implements OnClickListener {
	
	private ListView listBaseLiquors;
	protected MixerDbHelper myDatabaseAdapter;
	private EditText liquorName;
	private TextView liquorType;
	private Button btnSave,btnCancel;
	private CreateUpdateDAO dataDAO = new CreateUpdateDAO();
	String baseCatId;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_liquor);
		myDatabaseAdapter = MixerDbHelper.getInstance(this);
		
		initComponents();
	}
	
	private void initComponents() {
		baseCatId="";
		
		dataDAO.setSQLiteDatabase(myDatabaseAdapter.getDatabase());	
		
		liquorType = (TextView)findViewById(R.id.tvLiquorType);
		
		listBaseLiquors = (ListView)findViewById(R.id.listBaseLiquors);
		listBaseLiquors.setOnItemClickListener(baseLiquorItemListener);
		
		liquorName = (EditText)findViewById(R.id.etNewLiquorNm); 
		liquorName.setOnTouchListener(this);
		
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
	        
	        listBaseLiquors.setAdapter(records);
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
    		if(!baseCatId.equals("") && ("".equals(liquorName.getText().toString())
    				&& !getString(R.string.newLiquorName).equals(liquorName.getText().toString())))
    		{
	    		dataDAO.insertNewLiquor(liquorName.getText().toString(),baseCatId);
	    		
	    		liquorName.setText(getString(R.string.newLiquorName));
	    		liquorName.setTextColor(Color.LTGRAY);
	    		
	    		liquorType.setText(getString(R.string.newLiquorTypeAdded));
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
    		
    		liquorType.setText(getString(R.string.newLiquorTypeAdded)+" "+ (cursor.getString(cursor.getColumnIndexOrThrow(DataDAO.COL_NAME))));
    		baseCatId = id+"";
    		
			}};
	
}
