package com.juggler.dao;


public class QuiresDAO {
	
	public static final String TABLE_PASSWORDS = "tblPassword";
	public static final String TABLE_TEMPLATE = "tblTemplates";
	public static final String TABLE_CATS = "tblCats";
	public static final String TABLE_SUB_CATS = "tblSubCat";
	public static final String TABLE_PASSWOR_ENTRY = "tblPasswordEntry";
	public static final String TABLE_NOTES = "tblNotes";
	//public static final String TABLE_GEN_PASSWORD ="tblGenPassword";
	public static final String TABLE_LOGIN_TEMPLATE ="tblLoginTemplate";
	
	//columns
	public static final String COL_ID = "_id";
	public static final String COL_SECTION = "s34kder";//"section";
	public static final String 	COL_DETAIL_ID = "d993id";//"detail_id";
	public static final String COL_ENTRY_TYPE = "ekdiekk";//"entrytype";
	public static final String COL_PASSWORD_ID = "p3dddid";//"passwordId";
	public static final String COL_NAME = "n883iid";//"name";
	public static final String COL_PASSWORD_NAME = "pn33ddd";//"password_name";	
	public static final String COL_CAT_ID = "ce9id";//"catId";
	public static final String COL_NOTE = "n34fv";//"note";
	public static final String COL_SUB_CAT_ID = "sc33e5id";//"subCatId";
	public static final String COL_NOTE_ID = "nii33id";//"noteId";
	public static final String COL_URL ="u33er";//"url";
	public static final String COL_VALUE ="v33i3";//"value"; 
	public static final String COL_PASSWORD ="p28283i";//"pwd";
	public static final String COL_USAGE ="u383iid";//"usage";
	public static final String COL_TEMPLATE_LABEL ="leodk33";//"label";
	public static final String COL_TEMPLATE_ID ="sci3883id";//"subCatId";
	public static final String COL_TEMPLATE_SECTION_TITLE ="s338ieie";//"sectionTitle";
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
	
	public static final String sqlCreateLoginTemplateTable = "CREATE TABLE "+TABLE_LOGIN_TEMPLATE+" ("+COL_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+COL_NAME+" TEXT, "+COL_URL+" TEXT);";
	
	public static final String sqlCreateTemplateTable ="CREATE TABLE "+TABLE_TEMPLATE+" ("+COL_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+COL_TEMPLATE_LABEL+" TEXT, "+COL_TEMPLATE_ID+" INTEGER, "+COL_TEMPLATE_SECTION_TITLE+" TEXT);";
	
	//public static final String sqlCreateGenPassTable ="CREATE TABLE "+TABLE_GEN_PASSWORD+" ("+COL_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+COL_NAME+" TEXT, "+COL_PASSWORD+" TEXT, "+COL_USAGE+" TEXT);";
	
	public static final String sqlCreateCatsTable = "CREATE TABLE "+TABLE_CATS+" ("+COL_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+COL_NAME+" TEXT);";
	
	public static final String sqlCreateNotesTable = "CREATE TABLE "+TABLE_NOTES+" ("+COL_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+COL_NOTE+");";
	
	public static final String sqlCreatePasswordEntryTable = "CREATE TABLE "+TABLE_PASSWOR_ENTRY+" ("+COL_ID+" INTEGER PRIMARY KEY  NOT NULL , "+COL_NAME+" TEXT,"+COL_VALUE+" TEXT,"+COL_PASSWORD_ID+" INTEGER, "+COL_SECTION+" INTEGER DEFAULT 0);";
	
	public static final String sqlCreateSubCatTable = "CREATE TABLE "+TABLE_SUB_CATS+" ("+COL_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ,  "+COL_NAME+" TEXT, "+COL_CAT_ID+" INTEGER);";
	
	
	public static final String sqlGetSubCats= "SELECT "+COL_ID+","+COL_NAME+" from "+TABLE_SUB_CATS+" WHERE "+COL_CAT_ID+"=?";
	public static final String sqlGetTemplate= "SELECT * from "+TABLE_TEMPLATE+" WHERE "+COL_SUB_CAT_ID+"=?";
	public static final String sqlGetDetail1 = "SELECT p."+COL_NAME+"["+COL_PASSWORD_NAME+"],d."+COL_NAME+",d."+COL_VALUE+",p."+COL_NOTE+",d."+COL_ID+"["+COL_DETAIL_ID+"],d."+COL_SECTION+
	" FROM "+TABLE_PASSWORDS+" p INNER JOIN "
	+TABLE_PASSWOR_ENTRY+" d on d."+COL_PASSWORD_ID+" = p."+COL_ID+
	" LEFT JOIN "+TABLE_NOTES+" n on "+
	" WHERE p."+COL_ID+"=? ORDER BY "+COL_SECTION+";";
	
	public static final String sqlGetDetail = "SELECT p."+COL_NAME+"["+COL_PASSWORD_NAME+"],d."+COL_NAME+",d."+COL_VALUE+",n."+COL_NOTE+",d."+COL_ID+"["+COL_DETAIL_ID+"],d."+COL_SECTION+ " FROM tblPassword p"+
	" LEFT JOIN "+TABLE_NOTES+" n ON n."+COL_ID+"=p.noteId"  +
	" LEFT  JOIN "+TABLE_PASSWOR_ENTRY+" d  ON p."+COL_ID+"=d."+COL_PASSWORD_ID+" " +
			"WHERE p."+COL_ID+"=? ORDER BY "+COL_SECTION+";";
	
	
	
	public static final String sqlGetCount = "SELECT "+COL_ID+" from "+TABLE_PASSWORDS+" WHERE "+COL_ENTRY_TYPE+"=?;";
	
	public static final String sqlGetMaxNotesId ="SELECT MAX(_ID)["+COL_ID+"]  FROM "+TABLE_NOTES+";";
	public static final String sqlGetMaxPasswordId ="SELECT MAX(_ID)["+COL_ID+"]  FROM "+TABLE_PASSWORDS+";";
	
	public static final String sqlGetAll ="SELECT * FROM "+TABLE_PASSWORDS+" WHERE "+COL_ENTRY_TYPE+"=?;";
	public static final String sqlGetAllNotes ="SELECT p."+COL_NAME+",p."+COL_ID+",n."+COL_NOTE+",p."+COL_URL+" FROM "+TABLE_PASSWORDS+" p INNER JOIN "+TABLE_NOTES+" n on p."+COL_NOTE_ID+" = n."+COL_ID+" WHERE p."+COL_ENTRY_TYPE+" = "+ENTRY_TYPE_NOTES+";";
	
	public static final String sqlGetNotes ="SELECT * FROM "+TABLE_NOTES+" WHERE "+COL_ID+"=?;";
}
