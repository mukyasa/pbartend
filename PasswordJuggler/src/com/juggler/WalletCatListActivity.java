package com.juggler;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class WalletCatListActivity extends ListActivity {
   
	private final String[] baseCats = {"Computers","Financial","Government","Internet","Memberships"};
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_frame);
		//Intent intent = new Intent(this, FlashCardTest.class);
        initialize();
    }
    
    private void initialize(){
    	//hide next button
    	Button next = (Button)findViewById(R.id.butNext);
    	next.setVisibility(View.GONE);
    	
    	setListAdapter(new ArrayAdapter<String>(this,
    	         R.layout.list_item, baseCats));
    	  getListView().setTextFilterEnabled(true);

    }
    
}