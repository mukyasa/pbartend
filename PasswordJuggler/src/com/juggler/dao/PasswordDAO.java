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
	
	public static final String TABLE_PASSWORDS = "tblPwds";
	public static final String TABLE_CATS = "tblCats";
	
	public static final String sqlCreatePasswordTable = "CREATE TABLE "+
		TABLE_PASSWORDS+" (_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ," +
			" name TEXT, value TEXT, catId INTEGER);";
	
	public static final String sqlCreateCatsTable = "CREATE TABLE "+TABLE_CATS+
		" (_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , name TEXT);";
}
