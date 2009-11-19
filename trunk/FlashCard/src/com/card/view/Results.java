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
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

import com.card.R;
 
/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class Results extends Activity {
	
	private long screenwidth;
	private long screenheight;
	private int r;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.results);
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        
	        DisplayMetrics metrics = new DisplayMetrics();
	        getWindowManager().getDefaultDisplay().getMetrics(metrics);
	        int width = metrics.widthPixels;
	        int height = metrics.heightPixels;
	        
	        double dheight=height*.55;
	        double dwidth=width*.46;
	        
	        this.screenheight =  Math.round(dheight);
	        this.screenwidth = Math.round(dwidth);
	        
	        Double dr = (width * .56)/2;
	        
	        this.r = Math.round(dr.floatValue());
	        
	        initiaize();
	    }
	 
	 
	 private void initiaize()
	 {
		 LinearLayout main = (LinearLayout) findViewById(R.id.vResultsChart);
		 main.addView(new Chart(this,this.screenwidth,this.screenheight,this.r),150,150);
		 
	 }
	 
}
