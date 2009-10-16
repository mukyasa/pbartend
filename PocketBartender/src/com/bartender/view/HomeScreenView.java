package com.bartender.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.bartender.R;
import com.bartender.domain.ScreenType;

public class HomeScreenView extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	private Intent intent;
	private Button btnAll, btnCat, btnIng, btnFav, btnNew; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        
        initComponents();
        
    }
    
	private void initComponents() {
		
		btnAll = (Button) findViewById(R.id.btnAll);
		btnAll.setOnClickListener(this);
		
		btnCat = (Button) findViewById(R.id.btnCat);
		btnCat.setOnClickListener(this);

		btnFav = (Button) findViewById(R.id.btnFav);
		btnFav.setOnClickListener(this);

		btnIng = (Button) findViewById(R.id.btnIng); 
		btnIng.setOnClickListener(this);

		btnNew = (Button) findViewById(R.id.btnNew);
		btnNew.setOnClickListener(this);
	}
    
    
    public void onClick(View view) {
		if(view==btnAll)
		{
			ScreenType.getInstance().setScreenType(-1);
			intent = new Intent(this, DrinkListView.class);
			startActivity(intent);
		}
		else if(view==btnCat)
		{
			intent = new Intent(this, CategoryListView.class);
			startActivity(intent);
		}
		else if(view == btnFav)
		{
			ScreenType.getInstance().setScreenType(-1);
			intent = new Intent(this, FavoriteListView.class);
			startActivity(intent);
		}
		else if(view == btnIng)
		{
			intent = new Intent(this, IngredientsHomeView.class);
			startActivity(intent);
		}
		else if(view == btnNew)
		{
			ScreenType.getInstance().setScreenType(-1);
			intent = new Intent(this, CreateUpdateView.class);
			startActivity(intent);
		}
			
	}
}