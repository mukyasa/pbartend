package com.bartender.dao;


import android.database.sqlite.SQLiteDatabase;

public class DataDAO{ 

	protected SQLiteDatabase sqliteDatabase;
	
	public static final int INTENT_NEXT_SCREEN = 0;
	public static final String INTENT_EXTRA_SELECTED_ROW = "SELECTED_ROW";
	
	public static final String FAV_NO = "0";
	public static final String FAV_YES = "1";
	
	public static final String COL_ROW_ID = "_id";
	//new drink db cols
	public static final String COL_GLASS_ID = "glass_id";
	public static final String COL_DRINK_ID = "drink_id";
	public static final String COL_NAME = "name";
	public static final String COL_GLASS_NAME="glass_name";
	public static final String COL_CAT_NAME="cat_name";
	public static final String COL_INSTUCTIONS = "instructions";
	public static final String COL_CAT_ID = "category_id";
	public static final String COL_SUB_CAT_ID = "subcategory_id";
	public static final String COL_FLAGGED = "flagged";
	public static final String COL_FAVORITE = "favorite";
	public static final String COL_ING_NAME = "ingname";
	public static final String COL_CUSTOM = "custom";
	public static final String COL_DESCRIPTION = "description";
	public static final String COL_LIQUOR_SUB_CAT_ID = "liquor_subcategory_id";
	public static final String COL_MIX_SUB_CAT_ID = "mixer_subcategory_id";
	public static final String COL_FRACTION = "fraction";
	public static final String COL_NUMBER = "number";
	public static final String COL_CABINET ="cabinet";
	public static final String COL_INGREDIENT_ID ="ingredient_id";
	public static final String COL_AMOUNT="amount";
	public static final String COL_AMOUNT_METRIC="amount_metric";
	

	//table names
	//public static final String SQL_TYPE_TABLE_NAME = "tblDrinkType";
	public static final String TABLE_DRINK_CAT = "tblDrink_categories";
	public static final String TABLE_DRINK = "tblDrinks";
	public static final String TABLE_INGREDIENTS = "tblIngredients";
	public static final String TABLE_DRINK_INGREDIENTS = "tblDrinks_ingredients";
	public static final String TABLE_DRINK_SUB_CAT = "tblDrinks_subcategories";
	public static final String TABLE_FRACTIONAL_AMOUNTS = "tblFractional_amounts";
	public static final String TABLE_GLASSES = "tblGlasses";
	public static final String TABLE_INGREDIENTS_CAT ="tblIngredient_categories";
	public static final String TABLE_INGREDIENTS_SUB_CAT ="tblIngredient_subcategories";
	
	
	public static final String sqlGetDrinkDetailById ="SELECT d.favorite, d._id, d.name,d.instructions,dc.name[cat_name],di.amount,i.name[ingname],g.name[glass_name] " +
			"FROM tblDrinks d INNER JOIN tblDrink_categories dc on dc._id = d.category_id " +
			"INNER JOIN tblDrinks_ingredients di on di.drink_id = d._id " +
			"INNER JOIN tblIngredients i on di.ingredient_id = i._id " +
			"INNER JOIN tblGlasses g on d.glass_id = g._id " +
			"WHERE d._id =?;";
	
	public static final String sqlGetDrinkDetailByDrinkName ="SELECT d.* FROM "+TABLE_DRINK+" d where d."+COL_NAME+"=?;";
	public static final String sqlGetDrinkDetailByDrinkCatName ="";//SELECT d.*, dt.drinktype  FROM "+TABLE_DRINK+" d inner join tblDrinkType dt on d.drinkType = dt."+COL_ROW_ID+" where dt.drinktype=?;";
	public static final String sqlGetDrinkByCatId = "";//SELECT d.*, dt.drinktype FROM "+TABLE_DRINK+" d inner join tblDrinkType dt on d.drinkType = dt."+COL_ROW_ID+" where dt."+COL_ROW_ID+"=?";
	public static final String sqlGetAllFavorites = "SELECT * from "+TABLE_DRINK+" where favorites =?";
	//search filter sql
	public static final String sqlGetAllFavoritesFilter = "";//SELECT * FROM "+TABLE_DRINK+" where favorites ='"+FAV_YES+"' and "+COL_ROW_DRINK_NAME+" like ?";
	public static final String sqlGetAllDrinksFilter = "";//SELECT * FROM "+TABLE_DRINK+" where "+COL_ROW_DRINK_NAME+" like ?";
	public static final String sqlGetAllCategoriesFilter = "";//SELECT * FROM "+ SQL_TYPE_TABLE_NAME + " where  "+COL_ROW_DRINK_TYPE+" like ?";
	
	public static final String sqlGetAllIngredients = "SELECT i.name,i._id " +
			"FROM "+TABLE_INGREDIENTS_CAT+" ic  " +
			"INNER JOIN "+TABLE_INGREDIENTS+" i on ic._id = i.category_id " +
			"INNER JOIN "+TABLE_INGREDIENTS_SUB_CAT+" isc on i.category_id = isc._id where ic.name=? order by i.name";
	
