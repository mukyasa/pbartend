/**
 * Date: Dec 12, 2009
 * Project: KeyGenerator
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.mypockettechnologies.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class RegExpTest {
	/**
	 * Dec 12, 2009
	 * dmason
	 * @param args
	 *
	 */
	public static void main(String[] args) {
		String imgTag ="<img src=\"http://i.quizlet.com/i/4400a18b1ea131770665eacf8d002de9_m.jpg\" width=\"155\" height=\"240\" />";
		System.out.println(stripImage(imgTag));
	}
	
	//<img src="http://i.quizlet.com/i/4400a18b1ea131770665eacf8d002de9_m.jpg" width="155" height="240" />
	public static String stripImage(String imgTag){
		
		 // Create a pattern to match cat
        Pattern p = Pattern.compile("<img src=|width=|height=|/>|\"");
        // Create a matcher with an input string
        Matcher m = p.matcher(imgTag);
        StringBuffer sb = new StringBuffer();
        boolean result = m.find();
        
        while(result) {
    		m.appendReplacement(sb, "");
    		result = m.find();
        }
        m.appendTail(sb);
        
        String[] tokens = sb.toString().split(" ");
        
        
        return tokens[0];
	}
}
