package com.juggler;

import android.app.Activity;
import android.os.Bundle;

public class AllView extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_frame);
		//Intent intent = new Intent(this, FlashCardTest.class);
        initialize();
    }
    
    private void initialize(){
    	
    	
    }
    
}