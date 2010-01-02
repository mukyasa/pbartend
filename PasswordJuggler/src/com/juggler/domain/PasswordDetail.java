/**
 * Date: Jan 1, 2010
 * Project: PasswordJuggler
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.juggler.domain;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class PasswordDetail {
	
	//section type
	public static final int SMTP =1;
	public static final int PUBLISHER =2;
	public static final int ORDER =3;
	public static final int MORE_INFO =4;
	public static final int HOST =5;
	public static final int CONTACT_INFO =6;
	public static final int CONTACT =7;
	public static final int BRANCH_INFO =8;
	public static final int ADMIN_CONSOLE =9;
	public static final int ADD_DETAILS =10;
	public static final int GENERIC =0;
	
	public int section;
	public String value;
	public String id;
	
	public PasswordDetail(int section,String value,String id){
		this.section = section;
		this.value = value;
		this.id=id;
		
	}
	
}
