package com.bartender.dao;

import android.database.Cursor;

public class CategoryDAO extends DataDAO {
	
	/**
	 * gets all records for type
	 * 
	 * @return
	 */
	public Cursor retrieveAllDrinktypes() {
		Cursor cursor = sqliteDatabase.query(SQL_TYPE_TABLE_NAME, new String[] {
				COL_ROW_ID, COL_TYPE }, null, null, null, null, null);

		return cursor;
	}
	
	public Cursor retrieveAllFilteredDrinktypes(String searchresult) {
		
		String [] selectionArgs = {searchresult + "%"};
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetAllCategoriesFilter, selectionArgs);

		return cursor;
	}

}
