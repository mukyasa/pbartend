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

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

}
