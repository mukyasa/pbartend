package com.juggler;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class WalletCatListActivity extends ListActivity implements OnClickListener {
   
	private Button butPrev;
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
    	//hide title
    	TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
    	tvTitle.setVisibility(View.GONE);
    	
    	butPrev =(Button)findViewById(R.id.butPrev);
    	butPrev.setOnClickListener(this);
    	
    	setListAdapter(new ArrayAdapter<String>(this,
    	         R.layout.list_item, baseCats));
    	  getListView().setTextFilterEnabled(true);

    }

	/* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v) {
    	
    	if(v==butPrev){
    		finish();
    	}
	   
	    
    }
    
}