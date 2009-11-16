/**
 * Date: Nov 16, 2009
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
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ViewFlipper;

import com.card.R;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class FlashCardTest extends Activity {
	
	@SuppressWarnings("unchecked")
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.card_test_view);  
		//force to be landscape
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		
        // Add a few countries to the spinner
        Spinner spinnerCountries = (Spinner) findViewById(R.id.spinner_country);
        ArrayAdapter countryArrayAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_spinner_dropdown_item,
                    new String[] { "Canada", "USA" });
        spinnerCountries.setAdapter(countryArrayAdapter);

        // Set the listener for Button_Next, a quick and dirty way to create a listener
        Button buttonNext = (Button) findViewById(R.id.Button_next);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Get the ViewFlipper from the layout
                ViewFlipper vf = (ViewFlipper) findViewById(R.id.details);

                // Set an animation from res/anim: 
                vf.setAnimation(AnimationUtils.loadAnimation(view.getContext(),  R.anim.slide_left));
                vf.showNext();
        }
        });

        // Set the listener for Button_Previous, a quick and dirty way to create a listener
        Button buttonPrevious = (Button) findViewById(R.id.Button_previous);
        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Get the ViewFlipper from the layout
                ViewFlipper vf = (ViewFlipper) findViewById(R.id.details);
                // Set an animation from res/anim: 
                vf.setAnimation(AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_right));
                vf.showPrevious();
        }

        });        

    }

	
}
