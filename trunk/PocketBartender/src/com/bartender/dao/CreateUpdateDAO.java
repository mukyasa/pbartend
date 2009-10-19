package com.bartender.dao;

import android.content.ContentValues;
import android.database.Cursor;

public class CreateUpdateDAO extends DataDAO {
	
	/**
	 * INSERT INTO tblDrinks (glass_id,name,instructions,category_id,flagged,favorite,custom) "	+
			"values(2,'Darren Test 2','do some work and make a drink',1,0,0,1);
	 * @return
	 */
	public long insertNewDrink(String drinknm,long cat_id,long glassId,String instructions) {
		ContentValues values = new ContentValues();
		
			values.put(COL_NAME, drinknm);
			values.put(COL_CAT_ID, cat_id);
			values.put(COL_GLASS_ID, glassId);
			values.put(COL_DESCRIPTION, instructions);
			values.put(COL_FLAGGED, 0);
			values.put(COL_FAVORITE, 0);
			values.put(COL_CUSTOM, 1);
			
			return sqliteDatabase.insert(TABLE_DRINK, null, values);
			
	}
	
	/**
	 * INSERT INTO tblDrinks_ingredients (drink_id,ingredient_id,amount) " +
	"values(9637,1,'2 oz');
	 * @return
	 */
	private long insertDrinkIng(long drink_id,long ing_id, String amount){
		
		ContentValues values = new ContentValues();
		
		values.put(COL_DRINK_ID, drink_id);
		values.put(COL_INGREDIENT_ID, ing_id);
		values.put(COL_AMOUNT, amount);
		
		return sqliteDatabase.insert(TABLE_DRINK_INGREDIENTS, null, values);
	}
	
	
	/**
	 * updates exiting user created drinks
	 * @return
	 */
	public Cursor updateDrink() {
		Cursor cursor = sqliteDatabase.query(TABLE_DRINK_CAT, new String[] {
				COL_ROW_ID, COL_NAME }, null, null, null, null, null);

		return cursor;
	}
	


}
