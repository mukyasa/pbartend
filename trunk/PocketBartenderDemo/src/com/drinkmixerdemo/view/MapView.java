package com.drinkmixerdemo.view;

import com.drinkmixerdemo.R;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MapView extends BaseActivity implements OnClickListener {

	private Button btnBar, btnLiquor,btnGrocery;
	
	@Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        //progress menu bar
        setContentView(R.layout.map_view);
        
        initComponents();
	}
	
	protected void initComponents() {
		super.initialize();
		 btnBar = (Button)findViewById(R.id.btnBars);
	        btnBar.setOnTouchListener(this);
	        btnBar.setOnClickListener(this);
	        
	        btnGrocery = (Button)findViewById(R.id.btnGrocery);
	        btnGrocery.setOnTouchListener(this);
	        btnGrocery.setOnClickListener(this);
	        
	        btnLiquor = (Button)findViewById(R.id.btnLiquorStore);
	        btnLiquor.setOnTouchListener(this);
	        btnLiquor.setOnClickListener(this);
	}
    
	  
    public void onClick(View view) {
    	
    	try {
			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			
			Criteria criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_FINE);
			criteria.setAltitudeRequired(false);
			criteria.setBearingRequired(false);
			criteria.setCostAllowed(true);
			criteria.setPowerRequirement(Criteria.POWER_LOW);
			String provider = locationManager.getBestProvider(criteria, false);
			Location location = locationManager.getLastKnownLocation(provider);
			
			Double latitude = location.getLatitude();
			Double longitude = location.getLongitude();
			
			if(view==btnLiquor)
			{
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:"+latitude+","+longitude+"?q=liquor")));
			}
			else if(view==btnBar)
			{	
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:"+latitude+","+longitude+"?q=bar")));
			}
			else if(view==btnGrocery)
			{	
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:"+latitude+","+longitude+"?q=grocery")));
			}
		} catch (Exception e) {
			showDialog(DIALOG_LOC_NOT_FOUND);
		}
			
	}

}
