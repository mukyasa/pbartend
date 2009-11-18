/**
 * Date: Nov 17, 2009
 * Project: FlashCard
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.card.view;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.card.R;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class Results extends Activity {
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.results);
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        initiaize();
	    }
	 
	 
	 private void initiaize()
	 {
		 LinearLayout main = (LinearLayout) findViewById(R.id.resultsLayout);
		    main.addView(new Chart(this,50,50,25));
		 
	 }
	 
}
