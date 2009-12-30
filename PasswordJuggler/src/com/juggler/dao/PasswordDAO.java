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

import java.util.Hashtable;
import java.util.Iterator;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.juggler.domain.NewPassword;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class PasswordDAO extends QuiresDAO {
	
	private SQLiteDatabase sqliteDatabase;
	
	/**
	 * get all cats
	 * Dec 24, 2009
	 * dmason
	 * @return
	 *
	 */
	public Cursor getCategories(){
		return sqliteDatabase.query(TABLE_CATS, new String[] {
				COL_ID, COL_NAME }, null, null, null, null, null);
	}
	
	public Cursor getLoginTemplates(){
		return sqliteDatabase.query(TABLE_LOGIN_TEMPLATE, new String[] {
				COL_ID, COL_NAME }, null, null, null, null, null);
	}
	
	/**
	 * Get all subcats
	 * Dec 24, 2009
	 * dmason
	 * @param catId
	 * @return
	 *
	 */
	public Cursor getSubCategories(String catId){
		String[]selectionArgs={catId};
		return sqliteDatabase.rawQuery(sqlGetSubCats, selectionArgs);
	}
	
	/**
	 * gets a template based on template id
	 * Dec 27, 2009
	 * dmason
	 * @param catId
	 * @return
	 *
	 */
	public Cursor getTemplate(long templateId){
		String[]selectionArgs={templateId+""};
		return sqliteDatabase.rawQuery(sqlGetTemplate, selectionArgs);
	}
	
	/**
	 * Sets the sqlite db name
	 * 
	 * @param sqliteDatabase
	 */
	public void setSQLiteDatabase(SQLiteDatabase sqliteDatabase) {
		this.sqliteDatabase = sqliteDatabase;
	}
	
	/**
	 * get all wallet items
	 * Dec 29, 2009
	 * dmason
	 * @return
	 *
	 */
	public Cursor getAllPasswords(){
		return sqliteDatabase.rawQuery(sqlGetAllPasswords, null);
	}
	
	/**
	 * gets all login items
	 * Dec 29, 2009
	 * dmason
	 * @return
	 *
	 */
	public Cursor getAllLogins(){
		return sqliteDatabase.rawQuery(sqlGetAllLogins, null);
	}
	
	/**
	 * gets all notes
	 * Dec 29, 2009
	 * dmason
	 * @return
	 *
	 */
	public Cursor getAllNotes(){
		return sqliteDatabase.rawQuery(sqlGetAllNotes, null);
	}
	
	/**
	 * gets all gen passwords
	 * Dec 29, 2009
	 * dmason
	 * @return
	 *
	 */
	public Cursor getAllGenPasswords(){
		return sqliteDatabase.rawQuery(sqlGetAllGenPasswords, null);
	}
	
	/**
	 * Gets gen password count
	 * Dec 24, 2009
	 * dmason
	 * @return
	 *
	 */
	public int getPasswordsCount()
	{
		String[]selectionArgs={};
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetPasswordCount, selectionArgs);
		return cursor.getCount();
	}
	
	/**
	 * gets logins count
	 * Dec 24, 2009
	 * dmason
	 * @return
	 *
	 */
	public int getLoginsCount()
	{
		String[]selectionArgs={QuiresDAO.ENTRY_TYPE_LOGINS+""};
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetLoginsCount, selectionArgs);
		return cursor.getCount();
	}
	
	/**
	 * gets notes count
	 * Dec 24, 2009
	 * dmason
	 * @return
	 *
	 */
	public int getNotesCount()
	{
		String[]selectionArgs={QuiresDAO.ENTRY_TYPE_NOTES+""};
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetNotesCount, selectionArgs);
		return cursor.getCount();
	}
	
	/**
	 * gets wallet iems count
	 * Dec 24, 2009
	 * dmason
	 * @return
	 *
	 */
	public int getWalletCount()
	{
		String[]selectionArgs={QuiresDAO.ENTRY_TYPE_WALLET+""};
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetWalletCount, selectionArgs);
		return cursor.getCount();
	}
	
	
	/** 
	 * Just saves a note in the note table
	 * Dec 29, 2009
	 * dmason
	 *
	 */
	private String saveNote(ContentValues note)
	{
		sqliteDatabase.insert(PasswordDAO.TABLE_NOTES, null, note);
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetMaxNotesId, new String[]{});
		cursor.moveToFirst();
		return cursor.getString(cursor.getColumnIndex(PasswordDAO.COL_ID));
	}
	
	/**
	 * inserts notes
	 * Dec 26, 2009
	 * dmason
	 *
	 */ 
	public void saveNotes(){
		NewPassword np = NewPassword.getInstance();
		ContentValues note = new ContentValues();
		note.put(COL_NOTE, np.note);
		
		String noteId = saveNote(note);
		
		ContentValues password = new ContentValues();
		password.put(COL_NAME,np.name);
		password.put(COL_NOTE_ID, noteId);
		password.put(COL_ENTRY_TYPE, ENTRY_TYPE_NOTES);
		
		savePassword(password);
	}
	
	/**
	 * inserts into table and returns its id
	 * Dec 29, 2009
	 * dmason
	 * @param password
	 * @return
	 *
	 */
	private String savePassword(ContentValues password){
		
		sqliteDatabase.insert(PasswordDAO.TABLE_PASSWORDS, null, password);
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetMaxPasswordId, new String[]{});
		cursor.moveToFirst();
		return cursor.getString(cursor.getColumnIndex(PasswordDAO.COL_ID));
		
	}
	
	/**
	 * Saves a Generated Password to the db
	 * Dec 29, 2009
	 * dmason
	 *
	 */
	public void saveGenPassword(){
		
		NewPassword np = NewPassword.getInstance();
		
		ContentValues genpassword = new ContentValues();
		genpassword.put(COL_NAME, np.name);
		genpassword.put(COL_PASSWORD, np.genPassword);
		genpassword.put(COL_USAGE, np.usage);
		
		sqliteDatabase.insert(PasswordDAO.TABLE_GEN_PASSWORD, null, genpassword);
	}
	
	/**
	 * Saves a wallet item
	 * Dec 29, 2009
	 * dmason
	 *
	 */
	public void saveWallet(){
		
		NewPassword np = NewPassword.getInstance();
		ContentValues note = new ContentValues();
		note.put(COL_NOTE, np.note);
		
		String noteId = saveNote(note);
		
		ContentValues password = new ContentValues();
		password.put(COL_NAME,np.name);
		password.put(COL_NOTE_ID, noteId);
		password.put(COL_CAT_ID, np.catId);
		password.put(COL_SUB_CAT_ID, np.subCatId);
		password.put(COL_ENTRY_TYPE, ENTRY_TYPE_WALLET);
		
		String passwordId = savePassword(password);
		
		Hashtable<String, String> passwordDetails = np.getNameValue();
		Iterator<String> Iter = passwordDetails.keySet().iterator();
		
		while (Iter.hasNext()) {
			ContentValues entry = new ContentValues();
	        String key = (String) Iter.next();
	        String value = passwordDetails.get(key);
	        
	        entry.put(COL_NAME, key);
	        entry.put(COL_VALUE, value);
	        entry.put(COL_PASSWORD_ID, passwordId);
	        
	        sqliteDatabase.insert(PasswordDAO.TABLE_PASSWOR_ENTRY, null, entry);
	        
        }
	}
	
	/**
	 * persists logins
	 * Dec 24, 2009
	 * dmason
	 *
	 */
	public void saveLogins()
	{
		NewPassword np = NewPassword.getInstance();
		
		ContentValues title = new ContentValues();
		title.put(COL_NAME, np.name);
		title.put(COL_URL, np.url);
		//title.put(COL_CAT_ID, np.catId);
		//title.put(COL_SUB_CAT_ID, np.subCatId);
		//title.put(COL_NOTE_ID, np.noteId);
		title.put(COL_ENTRY_TYPE, ENTRY_TYPE_LOGINS);
		
		ContentValues nameValue = new ContentValues();
		Hashtable<String, String> htable = np.getNameValue();
		Iterator<String> iter = htable.keySet().iterator();
		
		sqliteDatabase.insert(PasswordDAO.TABLE_PASSWORDS, null, title);
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetMaxPasswordId, new String[]{});
		cursor.moveToFirst();
		String passwordId = cursor.getString(cursor.getColumnIndex(PasswordDAO.COL_ID));
		
		while (iter.hasNext()) {
	        String key = (String) iter.next();
	        String value = htable.get(key);
	        
	        nameValue.put(COL_NAME, key);
	        nameValue.put(COL_VALUE, value);	        
	        nameValue.put(COL_PASSWORD_ID, passwordId);
	        
        }

		sqliteDatabase.insert(PasswordDAO.TABLE_PASSWOR_ENTRY, null, nameValue);
		
	}

}
