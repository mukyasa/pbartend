package com.bartender.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.bartender.R;
import com.bartender.dao.CategoryDAO;
import com.bartender.dao.DataDAO;
import com.bartender.dao.DatabaseAdapter;
import com.bartender.dao.DrinkListDAO;
import com.bartender.domain.NewDrinkDomain;

public class CategoryAndGlassPicker extends Activity implements OnClickListener {
	
	private CategoryDAO dataDAO = new CategoryDAO();
	protected DatabaseAdapter myDatabaseAdapter;
	private ListView listCategories,listGlasses; 
	private Button btnSave,btnCancel;
	private Intent intent;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);
        myDatabaseAdapter = DatabaseAdapter.getInstance(this);
        setContentView(com.bartender.R.layout.cat_glass);
        initComponents();
    }
   
    public void onClick(View v) {

    	if(v==btnSave)
		{
			intent = new Intent(this, CreateUpdateView.class);
			startActivity(intent);
		}
		else if(v==btnCancel)
		{
			intent = new Intent(this, CreateUpdateView.class);
			startActivity(intent);
		}
    	
	}
    
    private void initComponents() {

    	btnSave = (Button) findViewById(R.id.btnSave);
    	btnSave.setOnClickListener(this);
		
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);
		
        //instantiate the objects
    	listCategories = (ListView) findViewById(R.id.listCategories);
        listGlasses = (ListView)findViewById(R.id.listGlasses);
        
        HashMap<Integer, Drawable> map = new HashMap<Integer, Drawable>();

        List<HashMap<Integer, Drawable>> imageList = new ArrayList<HashMap<Integer, Drawable>>();
        
        map.put(1, getResources().getDrawable(R.drawable.rocks));
        imageList.add(map);
        map = new HashMap<Integer, Drawable>();
        map.put(2, getResources().getDrawable(R.drawable.highball));
        imageList.add(map);
        map = new HashMap<Integer, Drawable>();
        map.put(3, getResources().getDrawable(R.drawable.mug));
        imageList.add(map);
        map = new HashMap<Integer, Drawable>();
        map.put(4, getResources().getDrawable(R.drawable.hurricane));
        imageList.add(map);
        map = new HashMap<Integer, Drawable>();
        map.put(5, getResources().getDrawable(R.drawable.pint));
        imageList.add(map);
        map = new HashMap<Integer, Drawable>();
        map.put(6, getResources().getDrawable(R.drawable.whitewine));
        imageList.add(map);
        map = new HashMap<Integer, Drawable>();
        map.put(7, getResources().getDrawable(R.drawable.redwine));
        imageList.add(map);
        map = new HashMap<Integer, Drawable>();
        map.put(8, getResources().getDrawable(R.drawable.cocktail));
        imageList.add(map);
        map = new HashMap<Integer, Drawable>();
        map.put(10, getResources().getDrawable(R.drawable.champ));
        imageList.add(map);
        map = new HashMap<Integer, Drawable>();
        map.put(12, getResources().getDrawable(R.drawable.margarita));
        imageList.add(map);
        map = new HashMap<Integer, Drawable>();
        map.put(13, getResources().getDrawable(R.drawable.shot));
        imageList.add(map);
        map = new HashMap<Integer, Drawable>();
        map.put(14, getResources().getDrawable(R.drawable.sour));
        imageList.add(map);
        map = new HashMap<Integer, Drawable>();
        map.put(15, getResources().getDrawable(R.drawable.pousse_cafe));
        imageList.add(map);
        map = new HashMap<Integer, Drawable>();
        map.put(16, getResources().getDrawable(R.drawable.parfait));
        imageList.add(map);
        map = new HashMap<Integer, Drawable>();
        map.put(17, getResources().getDrawable(R.drawable.irish));
        imageList.add(map);
        map = new HashMap<Integer, Drawable>();
        map.put(18, getResources().getDrawable(R.drawable.pilsner));
        imageList.add(map);
        map = new HashMap<Integer, Drawable>();
        map.put(19, getResources().getDrawable(R.drawable.punch));
        imageList.add(map);
        map = new HashMap<Integer, Drawable>();
        map.put(20, getResources().getDrawable(R.drawable.snifter));
        imageList.add(map);
        
        
       
    	dataDAO.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
    	Cursor recordscCursor = dataDAO.retrieveAllDrinktypes();
    	startManagingCursor(recordscCursor);
    	String[] from = new String[] { DrinkListDAO.COL_NAME };
		int[] to = new int[] { R.id.tfName};
    	SimpleCursorAdapter records = new SimpleCursorAdapter(this,
				R.layout.item_row, recordscCursor, from, to);
    	
    	listCategories.setAdapter(records);
    	listGlasses.setAdapter(new ImageListAdapter(this,imageList,listGlasses));
        
    	//set event listeners
 		listGlasses.setOnItemClickListener(onItemListener);
 		listCategories.setOnItemClickListener(onItemListener);
	}
    
    
    //inner class
    AdapterView.OnItemClickListener onItemListener = new OnItemClickListener(){
		
    	@SuppressWarnings("unchecked")
        public void onItemClick(AdapterView<?> parent, View v, int position,long id) {

			if(v instanceof ImageView)
			{
				 HashMap<Integer, Drawable> map = (HashMap<Integer, Drawable>)parent.getItemAtPosition(position);
			        Iterator<Integer> iter = map.keySet().iterator();
			        
			        Drawable glassType=null;
			        while (iter.hasNext()) {
				        Integer key = (Integer) iter.next();
				        glassType = (Drawable)map.get(key);
				        id = key;
			        }
			        
				NewDrinkDomain ndd = NewDrinkDomain.getInstance();
				ndd.glassId=id;
				ndd.glassType=glassType;
				Cursor recordscCursor = dataDAO.retrieveGlassNameById(id+"");
				
				recordscCursor.moveToFirst();
				
				TextView tv = (TextView)findViewById(R.id.tvGlassSelect);
				tv.setText(recordscCursor.getString(recordscCursor.getColumnIndex(DataDAO.COL_GLASS_NAME)));
		    	
			}
			else
			{
				Cursor cursor = (Cursor) parent.getItemAtPosition(position);
		    	//set id and name to create domain
		    	NewDrinkDomain ndd = NewDrinkDomain.getInstance();
		    	//cat info
		    	ndd.categoryId=id;
		    	ndd.categoryName = (cursor.getString(cursor.getColumnIndexOrThrow(DataDAO.COL_NAME)));
		    	TextView tv = (TextView)findViewById(R.id.tvCatSelect);
	    		tv.setText(cursor.getString(cursor.getColumnIndexOrThrow(DataDAO.COL_NAME)));
			}
			
			}};

}