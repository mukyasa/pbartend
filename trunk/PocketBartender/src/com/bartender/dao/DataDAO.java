package com.bartender.dao;


import android.database.sqlite.SQLiteDatabase;

public class DataDAO{

	protected SQLiteDatabase sqliteDatabase;
	
	public static final int INTENT_NEXT_SCREEN = 0;
	public static final String INTENT_EXTRA_SELECTED_ROW = "SELECTED_ROW";
	
	public static final String FAV_NO = "no";
	public static final String FAV_YES = "yes";
	
	public static final String COL_TYPE = "drinktype";
	public static final String COL_ROW_ID = "_id";
	//drink cols
	public static final String COL_ROW_DRINK_NAME = "drinkName";
	public static final String COL_ROW_DRINK_TYPE = "drinktype";
	public static final String COL_ROW_GLASS = "drinkglass";
	public static final String COL_ROW_ING1 = "ing1"; 
	public static final String COL_ROW_ING2 = "ing2";
	public static final String COL_ROW_ING3 = "ing3";
	public static final String COL_ROW_INGREDIENTS = "ingredients";
	public static final String COL_ROW_INSTUCTIONS = "instructions";
	public static final String COL_ROW_INSTRUCTIONS2 = "instructions2";
	public static final String COL_ROW_FAV = "favorites";

	public static final String SQL_TYPE_TABLE_NAME = "tblDrinkType";
	public static final String SQL_DRINK_TABLE_NAME = "tblDrinks";
	public static final String SQL_INGREDIENTS_TABLE_NAME = "tblIng";
	
	public static final String sqlGetDrinkDetailById ="SELECT d.*, dt.drinktype FROM "+SQL_DRINK_TABLE_NAME+" d inner join tblDrinkType dt on d.drinkType = dt."+COL_ROW_ID+" where d."+COL_ROW_ID+"=?;";
	public static final String sqlGetDrinkDetailByDrinkName ="SELECT d.*, dt.drinktype FROM "+SQL_DRINK_TABLE_NAME+" d inner join tblDrinkType dt on d.drinkType = dt."+COL_ROW_ID+" where d."+COL_ROW_DRINK_NAME+"=?;";
	public static final String sqlGetDrinkDetailByDrinkTypeName ="SELECT d.*, dt.drinktype  FROM "+SQL_DRINK_TABLE_NAME+" d inner join tblDrinkType dt on d.drinkType = dt."+COL_ROW_ID+" where dt.drinktype=?;";
	public static final String sqlGetDrinkByTypeId = "SELECT d.*, dt.drinktype FROM "+SQL_DRINK_TABLE_NAME+" d inner join tblDrinkType dt on d.drinkType = dt."+COL_ROW_ID+" where dt."+COL_ROW_ID+"=?";
	public static final String sqlGetAllFavorites = "SELECT * from "+SQL_DRINK_TABLE_NAME+" where favorites =?";
	//search filter sql
	public static final String sqlGetAllFavoritesFilter = "SELECT * FROM "+SQL_DRINK_TABLE_NAME+" where favorites ='"+FAV_YES+"' and "+COL_ROW_DRINK_NAME+" like ?";
	public static final String sqlGetAllDrinksFilter = "SELECT * FROM "+SQL_DRINK_TABLE_NAME+" where "+COL_ROW_DRINK_NAME+" like ?";
	public static final String sqlGetAllCategoriesFilter = "SELECT * FROM "+ SQL_TYPE_TABLE_NAME + " where  "+COL_ROW_DRINK_TYPE+" like ?";
	
	public static final String sqlCreateDrinkTypeTable = "CREATE TABLE "
		+ SQL_TYPE_TABLE_NAME + " "
		+ " ("+COL_ROW_ID+" integer primary key autoincrement, " 
		+ COL_TYPE + " text not null); ";
	
	public static final String sqlCreateDrinksTable = "CREATE TABLE "
			+ SQL_DRINK_TABLE_NAME 
			+ " ("+COL_ROW_ID+" integer primary key autoincrement, "
			+ COL_ROW_DRINK_NAME+" VARCHAR(255), "
			+ COL_ROW_DRINK_TYPE+" INT, " 
			+ COL_ROW_GLASS+" VARCHAR(255), "
			+ COL_ROW_ING1+" VARCHAR(255), " 
			+ COL_ROW_ING2+" VARCHAR(255), "
			+ COL_ROW_ING3+" VARCHAR(255), " 
			+ COL_ROW_INGREDIENTS+" TEXT, "
			+ COL_ROW_INSTUCTIONS+" TEXT, " 
			+ COL_ROW_INSTRUCTIONS2+" TEXT, "
			+ COL_ROW_FAV+" VARCHAR(50), id AUTOINC);";
	
	public static final String sqlCreateIngTable = "CREATE TABLE " +SQL_INGREDIENTS_TABLE_NAME
			+ " ("+COL_ROW_ID+" integer primary key autoincrement, " 
			+ "id AUTOINC, "
			+ COL_ROW_ING1 + "  VARCHAR(255));";
	
	/**
	 * Sets the sqlite db name
	 * 
	 * @param sqliteDatabase
	 */
	public void setSQLiteDatabase(SQLiteDatabase sqliteDatabase) {
		this.sqliteDatabase = sqliteDatabase;
	}
	
	
}

