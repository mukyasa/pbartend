/**
 * Date: Jan 14, 2010
 * Project: PocketBartender
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.drinkmixer.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.StringTokenizer;

import android.database.SQLException;

import com.drinkmixer.domain.LearnBartender;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class FileParser {
	
	public static final int BARSTOCK=0;
	public static final int BASIC_TEQ=1;
	public static final int GLASSES=2;
	public static final int TERMINOLOGY=3;
	public static final String BE_A_BARTENDER="learn";
	public static final String BE_A_BARTENDER_DETAILS="learndetails";
	
	
	
	public static void loadBarStock(InputStream in)
	{
		 try {
			 
		        Reader inr = new InputStreamReader(in);
		        LearnBartender lbartend = LearnBartender.getInstance();
		        
		        String sCurrentLine;
		        
		        BufferedReader bin = new BufferedReader(inr); 
		        
		        while ((sCurrentLine = bin.readLine()) != null) 
		        {
		        	StringTokenizer toker = new StringTokenizer(sCurrentLine,"|");
		        	
		        	String title = toker.nextToken();
		        	String details = toker.nextToken();
		        	
		        	lbartend.lesson.put(title, details);
		        	
		        }
		        
	        } catch (SQLException e) {
		        e.printStackTrace();
	        } catch (IOException e) {
		        e.printStackTrace();
	        }
	}
	
	
}
