package com.drinkmixer.view;

import java.io.File;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.admob.android.ads.AdManager;
import com.admob.android.ads.AdView;
import com.admob.android.ads.SimpleAdListener;
import com.drinkmixer.R;
import com.drinkmixer.dao.DataDAO;
import com.drinkmixer.dao.DetailDAO;
import com.drinkmixer.dao.DrinkIngredientsInsert;
import com.drinkmixer.dao.DrinkInserts;
import com.drinkmixer.dao.MixerDbHelper;
import com.drinkmixer.domain.NewDrinkDomain;
import com.drinkmixer.domain.ScreenType;
import com.drinkmixer.utils.Constants;

public class HomeScreenView extends Activity implements OnClickListener,OnTouchListener,Runnable {

	/** Called when the activity is first created. */
	private Button btnAll, btnCat, btnIng, btnFav; 
	private ImageView info;
	private MixerDbHelper myDatabaseAdapter;
	private ProgressDialog pd;
	private DataDAO dataDAO = new DetailDAO();
	private final int DIALOG_ABOUT = 0;
	private final int DIALOG_LOC=1;
	private Context context=null;
	private final int MENU_CREATE=0;
	private final int MENU_MOVE=1;
	private final int DIALOG_CHANGE_LOC=2;
	private final int MENU_LESSONS =3;
	private Thread thread;
	private File dbfile;
	private File dbfilesd;
	private AdView mAd; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        //progress menu bar
        setContentView(R.layout.home);
        context=this;
        initComponents();
        
        dbfile = new File(Environment.getDataDirectory()+"/data/com.drinkmixer/databases/", MixerDbHelper.DATABASE_NAME); 
        dbfilesd = new File(Environment.getExternalStorageDirectory(), MixerDbHelper.DATABASE_EXTERNAL_FOLDER+"/"+MixerDbHelper.DATABASE_NAME);
        
        if(!dbfile.exists() && !dbfilesd.exists())
        {
        	thread = new Thread(this);
        	//let user choose db location
        	showDialog(DIALOG_LOC);
        }
        
       /*AdManager.setTestDevices( new String[] {
                AdManager.TEST_EMULATOR ,         // Android emulator
               "ffffffff-9eaf-9658-ffff-ffff99d603a9",
        } );*/
        if(Constants.showAds)
        {
	 		AdManager.setAllowUseOfLocation(true);
	 		
			mAd = (AdView) findViewById(R.id.ad);
			mAd.setAdListener(new AdMobListener());
			mAd.setVisibility( View.VISIBLE  );
	        // The ad will fade in over 0.4 seconds.
	 		AlphaAnimation animation = new AlphaAnimation( 0.0f, 1.0f );
	 		animation.setDuration( 400 );
	 		animation.setFillAfter( true );
	 		animation.setInterpolator( new AccelerateInterpolator() );
	 		mAd.startAnimation( animation );
        }
 		
 		/*final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

 	    final String tmDevice, tmSerial, tmPhone, androidId;
 	    tmDevice = "" + tm.getDeviceId();
 	    tmSerial = "" + tm.getSimSerialNumber();
 	    androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

 	    UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
 	    String deviceId = deviceUuid.toString();
 	    System.out.println("DEVICE ID:"+deviceId);*/

 			 
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onStop()
     */
    @Override
    protected void onStop() {
    	if(pd!=null)
    		pd.dismiss();
    	
        super.onStop();
    }
    
