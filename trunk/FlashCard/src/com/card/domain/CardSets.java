/**
 * Date: Nov 15, 2009
 * Project: XMLParsingDemo
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.card.domain;

import org.json.JSONArray;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class CardSets {
	
	public String title;
	public Integer cardCount;
	public JSONArray flashcards;
	
	public CardSets(){}
	
	public CardSets(String title,JSONArray flashcards,Integer cardCount){
		this.title = title;
		this.cardCount = cardCount;
		this.flashcards = flashcards;	
	}
}
