/**
 * Date: Dec 8, 2009 Project: PasswordJuggler User: dmason This software is
 * subject to license of IBBL-TGen http://www.gouvernement.lu/
 * http://www.tgen.org
 */
package com.juggler.utils;

import java.security.SecureRandom;
import java.util.ArrayList;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class Encrypt {
	
	private static String seed="dJm4S0n9";
	
	public static String genPassword(int strength,boolean isNumbers,boolean isAscii) throws Exception
	{
		String result="";
		ArrayList<String> args = new ArrayList<String>();
		args.add(strength+"");
		
		int passLength = 0;
        SecureRandom wheel = SecureRandom.getInstance("SHA1PRNG");

        char[] upperLowerCase = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z','A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        char[] printableAscii = new char[]{'!', '\"', '#', '$', '%', '(', ')', '*', '+', '-', '.', '/', '\'',
            '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', ':', '<', '=', '>', '?', '@',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        char[] alphaNumberic = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};


            if (isNumbers && !isAscii) {//no ascii, numbers and letters
                passLength = Integer.parseInt(args.get(0));
                for (int i = 0; i < passLength; i++) {
                	int random = wheel.nextInt(alphaNumberic.length);
                    result+=alphaNumberic[random]+"";
                }
            }else if (!isNumbers && !isAscii) { //no ascii no numbers
                passLength = Integer.parseInt(args.get(0));
                for (int i = 0; i < passLength; i++) {
                    int random = wheel.nextInt(upperLowerCase.length);
                    result+=upperLowerCase[random]+"";
                }
            } else if (isAscii) { // all
                passLength = Integer.parseInt(args.get(0));
                for (int i = 0; i < passLength; i++) {
                    int random = wheel.nextInt(printableAscii.length);
                    result+=printableAscii[random]+"";
                }
            
            }
    
	    return result;
	    
	}


	private static byte[] getRawKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(seed);
	    kgen.init(128, sr); // 192 and 256 bits may not be available
	    SecretKey skey = kgen.generateKey();
	    byte[] raw = skey.getEncoded();
	    return raw;
	}

	
	private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
	    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
	    byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
	    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.DECRYPT_MODE, skeySpec);
	    byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	public static String toHex(String txt) {
		return toHex(txt.getBytes());
	}
	public static String fromHex(String hex) {
		return new String(toByte(hex));
	}
	
	public static byte[] toByte(String hexString) {
		int len = hexString.length()/2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++)
			result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
		return result;
	}

	public static String toHex(byte[] buf) {
		if (buf == null)
			return "";
		StringBuffer result = new StringBuffer(2*buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}
		return result.toString();
	}
	private final static String HEX = "0123456789ABCDEF";
	private static void appendHex(StringBuffer sb, byte b) {
		sb.append(HEX.charAt((b>>4)&0x0f)).append(HEX.charAt(b&0x0f));
	}
	
	// this takes a string and returns the string encrypted.
	public static String encryptA(String str) {
		return str;/*
        if(str != null)
        {
        	try {
	        	byte[] rawKey = getRawKey(seed.getBytes());
	        	byte[] result = encrypt(rawKey, str.getBytes());
	        	return toHex(result);
        	} catch (Exception e) {
        		return str;
             }
        }else
        	return str;
        	*/
       
	}
	
	public static String decryptA(String str) {

		return str;
		/*
		if(str != null)
		{
			try {
		        byte[] rawKey = getRawKey(seed.getBytes());
		        byte[] enc = toByte(str);
		        byte[] result = decrypt(rawKey, enc);
		        return new String(result);
	        } catch (Exception e) {
	        	return str;
	        }
		}else
			return str;
			*/
	}
}
