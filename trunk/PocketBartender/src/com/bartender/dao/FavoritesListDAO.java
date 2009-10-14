package com.bartender.dao;


import android.database.Cursor;

public class FavoritesListDAO extends DataDAO{

	
	/**
	 * gets all records for name
	 */
	public Cursor retrieveAllFavorites() {
		
		String [] selectionArgs = {DataDAO.FAV_YES};
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetAllFavorites, selectionArgs);

		return cursor;
	}
	
	public Cursor retrieveAllFilteredFavorites(String searchresult) {
		
		String [] selectionArgs = {searchresult + "%"};
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetAllFavoritesFilter, selectionArgs);

		return cursor;
	}


}
