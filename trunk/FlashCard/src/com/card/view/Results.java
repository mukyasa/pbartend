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
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.card.R;
import com.card.domain.ResultsBean;
 
/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class Results extends Activity implements OnTouchListener {
	
	private long x;
	private long y;
	private int screenwidth;
	private int screenheight;
	private int adjHeight;
	private int r;
	private ResultsBean rbean;
	private final int BASE_SCREEN_HEIGHT=180;
	private final int LARGE_SCREEN_HEIGHT=450;
	private final double BASE_WIDTH_PERCENT=.46;
	private final double BASE_HEIGHT_PERCENT=.21;
	private final double BASE_RADIUS_PERCENT=.53;
	private final int MENU_NEW=0;
	
	
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
	        this.screenheight=BASE_SCREEN_HEIGHT;
	        this.adjHeight=this.screenheight;
	        //for larger screens
	        if(height> LARGE_SCREEN_HEIGHT)
	        	this.adjHeight=220;
	        
	        double dheight=height*BASE_HEIGHT_PERCENT;
	        double dwidth=width*BASE_WIDTH_PERCENT;
	        
	        this.y =  Math.round(dheight);
	        this.x = Math.round(dwidth);
	        
	        double radius = BASE_RADIUS_PERCENT;
	        
	        Double dr = (width * radius)/2;
	        
	        //set radius
	        this.r = Math.round(dr.floatValue());
	        //result bean to be populated
	        rbean = new ResultsBean();
	        //get the chart
	        initiaize();
	        
	        //set values
	        TextView total = (TextView)findViewById(R.id.tvToBeStudied);
	        total.setText("Cards to be studied:\t\t\t\t\t\t"+Math.round(rbean.totalCards));
	        
	        TextView seen =(TextView)findViewById(R.id.tvSeen);
	        seen.setText("Cards seen:\t\t\t\t\t\t\t\t\t"+rbean.countseen);
	        
	        TextView correct = (TextView)findViewById(R.id.tvCorrect);
	        correct.setText("Correct:\t\t\t\t\t\t\t\t\t\t" + Math.round(rbean.correctcardcount));
	        
	        TextView wrong = (TextView)findViewById(R.id.tvIncorrect);
	        wrong.setText("Incorrect:\t\t\t\t\t\t\t\t\t"+Math.round(rbean.wrongcardcount));
	        
	        
	    }
	 
	 
	 private void initiaize()
	 {
		 LinearLayout main = (LinearLayout) findViewById(R.id.vResultsChart);
		 main.addView(new Chart(this,this.x,this.y,this.r,this.screenwidth,this.screenheight,rbean),this.screenwidth,this.adjHeight);
		 
		 
		 Button retrest = (Button)findViewById(R.id.btRetest);
		 retrest.setOnTouchListener(this);		 
		 
	 }

	    public boolean onCreateOptionsMenu(Menu menu) {
	    	menu.add(0, MENU_NEW, 0, "New Card Set").setIcon(R.drawable.newcardset);
		    return true;
		}

		/* Handles item selections */
		public boolean onOptionsItemSelected(MenuItem item) {
			Intent intent;
		    switch (item.getItemId()) {
		    case MENU_NEW:
		    	intent = new Intent(this, Home.class);
				startActivity(intent);
		    	return true;
		    }
		    return false;
		}

	/* (non-Javadoc)
     * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
     */
    public boolean onTouch(View v, MotionEvent event) {
    	
    	if(v instanceof Button )
	  	{
    		
	  		if(event.getAction() == MotionEvent.ACTION_DOWN)
	  		{
	  			v.setBackgroundResource(R.drawable.button_hvr);
	  			v.setPadding(30, 0, 40, 5);
	  		}
	  		
	  		else if(event.getAction()== MotionEvent.ACTION_UP)
	  		{
	  			v.setBackgroundResource(R.drawable.button);
	  			v.setPadding(30, 0, 40, 5);
	  			//reconfig for retest
	  		}
	  	}
    	
	    return false;
    }
	 
}
