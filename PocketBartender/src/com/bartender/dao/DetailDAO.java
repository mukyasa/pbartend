package com.bartender.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.bartender.domain.DetailsDomain;


public class DetailDAO extends DataDAO{
	

	private void setDrinkDomain(DetailsDomain drink,Cursor cursor)
	{
		drink.setId(cursor.getInt(cursor.getColumnIndex(COL_ROW_ID)));
		drink.setDrinkName(cursor.getString(cursor.getColumnIndex(COL_ROW_DRINK_NAME)));
		drink.setGlass(cursor.getString(cursor.getColumnIndex(COL_ROW_GLASS)));
		drink.setDrinkType(cursor.getString(cursor.getColumnIndex(COL_ROW_DRINK_TYPE)));
		drink.setIng1(cursor.getString(cursor.getColumnIndex(COL_ROW_ING1)));
		drink.setIng2(cursor.getString(cursor.getColumnIndex(COL_ROW_ING2)));
		drink.setIng3(cursor.getString(cursor.getColumnIndex(COL_ROW_ING3)));
		drink.setIngredients(cursor.getString(cursor.getColumnIndex(COL_ROW_INGREDIENTS)));
		drink.setInstructions(cursor.getString(cursor.getColumnIndex(COL_ROW_INSTUCTIONS)));
		drink.setInstructions2(cursor.getString(cursor.getColumnIndex(COL_ROW_INSTRUCTIONS2)));
		drink.setFavorites(cursor.getString(cursor.getColumnIndex(COL_ROW_FAV)));
	}
	
	/**
	 * Loads drink detail based on the selected drink id.
	 * @param activity
	 * @param drink
	 */
	public void load(Activity activity,DetailsDomain drink) {
		
		String selectionArgs[] = {drink.getId()+""};
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
		
		String selectionArgs[] = {drink.getDrinkType()};
		Cursor cursor = sqliteDatabase.rawQuery(DataDAO.sqlGetDrinkDetailByDrinkTypeName,selectionArgs);
		
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
		values.put(COL_ROW_FAV, "no");
		
		sqliteDatabase.update(SQL_DRINK_TABLE_NAME, values, COL_ROW_ID + "=" + id, null);
		
	}

	/**
	 * adds favorite
	 * @param id
	 */
	public void setFavoritesYes(int id)
	{
		ContentValues values = new ContentValues();
		values.put(COL_ROW_FAV, "yes");
		
		sqliteDatabase.update(SQL_DRINK_TABLE_NAME, values, COL_ROW_ID + "=" + id, null);
	}
	
	/**
	 * loads all drinks by name
	 * @param activity
	 * @param drink
	 */
	public void loadByDrinkNm(Activity activity,DetailsDomain drink) {
		
		String selectionArgs[] = {drink.getDrinkName()};
		Cursor cursor = sqliteDatabase.rawQuery(DataDAO.sqlGetDrinkDetailByDrinkName,selectionArgs);
		
		if (cursor != null) {
			cursor.moveToFirst();
			activity.startManagingCursor(cursor);
			setDrinkDomain(drink,cursor);			
		}
	}
	
	/**
	 * Loads the drink in the spinner widget 
	 * based on the id of the selected category
	 * @param spinnerDrinkNames
	 * @param drink
	 * @param context
	 */
	public void loadDrinkIds(Spinner spinnerDrinkNames,DetailsDomain drink,Context context){
		
		String selectionArgs[] = {drink.getId()+""};
		Cursor cursor = sqliteDatabase.rawQuery(DataDAO.sqlGetDrinkByTypeId,selectionArgs);
		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(context,
			    android.R.layout.simple_spinner_item,
			    cursor,
				new String[] {COL_ROW_DRINK_NAME},
				new int[] {android.R.id.text1}); 

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerDrinkNames.setAdapter(adapter);
		
		cursor.move(0);
		drink.setDrinkType(cursor.getString(cursor.getColumnIndex(COL_ROW_DRINK_TYPE)));
		
	}
}
