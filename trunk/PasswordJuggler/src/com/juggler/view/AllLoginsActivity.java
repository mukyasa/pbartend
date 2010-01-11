/**
 * Date: Dec 29, 2009
 * Project: PasswordJuggler
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.juggler.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.juggler.domain.NewPassword;
import com.juggler.utils.Constants;
import com.juggler.utils.LoginAuthHandler;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class AllLoginsActivity extends AllViewActivity implements OnClickListener {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	/* (non-Javadoc)
	 * @see com.juggler.view.FooterListActivity#onResume()
	 */
	@Override
	protected void onResume() {
		initialize();
	    super.onResume();
	}
	
	/* (non-Javadoc)
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		/*this is required to reset boolean on every action if the 
		activty is stoped with out this set the login screen shows*/
		LoginAuthHandler lah = LoginAuthHandler.getInstance(this);
	 	lah.setLoginRequired(false);
	    super.onListItemClick(l, v, position, id);
	    NewPassword np = NewPassword.getInstance();
	    np.passwordId = id;
	    
	    Intent intent = new Intent(this, DetailsActivity.class);
	    intent.putExtra(Constants.INTENT_EXTRA_SELECTED_TEXT,((TextView)v).getText());
	    intent.putExtra(Constants.INTENT_EXTRA_SELECTED_URL,((TextView)v).getHint());
	    intent.putExtra(Constants.INTENT_EXTRA_SELECTED_ROW, id);
    	startActivity(intent);
	}
	
	protected void initialize() {
		super.initialize();
		//set title
		TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText(getString(R.string.alllogins));
		
		recordscCursor = passDao.getAllLogins();
		
		//set screen type
		Constants.SCREEN_TYPE=Constants.LOGINS;
		
	}
}
