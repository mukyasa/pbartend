package com.mixer.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;

import com.mixer.R;
import com.mixer.dao.DataDAO;
import com.mixer.dao.DatabaseAdapter;
import com.mixer.dao.DetailDAO;
import com.mixer.domain.ScreenType;
import com.mixer.utils.Constants;

public class HomeScreenView extends Activity implements OnClickListener,OnTouchListener,Runnable {

	/** Called when the activity is first created. */
	private Intent intent;
	private Button btnAll, btnCat, btnIng, btnFav; 
	private ImageView info;
	private DatabaseAdapter myDatabaseAdapter;
	private ProgressDialog pd;
	private DataDAO dataDAO = new DetailDAO();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //progress menu bar
        setContentView(R.layout.home);
        initComponents();
        
        if(DatabaseAdapter.sqliteDb== null)
        {
        	MediaPlayer mp = MediaPlayer.create(this, R.raw.pouring);
            mp.start();

        	pd = ProgressDialog.show(this, null,"Building the database, please be patient.");
        	Thread thread = new Thread(this);
        	thread.start();
        }
        
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
		menu.add(0, 0, 0, "Create New").setIcon(android.R.drawable.ic_menu_add);
	    return true;
	}

	/* Handles item selections */
	public boolean onOptionsItemSelected(MenuItem item) {

			ScreenType.getInstance().screenType= -1;
	    	Intent intent = new Intent(this, CreateUpdateView.class);
			startActivity(intent);
			
	    	return true;

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
				ScreenType.getInstance().screenType= -1;
				intent = new Intent(this, FavoriteListView.class);
				startActivity(intent);
			}
			else if(view == btnIng)
			{
				ScreenType.getInstance().screenType= ScreenType.SCREEN_TYPE_ING;
				intent = new Intent(this, IngredientsHomeView.class);
				startActivity(intent);
			}
			else if(view == info){
				showDialog(0);
			}
	}

    /* (non-Javadoc)
     * @see android.app.Activity#onCreateDialog(int)
     */
    @Override
    protected Dialog onCreateDialog(int id) {
    	return new AlertDialog.Builder(HomeScreenView.this)
        .setIcon(R.drawable.info)
        .setMessage("Ver:"+Constants.VERSION+"\nsupport@"+Constants.COMPANY_NAME+".com \n\n"+Constants.COMPANY_NAME+".com\ncopyright © 2009")
        .setTitle("Application Information")
        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

               dismissDialog(0);
            }
        })      
       .create();

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
    	 myDatabaseAdapter = DatabaseAdapter.getInstance(this);
         dataDAO.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
         handler.sendEmptyMessage(0);
	    
    }
}