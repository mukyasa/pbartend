package com.juggler.view;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

import com.juggler.dao.PasswordDAO;
import com.juggler.dao.PasswordDbHelper;
import com.juggler.dao.QuiresDAO;
import com.juggler.utils.Constants;
import com.juggler.utils.TempletUtil;

public class CreateWalletTemplateActivity extends BaseActivity implements OnClickListener {
	private CharSequence text;
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

	private void initialize() {

		//hide next button
		Button bNext = (Button)findViewById(R.id.butNext);
		bNext.setVisibility(View.GONE);
		
		//set title
		Intent selectedIntent = getIntent();
		text =  selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_SELECTED_TEXT);
		long selectedRow =  selectedIntent.getLongExtra(Constants.INTENT_EXTRA_SELECTED_ROW, 0);
		
		TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText("");
		
		TextView tvWalletTitle = (TextView)findViewById(R.id.tvWalletTitle);
		tvWalletTitle.setText(text);
		
		TableLayout detailLayout = (TableLayout)findViewById(R.id.tlDetails);
		TableLayout detailLayout_wrapper = (TableLayout)findViewById(R.id.tblDetailsWrapper);
		
		//get template
		Cursor cursor = passDao.getTemplate(selectedRow);
		
		if (cursor != null) {
			cursor.moveToFirst();
			startManagingCursor(cursor);
			String sectionTitle="";
			boolean isFirst = true;
			do{
				String label = cursor.getString(cursor.getColumnIndex(QuiresDAO.COL_TEMPLATE_LABEL));
				String section = cursor.getString(cursor.getColumnIndex(QuiresDAO.COL_TEMPLATE_SECTION_TITLE));
				
				//check if new section is needed
				if(section != null && !section.equals(sectionTitle)) 
				{
					/**
					android:textStyle="bold" 
					android:shadowColor="#fff" 
					android:shadowDx="1" 
					android:shadowDy="1"
					android:shadowRadius="1" 
					android:layout_marginLeft="10sp"
					android:textColor="#666" 
					android:textSize="14sp"
					android:layout_marginTop="8sp"
					 */
					TextView tvSubTitle = new TextView(this);
					tvSubTitle.setTextColor(Color.rgb(102, 102, 102));
					tvSubTitle.setTypeface(Typeface.DEFAULT_BOLD);
					tvSubTitle.setShadowLayer(1, 1, 1, Color.WHITE);
					LayoutParams params = new LayoutParams();
					params.leftMargin=10;
					params.topMargin=8;
					tvSubTitle.setLayoutParams(params);
					tvSubTitle.setTextSize(14);
					tvSubTitle.setText(section);
					
					detailLayout = TempletUtil.getNewTableLayout(this);
					detailLayout_wrapper.addView(tvSubTitle);
					detailLayout_wrapper.addView(detailLayout);
					isFirst=true;
					
				}
				
				TableRow tr = TempletUtil.getRow(this,label,"",isFirst);
				tr.setOnClickListener(this);
				
				detailLayout.addView(tr);
				isFirst=false;
				
				//move to next row
				cursor.moveToNext();
				//reset title
				sectionTitle = section;
			}while(!cursor.isLast());
			
		}
		
		
		
		
	}
	
	/* (non-Javadoc)
	 * @see com.juggler.view.BaseActivity#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
	    super.onClick(v);
	    
	    if(v instanceof TableRow)
	    {
	    	
	    	Intent intent = new Intent(this, CreateWalletField.class);
	    	View view =((TableRow)v).getChildAt(0);
	    	
	    	intent.putExtra(Constants.INTENT_EXTRA_SELECTED_TEXT,((TextView)view).getText());
	    	startActivity(intent);
	    	
	    }
	    
	    
	}
	
}
