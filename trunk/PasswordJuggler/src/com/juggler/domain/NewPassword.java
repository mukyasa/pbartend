/**
 * Date: Dec 24, 2009
 * Project: PasswordJuggler
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.juggler.domain;

import java.util.Hashtable;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class NewPassword {
	
	private static NewPassword instance=null;
	public String name;
	public String note;
	public String usage;
	public String url;
	public long catId;
	public long subCatId;
	public long noteId;
	//password id set after insert of password
	public int passwordId;
	
	public Hashtable<String, String> nameValue;
	
	public static NewPassword getInstance(){
		
		if(instance==null)
			instance = new NewPassword();
		
		return instance;
	}
	
}
