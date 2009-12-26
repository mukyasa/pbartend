package com.juggler.dao;

public class QuiresDAO {
	
	public static final String TABLE_PASSWORDS = "tblPassword";
	public static final String TABLE_CATS = "tblCats";
	public static final String TABLE_SUB_CATS = "tblSubCat";
	public static final String TABLE_PASSWOR_ENTRY = "tblPasswordEntry";
	public static final String TABLE_NOTES = "tblNotes";
	public static final String TABLE_GEN_PASSWORD ="tblGenPassword";
	
	//columns
	public static final String COL_ID = "_id";
	public static final String COL_ENTRY_TYPE = "entrytype";
	public static final String COL_PASSWORD_ID = "passwordId";
	public static final String COL_NAME = "name";//name
	public static final String COL_CAT_ID = "catId";
	public static final String COL_NOTE = "note";
	public static final String COL_SUB_CAT_ID = "subCatId";
	public static final String COL_NOTE_ID = "noteId";
	public static final String COL_URL ="url";
	public static final String COL_VALUE ="value"; //value
	public static final String COL_PASSWORD ="pwd";
	public static final String COL_USAGE ="usage";
	public static final int  ENTRY_TYPE_LOGINS = 1;
	public static final int ENTRY_TYPE_NOTES = 2;
	public static final int ENTRY_TYPE_GEN_PASSWORD = 3;
	public static final int ENTRY_TYPE_WALLET = 4;
	public static final String COL_COUNT = "[count]";
	
	
	public static final String sqlCreatePasswordTable = "CREATE TABLE "+TABLE_PASSWORDS+" ("+
	COL_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+
	COL_NAME+" TEXT, "+
	COL_URL+" TEXT, "+
	COL_CAT_ID+" INTEGER DEFAULT -1 ,"+
	COL_SUB_CAT_ID+" INTEGER DEFAULT -1 , "+
	COL_NOTE_ID+" INTEGER DEFAULT -1 ,"+
	COL_ENTRY_TYPE+" INTEGER DEFAULT -1 );"; 
	
	public static final String sqlCreateGenPassTable ="CREATE TABLE "+TABLE_GEN_PASSWORD+" ("+COL_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+COL_NAME+" TEXT, "+COL_PASSWORD+" TEXT, "+COL_USAGE+" TEXT);";
	
	public static final String sqlCreateCatsTable = "CREATE TABLE "+TABLE_CATS+" ("+COL_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+COL_NAME+" TEXT);";
	
	public static final String sqlCreateNotesTable = "CREATE TABLE "+TABLE_NOTES+" ("+COL_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+COL_NOTE+");";
	
	public static final String sqlCreatePasswordEntryTable = "CREATE TABLE "+TABLE_PASSWOR_ENTRY+" ("+COL_ID+" INTEGER PRIMARY KEY  NOT NULL , "+COL_NAME+" TEXT,"+COL_VALUE+" TEXT,"+COL_PASSWORD_ID+" INTEGER);";
	
	public static final String sqlCreateSubCatTable = "CREATE TABLE "+TABLE_SUB_CATS+" ("+COL_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ,  "+COL_NAME+" TEXT, "+COL_CAT_ID+" INTEGER);";
	
	
	public static final String sqlGetSubCats= "SELECT "+COL_ID+","+COL_NAME+" from "+TABLE_SUB_CATS+" WHERE "+COL_CAT_ID+"=?";
	
	public static final String sqlGetPasswordCount = "SELECT "+COL_ID+" from "+TABLE_GEN_PASSWORD+";";
	public static final String sqlGetNotesCount = "SELECT "+COL_ID+" from "+TABLE_NOTES+";";
	public static final String sqlGetWalletCount = "SELECT "+COL_ID+" from "+TABLE_PASSWORDS+" WHERE "+COL_ENTRY_TYPE+"=?;";
	public static final String sqlGetLoginsCount = "SELECT "+COL_ID+" from "+TABLE_PASSWORDS+" WHERE "+COL_ENTRY_TYPE+"=?;";
	
	public static final String sqlGetMaxNotesId ="SELECT MAX(_ID)["+COL_ID+"]  FROM "+TABLE_NOTES+";";
	public static final String sqlGetMaxPasswordId ="SELECT MAX(_ID)["+COL_ID+"]  FROM "+TABLE_PASSWORDS+";";
	
}
