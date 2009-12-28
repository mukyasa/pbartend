/**
 * Date: Dec 25, 2009
 * Project: PasswordJuggler
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.juggler.view;

import com.juggler.dao.PasswordDAO;
import com.juggler.dao.PasswordDbHelper;
import com.juggler.domain.NewPassword;
import com.juggler.utils.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class CreateNoteActivity extends Activity implements OnClickListener {

	private Button butNext,butPrev;
	private PasswordDAO passDao;
	private PasswordDbHelper myDatabaseAdapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_note_frame);
        
      //set up database for use
		passDao = new PasswordDAO();
		myDatabaseAdapter = PasswordDbHelper.getInstance(this);
		passDao.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
		
        initialize();
    }
	
	private void initialize(){
		
		EditText etNote = (EditText)findViewById(R.id.etNote);
		String text = getIntent().getStringExtra(Constants.INTENT_EXTRA_SELECTED_TEXT);
		etNote.setText(text);
		
		//set title
		TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText("");
		
		butNext= (Button)findViewById(R.id.butNext);
		butNext.setOnClickListener(this);
		
		butPrev= (Button)findViewById(R.id.butPrev);
		butPrev.setOnClickListener(this);
	}

	/* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v) {
    	
    	if(v==butNext)
    	{
    		EditText etNote = (EditText)findViewById(R.id.etNote);
    		NewPassword np = NewPassword.getInstance();
    		np.note = etNote.getText().toString();
    		
    		boolean isWalletNote = getIntent().getBooleanExtra(Constants.INTENT_EXTRA_NOTE, false);
    		
    		if(!isWalletNote)
    		{
	    		passDao.saveNotes();
	    		
	    		Intent intent = new Intent(this,HomeView.class);
	    		startActivity(intent);
    		}
    		else
    			finish();
    	}
    	else
    	{
    		finish();
    	}
    	
    }
}

