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

import com.juggler.domain.NewPassword;
import com.juggler.domain.PasswordDetail;
import com.juggler.utils.Encrypt;

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
				COL_ID, COL_NAME,COL_URL }, null, null, null, null, null);
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
	
	public Cursor getNotes(String noteId){
		String[]selectionArgs={noteId};
		return sqliteDatabase.rawQuery(sqlGetNotes, selectionArgs);
	}
	
	
	/**
	 * 
	 * Dec 31, 2009
	 * dmason
	 * @param templateId
	 * @return
	 *
	 */
	public Cursor getDetail(long templateId){
		String[]selectionArgs={templateId+""};
		return sqliteDatabase.rawQuery(sqlGetDetail, selectionArgs);
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
	public Cursor getAllWallet(){
		return sqliteDatabase.rawQuery(sqlGetAll, new String[] {QuiresDAO.ENTRY_TYPE_WALLET+""});
	}
	
	/**
	 * gets all login items
	 * Dec 29, 2009
	 * dmason
	 * @return
	 *
	 */
	public Cursor getAllLogins(){
		return sqliteDatabase.rawQuery(sqlGetAll, new String[] {QuiresDAO.ENTRY_TYPE_LOGINS+""});
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
	public Cursor getAllPasswords(){
		return sqliteDatabase.rawQuery(sqlGetAll, new String[] {QuiresDAO.ENTRY_TYPE_GEN_PASSWORD+""});
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
		String[]selectionArgs={QuiresDAO.ENTRY_TYPE_GEN_PASSWORD+""};
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetCount, selectionArgs);
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
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetCount, selectionArgs);
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
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetCount, selectionArgs);
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
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetCount, selectionArgs);
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
		note.put(COL_NOTE, Encrypt.encryptA(np.note));
		
		String noteId = saveNote(note);
		
		ContentValues password = new ContentValues();
		password.put(COL_NAME,Encrypt.encryptA(np.name));
		password.put(COL_NOTE_ID, noteId);
		password.put(COL_ENTRY_TYPE, ENTRY_TYPE_NOTES);
		
		savePassword(password);
	}
	
	/**
	 * Update Notes
	 * Jan 1, 2010
	 * dmason
	 *
	 */
	private void updateNote(ContentValues note,NewPassword np){
		
		String[] whereArgs ={np.noteId+""};
		sqliteDatabase.update(PasswordDAO.TABLE_NOTES,note, COL_ID +"=?", whereArgs);

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
	 * Updates password table
	 * Jan 1, 2010
	 * dmason
	 * @param password
	 * @param whereArg
	 * @return
	 *
	 */
	private String updatePassword(ContentValues password,String whereArg){
		
		String[] whereArgs = {whereArg};
		sqliteDatabase.update(QuiresDAO.TABLE_PASSWORDS, password, QuiresDAO.COL_ID+"=?", whereArgs);
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
		
		ContentValues password = new ContentValues();
		password.put(COL_NAME,Encrypt.encryptA(np.name));
		password.put(COL_ENTRY_TYPE, ENTRY_TYPE_GEN_PASSWORD);
		
		String passwordId = savePassword(password);
		
		ContentValues entry = new ContentValues();
        
        entry.put(COL_NAME, Encrypt.encryptA(np.usage));
        entry.put(COL_VALUE, Encrypt.encryptA(np.genPassword));
        entry.put(COL_PASSWORD_ID, passwordId);
        entry.put(COL_SECTION, PasswordDetail.GENERIC);
        
        sqliteDatabase.insert(PasswordDAO.TABLE_PASSWOR_ENTRY, null, entry);
	}
	
	/**
	 * updates the login
	 * Dec 31, 2009
	 * dmason
	 *
	 */
	public void updateLogin(){
		
		NewPassword np = NewPassword.getInstance();
		ContentValues note = new ContentValues();
		note.put(COL_NOTE, Encrypt.encryptA(np.note));
		
		updateNote(note,np);
		
		ContentValues password = new ContentValues();
		password.put(COL_NAME,Encrypt.encryptA(np.name));
		password.put(COL_ID, np.passwordId);
		password.put(COL_CAT_ID, np.catId);
		password.put(COL_SUB_CAT_ID, np.subCatId);
		password.put(COL_ENTRY_TYPE, ENTRY_TYPE_LOGINS);
		
		String passwordId = updatePassword(password,np.passwordId+"");
		
		Hashtable<String, PasswordDetail> passwordDetails = np.getNameValue();
		Iterator<String> Iter = passwordDetails.keySet().iterator();
		
		while (Iter.hasNext()) {
			ContentValues entry = new ContentValues();
	        String key = (String) Iter.next();
	        PasswordDetail detail = passwordDetails.get(key);
	        
	        entry.put(COL_NAME, Encrypt.encryptA(key));
	        entry.put(COL_VALUE, Encrypt.encryptA(detail.value));
	        entry.put(COL_PASSWORD_ID, passwordId);
	        
	        String[] whereArgs = {Encrypt.encryptA(key),passwordId};
			sqliteDatabase.update(QuiresDAO.TABLE_PASSWOR_ENTRY, entry, QuiresDAO.COL_NAME+"=? AND "+COL_PASSWORD_ID+"=?", whereArgs);
	        
        }
		
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
		note.put(COL_NOTE, Encrypt.encryptA(np.note));
		
		String noteId = saveNote(note);
		
		ContentValues password = new ContentValues();
		password.put(COL_NAME,Encrypt.encryptA(np.name));
		password.put(COL_NOTE_ID, noteId);
		password.put(COL_CAT_ID, np.catId);
		password.put(COL_SUB_CAT_ID, np.subCatId);
		password.put(COL_ENTRY_TYPE, ENTRY_TYPE_WALLET);
		
		String passwordId = savePassword(password);
		
		Hashtable<String, PasswordDetail> passwordDetails = np.getNameValue();
		Iterator<String> Iter = passwordDetails.keySet().iterator();
		
		while (Iter.hasNext()) {
			ContentValues entry = new ContentValues();
	        String key = (String) Iter.next();
	        PasswordDetail detail = passwordDetails.get(key);
	        
	        entry.put(COL_NAME, Encrypt.encryptA(key));
	        entry.put(COL_VALUE, Encrypt.encryptA(detail.value));
	        entry.put(COL_PASSWORD_ID, passwordId);
	        entry.put(COL_SECTION, detail.section);
	        
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
		title.put(COL_NAME, Encrypt.encryptA(np.name));
		title.put(COL_URL, Encrypt.encryptA(np.url));
		title.put(COL_ENTRY_TYPE, ENTRY_TYPE_LOGINS);
		
		ContentValues nameValue = new ContentValues();
		Hashtable<String, PasswordDetail> htable = np.getNameValue();
		Iterator<String> iter = htable.keySet().iterator();
		
		sqliteDatabase.insert(PasswordDAO.TABLE_PASSWORDS, null, title);
		Cursor cursor = sqliteDatabase.rawQuery(sqlGetMaxPasswordId, new String[]{});
		cursor.moveToFirst();
		String passwordId = cursor.getString(cursor.getColumnIndex(PasswordDAO.COL_ID));
		
		while (iter.hasNext()) {
	        String key = (String) iter.next();
	        PasswordDetail detail = htable.get(key);
	        
	        nameValue.put(COL_NAME, Encrypt.encryptA(key));
	        nameValue.put(COL_VALUE, Encrypt.encryptA(detail.value));	 
	        nameValue.put(COL_SECTION,detail.section);
	        nameValue.put(COL_PASSWORD_ID, passwordId);
	        sqliteDatabase.insert(PasswordDAO.TABLE_PASSWOR_ENTRY, null, nameValue);
        }
		
	}

}