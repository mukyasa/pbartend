package com.mixer.dao;


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
	
	
	public static final String sqlGetAllDrinksAndGlass ="SELECT g.name["+COL_GLASS_NAME+"],d.name,d._id FROM "+TABLE_DRINK+" d INNER JOIN "+TABLE_GLASSES+" g on g._id = d.glass_id";
	
	public static final String sqlGetMaxId ="SELECT MAX(_ID)["+COL_ROW_ID+"]  FROM "+TABLE_DRINK;
	public static final String sqlGetGlassNameById = "SELECT name["+COL_GLASS_NAME+"] from "+TABLE_GLASSES+" WHERE _id=?";
	public static final String sqlGetDrinkDetailById ="SELECT d.favorite, d._id, d.name,d.instructions,dc.name["+COL_CAT_NAME+"],di.amount,i.name["+COL_ING_NAME+"],g.name["+COL_GLASS_NAME+"] " +
			"FROM "+TABLE_DRINK+" d " +
			"INNER JOIN "+TABLE_DRINK_CAT+" dc on dc._id = d.category_id " +
			"INNER JOIN "+TABLE_DRINK_INGREDIENTS+" di on di.drink_id = d._id " +
			"INNER JOIN "+TABLE_INGREDIENTS+" i on di.ingredient_id = i._id " + 
			"INNER JOIN "+TABLE_GLASSES+" g on d.glass_id = g._id " +
			"WHERE d._id =?;";
	
	public static final String sqlGetDrinkDetailByDrinkName ="SELECT d.name,d._id FROM "+TABLE_DRINK+" d where d."+COL_NAME+"=?;";
	public static final String sqlGetDrinkDetailByDrinkCatName ="SELECT d.name,d._id FROM "+TABLE_DRINK+" d INNER JOIN tblDrinkType dt on d.drinkType = dt."+COL_ROW_ID+" where dt.drinktype=?;";
	public static final String sqlGetAllFavorites = "SELECT d.name,d._id,d.favorite FROM "+TABLE_DRINK+" d where favorite =?";
	
	public static final String sqlGetDrinksByDrinkCatId = "SELECT  d.name,d._id,g.name["+COL_GLASS_NAME+"] " +
			"FROM "+TABLE_DRINK+" d " +
			"INNER JOIN "+TABLE_GLASSES+" g on g._id = d.glass_id "+
			"INNER JOIN "+TABLE_DRINK_CAT+" dc on dc._id = d.category_id " +
			"WHERE dc._id =?";
	
	//search filter sql
	public static final String sqlGetAllFavoritesFilter = "SELECT d.name,d._id,d.favorite, g.name["+COL_GLASS_NAME+"] FROM "+TABLE_DRINK+" d INNER JOIN "+TABLE_GLASSES+" g on g._id = d.glass_id WHERE favorite ="+FAV_YES+" and d."+COL_NAME+" like ?";
	
	public static final String sqlGetAllDrinksFilter = "SELECT g.name["+COL_GLASS_NAME+"],d.name,d._id FROM "+TABLE_DRINK+" d INNER JOIN "+TABLE_GLASSES+" g on g._id = d.glass_id WHERE d."+COL_NAME+" like ?";
	public static final String sqlGetAllCategoriesFilter = "SELECT d.name,d._id FROM "+ TABLE_DRINK_CAT + " d  WHERE  "+COL_NAME+" like ?";
	public static final String sqlGetAllDringksFilterCategories = "SELECT g.name["+COL_GLASS_NAME+"],d.name,d._id FROM "+TABLE_DRINK+" d INNER JOIN "+TABLE_GLASSES+" g on g._id = d.glass_id WHERE d."+COL_NAME+" like ? AND category_id=?";
	
	public static final String sqlGetAllIngredients = "SELECT i.name["+COL_CAT_NAME+"],i._id " +
			"FROM "+TABLE_INGREDIENTS_CAT+" ic  " +
			"INNER JOIN "+TABLE_INGREDIENTS+" i on ic._id = i.category_id " + 
			"INNER JOIN "+TABLE_INGREDIENTS_SUB_CAT+" isc on i.category_id = isc._id where ic.name=? order by i.name";
	
	public static final String sqlGetAllIngredientsFilter = "SELECT i._id,i.name["+COL_CAT_NAME+"] " +
			"FROM "+TABLE_INGREDIENTS_CAT+" ic  " +
			"INNER JOIN "+TABLE_INGREDIENTS+" i on ic._id = i.category_id " +
			"INNER JOIN "+TABLE_INGREDIENTS_SUB_CAT+" isc on i.category_id = isc._id " +
			"WHERE ic.name=? and i.name like ? order by i.name";
	
	public static final String sqlGetAllDrinksByIngredients= "SELECT d.name,d._id,g.name["+COL_GLASS_NAME+"] "
	+"FROM "+TABLE_DRINK+" d "
	+"INNER JOIN "+TABLE_DRINK_CAT+" dc on dc._id = d.category_id "
	+"INNER JOIN "+TABLE_DRINK_INGREDIENTS+" di on di.drink_id = d._id "
	+"INNER JOIN "+TABLE_INGREDIENTS+" i on di.ingredient_id = i._id "
	+"INNER JOIN "+TABLE_GLASSES+" g on d.glass_id = g._id "
	+"INNER JOIN "+TABLE_INGREDIENTS_CAT+" ic on ic._id = i.category_id "
	+"WHERE i._id =? and ic.name=? order by d.name";
	
	public static final String sqlGetAllMeasurements="SELECT fraction FROM " + TABLE_FRACTIONAL_AMOUNTS + ";";
	
	public static final String sqlInsertNewDrink="INSERT INTO tblDrinks (glass_id,name,instructions,category_id,flagged,favorite,custom) "	+
			"values(2,'Darren Test 2','do some work and make a drink',1,0,0,1);";

	//for each ingredient need to insert
	public static final String sqlInsertNewDrinkIngredients ="INSERT INTO tblDrinks_ingredients (drink_id,ingredient_id,amount) " +
			"values(9637,1,'2 oz');";
	
	// get new drinkid
	public static final String sqlGetNewDrinkIdByName ="SELECT _id from tblDrinks where name ='Darren Test 2';";
	
	
	// get ingredients ids could be many
	public static final String sqlGetNewIngredientsIdByName ="SELECT "+COL_ROW_ID+" from "+TABLE_INGREDIENTS+" where name=?";
	
	

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

