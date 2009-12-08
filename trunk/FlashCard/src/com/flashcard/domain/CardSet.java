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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

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
	
	private String defringe(String title){
		
        // Create a pattern to match cat
        Pattern p = Pattern.compile("&quot;");
        // Create a matcher with an input string
        Matcher m = p.matcher(title);
        StringBuffer sb = new StringBuffer();
        boolean result = m.find();
        
        while(result) {
    		m.appendReplacement(sb, "\"");
    		result = m.find();
        }
        m.appendTail(sb);
        
        
        
        title = sb.toString();
        sb = new StringBuffer();
        // Create a pattern to match cat
       // p = Pattern.compile("&quot;|&#039;|&amp;");
        p = Pattern.compile("&#039;");
        // Create a matcher with an input string
        m = p.matcher(title);
        result = m.find();
        
        while(result) {
    		m.appendReplacement(sb, "'");
    		//m.appendReplacement(sb, "&");
    	
    		result = m.find();
        }
        m.appendTail(sb);
        
        
        
        title = sb.toString();
        sb = new StringBuffer();
        // Create a pattern to match cat
        p = Pattern.compile("&amp;");
        // Create a matcher with an input string
        m = p.matcher(title);
        result = m.find();
        
        while(result) {
    		m.appendReplacement(sb, "&");
    		result = m.find();
        }
        m.appendTail(sb);
        
        
        return sb.toString();
	        
      
	}
	
	public CardSet(String title,JSONArray flashcards,Integer cardCount,int id){
		
		this.title = defringe(title);
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

	