/**
 * Date: Jan 14, 2010
 * Project: PocketBartender
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.drinkmixerdemo.view;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

import com.drinkmixerdemo.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.drinkmixerdemo.domain.LearnBartender;
import com.drinkmixerdemo.domain.NewDrinkDomain;
import com.drinkmixerdemo.domain.ScreenType;
import com.drinkmixerdemo.utils.FileParser;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class KnowledgeListActivity extends ListActivity implements TextWatcher {
	
	private boolean isGlasses = false;
	protected EditText searchbox;
	@Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        //progress menu bar
        setContentView(R.layout.list_frame);
        
        
		 int key = getIntent().getIntExtra(FileParser.BE_A_BARTENDER, -1); 
		 
		 InputStream in=null;
		 
		 switch (key) {
	        case FileParser.TERMINOLOGY://terminology
	   		 in = getResources().openRawResource(R.raw.terminology);
	   		 break;
	        case FileParser.GLASSES: //glass info
	        	isGlasses=true;
	        	in = getResources().openRawResource(R.raw.glasses);
	        	break;
	        case FileParser.BASIC_TEQ://techniques
			 in = getResources().openRawResource(R.raw.basictechniques);
			 break;
	        case FileParser.BARSTOCK: //bar stock
			 in = getResources().openRawResource(R.raw.barstock);
			 break;
       }
		 
		 FileParser.loadBarStock(in);
		 
		 initialize();
	}
	 public boolean onCreateOptionsMenu(Menu menu) {
			menu.add(0, 0, 0, "Go Back").setIcon(android.R.drawable.ic_menu_directions);
		    return true;
		}

		/* Handles item selections */
		public boolean onOptionsItemSelected(MenuItem item) {

			 switch (item.getItemId()) {
				 case 0:
					 	NewDrinkDomain.getInstance().clearDomain();
						ScreenType.getInstance().screenType= -1;
						startActivity(new Intent(this, BartenderKnowledgeActivity.class));
				    	return true;
			 }
			 return false;

		}
	
		
		/* (non-Javadoc)
		 * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
		 */
		@Override
		protected void onListItemClick(ListView l, View v, int position, long id) {
			
			String text = ((TextView)v).getText().toString();	
			Intent intent = new Intent(this,KnowledgeDetailActivity.class);
			intent.putExtra(FileParser.BE_A_BARTENDER_DETAILS, text);
			
			startActivity(intent);
			
		    super.onListItemClick(l, v, position, id);
		}
	
		private void initialize(){
		
		LearnBartender lbartend = LearnBartender.getInstance();
		TreeMap<String, String> hashtable = lbartend.lesson;
		Set<String> set = hashtable.keySet();
		String[] titles = set.toArray(new String[set.size()]); 
		searchbox = (EditText)findViewById(R.id.etSearch);
		searchbox.addTextChangedListener(this);
		
		if(isGlasses)
			setListAdapter(new ImageAndTextListAdapter(this,R.layout.item_row,titles));
		else
			setListAdapter(new ArrayAdapter<String>(this,R.layout.textviewrow,titles));
		getListView().setTextFilterEnabled(true);

	}
		/* (non-Javadoc)
         * @see android.text.TextWatcher#afterTextChanged(android.text.Editable)
         */
        public void afterTextChanged(Editable s) {
	        // TODO Auto-generated method stub
	        
        }
		/* (non-Javadoc)
         * @see android.text.TextWatcher#beforeTextChanged(java.lang.CharSequence, int, int, int)
         */
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	        // TODO Auto-generated method stub
	        
        }
		/* (non-Javadoc)
         * @see android.text.TextWatcher#onTextChanged(java.lang.CharSequence, int, int, int)
         */
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        	Editable et = searchbox.getText();
        	String letters =et.toString().trim();
    	
        	LearnBartender lbartend = LearnBartender.getInstance();
    		TreeMap<String, String> hashtable = lbartend.lesson;
        	Set<String> set = hashtable.keySet();
    		String[] titles = set.toArray(new String[set.size()]);
    		ArrayList<String> newTitles = new ArrayList<String>();
    		
    		for (int i = 0; i < titles.length; i++) {
    			String key = titles[i];
    			
    			if(key.toLowerCase().startsWith(letters.toLowerCase()))
    				newTitles.add(key);
			
            }
    		
    		titles = newTitles.toArray(new String[newTitles.size()]);
        	
    		if(isGlasses)
				setListAdapter(new ImageAndTextListAdapter(this,R.layout.item_row,titles));
			else
				setListAdapter(new ArrayAdapter<String>(this,R.layout.textviewrow,titles));
			getListView().setTextFilterEnabled(true);
        }
}
