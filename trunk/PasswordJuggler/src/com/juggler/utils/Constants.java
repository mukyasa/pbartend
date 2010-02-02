/**
 * Date: Dec 23, 2009
 * Project: PasswordJuggler
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.juggler.utils;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class Constants {
	
	public static String VERSION = "1.0.0";
	public static String COMPANY_NAME = "mypocket-technologies";
	
	public static final int HOME = 0;
	public static final int WALLET = 1;
	public static final int LOGINS = 2;
	public static final int NOTES = 3;
	public static final int PASSWORDS = 4;
	public static final int SETTINGS = 5;
	public static int SCREEN_TYPE=0;
	
	public static String STANDARD_LOGIN = "Standard Login";
	
	public static final String INTENT_EXTRA_SELECTED_ROW = "selected_id";
	public static final String INTENT_EXTRA_SELECTED_TEXT = "selected_text";
	public static final String INTENT_EXTRA_SELECTED_URL = "selected_url"; //hint for url
	public static final String INTENT_EXTRA_SELECTED_LABEL = "selected_label";
	public static final String INTENT_EXTRA_SELECTED_SECTION = "section"; // tag for section
	public static final String INTENT_EXTRA_NOTE = "isnote";
	public static final String INTENT_EXTRA_SELECTED_FIELD_ID = "cf";
	
	public static final String INTENT_EXTRA_STEP2_FIELD1 = "field1";
	public static final String INTENT_EXTRA_STEP2_FIELD2 = "field2";
	public static final String INTENT_EXTRA_IS_GENPASSWORD ="isgenpassword";
	
}
