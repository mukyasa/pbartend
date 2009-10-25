package com.bartender.view;

import java.util.ArrayList;
import java.util.List;

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

    		
			if(v instanceof ImageView)
			{
				Drawable glassType = (Drawable) parent.getItemAtPosition(position);
				
				NewDrinkDomain ndd = NewDrinkDomain.getInstance();
				ndd.glassId=id;
				ndd.glassType=glassType;
		    	
			}
			else
			{
				Cursor cursor = (Cursor) parent.getItemAtPosition(position);
		    	//set id and name to create domain
		    	NewDrinkDomain ndd = NewDrinkDomain.getInstance();
		    	//cat info
		    	ndd.categoryId=id;
		    	ndd.categoryName = (cursor.getString(cursor.getColumnIndexOrThrow(DataDAO.COL_NAME)));
			}
			
			}};

}