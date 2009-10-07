package com.bartender.dao;


import android.database.sqlite.SQLiteDatabase;

public class DataDAO{

	protected SQLiteDatabase sqliteDatabase;
	
	public static final int INTENT_NEXT_SCREEN = 0;
	public static final String INTENT_EXTRA_SELECTED_ROW = "SELECTED_ROW";
	
	public static final String COL_TYPE = "drinktype";
	public static final String COL_ID = "id";
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
	public static final String COL_ROW_DRINK_ID = "id";
	

	public static final String SQL_TYPE_TABLE_NAME = "tblDrinkType";
	public static final String SQL_DRINK_TABLE_NAME = "tblDrinks";
	public static final String SQL_INGREDIENTS_TABLE_NAME = "tblIng";
	public static final String sqlCreateDrinkTypeTable = "CREATE TABLE "
			+ SQL_TYPE_TABLE_NAME + " "
			+ " ("+COL_ROW_ID+" integer primary key autoincrement, " 
			+ COL_TYPE + " text not null, " 
			+ COL_ID + " int not null); ";
	
	public static final String sqlGetDrinkDetailById ="SELECT * FROM tblDrinks d inner join tblDrinkType dt on d.drinkType = dt.id where d.id=?;";
	public static final String sqlGetDrinkDetailByDrinkName ="SELECT * FROM tblDrinks d inner join tblDrinkType dt on d.drinkType = dt.id where d.drinkName=?;";
	public static final String sqlGetDrinkDetailByDrinkTypeName ="SELECT * FROM tblDrinks d inner join tblDrinkType dt on d.drinkType = dt.id where dt.drinktype=?;";
	public static final String sqlGetDrinkByTypeId = "SELECT *  FROM tblDrinks d inner join tblDrinkType dt on d.drinkType = dt.id where dt.id=?";

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
			+ COL_ROW_FAV+" VARCHAR(50), "
			+ COL_ROW_DRINK_ID + " AUTOINC NOT NULL ON CONFLICT ROLLBACK);";
	
	public static final String sqlCreateIngTable = "CREATE TABLE " +SQL_INGREDIENTS_TABLE_NAME
			+ " ("+COL_ROW_ID+" integer primary key autoincrement, " 
			+ COL_ROW_DRINK_ID + " AUTOINC NOT NULL ON CONFLICT ROLLBACK, "
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