	private void initComponents() {
		
		info = (ImageView) findViewById(R.id.ivInfo);
		info.setOnClickListener(this);
		
		btnAll = (Button) findViewById(R.id.btnAll);
		btnAll.setOnClickListener(this);
		btnAll.setOnTouchListener(this);
		
		btnCat = (Button) findViewById(R.id.btnCat);
		btnCat.setOnClickListener(this);
		btnCat.setOnTouchListener(this);

		btnFav = (Button) findViewById(R.id.btnFav);
		btnFav.setOnClickListener(this);
		btnFav.setOnTouchListener(this);

		btnIng = (Button) findViewById(R.id.btnIng); 
		btnIng.setOnClickListener(this);
		btnIng.setOnTouchListener(this);

	}
    
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_CREATE, 0, "Create New").setIcon(android.R.drawable.ic_menu_add);
		menu.add(0, MENU_MOVE, 0, "Move Database").setIcon(android.R.drawable.ic_menu_share);
		menu.add(0, MENU_LESSONS, 0, "Learn Bartending").setIcon(android.R.drawable.ic_menu_info_details);
	    return true;
	}

	/* Handles item selections */
	public boolean onOptionsItemSelected(MenuItem item) {

		 switch (item.getItemId()) {
			 case MENU_CREATE:
				 	NewDrinkDomain.getInstance().clearDomain();
					ScreenType.getInstance().screenType= -1;
					startActivity(new Intent(this, CreateUpdateView.class));
			    	return true;
			 case MENU_LESSONS:
					startActivity(new Intent(this, BartenderKnowledgeActivity.class));
			    	return true;
			 case MENU_MOVE:
				 thread = new Thread(this);
				 showDialog(DIALOG_CHANGE_LOC);
			    	return true;
		 }
		 return false;

	}
	
	  public boolean onTouch(View v, MotionEvent event) {
		    
		  int left=60;
		  	if(v instanceof Button )
		  	{
		  		if(v == btnFav)
		  			left=25;
		  		if(event.getAction() == MotionEvent.ACTION_DOWN)
		  		{
		  			v.setBackgroundResource(R.drawable.button_over);
		  			v.setPadding(left, 0, 0, 10);
		  		}
		  		
		  		else if(event.getAction()== MotionEvent.ACTION_UP)
		  		{
		  			v.setBackgroundResource(R.drawable.button_bg);
		  			v.setPadding(left, 0, 0, 10);
		  		}
		  	}
		  
		    return false;
	    }
    
	  
    public void onClick(View view) {
    	
			if(view==btnAll)
			{
				pd = ProgressDialog.show(this, null,"LOADING...");
				ScreenType.getInstance().screenType= -1;
				startActivity(new Intent(this, DrinkListView.class));
			}
			else if(view==btnCat)
			{	
				startActivity( new Intent(this, CategoryListView.class));
			}
			else if(view == btnFav)
			{
				ScreenType.getInstance().screenType= -1;
				startActivity(new Intent(this, FavoriteListView.class));
			}
			else if(view == btnIng)
			{
				ScreenType.getInstance().screenType= ScreenType.SCREEN_TYPE_ING;
				startActivity(new Intent(this, IngredientsHomeView.class));
			}
			else if(view == info){
				showDialog(DIALOG_ABOUT);
			}
	}

    /* (non-Javadoc)
     * @see android.app.Activity#onCreateDialog(int)
     */
    @Override
    protected Dialog onCreateDialog(int id) {
    	
    	Calendar c = Calendar.getInstance();
    	
    	if(id==DIALOG_ABOUT)
    	{
	    	return new AlertDialog.Builder(HomeScreenView.this)
	        .setIcon(R.drawable.info)
	        .setMessage("Ver:"+Constants.VERSION+"\nsupport@"+Constants.COMPANY_NAME+".com \n\n"+Constants.COMPANY_NAME+".com\ncopyright "+ c.get(Calendar.YEAR))
	        .setTitle("Application Information")
	        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	
	               dismissDialog(DIALOG_ABOUT);
	            }
	        })      
	       .create();
    	}else if(id==DIALOG_LOC){
        	
        	LayoutInflater factory = LayoutInflater.from(this); 
            final View textEntryView = factory.inflate(R.layout.dblocation, null);
            return new AlertDialog.Builder(HomeScreenView.this)
                .setIcon(R.drawable.info)
                .setTitle(R.string.dblocation) 
                .setView(textEntryView)
                .setCancelable(false)
                .setPositiveButton(R.string.setlocation, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    	dismissDialog(DIALOG_LOC);
                    	RadioButton local =  (RadioButton)textEntryView.findViewById(R.id.rbLocal);
                    	if(dbfile.exists())
                    		dbfile.delete();
                    	else if(dbfilesd.exists())
                    		dbfilesd.delete();
                    	
                        if(local.isChecked())
                        	MixerDbHelper.isLocal=true;
                        else
                        	MixerDbHelper.isLocal=false;                           
                        
                    	MediaPlayer mp = MediaPlayer.create(context, R.raw.pouring);
                    	DrinkIngredientsInsert.in = context.getResources().openRawResource(R.raw.tbldrinks_ingredients);
                    	DrinkInserts.in = context.getResources().openRawResource(R.raw.tbldrinks);
                    	
                        mp.start();

                    	pd = ProgressDialog.show(context, null,getString(R.string.buildingdb));
                    	
                    	thread.start();
                        	
                    }
                }).create();
        	}else if(id==DIALOG_CHANGE_LOC){
            	
            	LayoutInflater factory = LayoutInflater.from(this); 
                final View textEntryView = factory.inflate(R.layout.dblocation, null);
                return new AlertDialog.Builder(HomeScreenView.this)
                    .setIcon(R.drawable.info)
                    .setTitle(R.string.dblocation) 
                    .setView(textEntryView)
                    .setCancelable(false)
                    .setPositiveButton(R.string.setlocation, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        	dismissDialog(DIALOG_CHANGE_LOC);
                        	
                        	RadioButton local =  (RadioButton)textEntryView.findViewById(R.id.rbLocal);
                        	if(dbfile.exists()) 
                        		dbfile.delete();
                        	else if(dbfilesd.exists())
                        		dbfilesd.delete();
                        	
                        	MixerDbHelper.isRelocating=true;
                            if(local.isChecked())
                            	MixerDbHelper.isLocal=true;
                            else
                            	MixerDbHelper.isLocal=false;
                        	
                        	MediaPlayer mp = MediaPlayer.create(context, R.raw.pouring);
                        	DrinkIngredientsInsert.in = context.getResources().openRawResource(R.raw.tbldrinks_ingredients);
                        	DrinkInserts.in = context.getResources().openRawResource(R.raw.tbldrinks);
                            mp.start();

                        	pd = ProgressDialog.show(context, null,"Building the database, please be patient.");
                        	thread.start();
                            	
                        }
                    }).setNegativeButton(R.string.btnCancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        	dismissDialog(DIALOG_CHANGE_LOC);
                        	thread = null;
                        }
                    }).create();
            	}else
    		return null;

    }
    
    
    private Handler handler = new Handler() {
    
            @Override
            public void handleMessage(Message msg) {
            	pd.dismiss();
            }
        };
        
	/* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
    	 myDatabaseAdapter = MixerDbHelper.getInstance(this);
         dataDAO.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
         handler.sendEmptyMessage(0);
	    
    }
    
    
    
    
    private class AdMobListener extends SimpleAdListener
    {

		/* (non-Javadoc)
		 * @see com.admob.android.ads.AdView.SimpleAdListener#onFailedToReceiveAd(com.admob.android.ads.AdView)
		 */
		@Override
		public void onFailedToReceiveAd(AdView adView)
		{
			// TODO Auto-generated method stub
			super.onFailedToReceiveAd(adView);
		}

		/* (non-Javadoc)
		 * @see com.admob.android.ads.AdView.SimpleAdListener#onFailedToReceiveRefreshedAd(com.admob.android.ads.AdView)
		 */
		@Override
		public void onFailedToReceiveRefreshedAd(AdView adView)
		{
			// TODO Auto-generated method stub
			super.onFailedToReceiveRefreshedAd(adView);
		}

		/* (non-Javadoc)
		 * @see com.admob.android.ads.AdView.SimpleAdListener#onReceiveAd(com.admob.android.ads.AdView)
		 */
		@Override
		public void onReceiveAd(AdView adView)
		{
			// TODO Auto-generated method stub
			super.onReceiveAd(adView);
		}

		/* (non-Javadoc)
		 * @see com.admob.android.ads.AdView.SimpleAdListener#onReceiveRefreshedAd(com.admob.android.ads.AdView)
		 */
		@Override
		public void onReceiveRefreshedAd(AdView adView)
		{
			// TODO Auto-generated method stub
			super.onReceiveRefreshedAd(adView);
		}
    	
    }
}