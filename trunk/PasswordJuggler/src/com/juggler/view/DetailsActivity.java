package com.juggler.view;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

import com.juggler.dao.QuiresDAO;
import com.juggler.domain.NewPassword;
import com.juggler.domain.PasswordDetail;
import com.juggler.utils.AppUtils;
import com.juggler.utils.Constants;
import com.juggler.utils.Encrypt;
import com.juggler.utils.TempletUtil;

public class DetailsActivity extends BaseActivity implements OnTouchListener,OnLongClickListener {
	
	private CharSequence text;
	private TextView tvWalletTitle;
	private TableLayout detailLayout_wrapper;
	private TableLayout detailLayout;
	private TableRow addmoreTR;
	private int ADDMORE_ID =15987422;
	private int NOTE_ROW_ID=1234556789;
	private boolean wasDelete=false;
	private TextView directions=null;
	private Drawable bgHolder=null;
	
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
			String label="";
			
			if(value!= null)
			{
				TextView selected = (TextView)findViewById(np.templateId);
				if(selected != null && value != null)
					selected.setText(value);
			}
			else
			{
				Set<String> set = nvpair.keySet();
				if(set!=null)
				{
					Iterator<String> iter = set.iterator();
					
					while (iter.hasNext()) {
						label = (String) iter.next();
						value = nvpair.get(label);
					}
					if(label != null && !label.equals("-1") && value!=null)
					{
						addNewRow(label,value);
						
						if(addmoreTR!=null)
						{
							TableRow trnew = (TableRow)findViewById(ADDMORE_ID);
							//re-add new row button
							detailLayout.removeView(trnew);
							addNewRowButton();
							Hashtable<String, String> tmphash = np.getTemplateSaver();
							tmphash.clear();
						}
					}
				}
				
			}
		}

		super.onResume();
	}

	private void initialize() {

		directions = (TextView)findViewById(R.id.tvDirections);
		
		//hide next button
		bNext = (Button)findViewById(R.id.butNext);
		bNext.setText(getString(R.string.edit));
		
		//set title
		Intent selectedIntent = getIntent();
		text =  selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_SELECTED_TEXT);
		CharSequence url = selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_SELECTED_URL);
		long selectedRow =  selectedIntent.getLongExtra(Constants.INTENT_EXTRA_SELECTED_ROW, 0);
		
		TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText("");
		
		tvWalletTitle = (TextView)findViewById(R.id.tvWalletTitle);
		
		NewPassword np = NewPassword.getInstance();
		if(np.entry_type == QuiresDAO.ENTRY_TYPE_GEN_PASSWORD)
			tvWalletTitle.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.passwords_lg), null,null,null);
		else if(np.entry_type == QuiresDAO.ENTRY_TYPE_LOGINS)
			tvWalletTitle.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.logins_lg), null,null,null);
		else if(np.entry_type == QuiresDAO.ENTRY_TYPE_NOTES)
			tvWalletTitle.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.note_lg), null,null,null);
		else if(np.entry_type == QuiresDAO.ENTRY_TYPE_WALLET)
			tvWalletTitle.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.wallet_lg), null,null,null);
		
		tvWalletTitle.setText(text);
		tvWalletTitle.setId(R.string.wallet);
		tvWalletTitle.setOnClickListener(this);
		np.name = text.toString();
		
		detailLayout_wrapper = (TableLayout)findViewById(R.id.tblDetailsWrapper);
		detailLayout = (TableLayout)findViewById(R.id.tlDetails);
		
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
				tr.setOnLongClickListener(this);
				detailLayout.addView(tr);
				isFirst=false;
			}
			
			
			for(int i=0;i<cursor.getCount();i++){
				
				String label = Encrypt.decryptA(cursor.getString(cursor.getColumnIndex(QuiresDAO.COL_NAME)));
				String value="";
				//if concealed then only show **************
				if(label != null && AppUtils.passwordTypes.containsKey(label))
				{
					if(AppUtils.getClearTextSetting(this).equalsIgnoreCase("true"))
						value = "**************";
					else
						value = Encrypt.decryptA(cursor.getString(cursor.getColumnIndex(QuiresDAO.COL_VALUE)));
				}else
					value = Encrypt.decryptA(cursor.getString(cursor.getColumnIndex(QuiresDAO.COL_VALUE)));
				
				int section = cursor.getInt(cursor.getColumnIndex(QuiresDAO.COL_SECTION));
				note = Encrypt.decryptA(cursor.getString(cursor.getColumnIndex(QuiresDAO.COL_NOTE)));
				np.passwordId = cursor.getLong(cursor.getColumnIndex(QuiresDAO.COL_ID));
				
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
				tr.setOnTouchListener(this);
				tr.setOnLongClickListener(this);
				
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
			tr.setId(NOTE_ROW_ID);
			
			TextView tvSubTitle = TempletUtil.getTextView(this,"");
			//if not a note add new table
			if(!isNote)
			{
				TableLayout detailLayoutNotes = TempletUtil.getNewTableLayout(this);
				detailLayout_wrapper.addView(tvSubTitle);
				detailLayoutNotes.addView(tr);
				detailLayout_wrapper.addView(detailLayoutNotes);
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
	    String nextButtonText =bNext.getText().toString();
	    //only if they have hit edit
	    if(nextButtonText.equals(getString(R.string.commit)))
	    {
	    
		    if(v == tvWalletTitle)
		    {
		    	String title = ((TextView)v).getText().toString();
		    	intent.putExtra(Constants.INTENT_EXTRA_SELECTED_TEXT,title);
		    	intent.putExtra(Constants.INTENT_EXTRA_SELECTED_LABEL,getString(R.string.title));
		    	intent.putExtra(Constants.INTENT_EXTRA_SELECTED_FIELD_ID,((TextView)v).getId());
		    	
		    	//for notes and the key to the hash table
		    	NewPassword np = NewPassword.getInstance();
		    	np.templateId = ((TextView)v).getId();
		    	//Log.v("TEMP ID", np.templateId+"");
		    	startActivity(intent);
		    	 
		    }
		    else if( v == bDelete)
		    {
		    	//delete this entry
		    	passDao.deleteEntry();
		    	startActivity(new Intent(this,HomeView.class));
		    }
		    else if(v instanceof TableRow)
		    {
		    	if( ((TableRow)v).getId() == ADDMORE_ID)
			    {
			    	//when clicking on the addnew row
		    		startActivity(new Intent(this,CreateNewRowActivity.class));
			    }
		    	else
		    	{
		    		if(!wasDelete) //if this was a long delete then we dont want to do this.
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
		    		else
		    			wasDelete = false;
		    	}
		    	
		    }
		    else if(v == bNext)
		    {
		    	/******DATABASE CALLS******/
		    		passDao.updateEntry();
		    	/******DATABASE CALLS******/
	    		startActivity(new Intent(this,HomeView.class));
		    }
	    }
	    else if(v == bNext){
	    	if(nextButtonText.equals(getString(R.string.edit)))
	    	{
	    		addNewRowButton();
	    		
	    		TableLayout tblDetails_wrapper = (TableLayout)findViewById(R.id.tblDetailsWrapper);
	    		
	    		for(int x =0;x<tblDetails_wrapper.getChildCount();x++)
	    		{
	    			if(tblDetails_wrapper.getChildAt(x) instanceof TableLayout)
	    			{
		    			TableLayout tblDetails = (TableLayout)tblDetails_wrapper.getChildAt(x);
		    		
			    		for(int i=0;i<tblDetails.getChildCount();i++)
			    		{
			    			TableRow tr =  (TableRow)tblDetails.getChildAt(i);
			    			if(tr.getId() != ADDMORE_ID && tr.getId() != NOTE_ROW_ID)
			    			{
			    				if(i==1)
			    					tr.setBackgroundResource(R.drawable.item_spacer_delete);
			    				else
			    					tr.setBackgroundResource(R.drawable.toplines_delete);
			    			}
			    		}
	    			}
	    		}
	    		
	    	}
	    }	
	}
	
	/***
	 * generic new row
	 */
	private void addNewRow(String label,String value){
		
		int elmId=label.hashCode()+100;
		//add new row
		TableRow tr = TempletUtil.getRow(this,label,value,false,elmId,PasswordDetail.GENERIC);
		tr.setOnClickListener(this);
		tr.setOnTouchListener(this);
		detailLayout = (TableLayout)findViewById(R.id.tlDetails);
		detailLayout.addView(tr);
	}
	
	/**
	 * adds a new row button to add new rows
	 */
	private void addNewRowButton()
	{
		bNext.setText(getString(R.string.commit));
		directions.setText(getString(R.string.editDirections));
		
		bDelete.setVisibility(View.VISIBLE);
		
		
		TableLayout tblDetails_wrapper = (TableLayout)findViewById(R.id.tblDetailsWrapper);
		
		for(int x =0;x<tblDetails_wrapper.getChildCount();x++)
		{
			if(tblDetails_wrapper.getChildAt(x) instanceof TableLayout)
			{
				TextView addmore = new TextView(this);
    			TableLayout tblDetails = (TableLayout)tblDetails_wrapper.getChildAt(x);
				addmore.setText("Click to add more rows.");
				addmore.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.addmore), null,null,null);
				addmore.setGravity(Gravity.CENTER_VERTICAL);
				LayoutParams addmoreparams = new LayoutParams();
				addmoreparams.span=2;
				addmoreparams.column=0;
				addmoreparams.weight=2;
				addmore.setLayoutParams(addmoreparams);
				TableRow trAdd = new TableRow(this);
				trAdd.setId(ADDMORE_ID);
				trAdd.addView(addmore);
				trAdd.setBackgroundResource(R.drawable.botlines);
				trAdd.setGravity(Gravity.CENTER_VERTICAL);
				trAdd.setPadding(3, 3, 0, 3);
				LayoutParams params = new LayoutParams();
				params.width = LayoutParams.FILL_PARENT;
				trAdd.setLayoutParams(params);
				trAdd.setOnClickListener(this);
				
				if(tblDetails.getChildAt(0).getId() != NOTE_ROW_ID)
					tblDetails.addView(trAdd, 0);
			}
		}
	}

	public boolean onTouch(View v, MotionEvent event) {
		super.onTouch(v, event);
		
		String nextButtonText =bNext.getText().toString();
		
		if(!nextButtonText.equals(getString(R.string.commit)))
	    {
			if(v instanceof TableRow)
		    {
				TextView value =(TextView)((TableRow)v).getChildAt(1);
				
				((TableRow)v).setPadding(3, 3, 0, 3);
				LayoutParams params = new LayoutParams();
				params.width = LayoutParams.FILL_PARENT;
				((TableRow)v).setLayoutParams(params);
				
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
			    	ClipboardManager clip = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
			    	clip.setText(value.getText().toString());
			    	bgHolder=((TableRow)v).getBackground();
					((TableRow)v).setBackgroundResource(R.drawable.toplines_clk);
				}
				else if(event.getAction() == MotionEvent.ACTION_UP)
				{
					((TableRow)v).setBackgroundDrawable(bgHolder);
				}
		    }
	    }
		
		return false;
	}

	/* (non-Javadoc)
     * @see android.view.View.OnLongClickListener#onLongClick(android.view.View)
     */
    public boolean onLongClick(View v) {
	    
    	String nextButtonText =bNext.getText().toString();
    	
    	 if(nextButtonText.equals(getString(R.string.commit)))
    	 {
	    	((TableRow)v).setVisibility(View.GONE);
	    	wasDelete=true;
    	 }
	    return false;
    }

}
