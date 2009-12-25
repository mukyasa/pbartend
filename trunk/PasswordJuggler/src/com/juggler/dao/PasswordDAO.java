/**
 * Date: Dec 23, 2009
 * Project: PasswordJuggler
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.juggler.dao;

import java.util.Hashtable;
import java.util.Iterator;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.juggler.domain.NewPassword;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class PasswordDAO extends QuiresDAO {
	
	private SQLiteDatabase sqliteDatabase;
	
	/**
	 * get all cats
	 * Dec 24, 2009
	 * dmason
	 * @return
	 *
	 */
	public Cursor getCategories(){
		return sqliteDatabase.query(TABLE_CATS, new String[] {
				COL_ID, COL_NAME }, null, null, null, null, null);
	}
	
	/**
	 * Get all subcats
	 * Dec 24, 2009
	 * dmason
	 * @param catId
	 * @return
	 *
	 */
	public Cursor getSubCategories(String catId){
		String[]selectionArgs={catId};
		return sqliteDatabase.rawQuery(sqlGetSubCats, selectionArgs);
	}
	
	/**
	 * Sets the sqlite db name
	 * 
	 * @param sqliteDatabase
	 */
	public void setSQLiteDatabase(SQLiteDatabase sqliteDatabase) {
		this.sqliteDatabase = sqliteDatabase;
	}
	
	/**
	 * Gets gen password count
	 * Dec 24, 2009
	 * dmason
	 * @return
	 *
	 */
	public int getPasswordsCount()
	{
		String[]selectionArgs={};
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetPasswordCount, selectionArgs);
		return cursor.getCount();
	}
	
	/**
	 * gets logins count
	 * Dec 24, 2009
	 * dmason
	 * @return
	 *
	 */
	public int getLoginsCount()
	{
		String[]selectionArgs={QuiresDAO.LOGINS_ID+""};
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetLoginsCount, selectionArgs);
		return cursor.getCount();
	}
	
	/**
	 * gets notes count
	 * Dec 24, 2009
	 * dmason
	 * @return
	 *
	 */
	public int getNotesCount()
	{
		String[]selectionArgs={};
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetNotesCount, selectionArgs);
		return cursor.getCount();
	}
	
	/**
	 * gets wallet iems count
	 * Dec 24, 2009
	 * dmason
	 * @return
	 *
	 */
	public int getWalletCount()
	{
		String[]selectionArgs={QuiresDAO.LOGINS_ID+""};
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetWalletCount, selectionArgs);
		return cursor.getCount();
	}
	
	/**
	 * persists logins
	 * Dec 24, 2009
	 * dmason
	 *
	 */
	public void saveLogins()
	{
		NewPassword np = NewPassword.getInstance();
		
		ContentValues title = new ContentValues();
		title.put(COL_NAME, np.name);
		title.put(COL_URL, np.url);
		title.put(COL_CAT_ID, np.catId);
		title.put(COL_SUB_CAT_ID, np.subCatId);
		title.put(COL_NOTE_ID, np.noteId);
		
		ContentValues nameValue = new ContentValues();
		Hashtable<String, String> htable = np.nameValue;
		Iterator<String> iter = htable.keySet().iterator();
		
		sqliteDatabase.insert(PasswordDAO.TABLE_PASSWORDS, null, title);
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetMaxId, new String[]{});
		cursor.moveToFirst();
		String passwordId = cursor.getString(cursor.getColumnIndex(PasswordDAO.COL_ID));
		
		while (iter.hasNext()) {
	        String key = (String) iter.next();
	        String value = htable.get(key);
	        
	        nameValue.put(COL_NAME, key);
	        nameValue.put(COL_VALUE, value);	        
	        nameValue.put(COL_PASSWORD_ID, passwordId);
	        
        }

		
		//loop
		sqliteDatabase.insert(PasswordDAO.TABLE_PASSWOR_ENTRY, null, nameValue);
		
	}

}
