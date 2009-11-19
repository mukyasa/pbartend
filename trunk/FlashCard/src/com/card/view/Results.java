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
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.card.R;
 
/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class Results extends Activity {
	
	private long x;
	private long y;
	private int screenwidth;
	private int screenheight;
	private int adjHeight;
	private int r;
	
	 public void onCreate(Bundle savedInstanceState) {
		 //need to be full screen this needs to be called FIRST
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	                WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.results);
	        
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        DisplayMetrics metrics = new DisplayMetrics();
	        getWindowManager().getDefaultDisplay().getMetrics(metrics);
	        int width = metrics.widthPixels;
	        int height = metrics.heightPixels;
	        
	        this.screenwidth=width;
	        this.screenheight=180;
	        this.adjHeight=this.screenheight;
	        //for larger screens
	        if(height> 450)
	        	this.adjHeight=220;
	        
	        double dheight=height*.21;
	        double dwidth=width*.46;
	        
	        this.y =  Math.round(dheight);
	        this.x = Math.round(dwidth);
	        
	        double radius = .53;
	        
	        Double dr = (width * radius)/2;
	        
	        this.r = Math.round(dr.floatValue());
	        
	        initiaize();
	    }
	 
	 
	 private void initiaize()
	 {
		 LinearLayout main = (LinearLayout) findViewById(R.id.vResultsChart);
		 main.addView(new Chart(this,this.x,this.y,this.r,this.screenwidth,this.screenheight),this.screenwidth,this.adjHeight); 
		 
	 }
	 
}
