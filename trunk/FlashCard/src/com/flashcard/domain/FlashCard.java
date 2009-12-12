/**
 * Date: Nov 14, 2009
 * Project: XMLParsingDemo
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.flashcard.domain;

import com.flashcard.util.AppUtil;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class FlashCard {
	
	public String question;
	public String imageURL;
	public String answer;
	public int cardNum;
	public boolean isCorrect=true; //assumes is correct by default
	public boolean wasSeen=false; //assuses not seen by default

	public FlashCard(String q,String a,int num,String imageURL){
		this.question = AppUtil.defringe(q);
		this.answer=AppUtil.defringe(a);
		this.cardNum=num;
		this.imageURL=AppUtil.stripImage(imageURL);
	}
	
}
