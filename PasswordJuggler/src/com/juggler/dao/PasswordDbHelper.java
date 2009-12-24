package com.juggler.dao;

import java.io.File;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

/**
 * 
 * This class helps open, create, and upgrade the database file. <br>
 * By default it's hard coded to store the DB on the SD card. Thread safety and
 * closure safety are pulled straight from the parent class for db management
 * for getReadable() and getWriteable()
 * 
 */

public class PasswordDbHelper extends SQLiteOpenHelper {
	public static SQLiteDatabase sqliteDb=null;
	private static PasswordDbHelper instance; //for singleton
	public static boolean isLocal=false;

	public static final String DATABASE_NAME = "pwjdb";
	public static final String DATABASE_EXTERNAL_FOLDER="pwj";
	public static final String DATABASE_PATH_EXTERNAL = "/sdcard/"+DATABASE_EXTERNAL_FOLDER+"/"+DATABASE_NAME;
	private static final String DATABASE_PATH_LOCAL = DATABASE_NAME;

	private boolean useLocal = false; 
	private String dbPathToUse = DATABASE_PATH_EXTERNAL;

	private static final int DATABASE_VERSION = 3;		

	// Sections lifted from the originating class SqliteOpenHelper.java
	private SQLiteDatabase mDatabase = null;
	private boolean mIsInitializing = false;

	
	/**
	 * Pubic method to instantiate class
	 * @param context
	 * @return
	 */
	public static final PasswordDbHelper getInstance(Context context) {
		initialize(context);
		return instance;
	}
	
	//singleton initialize
	private static void initialize(Context context) {
		
		if(instance == null) {
			instance = new PasswordDbHelper(context);
			sqliteDb = instance.getWritableDatabase();
		}
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
	
	
	public PasswordDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

		File sdcard = Environment.getExternalStorageDirectory();

		File destination = new File(sdcard, DATABASE_EXTERNAL_FOLDER);
		if (!isLocal)
			destination.mkdir();

		if (!isLocal && destination.exists()) {
			useLocal = false;
			dbPathToUse = DATABASE_PATH_EXTERNAL;
		} else {
			useLocal = true;
			dbPathToUse = DATABASE_PATH_LOCAL;
		}

	}

	/**
	 * Create the tables
	 */
	@Override
	public void onCreate(SQLiteDatabase db) { 
		
		db.execSQL(PasswordDAO.sqlCreatePasswordTable);
		db.execSQL(PasswordDAO.sqlCreateCatsTable);
		db.execSQL(PasswordDAO.sqlCreateNotesTable);
		db.execSQL(PasswordDAO.sqlCreatePasswordEntryTable);
		db.execSQL(PasswordDAO.sqlCreateSubCatTable);
		
		//populate cats and sub cats
		hydrateCategories(db);
		
	}
	
	private int subcatindex=0;
	private void hydrateSubCategories(SQLiteDatabase db, String [] catNames, int catId) {
		
		ContentValues values;
		//loop the sub cat array but the _id needs to be subcatindex
		for(int i=0;i<catNames.length;i++)
		{
			values = new ContentValues();
			values.put(PasswordDAO.COL_ID, subcatindex);
			values.put(PasswordDAO.COL_NAME, catNames[i]); 
			values.put(PasswordDAO.COL_CAT_ID, catId); 
			
			db.insert(PasswordDAO.TABLE_SUB_CATS, null, values);
			//next subcat index
			subcatindex++;
		}
		 
	}

