package com.bartender.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.bartender.R;

public class IngredientsHomeView extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	private Intent intent;
	private Button btnLiquor, btnMixers, btnGarnish;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        initComponents();
        
    }
    
	private void initComponents() {
		
		btnLiquor = (Button) findViewById(R.id.btnLiquor);
		btnLiquor.setOnClickListener(this);
		
		btnMixers = (Button) findViewById(R.id.btnMixers);
		btnMixers.setOnClickListener(this);

		btnGarnish = (Button) findViewById(R.id.btnGarnish);
		btnGarnish.setOnClickListener(this);
	}
    
    
    public void onClick(View view) {
		if(view==btnLiquor)
		{
			intent = new Intent(this, LiquorListView.class);
			startActivity(intent);
		}
		else if(view==btnMixers)
		{
			intent = new Intent(this, MixersListView.class);
			startActivity(intent);
		}
		else if(view == btnGarnish)
		{
			intent = new Intent(this, GarnishListView.class);
			startActivity(intent);
		}
	
			
	}
}