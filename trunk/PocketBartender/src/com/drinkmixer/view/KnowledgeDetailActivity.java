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
import java.util.Iterator;
import java.util.TreeMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.SingleLineTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

import com.drinkmixer.R;
import com.drinkmixer.domain.LearnBartender;
import com.drinkmixer.domain.NewDrinkDomain;
import com.drinkmixer.domain.ScreenType;
import com.drinkmixer.utils.FileParser;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class KnowledgeDetailActivity extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        //progress menu bar
        setContentView(R.layout.learn_bartender_detail);
        
        
		 int key = getIntent().getIntExtra(FileParser.BE_A_BARTENDER, -1); 
		 
		 InputStream in=null;
		 
		 switch (key) {
	        case FileParser.TERMINOLOGY://terminology
	   		 in = getResources().openRawResource(R.raw.terminology);
	   		 break;
	        case FileParser.GLASSES: //glass info
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
	
	private void initialize(){
		
		LearnBartender lbartend = LearnBartender.getInstance();
		TreeMap<String, String> hashtable = lbartend.lesson;
		
		/* <TextView android:id="@+id/TextView01" android:layout_width="wrap_content"
				android:layout_height="wrap_content" android:textColor="#000"
				android:text="Label" android:textStyle="bold"></TextView>
			<TextView android:id="@+id/TextView01" android:layout_width="wrap_content"
				android:layout_height="wrap_content" android:textColor="#666"
				android:text="Details"
				android:layout_marginBottom="10sp"></TextView>
		 */
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
		
	}
}
