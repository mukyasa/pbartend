/**
 * Date: Dec 23, 2009
 * Project: PasswordJuggler
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.juggler.dao;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class PasswordDAO {
	
	public static final String TABLE_PASSWORDS = "tblPassword";
	public static final String TABLE_CATS = "tblCats";
	public static final String TABLE_SUB_CATS = "tblSubCat";
	public static final String TABLE_PASSWOR_ENTRY = "tblPasswordEntry";
	public static final String TABLE_NOTES = "tblNotes";
	
	public static final String sqlCreatePasswordTable = "CREATE TABLE "+TABLE_PASSWORDS+" (_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , name TEXT, url TEXT, catId INTEGER, subCatId INTEGER, noteId INTEGER);";
	
	public static final String sqlCreateCatsTable = "CREATE TABLE "+TABLE_CATS+" (_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , name TEXT);";
	
	public static final String sqlCreateNotesTable = "CREATE TABLE "+TABLE_NOTES+" (_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , note);";
	
	public static final String sqlCreatePasswordEntryTable = "CREATE TABLE "+TABLE_PASSWOR_ENTRY+" (_id INTEGER PRIMARY KEY  NOT NULL ,name TEXT,value TEXT,passwordId INTEGER);";
	
	public static final String sqlCreateSubCatTable = "CREATE TABLE "+TABLE_SUB_CATS+" (_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , name TEXT);";
}
