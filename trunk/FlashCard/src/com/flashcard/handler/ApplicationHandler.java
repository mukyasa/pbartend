/**
 * Date: Nov 15, 2009
 * Project: FlashCard
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.flashcard.handler;

import java.util.ArrayList;

import com.flashcard.domain.CardSet;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class ApplicationHandler {
	
	private static ApplicationHandler handler=null;
	public ArrayList<CardSet> cardsets = new ArrayList<CardSet>();
	//these are both init set in the CardSetList screen
	public CardSet currentlyUsedSet = null;
	public CardSet orignalUsedSet=null;
	
		
	/**
	 * gets handler instance
	 * Nov 15, 2009
	 * dmason
	 * @return
	 *
	 */
	public static ApplicationHandler instance(){
		if(handler == null)
			handler = new ApplicationHandler();
		
		return handler;
	}

	/**
	 * resets handler
	 * Nov 15, 2009
	 * dmason
	 *
	 */
	public static void clearHandlerInstance(){
		handler =null;
	}
	
}
