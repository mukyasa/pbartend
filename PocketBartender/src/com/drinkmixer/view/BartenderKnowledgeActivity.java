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

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.drinkmixer.R;
import com.drinkmixer.domain.LearnBartender;
import com.drinkmixer.domain.NewDrinkDomain;
import com.drinkmixer.domain.ScreenType;
import com.drinkmixer.utils.FileParser;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class BartenderKnowledgeActivity extends BaseActivity implements OnClickListener {
	
	Button btnGlasses,btnTech,btnTerms,btnStock;
	 
	 @Override
	    public void onCreate(Bundle savedInstanceState) { 
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.learn_bartender);
	        
	        initialize();
	 }
	 
	 public boolean onCreateOptionsMenu(Menu menu) {
			menu.add(0, 0, 0, "Home").setIcon(R.drawable.home);
		    return true;
		}

		/* Handles item selections */
		public boolean onOptionsItemSelected(MenuItem item) {

			 switch (item.getItemId()) {
				 case 0:
					 	NewDrinkDomain.getInstance().clearDomain();
						ScreenType.getInstance().screenType= -1;
						startActivity(new Intent(this, HomeScreenView.class));
				    	return true;
			 }
			 return false;

		}
	 private void initialize(){
		 
		 btnTech = (Button)findViewById(R.id.btnTech);
		 btnTech.setOnClickListener(this);
		 btnTerms = (Button)findViewById(R.id.btnTerms);
		 btnTerms.setOnClickListener(this);
		 btnStock = (Button)findViewById(R.id.btnStock);
		 btnStock.setVisibility(View.GONE);
		 btnStock.setOnClickListener(this);
		 btnGlasses = (Button)findViewById(R.id.btnGlasses);
		 btnGlasses.setOnClickListener(this);
		 
	 }

	/* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v) {
	   
    	Intent intent = new Intent(this,KnowledgeListActivity.class);
    	LearnBartender.getInstance().clear();
    	if(v == btnGlasses)
    		intent.putExtra(FileParser.BE_A_BARTENDER, FileParser.GLASSES);
    	else if(v == btnStock)
    		intent.putExtra(FileParser.BE_A_BARTENDER, FileParser.BARSTOCK);
    	else if(v == btnTech)
    		intent.putExtra(FileParser.BE_A_BARTENDER, FileParser.BASIC_TEQ);
    	else if(v == btnTerms)
    		intent.putExtra(FileParser.BE_A_BARTENDER, FileParser.TERMINOLOGY);
    	
    	startActivity(intent);
	    
    }
}
