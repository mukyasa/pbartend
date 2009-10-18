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
		String [] selectionArgs = {""};
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetAllMeasurements, selectionArgs);

		return cursor;
	}

}