	private void hydrateCategories(SQLiteDatabase db) {
		
		String[] baseCats = { "Computers", "Financial", "Government", "Internet", "Memberships" };
		String[] list=null;
		
		ContentValues values;
		
		for(int i=0;i<baseCats.length;i++)
		{
			values = new ContentValues();
			values.put(PasswordDAO.COL_ID, i);
			values.put(PasswordDAO.COL_NAME, baseCats[i]); 
			
			db.insert(PasswordDAO.TABLE_CATS, null, values);
			
			switch(i)
			{
				case 0:
					String[] list0 = { "Software License", "Database", "WiFi", "Server" };
					list = list0;break;
				case 1:
					String[] list1 = { "Credit Card", "Bank Account (US)", "Bank Account (CA)" };
					list = list1;break;
				case 2:
					String[] list2 = { "Passport", "Driver's License", "Social Security Number","Hunting License" };
					list = list2;break;
				case 3:
					String[] list3 = { "Email Account", "Instant Messenger","FTP","iTunes","ISP"};
					list = list3;break;
				case 4:
					String[] list4 = { "Rewards Program","Membership"};
					list = list4;break;
			}
			//do sub cat
			hydrateSubCategories(db,list,i);
		}
		 
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see android.database.sqlite.SQLiteOpenHelper#getReadableDatabase()
	 */
	@Override
	public synchronized SQLiteDatabase getReadableDatabase() {
		if(useLocal) {
			return super.getReadableDatabase();
		}
		
		
		if (mDatabase != null && mDatabase.isOpen()) {
			return mDatabase; // The database is already open for business
		}

		if (mIsInitializing) {
			throw new IllegalStateException("getReadableDatabase called recursively");
		}

		try {
			return getWritableDatabase();
		} catch (SQLiteException e) {
			//Log.e(TAG, "Couldn't open " + DATABASE_NAME + " for writing (will try read-only):", e);
		}

		SQLiteDatabase db = null;
		try {
			mIsInitializing = true;
			db = SQLiteDatabase.openDatabase(dbPathToUse, null, SQLiteDatabase.OPEN_READONLY);
			if (db.getVersion() != DATABASE_VERSION) {
				throw new SQLiteException("Can't upgrade read-only database from version " + db.getVersion() + " to "
						+ DATABASE_VERSION + ": " + dbPathToUse);
			}

			onOpen(db);
			//Log.w(TAG, "Opened " + DATABASE_NAME + " in read-only mode");
			mDatabase = db;
			return mDatabase;
		} finally {
			mIsInitializing = false;
			if (db != null && db != mDatabase)
				db.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.database.sqlite.SQLiteOpenHelper#getWritableDatabase()
	 */
	@Override
	public synchronized SQLiteDatabase getWritableDatabase() {
		if(useLocal) {
			return super.getWritableDatabase();
		}
		if (mDatabase != null && mDatabase.isOpen() && !mDatabase.isReadOnly()) {
			return mDatabase; // The database is already open for business
		}

		if (mIsInitializing) {
			throw new IllegalStateException("getWritableDatabase called recursively");
		}

		// If we have a read-only database open, someone could be using it
		// (though they shouldn't), which would cause a lock to be held on
		// the file, and our attempts to open the database read-write would
		// fail waiting for the file lock. To prevent that, we acquire the
		// lock on the read-only database, which shuts out other users.

		boolean success = false;
		SQLiteDatabase db = null;
		// if (mDatabase != null) mDatabase.lock(); //can't call the locks for
		// some reason. beginTransaction does lock it though
		try {
			mIsInitializing = true;
			db = SQLiteDatabase.openOrCreateDatabase(dbPathToUse, null);
			int version = db.getVersion();
			if (version != DATABASE_VERSION) {
				db.beginTransaction();
				try {
					if (version == 0) {
						onCreate(db);
					} else {
						onUpgrade(db, version, DATABASE_VERSION);
					}
					db.setVersion(DATABASE_VERSION);
					db.setTransactionSuccessful();
				} finally {
					db.endTransaction();
				}
			}

			onOpen(db);
			success = true;
			return db;
		} finally {
			mIsInitializing = false;
			if (success) {
				if (mDatabase != null) {
					try {
						mDatabase.close();
					} catch (Exception e) {
					}
					// mDatabase.unlock();
				}
				mDatabase = db;
			} else {
				// if (mDatabase != null) mDatabase.unlock();
				if (db != null)
					db.close();
			}
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	//Log.w(getClass().getSimpleName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
	  
		  dropTables(db);
	      onCreate(db);
		
	}
	
	/**
	 * Drops all the tables
	 * @param db
	 */
	private void dropTables(SQLiteDatabase db)
	{
			db.execSQL("DROP TABLE IF EXISTS " + PasswordDAO.TABLE_PASSWORDS );
			db.execSQL("DROP TABLE IF EXISTS " + PasswordDAO.TABLE_CATS );
	}

}