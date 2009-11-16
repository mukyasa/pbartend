/**
 * Date: Nov 14, 2009
 * Project: XMLParsingDemo
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.card.domain;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class FlashCard {
	
	public String question;
	public String answer;
	public int cardNum;

	public String toString(){
         return "Question = " + this.question
                   + "\nAnswer = " + this.answer;
    }
	
}
