/**
 * Date: Dec 8, 2009 Project: PasswordJuggler User: dmason This software is
 * subject to license of IBBL-TGen http://www.gouvernement.lu/
 * http://www.tgen.org
 */
package com.juggler.utils;

import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class Encrypt {
	
	public static String genPassword(int strength,boolean isNumbers,boolean isAscii) throws Exception
	{
		String result="";
		ArrayList<String> args = new ArrayList<String>();
		args.add(strength+"");
		
		int passLength = 0;
        SecureRandom wheel = SecureRandom.getInstance("SHA1PRNG");

        char[] lowerCase = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        char[] upperCase = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        char[] upperLowerCase = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z','A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        char[] numeric = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

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
	
	// this takes a string and returns the string encrypted.
	public static String encryptA(String str) {
		
		if(str != null)
		{
			char[] letters = str.toCharArray();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < letters.length; i++) // loop through char array
			{
				// add the new encrypted char to a string
				sb.append(crypto(letters[i] + i));
			}
			return sb.toString();
		}
		else 
			return str;
	}
	public static String decryptA(String str) {
		if(str != null)
		{
			int newInt = 0; // gets the int that the encryptions returns 
			int k = 0;
			// char letters;
			StringBuffer letters = new StringBuffer();
			String sub;
			for (int i = 0; i <= (str.length() - 2); i++) {
				
				if (i % 2 == 0 && i != 0)// only take every other letter
				{
					sub = str.substring(i, i+2);
					newInt = decrypto(sub);
					newInt = newInt - k;
					letters.append((char) newInt);
					k++;
				} else if (i == 0) // this only is for the first one because we
				{				   // don"t want to sub off 1
					sub = str.substring(i, i+2);
					newInt = decrypto(sub);
					letters.append((char) newInt);
					k++;
				}
			}
			return letters.toString();
		}
		else
			return str;
	}
	private static int decrypto(String chars) {
		String[] code =
		        new String[] { "5f", "x2", "d8", "ff", "dx", "3e", "r4", "1a", "ww", "2a", "44", "3a", "66", "4a",
		                "88", "o0", "p9", "x1", "s4", "x3", "s2", "s1", "x4", "s5", "x5", "s8", "x6", "s9", "f0", "x7",
		                "n6", "j1", "t1", "x9", "t2", "n5", "t3", "t4", "t7", "t8", "n4", "t9", "j2", "n3", "y2", "t6",
		                "y1", "u1", "n1", "b1", "h2", "n2", "h3", "t5", "kk", "k1", "k0", "RR", "j6", "R1", "L1", "h1",
		                "j3", "L2", "y3", "j4", "W0", "L3", "SU", "SY", "hx", "u2", "P1", "j5", "SL", "t0", "L9", "SS",
		                "S9", "b4", "S8", "yp", "L8", "S7", "b3", "S6", "k2", "b2", "R2", "S3", "u3", "L7", "g7", "S5",
		                "g8", "j7", "S4", "L6", "h5", "P3", "j8", "L5", "h6", "u5", "g6", "g5", "W9", "W8", "u4", "P4",
		                "g4", "P2", "zv", "h7", "L4", "k3", "va", "u7", "R3", "g3", "v9", "vv", "v8", "z1", "z2", "v7",
		                "v6", "R5", "v5", "v4", "u6", "v3", "R4", "g9", "z3", "WW", "v2", "b6", "v0", "P5", "v1", "b5",
		                "B3", "g2", "S2", "k4", "B2", "h4", "W6", "g1", "W5", "P6", "Y3", "Y2", "B4", "u8", "B1", "B6",
		                "B5", "R6", "S1", "P7", "k5", "W4", "P8", "Y1", "Y8", "E2", "W1", "W2", "z4", "W3", "Y7", "Y9",
		                "P9", "m4", "Y4", "R7", "Y5", "G2", "m5", "R9", "G1", "zd", "u9", "Y5", "k6", "zc", "z5", "zb",
		                "b7", "R8", "z9", "za", "k7", "z0", "m1", "z8", "y9", "m6", "z7", "zz", "y8", "m2", "m9", "zf",
		                "h0", "z6", "m3", "h9", "m8", "k8", "k9", "y7", "m7", "m0", "h8", "b8", "b9", "y6", "y5", "y4","zx" };
		
		int num=0;
		for (; num < code.length; num++) {
			
			if (chars.equals(code[num])) 
				break;
			
		}
		return num + 32;
	}
	private static String crypto(int num) {
		String nCode = "";
		if (num > 254) num = 32;
		switch (num) {
			case 32:
				nCode = "5f";
				break;
			case 33:
				nCode = "x2";
				break;
			case 34:
				nCode = "d8";
				break;
			case 35:
				nCode = "ff";
				break;
			case 36:
				nCode = "dx";
				break;
			case 37:
				nCode = "3e";
				break;
			case 38:
				nCode = "r4";
				break;
			case 39:
				nCode = "1a";
				break;
			case 40:
				nCode = "ww";
				break;
			case 41:
				nCode = "2a";
				break;
			case 42:
				nCode = "44";
				break;
			case 43:
				nCode = "3a";
				break;
			case 44:
				nCode = "66";
				break;
			case 45:
				nCode = "4a";
				break;
			case 46:
				nCode = "88";
				break;
			case 47:
				nCode = "o0";
				break;
			case 48:
				nCode = "p9";
				break;
			case 49:
				nCode = "x1";
				break;
			case 50:
				nCode = "s4";
				break;
			case 51:
				nCode = "x3";
				break;
			case 52:
				nCode = "s2";
				break;
			case 53:
				nCode = "s1";
				break;
			case 54:
				nCode = "x4";
				break;
			case 55:
				nCode = "s5";
				break;
			case 56:
				nCode = "x5";
				break;
			case 57:
				nCode = "s8";
				break;
			case 58:
				nCode = "x6";
				break;
			case 59:
				nCode = "s9";
				break;
			case 60:
				nCode = "f0";
				break;
			case 61:
				nCode = "x7";
				break;
			case 62:
				nCode = "n6";
				break;
			case 63:
				nCode = "j1";
				break;
			case 64:
				nCode = "t1";
				break;
			case 65:
				nCode = "x9";
				break;
			case 66:
				nCode = "t2";
				break;
			case 67:
				nCode = "n5";
				break;
			case 68:
				nCode = "t3";
				break;
			case 69:
				nCode = "t4";
				break;
			case 70:
				nCode = "t7";
				break;
			case 71:
				nCode = "t8";
				break;
			case 72:
				nCode = "n4";
				break;
			case 73:
				nCode = "t9";
				break;
			case 74:
				nCode = "j2";
				break;
			case 75:
				nCode = "n3";
				break;
			case 76:
				nCode = "y2";
				break;
			case 77:
				nCode = "t6";
				break;
			case 78:
				nCode = "y1";
				break;
			case 79:
				nCode = "u1";
				break;
			case 80:
				nCode = "n1";
				break;
			case 81:
				nCode = "b1";
				break;
			case 82:
				nCode = "h2";
				break;
			case 83:
				nCode = "n2";
				break;
			case 84:
				nCode = "h3";
				break;
			case 85:
				nCode = "t5";
				break;
			case 86:
				nCode = "kk";
				break;
			case 87:
				nCode = "k1";
				break;
			case 88:
				nCode = "k0";
				break;
			case 89:
				nCode = "RR";
				break;
			case 90:
				nCode = "j6";
				break;
			case 91:
				nCode = "R1";
				break;
			case 92:
				nCode = "L1";
				break;
			case 93:
				nCode = "h1";
				break;
			case 94:
				nCode = "j3";
				break;
			case 95:
				nCode = "L2";
				break;
			case 96:
				nCode = "y3";
				break;
			case 97:
				nCode = "j4";
				break;
			case 98:
				nCode = "W0";
				break;
			case 99:
				nCode = "L3";
				break;
			case 100:
				nCode = "SU";
				break;
			case 101:
				nCode = "SY";
				break;
			case 102:
				nCode = "hx";
				break;
			case 103:
				nCode = "u2";
				break;
			case 104:
				nCode = "P1";
				break;
			case 105:
				nCode = "j5";
				break;
			case 106:
				nCode = "SL";
				break;
			case 107:
				nCode = "t0";
				break;
			case 108:
				nCode = "L9";
				break;
			case 109:
				nCode = "SS";
				break;
			case 110:
				nCode = "S9";
				break;
			case 111:
				nCode = "b4";
				break;
			case 112:
				nCode = "S8";
				break;
			case 113:
				nCode = "yp";
				break;
			case 114:
				nCode = "L8";
				break;
			case 115:
				nCode = "S7";
				break;
			case 116:
				nCode = "b3";
				break;
			case 117:
				nCode = "S6";
				break;
			case 118:
				nCode = "k2";
				break;
			case 119:
				nCode = "b2";
				break;
			case 120:
				nCode = "R2";
				break;
			case 121:
				nCode = "S3";
				break;
			case 122:
				nCode = "u3";
				break;
			case 123:
				nCode = "L7";
				break;
			case 124:
				nCode = "g7";
				break;
			case 125:
				nCode = "S5";
				break;
			case 126:
				nCode = "g8";
				break;
			case 127:
				nCode = "j7";
				break;
			case 128:
				nCode = "S4";
				break;
			case 129:
				nCode = "L6";
				break;
			case 130:
				nCode = "h5";
				break;
			case 131:
				nCode = "P3";
				break;
			case 132:
				nCode = "j8";
				break;
			case 133:
				nCode = "L5";
				break;
			case 134:
				nCode = "h6";
				break;
			case 135:
				nCode = "u5";
				break;
			case 136:
				nCode = "g6";
				break;
			case 137:
				nCode = "g5";
				break;
			case 138:
				nCode = "W9";
				break;
			case 139:
				nCode = "W8";
				break;
			case 140:
				nCode = "u4";
				break;
			case 141:
				nCode = "P4";
				break;
			case 142:
				nCode = "g4";
				break;
			case 143:
				nCode = "P2";
				break;
			case 144:
				nCode = "zv";
				break;
			case 145:
				nCode = "h7";
				break;
			case 146:
				nCode = "L4";
				break;
			case 147:
				nCode = "k3";
				break;
			case 148:
				nCode = "va";
				break;
			case 149:
				nCode = "u7";
				break;
			case 150:
				nCode = "R3";
				break;
			case 151:
				nCode = "g3";
				break;
			case 152:
				nCode = "v9";
				break;
			case 153:
				nCode = "vv";
				break;
			case 154:
				nCode = "v8";
				break;
			case 155:
				nCode = "z1";
				break;
			case 156:
				nCode = "z2";
				break;
			case 157:
				nCode = "v7";
				break;
			case 158:
				nCode = "v6";
				break;
			case 159:
				nCode = "R5";
				break;
			case 160:
				nCode = "v5";
				break;
			case 161:
				nCode = "v4";
				break;
			case 162:
				nCode = "u6";
				break;
			case 163:
				nCode = "v3";
				break;
			case 164:
				nCode = "R4";
				break;
			case 165:
				nCode = "g9";
				break;
			case 166:
				nCode = "z3";
				break;
			case 167:
				nCode = "WW";
				break;
			case 168:
				nCode = "v2";
				break;
			case 169:
				nCode = "b6";
				break;
			case 170:
				nCode = "v0";
				break;
			case 171:
				nCode = "P5";
				break;
			case 172:
				nCode = "v1";
				break;
			case 173:
				nCode = "b5";
				break;
			case 174:
				nCode = "B3";
				break;
			case 175:
				nCode = "g2";
				break;
			case 176:
				nCode = "S2";
				break;
			case 177:
				nCode = "k4";
				break;
			case 178:
				nCode = "B2";
				break;
			case 179:
				nCode = "h4";
				break;
			case 180:
				nCode = "W6";
				break;
			case 181:
				nCode = "g1";
				break;
			case 182:
				nCode = "W5";
				break;
			case 183:
				nCode = "P6";
				break;
			case 184:
				nCode = "Y3";
				break;
			case 185:
				nCode = "Y2";
				break;
			case 186:
				nCode = "B4";
				break;
			case 187:
				nCode = "u8";
				break;
			case 188:
				nCode = "B1";
				break;
			case 189:
				nCode = "B6";
				break;
			case 190:
				nCode = "B5";
				break;
			case 191:
				nCode = "R6";
				break;
			case 192:
				nCode = "S1";
				break;
			case 193:
				nCode = "P7";
				break;
			case 194:
				nCode = "k5";
				break;
			case 195:
				nCode = "W4";
				break;
			case 196:
				nCode = "P8";
				break;
			case 197:
				nCode = "Y1";
				break;
			case 198:
				nCode = "Y8";
				break;
			case 199:
				nCode = "E2";
				break;
			case 200:
				nCode = "W1";
				break;
			case 201:
				nCode = "W2";
				break;
			case 202:
				nCode = "z4";
				break;
			case 203:
				nCode = "W3";
				break;
			case 204:
				nCode = "Y7";
				break;
			case 205:
				nCode = "Y9";
				break;
			case 206:
				nCode = "P9";
				break;
			case 207:
				nCode = "m4";
				break;
			case 208:
				nCode = "Y4";
				break;
			case 209:
				nCode = "R7";
				break;
			case 210:
				nCode = "Y5";
				break;
			case 211:
				nCode = "G2";
				break;
			case 212:
				nCode = "m5";
				break;
			case 213:
				nCode = "R9";
				break;
			case 214:
				nCode = "G1";
				break;
			case 215:
				nCode = "zd";
				break;
			case 216:
				nCode = "u9";
				break;
			case 217:
				nCode = "Y5";
				break;
			case 218:
				nCode = "k6";
				break;
			case 219:
				nCode = "zc";
				break;
			case 220:
				nCode = "z5";
				break;
			case 221:
				nCode = "zb";
				break;
			case 222:
				nCode = "b7";
				break;
			case 223:
				nCode = "R8";
				break;
			case 224:
				nCode = "z9";
				break;
			case 225:
				nCode = "za";
				break;
			case 226:
				nCode = "k7";
				break;
			case 227:
				nCode = "z0";
				break;
			case 228:
				nCode = "m1";
				break;
			case 229:
				nCode = "z8";
				break;
			case 230:
				nCode = "y9";
				break;
			case 231:
				nCode = "m6";
				break;
			case 232:
				nCode = "z7";
				break;
			case 233:
				nCode = "zz";
				break;
			case 234:
				nCode = "y8";
				break;
			case 235:
				nCode = "m2";
				break;
			case 236:
				nCode = "m9";
				break;
			case 237:
				nCode = "zf";
				break;
			case 238:
				nCode = "h0";
				break;
			case 239:
				nCode = "z6";
				break;
			case 240:
				nCode = "m3";
				break;
			case 241:
				nCode = "h9";
				break;
			case 242:
				nCode = "m8";
				break;
			case 243:
				nCode = "k8";
				break;
			case 244:
				nCode = "k9";
				break;
			case 245:
				nCode = "y7";
				break;
			case 246:
				nCode = "m7";
				break;
			case 247:
				nCode = "m0";
				break;
			case 248:
				nCode = "h8";
				break;
			case 249:
				nCode = "b8";
				break;
			case 250:
				nCode = "b9";
				break;
			case 252:
				nCode = "y6";
				break;
			case 253:
				nCode = "y5";
				break;
			case 254:
				nCode = "y4";
				break;
			case 255:
				nCode = "zx";
				break;
		}
		return nCode;
	}
}
