package com.bartender.dao;


import android.database.Cursor;

public class DrinkListDAO extends DataDAO{

	
	/**
	 * gets all records for name
	 */
	public Cursor retrieveAllDrinks() {
		Cursor cursor = sqliteDatabase.query(TABLE_DRINK, new String[] {
				COL_ROW_ID, COL_NAME }, null, null, null, null, null);

		return cursor;
	}
	
	public Cursor retrieveAllFilteredDrinks(String searchresult) {
		
		String [] selectionArgs = {searchresult + "%"};
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetAllDrinksFilter, selectionArgs);

		return cursor;
	}
	


}
