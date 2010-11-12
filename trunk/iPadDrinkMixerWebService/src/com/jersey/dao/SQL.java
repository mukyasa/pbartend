package com.jersey.dao;



public class SQL{ 

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
	public static final String COL_CABINET ="cabinet";
	public static final String COL_INGREDIENT_ID ="ingredient_id";
	public static final String COL_SHARED_INGREDIENT_ID ="shared_ingredient_id";
	public static final String COL_AMOUNT="amount";
	public static final String COL_RATING="rating";
	public static final String COL_UID="uid";
	public static final String COL_IMG="img";
	public static final String COL_IMG_LOC="img_loc";
	

	//table names
	//public static final String SQL_TYPE_TABLE_NAME = "tblDrinkType";
	public static final String TABLE_DRINK_CAT = "tblDrink_categories";
	public static final String TABLE_DRINK = "tblDrinks";
	public static final String TABLE_SHARED_DRINK = "tblShared";
	public static final String TABLE_SHARED_DRINK_INGREDIENTS = "tblShared_ingredients";
	public static final String TABLE_INGREDIENTS = "tblIngredients";
	public static final String TABLE_DRINK_INGREDIENTS = "tblDrinks_ingredients";
	public static final String TABLE_DRINK_SUB_CAT = "tblDrinks_subcategories";
	public static final String TABLE_FRACTIONAL_AMOUNTS = "tblFractional_amounts";
	public static final String TABLE_GLASSES = "tblGlasses";
	public static final String TABLE_INGREDIENTS_CAT ="tblIngredient_categories";
	public static final String TABLE_INGREDIENTS_SUB_CAT ="tblIngredient_subcategories";
	public static final String TABLE_RATINGS ="tblRating";
	
	/****** SHARED *******/
	public static final String sqlSetRating = "INSERT INTO "+TABLE_RATINGS+" (drink_id, rating,ip_address,uid,version,name) VALUES (?,?,?,?,?,?)";
	public static final String sqlGetAllSharedDrinksAndGlass ="SELECT g.name AS "+COL_GLASS_NAME+",d.name,d._id FROM "+TABLE_SHARED_DRINK+" d INNER JOIN "+TABLE_GLASSES+" g on g._id = d.glass_id";
	public static final String sqlGetSharedDrinkDetailById ="SELECT d."+COL_IMG+", di."+COL_INGREDIENT_ID+" AS "+COL_SHARED_INGREDIENT_ID+", d.uid, d._id, d.name,d.instructions,dc.name  AS "+
	COL_CAT_NAME+",di.amount,i.name AS "+COL_ING_NAME+",g.name AS "+
	COL_GLASS_NAME+" ,g._id AS "+COL_GLASS_ID+",dc._id AS "+COL_CAT_ID+" " +
	"FROM "+TABLE_SHARED_DRINK+" d " +
	"INNER JOIN "+TABLE_DRINK_CAT+" dc on dc._id = d.category_id " +
	"INNER JOIN "+TABLE_SHARED_DRINK_INGREDIENTS+" di on di.drink_id = d._id " +
	"INNER JOIN "+TABLE_INGREDIENTS+" i on di.ingredient_id = i._id " + 
	"INNER JOIN "+TABLE_GLASSES+" g on d.glass_id = g._id " +
	"WHERE d._id =?;";
	
	public static final String sqlGetAllSharedDrinksAndGlassById ="SELECT d.favorite, g.name AS "+COL_GLASS_NAME+",d.name,d._id FROM "+TABLE_SHARED_DRINK+" d INNER JOIN "+TABLE_GLASSES+" g on g._id = d.glass_id WHERE ";
	
	public static final String sqlCreateSharedDrink ="INSERT INTO tblShared (glass_id, name,instructions,category_id,uid,img) VALUES (?,?,?,?,?,?)";
	public static final String sqlCreateSharedDrinkIngredients ="INSERT INTO tblShared_ingredients(drink_id,ingredient_id,amount) VALUES (?,?,?)";
	public static final String sqlUpdateSharedDrink ="UPDATE tblShared SET "+COL_GLASS_ID+"=?, "+COL_NAME+"=?, "+COL_INSTUCTIONS+"=?, "+COL_IMG+"=?, "+COL_CAT_ID+"=? WHERE "+COL_ROW_ID+"=?";
	/*********************/
	
	public static final String sqlGetAllDrinksAndGlass ="SELECT d.favorite, g.name AS "+COL_GLASS_NAME+",d.name,d._id FROM "+TABLE_DRINK+" d INNER JOIN "+TABLE_GLASSES+" g on g._id = d.glass_id";
	public static final String sqlGetAllDrinksAndGlassById ="SELECT d.favorite, g.name AS "+COL_GLASS_NAME+",d.name,d._id FROM "+TABLE_DRINK+" d INNER JOIN "+TABLE_GLASSES+" g on g._id = d.glass_id WHERE ";
	
	public static final String sqlGetMaxId ="SELECT MAX(_ID) AS "+COL_ROW_ID+"  FROM "+TABLE_SHARED_DRINK;
	public static final String sqlGetGlassNameById = "SELECT name AS "+COL_GLASS_NAME+" from "+TABLE_GLASSES+" WHERE _id=?";
	public static final String sqlGetDrinkDetailById ="SELECT d."+COL_IMG_LOC + ", di."+COL_INGREDIENT_ID+" AS "+COL_SHARED_INGREDIENT_ID+", d.uid,d.favorite,d._id, d.name,d.instructions,dc.name  AS "+
			COL_CAT_NAME+",di.amount,i.name AS "+COL_ING_NAME+",g.name AS "+
			COL_GLASS_NAME+" ,g._id AS "+COL_GLASS_ID+",dc._id AS "+COL_CAT_ID+" " +
			"FROM "+TABLE_DRINK+" d " +
			"INNER JOIN "+TABLE_DRINK_CAT+" dc on dc._id = d.category_id " +
			"INNER JOIN "+TABLE_DRINK_INGREDIENTS+" di on di.drink_id = d._id " +
			"INNER JOIN "+TABLE_INGREDIENTS+" i on di.ingredient_id = i._id " + 
			"INNER JOIN "+TABLE_GLASSES+" g on d.glass_id = g._id " +
			"WHERE d._id =?;";
	
