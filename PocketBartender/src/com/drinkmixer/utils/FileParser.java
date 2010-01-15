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
import android.view.View;
import android.widget.ImageView;

import com.drinkmixer.R;
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
	
	
	
	public static final String[] glasses= {"champagne","cocktail","highball","hurricane","irish coffee",
		"pint","margarita","mug","parfait","pilsner","pousse cafe","punch","rocks","shot","snifter","sour",
		"white wine","red wine","old-fashioned","cordial","collins"};
		
		public static void getGlassImage(ImageView imgGlassType,String glassName)
		{
			//set glass image
			if(glasses[0].equalsIgnoreCase(glassName))
				imgGlassType.setBackgroundResource(R.drawable.champ);
			else if(glasses[1].equalsIgnoreCase(glassName))
				imgGlassType.setBackgroundResource(R.drawable.cocktail);
			else if(glasses[2].equalsIgnoreCase(glassName))
				imgGlassType.setBackgroundResource(R.drawable.highball);
			else if(glasses[3].equalsIgnoreCase(glassName))
				imgGlassType.setBackgroundResource(R.drawable.hurricane);
			else if(glasses[4].equalsIgnoreCase(glassName))
				imgGlassType.setBackgroundResource(R.drawable.irish);
			else if(glasses[5].equalsIgnoreCase(glassName))
				imgGlassType.setBackgroundResource(R.drawable.pint);
			else if(glasses[6].equalsIgnoreCase(glassName))
				imgGlassType.setBackgroundResource(R.drawable.margarita);
			else if(glasses[7].equalsIgnoreCase(glassName))
				imgGlassType.setBackgroundResource(R.drawable.mug);
			else if(glasses[8].equalsIgnoreCase(glassName))
				imgGlassType.setBackgroundResource(R.drawable.parfait);
			else if(glasses[9].equalsIgnoreCase(glassName))
				imgGlassType.setBackgroundResource(R.drawable.pilsner);
			else if(glasses[10].equalsIgnoreCase(glassName))
				imgGlassType.setBackgroundResource(R.drawable.pousse_cafe);
			else if(glasses[11].equalsIgnoreCase(glassName))
				imgGlassType.setBackgroundResource(R.drawable.punch);
			else if(glasses[12].equalsIgnoreCase(glassName))
				imgGlassType.setBackgroundResource(R.drawable.rocks);
			else if(glasses[13].equalsIgnoreCase(glassName))
				imgGlassType.setBackgroundResource(R.drawable.shot);
			else if(glasses[14].equalsIgnoreCase(glassName))
				imgGlassType.setBackgroundResource(R.drawable.snifter);
			else if(glasses[15].equalsIgnoreCase(glassName))
				imgGlassType.setBackgroundResource(R.drawable.sour);
			else if(glasses[16].equalsIgnoreCase(glassName))
				imgGlassType.setBackgroundResource(R.drawable.whitewine);
			else if(glasses[17].equalsIgnoreCase(glassName))
				imgGlassType.setBackgroundResource(R.drawable.redwine);
			
			else if(glasses[18].equalsIgnoreCase(glassName))
				imgGlassType.setBackgroundResource(R.drawable.old_fashion);
			else if(glasses[19].equalsIgnoreCase(glassName))
				imgGlassType.setBackgroundResource(R.drawable.cordial);
			else if(glasses[20].equalsIgnoreCase(glassName))
				imgGlassType.setBackgroundResource(R.drawable.collins);
			else
				imgGlassType.setBackgroundResource(R.drawable.detail_info);
			
		}
		
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
