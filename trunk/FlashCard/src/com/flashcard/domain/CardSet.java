/**
 * Date: Nov 15, 2009
 * Project: XMLParsingDemo
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.flashcard.domain;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.flashcard.util.AppUtil;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class CardSet {
	
	public String title;
	public int id;
	public Integer cardCount;
	public ArrayList<FlashCard> flashcards=new ArrayList<FlashCard>();
	
	public CardSet(){}
	
	
	
	public CardSet(String title,JSONArray flashcards,Integer cardCount,int id){
		
		this.title = AppUtil.defringe(title);
		this.cardCount = cardCount;
		this.id = id;
		
		try {
			
	        for(int i=0;i<flashcards.length();i++)
	        {
	        	JSONArray terms = (JSONArray)flashcards.get(i);
	        	String question = (String)terms.get(0);
	        	String answer = (String)terms.get(1);
	        	String imageURL="";
	        	try {
	        		imageURL= (String)terms.get(2);
                } catch (Exception e) {
                	//do nothing
                }
	        	FlashCard flashcard = new FlashCard(question,answer,i+1,imageURL);
	        	this.flashcards.add(flashcard);
	        }
	        
        } catch (JSONException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		
		
	}
}

	