	public static final String sqlGetAllIngredientsFilter = "SELECT *,i.name["+COL_CAT_NAME+"] " +
			"FROM "+TABLE_INGREDIENTS_CAT+" ic  " +
			"INNER JOIN "+TABLE_INGREDIENTS+" i on ic._id = i.category_id " +
			"INNER JOIN "+TABLE_INGREDIENTS_SUB_CAT+" isc on i.category_id = isc._id " +
			"WHERE ic.name=? and i.name like ? order by i.name";
	
	public static final String sqlGetAllDrinksByIngredients= "SELECT  distinct d.* " +
			"FROM tblDrinks d " +
			"INNER JOIN tblDrinks_subcategories dsc on d._id = dsc.drink_id " +
			"INNER JOIN tblIngredients i on i.subcategory_id = dsc.liquor_subcategory_id " +
			"OR i.subcategory_id = dsc.mixer_subcategory_id " +
			"INNER JOIN tblIngredient_categories ic on ic._id = i.category_id " +
			"WHERE ic.name = ? " +
			"AND i._id=? ORDER BY i.name";

	/**** CREATE STATEMENTS*****/
	public static final String sqlDrinkIngredientsTable="CREATE TABLE " + TABLE_DRINK_INGREDIENTS
			  + " ("+COL_ROW_ID+" integer PRIMARY KEY autoincrement, "
			  +COL_DRINK_ID+" INT, "
			  + COL_INGREDIENT_ID + " INT , "
			  + COL_AMOUNT + " VARCHAR(64) , " 
			  + COL_AMOUNT_METRIC + " VARCHAR(64));";
	
	public static final String sqlIngredientsSubCatTable="CREATE TABLE " + TABLE_INGREDIENTS_SUB_CAT
		+ " ("+COL_ROW_ID+" integer PRIMARY KEY autoincrement, "
		+ COL_CAT_ID + " INTEGER, "
		+ COL_NAME +" VARCHAR(32), "
		+ COL_CABINET +" bit default 0);";
	
	public static final String sqlIngredientsCatTable="CREATE TABLE " + TABLE_INGREDIENTS_CAT
		+ " ("+COL_ROW_ID+" integer PRIMARY KEY autoincrement, "
		+ COL_NAME + " VARCHAR(64) NOT NULL);";
	
	public static final String sqlGlassesTable = "CREATE TABLE " + TABLE_GLASSES
		+ " ("+COL_ROW_ID+" integer PRIMARY KEY autoincrement, "
		+ COL_NAME + " VARCHAR(64) NOT NULL);";
	
	public static final String sqlFractional_amountsTable = "CREATE TABLE "+ TABLE_FRACTIONAL_AMOUNTS 
		+ " ("+COL_ROW_ID+" integer PRIMARY KEY autoincrement, "
			+ COL_FRACTION +" VARCHAR(8), "
			+ COL_NUMBER + " double);";
	
	public static final String sqlDrinkSubCategoriesTable = "CREATE TABLE " + TABLE_DRINK_SUB_CAT	
			+ " ("+COL_DRINK_ID + " INTEGER NOT NULL, "
			+ COL_LIQUOR_SUB_CAT_ID + " INTEGER NOT NULL, "
			+ COL_MIX_SUB_CAT_ID + " INTEGER NOT NULL);";
	
	/**
	 * Drink Table
	 */
	public static final String sqlCreateDrinksTable = "CREATE TABLE " + TABLE_DRINK 
			+ " ("+COL_ROW_ID+" integer PRIMARY KEY autoincrement, " 
			+ COL_GLASS_ID+ " INTEGER , "
			+ COL_NAME + " VARCHAR(64) NOT NULL, "
			+ COL_INSTUCTIONS + " TEXT, "
			+ COL_CAT_ID+" INTEGER, "
			+ COL_FLAGGED +" bit, "
			+ COL_FAVORITE + " bit default 0, "
			+ COL_CUSTOM +" bit default 0);";

	/**
	 * Ingredients Table
	 */
	public static final String sqlCreateIngTable = "CREATE TABLE " +TABLE_INGREDIENTS
			+ " ("+COL_ROW_ID+" integer PRIMARY KEY autoincrement, " 
			+ COL_NAME+ " VARCHAR(64) NOT NULL, "
			+ COL_DESCRIPTION + " text NOT NULL, "
			+ COL_CAT_ID + " INTEGER, "
			+ COL_SUB_CAT_ID + " INTEGER);";
	
	/**
	 * Drink Category Table
	 */
	public static final String sqlCreateDrinkCategoriesTable = "CREATE TABLE " + TABLE_DRINK_CAT
		+ " ("+COL_ROW_ID+" integer PRIMARY KEY autoincrement, " 
		+ COL_NAME+ " VARCHAR(64) NOT NULL);";

	
	/**
	 * Sets the sqlite db name
	 * 
	 * @param sqliteDatabase
	 */
	public void setSQLiteDatabase(SQLiteDatabase sqliteDatabase) {
		this.sqliteDatabase = sqliteDatabase;
	}
	
	
}

