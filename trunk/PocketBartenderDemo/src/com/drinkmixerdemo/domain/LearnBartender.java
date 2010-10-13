/**
 * Date: Jan 14, 2010
 * Project: PocketBartender
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.drinkmixerdemo.domain;

import java.util.TreeMap;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class LearnBartender {
	
	public TreeMap<String, String> lesson = new TreeMap<String, String>();
	
	private static LearnBartender learnBar= null;
	
	public static LearnBartender getInstance()
	{
		if(learnBar == null)
		{
			learnBar = new LearnBartender();
		}
		return learnBar;
	}
	
	public void clear()
	{
		lesson = new TreeMap<String, String>();
	}
	
	
}
