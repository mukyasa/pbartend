/**
 * Date: Nov 29, 2009
 * Project: DBMoveTest
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.move;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class Dbtest extends SQLiteOpenHelper {
	
	private static Dbtest instance; //for singleton
	public static SQLiteDatabase sqliteDb=null;
	public static String FILE_NM="pBartender7";
	
	/**
     * Nov 29, 2009
     * @param context
     * @param name
     * @param factory
     * @param version
     * 
     */
    public Dbtest(Context context, String name, CursorFactory factory, int version) {
	    super(context, name, factory, version);
    }
	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}
	
	public static final Dbtest getInstance(Context context) {
		initialize(context);
		return instance;
	}
	
	//singleton initialize
	private static void initialize(Context context) {
		if(instance == null) {
			instance = new Dbtest(context, FILE_NM, null, 1);
			sqliteDb = instance.getWritableDatabase();
		}
	}
	
}
