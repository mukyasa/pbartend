package com.bartender.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

import com.bartender.R;
import com.bartender.common.AppCommon;
import com.bartender.dao.CategoryDAO;
import com.bartender.dao.DataDAO;
import com.bartender.dao.DatabaseAdapter;
import com.bartender.dao.DrinkListDAO;
import com.bartender.domain.NewDrinkDomain;

public class CategoryAndGlassListView extends Activity {
	
	private CategoryDAO dataDAO = new CategoryDAO();
	protected DatabaseAdapter myDatabaseAdapter;
	private ListView listCategories,listGlasses; 
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);
        myDatabaseAdapter = DatabaseAdapter.getInstance(this);
        setContentView(com.bartender.R.layout.cat_glass);
        initComponents();
    }
   
    
    private void initComponents() {

        //instantiate the objects
    	listCategories = (ListView) findViewById(R.id.listCategories);
        listGlasses = (ListView)findViewById(R.id.listGlasses);
        
        //addes images to list items
        List<Drawable> imageList = new ArrayList<Drawable>(); 
        
        imageList.add(getResources().getDrawable(R.drawable.champ));
        imageList.add(getResources().getDrawable(R.drawable.cocktail));
        imageList.add(getResources().getDrawable(R.drawable.highball));
        imageList.add(getResources().getDrawable(R.drawable.hurricane));
        imageList.add(getResources().getDrawable(R.drawable.irish));
        imageList.add(getResources().getDrawable(R.drawable.margarita));
        imageList.add(getResources().getDrawable(R.drawable.mug));
        imageList.add(getResources().getDrawable(R.drawable.parfait));
        imageList.add(getResources().getDrawable(R.drawable.pilsner));
        imageList.add(getResources().getDrawable(R.drawable.pint));
        imageList.add(getResources().getDrawable(R.drawable.pousse_cafe));
        imageList.add(getResources().getDrawable(R.drawable.punch));
        imageList.add(getResources().getDrawable(R.drawable.rocks));
        imageList.add(getResources().getDrawable(R.drawable.shot));
        imageList.add(getResources().getDrawable(R.drawable.snifter));
        imageList.add(getResources().getDrawable(R.drawable.sour));
        imageList.add(getResources().getDrawable(R.drawable.wine));

        
       
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
		
    	public void onItemClick(AdapterView<?> parent, View v, int position,long id) {

    		Log.v(getClass().getSimpleName(), "class name=" + v.getClass().toString());
    		//set all to white
    		for(int i=0;i<parent.getCount();i++)
			{
				View vv = parent.getChildAt(i);
				if(vv != null)
					vv.setBackgroundColor(AppCommon.defaultColor);
			}
    		
			if(v instanceof ImageView)
			{
		    	NewDrinkDomain.getInstance().setGlassId(id);
			}
			else
			{
				Cursor cursor = (Cursor) parent.getItemAtPosition(position);
		    	//set id and name to create domain
		    	NewDrinkDomain ndd = NewDrinkDomain.getInstance();
		    	//cat info
		    	ndd.setCategoryId(id);
		    	ndd.setCategoryName(cursor.getString(cursor.getColumnIndexOrThrow(DataDAO.COL_NAME)));
			}
			
			v.setBackgroundColor(AppCommon.color);
			
			}};


}