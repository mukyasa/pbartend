package com.bartender.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.bartender.R;
import com.bartender.dao.DataDAO;
import com.bartender.dao.DatabaseAdapter;
import com.bartender.dao.DetailDAO;
import com.bartender.domain.ScreenType;

public class HomeScreenView extends Activity implements OnClickListener,Runnable {
    /** Called when the activity is first created. */
	private Intent intent;
	private Button btnAll, btnCat, btnIng, btnFav, btnNew; 
	private DatabaseAdapter myDatabaseAdapter;
	private ProgressDialog pd;
	private DataDAO dataDAO = new DetailDAO();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        initComponents();
        
        pd = ProgressDialog.show(this, null,"Building the database, please be patient.");        
        
        Thread thread = new Thread(this);
        thread.start();
        
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
				intent = new Intent(this, IngredientsHomeView.class);
				startActivity(intent);
			}
			else if(view == btnNew)
			{
				ScreenType.getInstance().screenType= -1;
				intent = new Intent(this, CreateUpdateView.class);
				startActivity(intent);
			}
			
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