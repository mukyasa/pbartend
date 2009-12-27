/**
 * Date: Dec 26, 2009
 * Project: PasswordJuggler
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.juggler.view;

import com.juggler.utils.Encrypt;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class GenPasswordActivity extends Activity implements OnSeekBarChangeListener {
	private SeekBar seekbar;
	private TextView tvGenPassowrd,tvStrength;
	private final int MIN_PROGRESS=1;
	private final int MAX_PROGRESS=13;
	private final String WEAK = "Weak";
	private final String FAIR = "Fair";
	private final String GOOD = "Good";
	private final String EXCELLENT = "Excellent";
	private final String FANTASTIC = "Fantastic";
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        setContentView(R.layout.genpassword_frame);
	        super.onCreate(savedInstanceState);
	        
	        
	        initialize();
	    }
	   
	   
	   private void initialize(){
		   seekbar = (SeekBar)findViewById(R.id.seekBar);
		   seekbar.setKeyProgressIncrement(10);
		   seekbar.setProgress(MIN_PROGRESS);
		   seekbar.setOnSeekBarChangeListener(this);
		   
		   tvStrength = (TextView)findViewById(R.id.tvGenStrength);
		   tvStrength.setText(WEAK);
		   tvStrength.setTextColor(Color.rgb(204, 0, 0));
		   
		   tvGenPassowrd = (TextView)findViewById(R.id.tvGenPassword);
	        try {
	        	String newPassword = Encrypt.genPassword(MIN_PROGRESS);
		        tvGenPassowrd.setText(newPassword);
	        } catch (Exception e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
	        }
	   }


	/* (non-Javadoc)
     * @see android.widget.SeekBar.OnSeekBarChangeListener#onProgressChanged(android.widget.SeekBar, int, boolean)
     */
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
	    try {
	    	
	    	if(progress<=MIN_PROGRESS)
	    	{
	        	seekbar.setProgress(MIN_PROGRESS);
	        	progress = MIN_PROGRESS;
	    	}
	        
	        if(progress>=MAX_PROGRESS)
	        {
	        	seekbar.setProgress(MAX_PROGRESS);
	        	progress = MAX_PROGRESS;
	        }
	        
	        String strength=WEAK;
	        
	        if(progress < 3)
	        {
	        	strength = WEAK;
	        	tvStrength.setTextColor(Color.rgb(204, 0, 0));
	        }
	        else if(progress >=3 && progress <=5)
	        {
	        	strength = FAIR;
	        	tvStrength.setTextColor(Color.rgb(204, 102, 51));
	        }
	        else if(progress >=5 && progress <=8)
	        {
	        	strength = GOOD;
	        	tvStrength.setTextColor(Color.rgb(51, 102, 204));
	        }
	        else if(progress >=8 && progress <=10)
	        {
	        	strength = EXCELLENT;
	        	tvStrength.setTextColor(Color.rgb(51, 153, 51));
	        }
	        else if(progress >=10)
	        {
	        	strength = FANTASTIC;
	        	tvStrength.setTextColor(Color.rgb(51, 153, 51));
	        }
	        
	        tvStrength.setText(strength);
	        
	        
	        String newPassword = Encrypt.genPassword(progress);
	        tvGenPassowrd.setText(newPassword);
	        
	        
        } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	    
    }


	/* (non-Javadoc)
     * @see android.widget.SeekBar.OnSeekBarChangeListener#onStartTrackingTouch(android.widget.SeekBar)
     */
    public void onStartTrackingTouch(SeekBar seekBar) {
	    // TODO Auto-generated method stub
	    
    }


	/* (non-Javadoc)
     * @see android.widget.SeekBar.OnSeekBarChangeListener#onStopTrackingTouch(android.widget.SeekBar)
     */
    public void onStopTrackingTouch(SeekBar seekBar) {
	    // TODO Auto-generated method stub
	    
    }
}
