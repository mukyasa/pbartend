package com.bartender.dao;

import android.R.raw;
import android.database.Cursor;

public class IngredientsDAO extends DataDAO {
	
	/**
	 * gets all records for type
	 * 
	 * @return
	 */
	
	public Cursor retrieveAllIngredients(String searchresult) {
		
		String [] selectionArgs = {searchresult};
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetAllIngredients, selectionArgs);

		return cursor;
	}
	
	public Cursor retrieveAllFilteredIngredients(String searchresult,String key) {
		
		String [] selectionArgs = {searchresult,key + "%"};
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetAllIngredientsFilter, selectionArgs);

		return cursor;
	}
	
	public Cursor retrieveAllMeasurements()
	{
		Cursor cursor = sqliteDatabase.query(TABLE_FRACTIONAL_AMOUNTS, new String[] {
				COL_ROW_ID, COL_FRACTION }, null, null, null, null, null);

		return cursor;
	}

}
