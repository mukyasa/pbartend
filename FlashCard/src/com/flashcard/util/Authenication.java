/**
 * Date: Nov 28, 2009
 * Project: FlashCard
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.flashcard.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class Authenication {
	
	/***************************/
	//IF THIS "IS" A DEMO THEN SET THIS TO FALSE
	private static boolean isNotDemo= true;
	/***************************/
	
	private static final String appname="flashcard101";
	public static final int ISDEMO = 99;
	private static final String PREF_REG ="regcode";
	
	public static boolean authenitcate(String emailsalt,String code){
			String result = "";
		    try {
	            MessageDigest m = MessageDigest.getInstance("MD5");
	            m.update(emailsalt.getBytes());
	            m.update(appname.getBytes("UTF8"));
	            byte s[] = m.digest();
	            
	            for (int i = 0; i < s.length; i++) {
	              result += Integer.toHexString((0x000000ff & s[i]) | 0xffffff00).substring(6);
	            }
	            
	            
            } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
            }
		
            return result.substring(0,10).equals(code.substring(0,10));
            
	}
	
	/**
	 * Checks to see if you are registered or not
	 * Nov 28, 2009
	 * dmason
	 * @param context
	 * @return
	 *
	 */
	public static boolean getRegPrefererences(Context context) {
		SharedPreferences settings = context.getSharedPreferences(AppUtil.PREFS_NAME, 0);
		
		if(!isNotDemo)
			return settings.getBoolean(PREF_REG, false);
		else
			return isNotDemo;
	}
	
	/**
	 * sets reg boolean true when called
	 * Nov 28, 2009
	 * dmason
	 * @param context
	 *
	 */
	public static void setRegPrefererences(Context context){
		
		SharedPreferences settings = context.getSharedPreferences(AppUtil.PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
	    editor.putBoolean(PREF_REG, true);
	    
	    // Don't forget to commit your edits!!!
	    //check to see if the bookmark exists if so dont do it
	    editor.commit();
	}
}
