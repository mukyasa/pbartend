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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class AuthenticationRegCode {
	
	public static void main2(String[] args) throws IOException {
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
    }
	
	public static void main(String args[]) throws Exception {
	    String name = "flashcard101";
	    String passwd = "3cb1b44022817390e265aee6dd1d31c8";
	    String salt = "djmason9@hotmail.com";//email address

	   
	    MessageDigest m = MessageDigest.getInstance("MD5");
	    m.update(salt.getBytes());
	    m.update(name.getBytes("UTF8"));
	    byte s[] = m.digest();
	    String result = "";
	    for (int i = 0; i < s.length; i++) {
	      result += Integer.toHexString((0x000000ff & s[i]) | 0xffffff00).substring(6);
	    }
	    System.out.println(result + " " + result.equals(passwd));
	  }

}
