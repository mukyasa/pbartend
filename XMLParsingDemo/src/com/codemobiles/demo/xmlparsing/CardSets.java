/**
 * Date: Nov 15, 2009
 * Project: XMLParsingDemo
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.codemobiles.demo.xmlparsing;

import org.json.JSONArray;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class CardSets {
	
	public String title;
	public JSONArray terms;
	
	public CardSets(){}
	public CardSets(String title,JSONArray terms){
		this.title = title;
		this.terms = terms;	
	}
}
