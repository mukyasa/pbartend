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
	public String name="";
	public String genPassword="";
	public String note="";
	public String usage="";
	public String url="";
	public long catId=-1;
	public long subCatId=-1;
	public long noteId=-1;
	//password id set after insert of password
	public long passwordId;
	
	//this is the temp holding zone for the currently selected textview id in CreateWalletTemplate
	public int templateId;
	
	private Hashtable<String, PasswordDetail> nameValue = new Hashtable<String, PasswordDetail>();
	private Hashtable<String, String> templateSaver = new Hashtable<String, String>();
	
	public void addTemplateSaver(String key,String value)
	{
		templateSaver.put(key, value);
	}
	
	public Hashtable<String, String> getTemplateSaver(){
		
		return templateSaver;
	}
	
	public void addNameValue(String key,PasswordDetail value)
	{
		nameValue.put(key, value);
	}
	
	public Hashtable<String, PasswordDetail> getNameValue(){
		
		return nameValue;
	}
	
	public void clear()
	{
		instance = null;
	}
	
	public static NewPassword getInstance(){
		
		if(instance==null)
			instance = new NewPassword();
		
		return instance;
	}
	
}
