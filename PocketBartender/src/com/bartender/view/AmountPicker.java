package com.bartender.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

import com.bartender.R;
import com.bartender.common.AppCommon;
import com.bartender.dao.DataDAO;
import com.bartender.dao.DatabaseAdapter;
import com.bartender.dao.DrinkListDAO;
import com.bartender.dao.IngredientsDAO;
import com.bartender.domain.NewDrinkDomain;

public class AmountPicker extends Activity  implements OnClickListener{
	
	 private ListView listWholeNums,listHalfNums,measurementtypes;
	 private Button btnSave,btnCancel;
	 private Intent intent;
	 protected DatabaseAdapter myDatabaseAdapter;
	 private IngredientsDAO dataDAO = new IngredientsDAO();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        myDatabaseAdapter = DatabaseAdapter.getInstance(this);
        setContentView(R.layout.amounts);
        initComponents();
        		
    }
    
    protected void initComponents() {

        listWholeNums = (ListView) findViewById(R.id.listWholeNums);
        listHalfNums = (ListView) findViewById(R.id.listHalfNums); 
        measurementtypes = (ListView)findViewById(R.id.listAmounts);
        
        //set click listeners
        listHalfNums.setOnItemClickListener(onHalfItemListener);
        listWholeNums.setOnItemClickListener(onWholeItemListener);
        measurementtypes.setOnItemClickListener(onMeasureItemListener);
        
        //fractions
        dataDAO.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
    	Cursor recordscCursor = dataDAO.retrieveAllMeasurements();
    	startManagingCursor(recordscCursor);
    	String[] from = new String[] { DrinkListDAO.COL_FRACTION };
		int[] to = new int[] { R.id.tfName};
    	SimpleCursorAdapter records = new SimpleCursorAdapter(this,
				R.layout.item_row, recordscCursor, from, to);
    	

    	//whole numbers to add
        List<String> tmp = new ArrayList<String>();
        for(int i=1;i<241;i++)
        	tmp.add(i+"");
        
        listHalfNums.setAdapter(records);
        listWholeNums.setAdapter(new ArrayAdapter<String>(this,R.layout.textviewrow, tmp));
        measurementtypes.setAdapter(new ArrayAdapter<String>(this,R.layout.textviewrow, AMOUNTS));
        
        //set event handlers to buttons
        btnSave = (Button) findViewById(R.id.btnAmountSave);
    	btnSave.setOnClickListener(this);
		
		btnCancel = (Button) findViewById(R.id.btnAmountCancel);
		btnCancel.setOnClickListener(this);
        
    }
    
    public void onClick(View v) {

    	if(v==btnSave)
		{
			intent = new Intent(this, CreateUpdateView.class);
			NewDrinkDomain ndd = NewDrinkDomain.getInstance();
			ndd.addIngredients(ndd.getWholeAmount() + " "+ndd.getHalfAmount() + " " + ndd.getIngredientsName());
			startActivity(intent);
		}
		else if(v==btnCancel)
		{
			intent = new Intent(this, CreateUpdateView.class);
			NewDrinkDomain.getInstance().clearIngredients();
			startActivity(intent);
		}
    	
	}
    
    static final String[] AMOUNTS = new String[] {"cup","oz","pint","quart","gallon","tsp","tbsp","lb","bottles(s)"
    	,"can(s)"
    	,"dashe(es)"
    	,"drop(s)"
    	,"part(s)"
    	,"pieces(s)"
    	,"scoop(s)"
    	,"splash(es)"
    	,"sprig(s)"
    	,"stick(s)"};
    
    
    private void setBackgroundDefault(AdapterView<?> parent)
    {
    	for(int i=0;i<parent.getCount();i++)
		{
			View vv = parent.getChildAt(i);
			if(vv != null)
				vv.setBackgroundColor(AppCommon.defaultColor);
		}
    }
    
    AdapterView.OnItemClickListener onWholeItemListener = new OnItemClickListener(){
		
    	public void onItemClick(AdapterView<?> parent, View v, int position,long id) {

    		//set all to white
    		setBackgroundDefault(parent);
    		NewDrinkDomain.getInstance().setWholeAmount((String)parent.getItemAtPosition(position));
    		
			v.setBackgroundColor(AppCommon.color);
			
			}};
			
	AdapterView.OnItemClickListener onHalfItemListener = new OnItemClickListener(){
		
		public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
	
			//set all to white
			setBackgroundDefault(parent);
			Cursor cursor = (Cursor)parent.getItemAtPosition(position);
			
			NewDrinkDomain.getInstance().setHalfAmount(cursor.getString(cursor.getColumnIndexOrThrow(DataDAO.COL_FRACTION)));		
			v.setBackgroundColor(AppCommon.color);
			
			}};
					
	AdapterView.OnItemClickListener onMeasureItemListener = new OnItemClickListener(){
		
    	public void onItemClick(AdapterView<?> parent, View v, int position,long id) {

    		//set all to white
    		setBackgroundDefault(parent);
    		NewDrinkDomain.getInstance().setMeasurment((String)parent.getItemAtPosition(position));
    		
			v.setBackgroundColor(AppCommon.color);
			
			}};
}
