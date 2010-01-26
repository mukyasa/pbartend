package com.juggler.view;

import java.util.Hashtable;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.juggler.dao.QuiresDAO;
import com.juggler.domain.NewPassword;
import com.juggler.domain.PasswordDetail;
import com.juggler.utils.Constants;
import com.juggler.utils.Encrypt;
import com.juggler.utils.TempletUtil;

public class CreateWalletTemplateActivity extends BaseActivity implements OnClickListener {
	private CharSequence text;
	private TextView tvWalletTitle;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.template_frame);
		super.onCreate(savedInstanceState);
		initialize();
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
		bNext.setText(getString(R.string.save));
		
		//set title
		Intent selectedIntent = getIntent();
		text =  selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_SELECTED_TEXT);
		long selectedRow =  selectedIntent.getLongExtra(Constants.INTENT_EXTRA_SELECTED_ROW, 0);
		
		TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText("");
		
		tvWalletTitle = (TextView)findViewById(R.id.tvWalletTitle);
		tvWalletTitle.setText(text);
		tvWalletTitle.setId(R.string.wallet);
		tvWalletTitle.setOnClickListener(this);
		NewPassword np = NewPassword.getInstance();
		np.name = text.toString();
		
		TableLayout detailLayout = (TableLayout)findViewById(R.id.tlDetails);
		TableLayout detailLayout_wrapper = (TableLayout)findViewById(R.id.tblDetailsWrapper);
		
		//get template
		Cursor cursor = passDao.getTemplate(selectedRow);
		
		if (cursor != null) {
			cursor.moveToFirst();
			startManagingCursor(cursor);
			String sectionTitle="";
			boolean isFirst = true;
			for(int i=0;i<cursor.getCount();i++){
				String label = Encrypt.decryptA(cursor.getString(cursor.getColumnIndex(QuiresDAO.COL_TEMPLATE_LABEL)));
				String sectionname = cursor.getString(cursor.getColumnIndex(QuiresDAO.COL_TEMPLATE_SECTION_TITLE));
				
				//check sectionname for null
				if(!sectionname.equalsIgnoreCase("null"))
					sectionname = Encrypt.decryptA(sectionname);
				
				//check if new section is needed
				if(!sectionname.equalsIgnoreCase("null") && !sectionname.equals(sectionTitle)) 
				{
					TextView tvSubTitle = TempletUtil.getTextView(this, sectionname);
					detailLayout = TempletUtil.getNewTableLayout(this,"",i);
					detailLayout_wrapper.addView(tvSubTitle);
					detailLayout_wrapper.addView(detailLayout);
					isFirst=true;
				}
				
				int elmId=label.hashCode()+i;
				if(elmId <0)
					elmId=(elmId  *-1);
					
				TableRow tr = TempletUtil.getRow(this,label,"",isFirst,elmId,TempletUtil.determineSection(sectionname),false);
				tr.setOnClickListener(this);
				
				detailLayout.addView(tr);
				isFirst=false;
				
				//move to next row
				cursor.moveToNext();
				//reset title
				sectionTitle = sectionname;
			};
			
			TableRow tr = TempletUtil.getRow(this,getString(R.string.note),"",true,R.string.note,PasswordDetail.GENERIC,false);
			tr.setOnClickListener(this);
			
			TextView tvSubTitle = TempletUtil.getTextView(this,"");
			detailLayout = TempletUtil.getNewTableLayout(this,"",R.string.note);
			detailLayout_wrapper.addView(tvSubTitle);
			detailLayout.addView(tr);
			detailLayout_wrapper.addView(detailLayout);
			
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
	    	intent.putExtra(Constants.INTENT_EXTRA_SELECTED_SECTION,(Integer)((TextView)v).getTag());
	    	
	    	//for notes and the key to the hashtable
	    	NewPassword np = NewPassword.getInstance();
	    	np.templateId = ((TextView)v).getId();
	    	//Log.v("TEMP ID", np.templateId+"");
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
	    	intent.putExtra(Constants.INTENT_EXTRA_SELECTED_SECTION,(Integer)((TextView)value).getTag());
	    	
	    	//for notes and the key to the hashtable
	    	NewPassword np = NewPassword.getInstance();
	    	np.templateId = ((TextView)value).getId();
	    	
	    			
	    	startActivity(intent);
	    	
	    }
	    else if(v == bNext)
	    {
	    	passDao.saveWallet();
    		startActivity(new Intent(this,HomeView.class));
	    }
	    
	    
	}
	
}
