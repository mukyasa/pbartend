package com.drinkmixer.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import com.drinkmixer.domain.DetailsDomain;


public class DetailDAO extends DataDAO{
	 
	/**
	 * sets the drink domain from database
	 * @param drink
	 * @param cursor
	 */
	private void setDrinkDomain(DetailsDomain drink,Cursor cursor)
	{
		//Log.v(getClass().getSimpleName(), "details count=" + cursor.getCount());
		StringBuffer ingredients= new StringBuffer();
		
		for(int i=0;i<cursor.getCount();i++)
		{
			ingredients.append("-"+cursor.getString(cursor.getColumnIndex(COL_AMOUNT)) + " " +  cursor.getString(cursor.getColumnIndex(COL_ING_NAME)) +  "\n");
			drink.addIng(cursor.getString(cursor.getColumnIndex(COL_AMOUNT)) + ", " +  cursor.getString(cursor.getColumnIndex(COL_ING_NAME)));
			drink.id=(cursor.getInt(cursor.getColumnIndex(COL_ROW_ID)));
			drink.drinkName=(cursor.getString(cursor.getColumnIndex(COL_NAME)));
			drink.glass=(cursor.getString(cursor.getColumnIndex(COL_GLASS_NAME)));
			drink.drinkType=(cursor.getString(cursor.getColumnIndex(COL_CAT_NAME)));
			drink.instructions=(cursor.getString(cursor.getColumnIndex(COL_INSTUCTIONS)));
			drink.favorites=(cursor.getString(cursor.getColumnIndex(COL_FAVORITE)));
			//move to next row
			cursor.moveToNext();
		}
		
		drink.ingredients=(ingredients.toString());
	}
	
	/**
	 * Loads drink detail based on the selected drink id.
	 * @param activity
	 * @param drink
	 */
	public void load(Activity activity,DetailsDomain drink) {
		
		String selectionArgs[] = {drink.id+""};
		Cursor cursor = sqliteDatabase.rawQuery(DataDAO.sqlGetDrinkDetailById,selectionArgs);
		
		if (cursor != null) {
			cursor.moveToFirst();
			activity.startManagingCursor(cursor);
			setDrinkDomain(drink,cursor);
			
		}
		
	}
	
	/**
	 * Loads first drink in the spinner based on the drink type name 
	 * @param activity
	 * @param drink
	 */
	public void loadByDrinkTypeNm(Activity activity,DetailsDomain drink) {
		
		String selectionArgs[] = {drink.drinkType};
		Cursor cursor = sqliteDatabase.rawQuery(DataDAO.sqlGetDrinkDetailByDrinkCatName,selectionArgs);
		
		if (cursor != null) {
			cursor.moveToFirst();
			activity.startManagingCursor(cursor);
			setDrinkDomain(drink,cursor);
			
		}
	}
	
	/**
	 * removes favorite 
	 * @param id
	 */
	public void removeFavorite(int id){
		
		ContentValues values = new ContentValues();
		values.put(COL_FAVORITE, FAV_NO);
		
		sqliteDatabase.update(TABLE_DRINK, values, COL_ROW_ID + "=" + id, null);
		
	}

	/**
	 * adds favorite
	 * @param id
	 */
	public void setFavoritesYes(int id)
	{
		ContentValues values = new ContentValues();
		values.put(COL_FAVORITE, FAV_YES);
		
		sqliteDatabase.update(TABLE_DRINK, values, COL_ROW_ID + "=" + id, null);
	}
	
	/**
	 * loads all drinks by name
	 * @param activity
	 * @param drink
	 */
	public void loadByDrinkNm(Activity activity,DetailsDomain drink) {
		
		String selectionArgs[] = {drink.drinkName};
		Cursor cursor = sqliteDatabase.rawQuery(DataDAO.sqlGetDrinkDetailByDrinkName,selectionArgs);
		
		if (cursor != null) {
			cursor.moveToFirst();
			activity.startManagingCursor(cursor);
			setDrinkDomain(drink,cursor);			
		}
	}
	
}
