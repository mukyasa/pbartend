/**
 * Date: Nov 28, 2009
 * Project: KeyGenerator
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.mypockettechnologies.key;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;


/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class AuthenticationRegCode {
	private static final String APP_NAME = "flashcard101";
	/*public static void main2(String[] args) throws IOException {
        BufferedReader userInput = new BufferedReader (new InputStreamReader(System.in));
 
        System.out.println("Enter string:");
        String rawString = userInput.readLine();
 
        try {
            System.out.println("SHA1 hash of string: " + AeSimpleSHA1.SHA1(rawString));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }*/
	/*
	Thank you for your purchase. 
	Here is the registration code you will need to register your product. 
	**************************** COPY BELOW ******************************** 
	O9d0M13g1jMyTJ1HPWKfMEczRTNPSG9nQ3ZBb0tpajFsWk1SNm5Oa2RXSnBibk5yZVVCNVlXaHZieTVqYjIwPQ== 
	**************************** COPY ABOVE ******************************** 
	You will also need to use the email address (sdubinsky@yahoo.com) you purchased the software with. 

	Darren Mason 
	myPocket technologies 
	www.mypocket-technolgies.com 
*/
	public static void main(String args[]) throws Exception {
	   
		BufferedReader userInput = new BufferedReader (new InputStreamReader(System.in));
		System.out.println("Enter email:");
	   // String passwd = "3485dadaee23848e31b3cc4bcc79e3ea";
	    String salt = userInput.readLine();//email address
	   
	    MessageDigest m = MessageDigest.getInstance("MD5");
	    m.update(salt.getBytes());
	    m.update(APP_NAME.getBytes("UTF8"));
	    byte s[] = m.digest();
	    String result = "";
	    for (int i = 0; i < s.length; i++) {
	      result += Integer.toHexString((0x000000ff & s[i]) | 0xffffff00).substring(6);
	    }
	    
	    String emailfiller = "Thank you for your purchase.\n\nHere is the registration code you will need " +
	    		"to register your product.\n********* COPY BELOW ********\n\t" +result.substring(0,10) + "\n******** COPY ABOVE ********\n" +
	    				"You will also need to use the email address (" +
	    		salt+") you purchased with.\n\nDarren Mason\nmyPocket technologies\nwww.mypocket-technolgies.com";
	    
	    //shorten key
	    System.out.println(emailfiller);// + " " + result.substring(0,10).equals(passwd.substring(0,10)));
	  }

}
