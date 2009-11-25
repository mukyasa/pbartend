/**
 * Date: Nov 24, 2009
 * Project: FlashCard
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
public class BookmarkDomain {
	public String id;
	public String title;
	
	public BookmarkDomain(String title, String id){
		
		this.id = id;
		this.title = title;
	}
	
	public BookmarkDomain(){
	}
	
}
