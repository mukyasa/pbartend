/**
 * Date: Nov 24, 2009
 * Project: FlashCard
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.flashcard.domain;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class BookmarkDomain {
	public Integer id;
	public String title;
	
	public BookmarkDomain(Integer id,String title){
		
		this.id = id;
		this.title = title;
	}
	
	public BookmarkDomain(){
	}
	
}
