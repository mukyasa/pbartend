/**
 * Date: Jan 1, 2010
 * Project: PasswordJuggler
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.juggler.utils;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.juggler.dao.QuiresDAO;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class CustomCursorAdapter extends SimpleCursorAdapter {
	
	
	 public CustomCursorAdapter(Context context, int layout, Cursor c,
				String[] from, int[] to) {
			super(context, layout, c, from, to);
		}
	 
	 public void bindView(View view, Context context, Cursor cursor) {
		 
		 try {
			 String text =cursor.getString(cursor.getColumnIndex(QuiresDAO.COL_NAME));
			 ((TextView)view).setText(Encrypt.decryptA(text));
			 
			 String id =cursor.getString(cursor.getColumnIndex(QuiresDAO.COL_ID));
			 ((TextView)view).setId(Integer.valueOf(id));
			 
			 //get hint and set it in case of exception
			 String hint =cursor.getString(cursor.getColumnIndex(QuiresDAO.COL_URL));
			 ((TextView)view).setHint(Encrypt.decryptA(hint));
			 
			 //probably going throw exception on login template
			 String section_tag =cursor.getString(cursor.getColumnIndex(QuiresDAO.COL_SECTION));
			 ((TextView)view).setTag(section_tag);
        } catch (Exception e) {
        }
		    
		// super.bindView(view, context, cursor);
	 }
}
