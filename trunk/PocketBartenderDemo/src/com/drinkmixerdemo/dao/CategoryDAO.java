package com.drinkmixerdemo.dao;

import android.database.Cursor;

public class CategoryDAO extends DataDAO {
	
	/**
	 * gets all records for type
	 * 
	 * @return   
	 */
	public Cursor retrieveAllDrinktypes() throws Exception {
		Cursor cursor = sqliteDatabase.query(TABLE_DRINK_CAT, new String[] {
				COL_ROW_ID, COL_NAME }, null, null, null, null, null);

		return cursor;
	}
	
	public Cursor retrieveAllFilteredDrinktypes(String searchresult) {
		
		String [] selectionArgs = {searchresult + "%"};
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetAllCategoriesFilter, selectionArgs);

		return cursor;
	}
	
	public Cursor retrieveGlassNameById(String searchresult) {
		
		String [] selectionArgs = {searchresult};
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetGlassNameById, selectionArgs);

		return cursor;
	}
	

}
