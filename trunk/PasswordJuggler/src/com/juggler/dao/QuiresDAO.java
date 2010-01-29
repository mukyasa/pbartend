package com.juggler.dao;


public class QuiresDAO {
	
	protected static boolean DEBUG_ENCRYPT=false;
	
	public static final String TABLE_PASSWORDS = "tblPassword";
	public static final String TABLE_TEMPLATE = "tblTemplates";
	public static final String TABLE_CATS = "tblCats";
	public static final String TABLE_SUB_CATS = "tblSubCat";
	public static final String TABLE_PASSWORD_ENTRY = "tblPasswordEntry";
	public static final String TABLE_NOTES = "tblNotes";
	public static final String TABLE_LOGIN ="tblLogin";
	public static final String TABLE_LOGIN_TEMPLATE ="tblLoginTemplate";
	
	//columns
	public static final String COL_ID = "_id";
	public static final String COL_NOTE_ID = "note_id";
	public static final String COL_SECTION = DEBUG_ENCRYPT ? "s34kder":"section";
	public static final String 	COL_DETAIL_ID = DEBUG_ENCRYPT ? "d993id":"detail_id";
	public static final String COL_ENTRY_TYPE = DEBUG_ENCRYPT ? "ekdiekk":"entrytype";
	public static final String COL_PASSWORD_ID = DEBUG_ENCRYPT ? "p3dddid":"passwordId";
	public static final String COL_NAME = DEBUG_ENCRYPT ? "n883iid":"name";
	public static final String COL_PASSWORD_NAME = DEBUG_ENCRYPT ? "pn33ddd":"password_name";	
	public static final String COL_CAT_ID = DEBUG_ENCRYPT ? "ce9id":"catId";
	public static final String COL_NOTE = DEBUG_ENCRYPT ? "n34fv":"note";
	public static final String COL_SUB_CAT_ID = DEBUG_ENCRYPT ? "sc33e5id":"subCatId";
	public static final String COL_URL = DEBUG_ENCRYPT ? "u33er":"url";
	public static final String COL_VALUE = DEBUG_ENCRYPT ? "v33i3":"value"; 
	public static final String COL_PASSWORD = DEBUG_ENCRYPT ? "p28283i":"pwd";
	public static final String COL_USAGE = DEBUG_ENCRYPT ? "u383iid":"usage";
	public static final String COL_TEMPLATE_LABEL = DEBUG_ENCRYPT ? "leodk33":"label";
	public static final String COL_TEMPLATE_SECTION_TITLE = DEBUG_ENCRYPT ? "s338ieie":"sectionTitle";
	public static final int  ENTRY_TYPE_LOGINS = 1;
	public static final int ENTRY_TYPE_NOTES = 2;
	public static final int ENTRY_TYPE_GEN_PASSWORD = 3;
	public static final int ENTRY_TYPE_WALLET = 4;
	public static final String COL_COUNT = "[count]";
	
