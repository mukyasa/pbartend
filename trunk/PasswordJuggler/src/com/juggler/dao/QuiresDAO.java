package com.juggler.dao;

public class QuiresDAO {
	
	public static final String TABLE_PASSWORDS = "tblPassword";
	public static final String TABLE_CATS = "tblCats";
	public static final String TABLE_SUB_CATS = "tblSubCat";
	public static final String TABLE_PASSWOR_ENTRY = "tblPasswordEntry";
	public static final String TABLE_NOTES = "tblNotes";
	
	//columns
	public static final String COL_ID = "_id";
	public static final String COL_PASSWORD_ID = "passwordId";
	public static final String COL_NAME = "name";
	public static final String COL_CAT_ID = "catId";
	public static final String COL_NOTE = "note";
	public static final String COL_SUB_CAT_ID = "subCatId";
	public static final String COL_NOTE_ID = "noteId";
	public static final String COL_URL ="url";
	public static final String COL_VALUE ="value";
	
	
	public static final String sqlCreatePasswordTable = "CREATE TABLE "+TABLE_PASSWORDS+" ("+
	COL_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+
	COL_NAME+" TEXT, "+
	COL_URL+" TEXT, "+
	COL_CAT_ID+" INTEGER, "+
	COL_SUB_CAT_ID+" INTEGER, "+
	COL_NOTE_ID+" INTEGER);";
	
	public static final String sqlCreateCatsTable = "CREATE TABLE "+TABLE_CATS+" ("+COL_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+COL_NAME+" TEXT);";
	
	public static final String sqlCreateNotesTable = "CREATE TABLE "+TABLE_NOTES+" ("+COL_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+COL_NOTE+");";
	
	public static final String sqlCreatePasswordEntryTable = "CREATE TABLE "+TABLE_PASSWOR_ENTRY+" ("+COL_ID+" INTEGER PRIMARY KEY  NOT NULL , "+COL_NAME+" TEXT,"+COL_VALUE+" TEXT,"+COL_PASSWORD_ID+" INTEGER);";
	
	public static final String sqlCreateSubCatTable = "CREATE TABLE "+TABLE_SUB_CATS+" ("+COL_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ,  "+COL_NAME+" TEXT, "+COL_CAT_ID+" INTEGER);";
	
	
	public static final String sqlGetSubCats= "SELECT "+COL_ID+","+COL_NAME+" from "+TABLE_SUB_CATS+" WHERE "+COL_CAT_ID+"=?";

}