	public static final String sqlGetDrinkDetailByDrinkName ="SELECT d.name,d._id FROM "+TABLE_DRINK+" d where d."+COL_NAME+"=?;";
	public static final String sqlGetDrinkDetailByDrinkCatName ="SELECT d.name,d._id FROM "+TABLE_DRINK+" d INNER JOIN tblDrinkType dt on d.drinkType = dt."+COL_ROW_ID+" where dt.drinktype=?;";
	public static final String sqlGetAllFavorites = "SELECT d.name,d._id,d.favorite FROM "+TABLE_DRINK+" d where favorite =?";
	
	public static final String sqlGetDrinksByDrinkCatId = "SELECT  d.favorite, d.name,d._id,g.name AS "+COL_GLASS_NAME+" " +
			"FROM "+TABLE_DRINK+" d " +
			"INNER JOIN "+TABLE_GLASSES+" g on g._id = d.glass_id "+
			"INNER JOIN "+TABLE_DRINK_CAT+" dc on dc._id = d.category_id " +
			"WHERE dc._id =?";
	
	//search filter sql
	public static final String sqlGetAllFavoritesFilter = "SELECT d.name,d._id,d.favorite, g.name AS "+COL_GLASS_NAME+" FROM "+TABLE_DRINK+" d INNER JOIN "+TABLE_GLASSES+" g on g._id = d.glass_id WHERE favorite ="+FAV_YES+" and d."+COL_NAME+" like ?";
	
	public static final String sqlGetAllDrinksFilter = "SELECT d.favorite, g.name AS "+COL_GLASS_NAME+",d.name,d._id FROM "+TABLE_DRINK+" d INNER JOIN "+TABLE_GLASSES+" g on g._id = d.glass_id WHERE d."+COL_NAME+" like ?";
	public static final String sqlGetAllCategoriesFilter = "SELECT d.name,d._id FROM "+ TABLE_DRINK_CAT + " d  WHERE  "+COL_NAME+" like ?";
	public static final String sqlGetAllDringksFilterCategories = "SELECT g.name AS "+COL_GLASS_NAME+",d.name,d._id FROM "+TABLE_DRINK+" d INNER JOIN "+TABLE_GLASSES+" g on g._id = d.glass_id WHERE d."+COL_NAME+" like ? AND category_id=?";
	
	public static final String sqlGetAllIngredients = "SELECT i.name AS "+COL_CAT_NAME+",i._id " +
			"FROM "+TABLE_INGREDIENTS_CAT+" ic  " +
			"INNER JOIN "+TABLE_INGREDIENTS+" i on ic._id = i.category_id " + 
			"INNER JOIN "+TABLE_INGREDIENTS_SUB_CAT+" isc on i.category_id = isc._id where ic.name=? order by i.name";
	
	public static final String sqlGetAllIngredientsFilter = "SELECT i._id,i.name AS "+COL_CAT_NAME+" " +
			"FROM "+TABLE_INGREDIENTS_CAT+" ic  " +
			"INNER JOIN "+TABLE_INGREDIENTS+" i on ic._id = i.category_id " +
			"INNER JOIN "+TABLE_INGREDIENTS_SUB_CAT+" isc on i.category_id = isc._id " +
			"WHERE ic.name=? and i.name like ? order by i.name";
	
	public static final String sqlGetAllDrinksByIngredients= "SELECT d.favorite, d.name,d._id,g.name AS "+COL_GLASS_NAME+" "
	+"FROM "+TABLE_DRINK+" d "
	+"INNER JOIN "+TABLE_DRINK_CAT+" dc on dc._id = d.category_id "
	+"INNER JOIN "+TABLE_DRINK_INGREDIENTS+" di on di.drink_id = d._id "
	+"INNER JOIN "+TABLE_INGREDIENTS+" i on di.ingredient_id = i._id "
	+"INNER JOIN "+TABLE_GLASSES+" g on d.glass_id = g._id "
	+"INNER JOIN "+TABLE_INGREDIENTS_CAT+" ic on ic._id = i.category_id "
	+"WHERE i._id =? and ic.name=? order by d.name";
	
	public static final String sqlGetAllMeasurements="SELECT fraction FROM " + TABLE_FRACTIONAL_AMOUNTS + ";";
	
	
	// get ingredients ids could be many
	public static final String sqlGetNewIngredientsIdByName ="SELECT "+COL_ROW_ID+" from "+TABLE_INGREDIENTS+" where name=?";
	

	/**** ADMIN SQL ***/
	public static final String sqlGetAllSharedAdmin ="SELECT * FROM "+TABLE_SHARED_DRINK;
	public static final String sqlGetAllRatingsAdmin ="SELECT * FROM "+TABLE_RATINGS;
	//delete
	public static final String sqlDeleteRatingByUIDAdmin ="DELETE FROM "+TABLE_RATINGS +" WHERE "+ COL_UID +"=?";
	public static final String sqlDeleteSharedByIdAdmin ="DELETE FROM "+TABLE_SHARED_DRINK +" WHERE "+ COL_ROW_ID +"=?";
	
	
	
	
	
}

