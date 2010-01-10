/**
 * Date: Jan 9, 2010
 * Project: PasswordJuggler
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.juggler.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class AppUtils {
	
	private static final String PREFS_NAME = "app_pref";
	public static final String PREFS_CLEAR_TEXT = "app_cleartext";
	private static final String PREFS_PASSWORD = Encrypt.encryptA("app_password");
	public static final String PREFS_AUTO_LOCK = "app_auto_time";
	
	public static Map<String, String> passwordTypes = new TreeMap<String, String>();
	
	static{
		passwordTypes.put("Password", "");
		passwordTypes.put("License Key", "");
		passwordTypes.put("Number", "");
		passwordTypes.put("PIN", "");
		passwordTypes.put("SSID", "");
	}
	
	public static String getClearTextSetting(Context context){
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		String cleartext = settings.getString(PREFS_CLEAR_TEXT, "false");
		return cleartext;
	}
	
	/**
	 * Sets the user settings
	 * Jan 4, 2010
	 * dmason
	 *
	 */
	public static void setSettings(Context context,HashMap<String, String> map){
		
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		
		SharedPreferences.Editor editor = settings.edit();
		if(map.get(PREFS_CLEAR_TEXT) != null)
			editor.putString(PREFS_CLEAR_TEXT, map.get(PREFS_CLEAR_TEXT));
		if(map.get(PREFS_AUTO_LOCK) != null)
			editor.putString(PREFS_AUTO_LOCK, map.get(PREFS_AUTO_LOCK));
		
		// Don't forget to commit your edits!!!
		editor.commit();
	}
}
