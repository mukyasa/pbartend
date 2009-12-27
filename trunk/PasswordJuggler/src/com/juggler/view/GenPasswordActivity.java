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

import android.graphics.Color;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.juggler.utils.Encrypt;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class GenPasswordActivity extends BaseActivity implements OnSeekBarChangeListener {
	private SeekBar seekbar;
	private TextView tvGenPassowrd,tvStrength,tvLength,tvTitle;
	private ToggleButton tbUseNumbers;//default yes
	private ToggleButton tbSpecialChar;//default yes
	private final int MIN_PROGRESS=4;
	private final int MAX_PROGRESS=24;
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
		   tvTitle = (TextView)findViewById(R.id.tvTitle);
		   tvTitle.setText(getString(R.string.genpasswords));
		   
		   seekbar = (SeekBar)findViewById(R.id.seekBar);
		   seekbar.setKeyProgressIncrement(1);
		   seekbar.setProgress(MIN_PROGRESS);
		   seekbar.setOnSeekBarChangeListener(this);
		   
		   tbUseNumbers = (ToggleButton)findViewById(R.id.tbUserNumbers);
		   tbSpecialChar = (ToggleButton)findViewById(R.id.tbSpecialChar);
		   
		   tvStrength = (TextView)findViewById(R.id.tvGenStrength);
		   tvStrength.setText(WEAK);
		   tvStrength.setTextColor(Color.rgb(204, 0, 0));
		   
		   tvLength = (TextView)findViewById(R.id.tvLength);
		   
		   tvGenPassowrd = (TextView)findViewById(R.id.tvGenPassword);
	        try {
	        	String newPassword = Encrypt.genPassword(MIN_PROGRESS,tbUseNumbers.isChecked(),tbSpecialChar.isChecked());
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
	        
	        if(progress < 5)
	        {
	        	strength = WEAK;
	        	tvStrength.setTextColor(Color.rgb(204, 0, 0));
	        }
	        else if(progress >=5 && progress <=8)
	        {
	        	strength = FAIR;
	        	tvStrength.setTextColor(Color.rgb(204, 102, 51));
	        }
	        else if(progress >=8 && progress <=12)
	        {
	        	strength = GOOD;
	        	tvStrength.setTextColor(Color.rgb(51, 102, 204));
	        }
	        else if(progress >=12 && progress <=19)
	        {
	        	strength = EXCELLENT;
	        	tvStrength.setTextColor(Color.rgb(51, 153, 51));
	        }
	        else if(progress >=19)
	        {
	        	strength = FANTASTIC;
	        	tvStrength.setTextColor(Color.rgb(51, 153, 51));
	        }
	        
	        tvStrength.setText(strength);
	        tvLength.setText(progress+"");
	        
	        String newPassword = Encrypt.genPassword(progress,tbUseNumbers.isChecked(),tbSpecialChar.isChecked());
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
