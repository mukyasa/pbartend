package com.drinkmixerdemo.view;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.drinkmixerdemo.R;
import com.drinkmixerdemo.dao.DataDAO;
import com.drinkmixerdemo.dao.DrinkListDAO;
import com.drinkmixerdemo.dao.IngredientsDAO;
import com.drinkmixerdemo.dao.MixerDbHelper;
import com.drinkmixerdemo.domain.NewDrinkDomain;
import com.drinkmixerdemo.domain.ScreenType;

public class AmountPicker extends BaseActivity  implements OnClickListener{
	
	 private ListView listWholeNums,listHalfNums,measurementtypes;
	 private final String none ="---";
	 private Button btnSave,btnCancel;
	 private Intent intent;
	 protected MixerDbHelper myDatabaseAdapter;
	 private IngredientsDAO dataDAO = new IngredientsDAO();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        myDatabaseAdapter = MixerDbHelper.getInstance(this);
        setContentView(R.layout.amounts);
        initComponents();
        		
    }

    @Override
    protected void onResume() {
    	 ScreenType.getInstance().screenType=(ScreenType.SCREEN_TYPE_NEW);
    	super.onResume();
    }
    
    protected void initComponents() 
    {

    	 //set event handlers to buttons
        btnSave = (Button) findViewById(R.id.btnAmountSave);
    	btnSave.setOnClickListener(this);
    	btnSave.setOnTouchListener(this);
		
		btnCancel = (Button) findViewById(R.id.btnAmountCancel);
		btnCancel.setOnClickListener(this);
		btnCancel.setOnTouchListener(this);
		
        listWholeNums = (ListView) findViewById(R.id.listWholeNums);
        listHalfNums = (ListView) findViewById(R.id.listHalfNums); 
        measurementtypes = (ListView)findViewById(R.id.listAmounts);
        
        //set click listeners
        listHalfNums.setOnItemClickListener(onHalfItemListener);
        listWholeNums.setOnItemClickListener(onWholeItemListener);
        measurementtypes.setOnItemClickListener(onMeasureItemListener);
        
        //set touch
        listHalfNums.setOnTouchListener(this);
        listWholeNums.setOnTouchListener(this);
        measurementtypes.setOnTouchListener(this);
        
        //fractions
        dataDAO.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
    	Cursor recordscCursor = dataDAO.retrieveAllMeasurements();
    	startManagingCursor(recordscCursor);
    	String[] from = new String[] { DrinkListDAO.COL_FRACTION};
		int[] to = new int[] { R.id.tvItem};
    	SimpleCursorAdapter records = new SimpleCursorAdapter(this,
				R.layout.textviewrow, recordscCursor, from, to);
    	

    	//whole numbers to add
        List<String> tmp = new ArrayList<String>();
        tmp.add(none);
        for(int i=1;i<241;i++)
        	tmp.add(i+"");
        
        listHalfNums.setAdapter(records);
        listWholeNums.setAdapter(new ArrayAdapter<String>(this,R.layout.textviewrow, tmp));
        measurementtypes.setAdapter(new ArrayAdapter<String>(this,R.layout.textviewrow, AMOUNTS));
        
        
        NewDrinkDomain.getInstance().clearIngredients();
        
    }
    

    
    public void onClick(View v) {

    	if(v==btnSave)
		{
			intent = new Intent(this, CreateUpdateView.class);
			NewDrinkDomain ndd = NewDrinkDomain.getInstance();
			ndd.addIngredients((ndd.wholeAmount != null?ndd.wholeAmount : "")
					+ " "+(ndd.halfAmount != null ? ndd.halfAmount:"") + " " 
					+ (ndd.measurment != null ?ndd.measurment:"") + ", " + ndd.ingredientsName);
			startActivity(intent);
		}
		else if(v==btnCancel)
		{
			intent = new Intent(this, CreateUpdateView.class);
			NewDrinkDomain.getInstance().clearIngredients();
			startActivity(intent);
		}
    	
	}
    
    final String[] AMOUNTS = new String[] {none,"cup","oz","pint","quart","gallon","tsp","tbsp","lb","bottle(s)"
    	,"can(s)"
    	,"dash(es)"
    	,"drop(s)"
    	,"gill"
    	,"jigger"
    	,"ml"
    	,"part(s)"
    	,"piece(s)"
    	,"pony"
    	,"scoop(s)"
    	,"splash(es)"
    	,"sprig(s)"
    	,"stick(s)"};
    
    
    
    AdapterView.OnItemClickListener onWholeItemListener = new OnItemClickListener(){
		
    	public void onItemClick(AdapterView<?> parent, View v, int position,long id) {

    		
    		NewDrinkDomain.getInstance().wholeAmount =((String)parent.getItemAtPosition(position));
    		TextView tv = (TextView)findViewById(R.id.tvWhole);
    		tv.setText((String)parent.getItemAtPosition(position) + " ");
    		
    		//remove if none
    		if(none.equals((String)parent.getItemAtPosition(position)))
    		{
    			tv.setText(null);
    			NewDrinkDomain.getInstance().wholeAmount=null;
    		}
    		
			}};
			
	AdapterView.OnItemClickListener onHalfItemListener = new OnItemClickListener(){
		
		public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
	
			Cursor cursor = (Cursor)parent.getItemAtPosition(position);
			
			NewDrinkDomain.getInstance().halfAmount=(cursor.getString(cursor.getColumnIndexOrThrow(DataDAO.COL_FRACTION)));		
			TextView tv = (TextView)findViewById(R.id.tvHalf);
    		tv.setText(cursor.getString(cursor.getColumnIndexOrThrow(DataDAO.COL_FRACTION)) + " ");
    		
    		//remove if none
    		if(none.equals(cursor.getString(cursor.getColumnIndexOrThrow(DataDAO.COL_FRACTION))))
    		{
    			tv.setText(null);
    			NewDrinkDomain.getInstance().halfAmount=null;
    		}
    		
			}};
					
	AdapterView.OnItemClickListener onMeasureItemListener = new OnItemClickListener(){
		
    	public void onItemClick(AdapterView<?> parent, View v, int position,long id) {

    		NewDrinkDomain.getInstance().measurment=((String)parent.getItemAtPosition(position));
    		TextView tv = (TextView)findViewById(R.id.tvMeasurement);
    		tv.setText((String)parent.getItemAtPosition(position));
    		
    		//remove if none
    		if(none.equals((String)parent.getItemAtPosition(position)))
    		{
    			tv.setText(null);
    			NewDrinkDomain.getInstance().measurment=null;
    		}
    		
    		
			}};
}
