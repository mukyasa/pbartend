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
	
	
	public Cursor getCategories(){
		Cursor cursor = sqliteDatabase.query(TABLE_CATS, new String[] {
				COL_ID, COL_NAME }, null, null, null, null, null);

		return cursor;
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