	public static final String sqlCreateLoginTable = "CREATE TABLE "+TABLE_LOGIN+" ("+COL_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ,"+COL_PASSWORD+" TEXT);";
	
	public static final String sqlCreatePasswordTable = "CREATE TABLE "+TABLE_PASSWORDS+" ("+
	COL_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+
	COL_NAME+" TEXT, "+
	COL_URL+" TEXT, "+
	COL_CAT_ID+" INTEGER DEFAULT -1 ,"+
	COL_SUB_CAT_ID+" INTEGER DEFAULT -1 , "+
	COL_ENTRY_TYPE+" INTEGER DEFAULT -1 );"; 
	
	public static final String sqlCreateLoginTemplateTable = "CREATE TABLE "+TABLE_LOGIN_TEMPLATE+" ("+COL_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+COL_NAME+" TEXT, "+COL_URL+" TEXT);";
	
	public static final String sqlCreateTemplateTable ="CREATE TABLE "+TABLE_TEMPLATE+" ("+COL_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+COL_TEMPLATE_LABEL+" TEXT, "+COL_SUB_CAT_ID+" INTEGER, "+COL_TEMPLATE_SECTION_TITLE+" TEXT);";
	
	//public static final String sqlCreateGenPassTable ="CREATE TABLE "+TABLE_GEN_PASSWORD+" ("+COL_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+COL_NAME+" TEXT, "+COL_PASSWORD+" TEXT, "+COL_USAGE+" TEXT);";
	
	public static final String sqlCreateCatsTable = "CREATE TABLE "+TABLE_CATS+" ("+COL_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+COL_NAME+" TEXT);";
	
	public static final String sqlCreateNotesTable = "CREATE TABLE "+TABLE_NOTES+" ("+COL_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+COL_NOTE+","+COL_PASSWORD_ID+" INTEGER DEFAULT -1);";
	
	public static final String sqlCreatePasswordEntryTable = "CREATE TABLE "+TABLE_PASSWORD_ENTRY+" ("+COL_ID+" INTEGER PRIMARY KEY  NOT NULL , "+COL_NAME+" TEXT,"+COL_VALUE+" TEXT,"+COL_PASSWORD_ID+" INTEGER, "+COL_SECTION+" INTEGER DEFAULT 0);";
	
	public static final String sqlCreateSubCatTable = "CREATE TABLE "+TABLE_SUB_CATS+" ("+COL_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ,  "+COL_NAME+" TEXT, "+COL_CAT_ID+" INTEGER);";
	
	
	public static final String sqlGetSubCats= "SELECT "+COL_ID+","+COL_NAME+" from "+TABLE_SUB_CATS+" WHERE "+COL_CAT_ID+"=?";
	public static final String sqlGetTemplate= "SELECT * from "+TABLE_TEMPLATE+" WHERE "+COL_SUB_CAT_ID+"=?";
	public static final String sqlGetDetail1 = "SELECT p."+COL_NAME+"["+COL_PASSWORD_NAME+"],d."+COL_NAME+",d."+COL_VALUE+",p."+COL_NOTE+",d."+COL_ID+"["+COL_DETAIL_ID+"],d."+COL_SECTION+
	" FROM "+TABLE_PASSWORDS+" p INNER JOIN "
	+TABLE_PASSWORD_ENTRY+" d on d."+COL_PASSWORD_ID+" = p."+COL_ID+
	" LEFT JOIN "+TABLE_NOTES+" n on "+
	" WHERE p."+COL_ID+"=? ORDER BY "+COL_SECTION+";";
	
	public static final String sqlGetDetail = "SELECT p."+COL_ID+",p."+COL_NAME+"["+COL_PASSWORD_NAME+"],d."+COL_NAME+",d."+COL_VALUE+",n."+COL_NOTE+",n."+COL_ID+"["+COL_NOTE_ID+"],d."+COL_ID+"["+COL_DETAIL_ID+"],d."+COL_SECTION+ " FROM "+TABLE_PASSWORDS+" p"+
	" LEFT JOIN "+TABLE_NOTES+" n ON n."+COL_PASSWORD_ID+"=p."+ COL_ID  +
	" LEFT  JOIN "+TABLE_PASSWORD_ENTRY+" d  ON p."+COL_ID+"=d."+COL_PASSWORD_ID+" " +
			"WHERE p."+COL_ID+"=? ORDER BY "+COL_SECTION+";";
	
	
	
	public static final String sqlGetCount = "SELECT "+COL_ID+" from "+TABLE_PASSWORDS+" WHERE "+COL_ENTRY_TYPE+"=?;";
	public static final String sqlGeAppLogin = "SELECT * from "+TABLE_LOGIN+";";
	
	public static final String sqlGetMaxNotesId ="SELECT MAX(_ID)["+COL_ID+"]  FROM "+TABLE_NOTES+";";
	public static final String sqlGetMaxPasswordId ="SELECT MAX(_ID)["+COL_ID+"]  FROM "+TABLE_PASSWORDS+";";
	
	public static final String sqlGetAll ="SELECT * FROM "+TABLE_PASSWORDS+" WHERE "+COL_ENTRY_TYPE+"=? ORDER BY "+COL_NAME+";";
	public static final String sqlGetAllNotes ="SELECT p."+COL_NAME+",p."+COL_ID+",n."+COL_NOTE+",p."+COL_URL+" FROM "+TABLE_PASSWORDS+" p INNER JOIN "+TABLE_NOTES+" n on p."+COL_ID+" = n."+COL_PASSWORD_ID+" WHERE p."+COL_ENTRY_TYPE+" = "+ENTRY_TYPE_NOTES+";";
	
	public static final String sqlGetNotes ="SELECT * FROM "+TABLE_NOTES+" WHERE "+COL_ID+"=?;";
}
