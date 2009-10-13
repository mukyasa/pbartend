package com.bartender.dao;

import android.database.Cursor;

public class IngredientsDAO extends DataDAO {
	
	/**
	 * gets all records for type
	 * 
	 * @return
	 */
	
	public Cursor retrieveAllFilteredIngredients(String searchresult) {
		
		String [] selectionArgs = {searchresult};
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetAllIngredientsFilter, selectionArgs);

		return cursor;
	}

}
