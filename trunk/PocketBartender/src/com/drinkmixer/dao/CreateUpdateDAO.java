package com.drinkmixer.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.drinkmixer.domain.NewDrinkDomain;

public class CreateUpdateDAO extends DataDAO {
	
	/**
	 * INSERT INTO tblDrinks (glass_id,name,instructions,category_id,flagged,favorite,custom) "	+
			"values(2,'Darren Test 2','do some work and make a drink',1,0,0,1);
	 * @return
	 */
	public void insertNewDrink(String drinknm,long cat_id,long glassId,String instructions) {
		ContentValues values = new ContentValues();
		
			values.put(COL_NAME, drinknm);
			values.put(COL_CAT_ID, cat_id);
			values.put(COL_GLASS_ID, glassId);
			values.put(COL_INSTUCTIONS, instructions);
			values.put(COL_FLAGGED, 0);
			values.put(COL_FAVORITE, 0);
			values.put(COL_CUSTOM, 1);
			
			sqliteDatabase.insert(TABLE_DRINK, null, values);
			
			Cursor cursor = sqliteDatabase.rawQuery(sqlGetMaxId, new String[]{});
			cursor.moveToFirst();
			String newDrinkId = cursor.getString(cursor.getColumnIndex(DataDAO.COL_ROW_ID));
			
			NewDrinkDomain ndd = NewDrinkDomain.getInstance();
			ndd.newDrinkId = newDrinkId;
	}
	
	public void updateDrink(NewDrinkDomain ndd)
	{
		String[] selectionArgs = {ndd.drink_id+""};
		ContentValues values = new ContentValues();
		values.put(COL_NAME, ndd.drinkName);
		values.put(COL_INSTUCTIONS, ndd.instructions);
		values.put(COL_GLASS_ID, ndd.glassId);
		values.put(COL_CAT_ID, ndd.categoryId);
		
		sqliteDatabase.update(TABLE_DRINK, values, COL_ROW_ID+"=?",selectionArgs);
		//blow away ings before readding
		sqliteDatabase.delete(TABLE_DRINK_INGREDIENTS, COL_DRINK_ID+"=?",selectionArgs);
	}
	
	public void updateDrinkIngs(NewDrinkDomain ndd,String amount)
	{
		ContentValues values = new ContentValues();
		
		values.put(COL_DRINK_ID, ndd.drink_id);
		values.put(COL_INGREDIENT_ID, ndd.newing_id);
		values.put(COL_AMOUNT, amount);
		
		sqliteDatabase.insert(TABLE_DRINK_INGREDIENTS, null, values);
	}
	
	/**
	 * INSERT INTO tblDrinks_ingredients (drink_id,ingredient_id,amount) " +
	"values(9637,1,'2 oz');
	 * @return
	 */
	public long insertDrinkIng(String drink_id,String ing_id, String amount){
		
		ContentValues values = new ContentValues();
		
		values.put(COL_DRINK_ID, drink_id);
		values.put(COL_INGREDIENT_ID, ing_id);
		values.put(COL_AMOUNT, amount);
		
		return sqliteDatabase.insert(TABLE_DRINK_INGREDIENTS, null, values);
	}
	
	/**
	 * get the ingredient id from given name
	 * Oct 27, 2009
	 * dmason
	 * @param ingredientsName
	 *
	 */
	public void getIngredientsId(String ingredientsName)
	{
		String[] selectionArgs = {ingredientsName};
		Cursor c = sqliteDatabase.rawQuery(sqlGetNewIngredientsIdByName, selectionArgs);
		c.moveToFirst();
		String ing_id = c.getString(c.getColumnIndex(DataDAO.COL_ROW_ID));
		
		NewDrinkDomain.getInstance().newing_id =ing_id;
	}
	
}
