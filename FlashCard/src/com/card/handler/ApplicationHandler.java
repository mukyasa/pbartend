/**
 * Date: Nov 15, 2009
 * Project: FlashCard
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.card.handler;

import java.util.ArrayList;

import com.card.domain.CardSets;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class ApplicationHandler {
	
	public ArrayList<CardSets> cardsets = new ArrayList<CardSets>();
	private static ApplicationHandler handler=null;
		
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
