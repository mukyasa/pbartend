package com.bartender.dao;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.bartender.domain.DetailsDomain;


public class DetailDAO extends DataDAO{
	

	public void load(Activity activity,DetailsDomain drink) {
		
		String selectionArgs[] = {drink.getId()+""};
		Cursor cursor = sqliteDatabase.rawQuery(DataDAO.sqlGetDrinkDetailById,selectionArgs);
		
		if (cursor != null) {
			cursor.moveToFirst();
			activity.startManagingCursor(cursor);
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
			
		}
		
	}
	
	public void loadByDrinkNm(Activity activity,DetailsDomain drink) {
		
		String selectionArgs[] = {drink.getDrinkType()};
		Cursor cursor = sqliteDatabase.rawQuery(DataDAO.sqlGetDrinkDetailByName,selectionArgs);
		
		if (cursor != null) {
			cursor.moveToFirst();
			activity.startManagingCursor(cursor);
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
			
		}
		
	}
	
	
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
