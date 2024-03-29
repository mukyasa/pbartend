package com.drinkmixerdemo.dao;


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
	
	public Cursor retrieveAllDrinkAndGlass() throws Exception
	{
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetAllDrinksAndGlass, new String[]{});

		return cursor;
		
	}
	
	public Cursor retrieveAllDrinksByIng(String ingType,String ingName)
	{
		String [] selectionArgs = {ingName,ingType};
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetAllDrinksByIngredients, selectionArgs);

		return cursor;
		
	}
	
	public Cursor retrieveAllDrinkByTypes(long catId) {
		
		String [] selectionArgs = {catId+""};
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetDrinksByDrinkCatId, selectionArgs);

		return cursor;
	}
	
	public Cursor retrieveAllFilteredDrinksByTypes(String searchresult,long catId) {
		
		String [] selectionArgs = {searchresult + "%",catId+""};
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetAllDringksFilterCategories, selectionArgs);

		return cursor;
	}


}
