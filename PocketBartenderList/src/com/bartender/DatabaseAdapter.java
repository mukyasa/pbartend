package com.bartender;

import java.util.Arrays;

import com.bartender.dao.DrinkDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DatabaseAdapter extends SQLiteOpenHelper {

	private static SQLiteDatabase sqliteDb;
	private static DatabaseAdapter instance; //for singleton
	
	private static final String DATABASE_NAME = "pBartender";
	private static final int DATABASE_VERSION = 1;
	
	public DatabaseAdapter(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//create drink type tbl
		db.execSQL(DrinkDAO.sqlCreateDrinkTypeTable);
		createTypesData(db);
		//create drinks
		db.execSQL(DrinkDAO.sqlCreateDrinksTable);
		DrinkInserts di = new DrinkInserts();
		for(int i=0;i<di.sqlInsertDrinks.length;i++)
			db.execSQL(di.sqlInsertDrinks[i]);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		  Log.w(getClass().getSimpleName(), "Upgrading database from version " + oldVersion + " to "
	                + newVersion + ", which will destroy all old data");
		  
		  db.execSQL("DROP TABLE IF EXISTS " + DrinkDAO.SQL_TYPE_TABLE_NAME );
		  db.execSQL("DROP TABLE IF EXISTS " + DrinkDAO.SQL_DRINK_TABLE_NAME );
	      onCreate(db);
	}

	/**
	 * creates the drink types in the database
	 * @param db
	 */
	void createTypesData(SQLiteDatabase db) {
		String[] types = { "Beer", "Fix", "Fizz", "Flip",
				"Highball", "Holiday", "Martini", "Mixed", "Non Alcoholic",
				"Other", "Pousse Cafe", "Blender", "Punch", "Rickey",
				"Sangaree", "Shooter", "Sling", "Smash", "Sour", "Toddy",
				"Wine", "Cobbler", "Cocktail", "Coffee", "Collins", "Cooler",
				"Crusta", "Daisy" };
		Arrays.sort(types);

		ContentValues values;
		
		for(int i=1;i<types.length;i++)
		{
			values = new ContentValues();
			values.put(DrinkDAO.COL_TYPE, types[i]); 
			values.put(DrinkDAO.COL_ID, i);
			db.insert(DrinkDAO.SQL_TYPE_TABLE_NAME, null, values);
		}
		 
	}

	
	//singleton initialize
	private static void initialize(Context context) {
		if(instance == null) {
			instance = new DatabaseAdapter(context, DATABASE_NAME, null, DATABASE_VERSION);
			sqliteDb = instance.getWritableDatabase();
		}
	}
	
	/**
	 * Pubic method to instantiate class
	 * @param context
	 * @return
	 */
	public static final DatabaseAdapter getInstance(Context context) {
		initialize(context);
		return instance;
	}
	
	/**
	 * gets the database we are using
	 * @return
	 */
	public SQLiteDatabase getDatabase() {
		return sqliteDb;	
	}
	
	/**
	 * closes instance of database
	 */
	public void close() {
		if(instance != null ) {
			instance.close();
			instance = null;
		}
	}

}
