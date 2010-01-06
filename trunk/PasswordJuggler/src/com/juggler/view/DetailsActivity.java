package com.juggler.view;

import java.util.Hashtable;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.juggler.dao.PasswordDAO;
import com.juggler.dao.PasswordDbHelper;
import com.juggler.dao.QuiresDAO;
import com.juggler.domain.NewPassword;
import com.juggler.domain.PasswordDetail;
import com.juggler.utils.Constants;
import com.juggler.utils.Encrypt;
import com.juggler.utils.TempletUtil;

public class DetailsActivity extends BaseActivity {
	
	private CharSequence text;
	private TextView tvWalletTitle;
	private PasswordDAO passDao;
	private PasswordDbHelper myDatabaseAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.template_frame);
		
		//set up database for use
		passDao = new PasswordDAO();
		myDatabaseAdapter = PasswordDbHelper.getInstance(this);
		passDao.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
		initialize();
		super.onCreate(savedInstanceState);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {

		//this is mainly used when you come back into the screen after you save the value
		NewPassword np = NewPassword.getInstance();
		
		//for other nv pairs
		Hashtable<String, String> nvpair = np.getTemplateSaver();
		if(nvpair != null)
		{
			String value = nvpair.get(np.templateId+"");
			TextView selected = (TextView)findViewById(np.templateId);
			if(selected != null && value != null)
				selected.setText(value);
		}

		super.onResume();
	}

	private void initialize() {

		//hide next button
		Button bNext = (Button)findViewById(R.id.butNext);
		bNext.setText(getString(R.string.commit));
		
		//set title
		Intent selectedIntent = getIntent();
		text =  selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_SELECTED_TEXT);
		CharSequence url = selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_SELECTED_URL);
		long selectedRow =  selectedIntent.getLongExtra(Constants.INTENT_EXTRA_SELECTED_ROW, 0);
		
		TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText("");
		
		tvWalletTitle = (TextView)findViewById(R.id.tvWalletTitle);
		tvWalletTitle.setText(text);
		tvWalletTitle.setId(R.string.wallet);
		tvWalletTitle.setOnClickListener(this);
		NewPassword np = NewPassword.getInstance();
		np.name = text.toString();
		
		TableLayout detailLayout_wrapper = (TableLayout)findViewById(R.id.tblDetailsWrapper);
		TableLayout detailLayout = (TableLayout)findViewById(R.id.tlDetails);
		
		//get template
		Cursor cursor = passDao.getDetail(selectedRow);
		
		if (cursor != null) {
			cursor.moveToFirst();
			startManagingCursor(cursor);
			boolean isFirst = true;
			String sectionTitle="";
			TableRow tr=null;
			String note="";
			boolean isNote=false;
			
			//url if applicable
			if(url!=null)
			{
				tr = TempletUtil.getRow(this,getString(R.string.url),url.toString(),isFirst,R.string.url,PasswordDetail.GENERIC);
				tr.setOnClickListener(this);
				detailLayout.addView(tr);
				isFirst=false;
			}
			
			
			for(int i=0;i<cursor.getCount();i++){
				
				String label = Encrypt.decryptA(cursor.getString(cursor.getColumnIndex(QuiresDAO.COL_NAME)));
				String value = Encrypt.decryptA(cursor.getString(cursor.getColumnIndex(QuiresDAO.COL_VALUE)));
				int section = cursor.getInt(cursor.getColumnIndex(QuiresDAO.COL_SECTION));
				note = Encrypt.decryptA(cursor.getString(cursor.getColumnIndex(QuiresDAO.COL_NOTE)));
				
				//if the label is null get out because its just a note
				if(label == null)
				{
					isNote=true;
					break; 
				}
				
				/********  section name *********/
				String sectionname = cursor.getString(cursor.getColumnIndex(QuiresDAO.COL_SECTION));
				
				//check if new section is needed
				if(sectionname != null && Integer.valueOf(sectionname) > PasswordDetail.GENERIC) 
				{
					TextView tvSubTitle = TempletUtil.getTextView(this, TempletUtil.determineSectionName(sectionname));
					detailLayout = TempletUtil.getNewTableLayout(this); 
					detailLayout_wrapper.addView(tvSubTitle);
					detailLayout_wrapper.addView(detailLayout);
					isFirst=true;				
				}
				/********  section name *********/
				
				int elmId=label.hashCode()+i;
				if(elmId <0)
					elmId=(elmId  *-1);
					
				tr = TempletUtil.getRow(this,label,value,isFirst,elmId,section);
				tr.setOnClickListener(this);
				
				detailLayout.addView(tr);
				isFirst=false;
				
				//move to next row
				cursor.moveToNext();
				//reset title
				sectionTitle = sectionname;
			};
			
			//add note at the end
			tr = TempletUtil.getRow(this,getString(R.string.note),note,true,R.string.note,PasswordDetail.GENERIC);
			tr.setOnClickListener(this);
			
			TextView tvSubTitle = TempletUtil.getTextView(this,"");
			//if not a note add new table
			if(!isNote)
			{
				detailLayout = TempletUtil.getNewTableLayout(this);
				detailLayout_wrapper.addView(tvSubTitle);
				detailLayout.addView(tr);
				detailLayout_wrapper.addView(detailLayout);
			}
			else
				detailLayout.addView(tr);
					
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.juggler.view.BaseActivity#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
	    super.onClick(v);
	    
	    Intent intent = new Intent(this, CreateWalletField.class);
	    
	    if(v == tvWalletTitle)
	    {
	    	String title = ((TextView)v).getText().toString();
	    	intent.putExtra(Constants.INTENT_EXTRA_SELECTED_TEXT,title);
	    	intent.putExtra(Constants.INTENT_EXTRA_SELECTED_LABEL,getString(R.string.title));
	    	intent.putExtra(Constants.INTENT_EXTRA_SELECTED_FIELD_ID,((TextView)v).getId());
	    	
	    	//for notes and the key to the hashtable
	    	NewPassword np = NewPassword.getInstance();
	    	np.templateId = ((TextView)v).getId();
	    	Log.v("TEMP ID", np.templateId+"");
	    	startActivity(intent);
	    	 
	    }
	    else if(v instanceof TableRow)
	    {
	    	
	    	TextView label =(TextView)((TableRow)v).getChildAt(0);
	    	TextView value =(TextView)((TableRow)v).getChildAt(1);
	    	
	    	//needed to determine the save button reaction
	    	if(((TextView)label).getText().equals(getString(R.string.note)+":"))
	    	{
	    		intent = new Intent(this, CreateNoteActivity.class);
	    		intent.putExtra(Constants.INTENT_EXTRA_NOTE,true);
	    	}
	    	
	    	/*if the value has not been filled the next field 
	    	will be the label otherwise make it the value*/
	    	String theValue="";
	    	if(value.getText().toString().equals(""))
	    	{
	    		theValue = label.getText().toString();
	    		//get ride of the :
	    		theValue = theValue.substring(0, theValue.length()-1);
	    	}
	    	else
	    		theValue = value.getText().toString();
	    	
	    	String labelValue =label.getText().toString();
	    	
	    	intent.putExtra(Constants.INTENT_EXTRA_SELECTED_TEXT,theValue);
	    	intent.putExtra(Constants.INTENT_EXTRA_SELECTED_LABEL,labelValue.substring(0, labelValue.length()-1));
	    	intent.putExtra(Constants.INTENT_EXTRA_SELECTED_FIELD_ID,((TextView)value).getId());
	    	
	    	//for notes and the key to the hashtable
	    	NewPassword np = NewPassword.getInstance();
	    	np.templateId = ((TextView)value).getId();
	    	
	    			
	    	startActivity(intent);
	    	
	    }
	    else if(v == bNext)
	    {
	    	passDao.updateLogin();
    		startActivity(new Intent(this,HomeView.class));
	    }
	    
	    
	}
	
}
