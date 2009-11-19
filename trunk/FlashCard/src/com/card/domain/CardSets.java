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

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class CardSets {
	
	public String title;
	public Integer cardCount;
	public ArrayList<FlashCard> flashcards=new ArrayList<FlashCard>();
	
	public CardSets(){}
	
	public CardSets(String title,JSONArray flashcards,Integer cardCount){
		this.title = title;
		this.cardCount = cardCount;
		
		try {
			
	        for(int i=0;i<flashcards.length();i++)
	        {
	        	JSONArray terms = (JSONArray)flashcards.get(i);
	        	String question = (String)terms.get(0);
	        	String answer = (String)terms.get(1);
	        	FlashCard flashcard = new FlashCard(question,answer,i+1);
	        	this.flashcards.add(flashcard);
	        }
	        
        } catch (JSONException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		
		
	}
}
