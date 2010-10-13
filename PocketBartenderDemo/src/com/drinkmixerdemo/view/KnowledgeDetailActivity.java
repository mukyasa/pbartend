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

import com.drinkmixerdemo.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.drinkmixerdemo.domain.LearnBartender;
import com.drinkmixerdemo.domain.NewDrinkDomain;
import com.drinkmixerdemo.domain.ScreenType;
import com.drinkmixerdemo.utils.FileParser;

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
		 
		CharSequence key = getIntent().getCharSequenceExtra(FileParser.BE_A_BARTENDER_DETAILS);
		LearnBartender lb = LearnBartender.getInstance();
		String details_str = (String)lb.lesson.get(key);
		
		ImageView glass = (ImageView)findViewById(R.id.imgGlassType);
		FileParser.getGlassImage(glass, key.toString());
		
		TextView title = (TextView)findViewById(R.id.tvDrinkName);
		title.setText(key);
		
		TextView details = (TextView)findViewById(R.id.tvDetails);
		details.setText(details_str);
		
		
	}
}
