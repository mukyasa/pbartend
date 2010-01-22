/**
 * Date: Dec 27, 2009
 * Project: PasswordJuggler
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.juggler.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.text.util.Linkify;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

import com.juggler.domain.PasswordDetail;
import com.juggler.view.R;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class TempletUtil {
	
	public static final int STADARD_LABEL_ID = 1909099;
	
	/**
	 * Gets the section name from the id
	 * Jan 2, 2010
	 * dmason
	 * @param sectionName
	 * @return
	 *
	 */
	public static String determineSectionName(String sectionId)
	{
		
		if(sectionId != null)
		{
			int key = Integer.valueOf(sectionId); 
			
			switch (key) {
				case PasswordDetail.SMTP:
	            	return "SMTP";
				case PasswordDetail.PUBLISHER:
	            	return "Publisher";
				case PasswordDetail.ORDER:
	            	return "Order";
				case PasswordDetail.MORE_INFO:
	            	return "More Information";
				case PasswordDetail.HOST:
	            	return "Hosting Provider Details";
				case PasswordDetail.CONTACT_INFO:
	            	return "Contact Information";
				case PasswordDetail.CONTACT:
	            	return "Contact";
				case PasswordDetail.BRANCH_INFO:
	            	return "Branch Information";
				case PasswordDetail.ADMIN_CONSOLE:
	            	return "Administration Console";
				case PasswordDetail.ADD_DETAILS:
	            	return "Additional Details";
	            default:
		            return "";
            }
		}
		else
			return "";
		
	}
	
	/**
	 * Takes the section string name and gets the int value
	 * Jan 2, 2010
	 * dmason
	 * @param sectionName
	 * @return
	 *
	 */
	public static int determineSection(String sectionName)
	{
		if(sectionName!=null){
			if(sectionName.equalsIgnoreCase("SMTP")) 
				return PasswordDetail.SMTP;
			else if(sectionName.equalsIgnoreCase("publisher"))
				return PasswordDetail.PUBLISHER;
			else if(sectionName.equalsIgnoreCase("order"))
				return PasswordDetail.ORDER;
			else if(sectionName.equalsIgnoreCase("More Information"))
				return PasswordDetail.MORE_INFO;
			else if(sectionName.equalsIgnoreCase("Hosting Provider Details"))
				return PasswordDetail.HOST;
			else if(sectionName.equalsIgnoreCase("Contact Information"))
				return PasswordDetail.CONTACT_INFO;
			else if(sectionName.equalsIgnoreCase("Contact"))
				return PasswordDetail.CONTACT;
			else if(sectionName.equalsIgnoreCase("Branch Information"))
				return PasswordDetail.BRANCH_INFO;
			else if(sectionName.equalsIgnoreCase("Administration Console"))
				return PasswordDetail.ADMIN_CONSOLE;
			else if(sectionName.equalsIgnoreCase("Additional Details"))
				return PasswordDetail.ADD_DETAILS;
			else
				return PasswordDetail.GENERIC;
		}
		else
			return PasswordDetail.GENERIC;
		
	}
	
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
	public static TextView getTextView(Context context,String section){
		
		TextView tvSubTitle = new TextView(context);
		tvSubTitle.setTextColor(Color.rgb(102, 102, 102));
		tvSubTitle.setTypeface(Typeface.DEFAULT_BOLD);
		tvSubTitle.setShadowLayer(1, 1, 1, Color.WHITE);
		LayoutParams params = new LayoutParams();
		params.leftMargin=10;
		params.topMargin=8;
		tvSubTitle.setLayoutParams(params);
		tvSubTitle.setTextSize(14);
		tvSubTitle.setText(section);
		
		return tvSubTitle;
	}
	
	/**
	android:background="@drawable/list_item"
	android:drawableLeft="@drawable/wallet_lg" 
	android:paddingTop="4sp"
	android:paddingBottom="4sp" 
	android:gravity="center_vertical"
	android:id="@+id/tvWalletTitle" 
	android:text="The Title"
	android:textColor="#000" 
	android:textSize="18sp"
	android:paddingLeft="10sp"
	 * Dec 27, 2009
	 * dmason
	 * @return
	 *
	 */
	public static TextView getTitleView(Context context,String section)
	{
		TextView tvTitle = new TextView(context);
		tvTitle.setTextColor(Color.rgb(0, 0, 0));
		tvTitle.setBackgroundResource(R.drawable.list_item);
		Drawable wallet = context.getResources().getDrawable(R.drawable.wallet_lg);
		tvTitle.setCompoundDrawablesWithIntrinsicBounds(wallet, null, null, null);
		tvTitle.setTextSize(18);
		tvTitle.setGravity(Gravity.CENTER_VERTICAL);
		tvTitle.setId(R.id.tvWalletTitle);
		tvTitle.setPadding(10, 4, 0, 4);
		tvTitle.setText(section);
		
		return tvTitle;
	}
	
	/**
	 * Creates a single detail row
	 * Dec 27, 2009
	 * dmason
	 * android:layout_height="wrap_content" 
	 * android:gravity="center_vertical"
	   android:layout_width="fill_parent" 
	   android:padding="3sp"
	   android:background="@drawable/item_spacer"
	 * @param context
	 * @param lbl
	 * @param val
	 * @return
	 *
	 */
	public static TableRow getRow(Context context,String lbl,String val,boolean isFirst,int id,int section){
		
		TableRow tr = new TableRow(context);
		
		if(isFirst) //no need for lines
			tr.setBackgroundResource(R.drawable.item_spacer);
		else
			tr.setBackgroundResource(R.drawable.toplines);
		
		tr.setGravity(Gravity.CENTER_VERTICAL);
		tr.setPadding(3, 3, 0, 3);
		LayoutParams params = new LayoutParams();
		params.width = LayoutParams.FILL_PARENT;
		tr.setLayoutParams(params);
		
		/**
		 * android:textColor="#1a63a1" 
		 * android:textStyle="bold" 
		 * android:layout_marginLeft="15sp"
		 * android:gravity="right" 
		 * android:textSize="11sp"
		 */
		TextView label = new TextView(context);
		label.setText(lbl+":");
		label.setTextColor(Color.rgb(26, 99, 161));
		label.setTypeface(Typeface.DEFAULT_BOLD);
		label.setGravity(Gravity.RIGHT);
		label.setTextSize(11);
		label.setWidth(80);
		LayoutParams lavelParams = new LayoutParams();
		lavelParams.leftMargin=15;
		label.setLayoutParams(lavelParams);
		label.setId(STADARD_LABEL_ID);
		
		/**
		 * android:paddingLeft="5sp"
		 * android:textColor="#444" 
		 * android:textSize="13sp"
		 */
		TextView value = new TextView(context);
		
		if(lbl != null && AppUtils.passwordTypes.containsKey(lbl) && AppUtils.getClearTextSetting(context).equalsIgnoreCase("true"))
		{
				PasswordTransformationMethod ptm = PasswordTransformationMethod.getInstance();
				ptm.getTransformation("*", value);
				value.setTransformationMethod(ptm);
		}
		
		value.setText(val);
		value.setHint(val);
		Linkify.addLinks(value, Linkify.WEB_URLS);
		value.setId(id);
		value.setGravity(Gravity.LEFT);
		lavelParams=new LayoutParams();
		lavelParams.width = LayoutParams.WRAP_CONTENT;
		value.setLayoutParams(lavelParams);
		value.setTextSize(14);
		value.setTag(section);
		//if(label.getText().equals(context.getString(R.string.url)+":"))
		//	value.setTextColor(Color.rgb(51, 102, 225));
		//else
			value.setTextColor(Color.rgb(33, 33, 33));
		
		value.setPadding(5, 0, 105, 0);
		
		//add text views to row
		tr.addView(label);
		tr.addView(value);
		
		return tr;
	}
	
	/**
	 * Creates a new table 
	 * android:background="@drawable/list_item"
	   android:layout_width="fill_parent" 
	   android:paddingRight="1sp"
	   android:layout_marginTop="5sp" 
	   android:layout_marginBottom="10sp"
	 * Dec 27, 2009
	 * dmason
	 * @param context
	 * @return
	 *
	 */
	public static TableLayout getNewTableLayout(Context context)
	{
		TableLayout tl = new TableLayout(context);
		tl.setBackgroundResource(R.drawable.list_item);
		return tl;
	}
	
	
	
}
