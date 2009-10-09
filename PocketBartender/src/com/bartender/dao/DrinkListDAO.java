package com.bartender.dao;


import android.database.Cursor;

public class DrinkListDAO extends DataDAO{

	
	/**
	 * gets all records for name
	 */
	public Cursor retrieveAllDrinks() {
		Cursor cursor = sqliteDatabase.query(SQL_DRINK_TABLE_NAME, new String[] {
				COL_ROW_ID, COL_ROW_DRINK_NAME }, null, null, null, null, null);

		return cursor;
	}
	
	


}
