/**
 * Date: Jan 14, 2010
 * Project: PocketBartender
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.drinkmixer.view;

import java.io.InputStream;
import java.util.Set;
import java.util.TreeMap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.drinkmixer.R;
import com.drinkmixer.domain.LearnBartender;
import com.drinkmixer.domain.NewDrinkDomain;
import com.drinkmixer.domain.ScreenType;
import com.drinkmixer.utils.FileParser;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class KnowledgeDetailActivity extends ListActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        //progress menu bar
        setContentView(R.layout.list_frame);
        
        
		 CharSequence key = getIntent().getCharSequenceExtra(FileParser.BE_A_BARTENDER_DETAILS); 
		 
		
		 
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
		
	
	private void initialize(){

		
		/* <TextView android:id="@+id/TextView01" android:layout_width="wrap_content"
				android:layout_height="wrap_content" android:textColor="#000"
				android:text="Label" android:textStyle="bold"></TextView>
			<TextView android:id="@+id/TextView01" android:layout_width="wrap_content"
				android:layout_height="wrap_content" android:textColor="#666"
				android:text="Details"
				android:layout_marginBottom="10sp"></TextView>
		 
		TableLayout detailstable = (TableLayout)findViewById(R.id.tlDetails);
		
		Iterator<String> iter = hashtable.keySet().iterator();
		
		while (iter.hasNext()) {
	        String key = (String) iter.next();
	        String details = hashtable.get(key);
	        
	        TextView tvLabel = new TextView(this);
			tvLabel.setText(key);
			tvLabel.setTextColor(Color.BLACK);
			tvLabel.setTypeface(Typeface.DEFAULT_BOLD);
			
			detailstable.addView(tvLabel);
			
			TextView tvDetails = new TextView(this);
			tvDetails.setText(details);
			tvDetails.setTextColor(Color.rgb(102, 102, 102));
			LayoutParams params = new LayoutParams();
			params.bottomMargin=10;
			tvDetails.setLayoutParams(params);
			
			detailstable.addView(tvDetails);
        }
		*/
	}
}
