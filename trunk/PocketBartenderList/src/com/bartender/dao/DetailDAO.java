package com.bartender.dao;

import android.app.Activity;
import android.database.Cursor;


public class DetailDAO extends DataDAO{
	
	private String drinkName;
	private String drinkType;
	private String glass;
	private int id;
	public String getDrinkName() {
		return drinkName;
	}
	public void setDrinkName(String drinkName) {
		this.drinkName = drinkName;
	}
	public String getDrinkType() {
		return drinkType;
	}
	public void setDrinkType(String drinkType) {
		this.drinkType = drinkType;
	}
	public String getGlass() {
		return glass;
	}
	public void setGlass(String glass) {
		this.glass = glass;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

	public void load(Activity activity) {
		Cursor cursor = sqliteDatabase.query(true, SQL_DRINK_TABLE_NAME,
				new String[] { COL_ROW_ID, COL_ROW_DRINK_NAME, COL_ROW_GLASS,
						COL_ROW_DRINK_TYPE }, COL_ROW_ID + "=" + id, null, null,
				null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			activity.startManagingCursor(cursor);
			setId(cursor.getInt(cursor
					.getColumnIndex(COL_ROW_ID)));
			setDrinkName(cursor.getString(cursor
					.getColumnIndex(COL_ROW_DRINK_NAME)));
			setGlass(cursor.getString(cursor
					.getColumnIndex(COL_ROW_GLASS)));
			setDrinkType(cursor.getString(cursor
					.getColumnIndex(COL_ROW_DRINK_TYPE)));
		}
	}
	

}
