package com.drinkmixer.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.StringTokenizer;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DrinkIngredientsInsert {
	public static InputStream in=null;

	public static  void insertDrinkIng(SQLiteDatabase db){
		
		try {
	        Reader inr = new InputStreamReader(in);
	        
	        String sCurrentLine;
	        
	        BufferedReader bin = new BufferedReader(inr);
	        
	        while ((sCurrentLine = bin.readLine()) != null) 
	        {
	        	StringTokenizer toker = new StringTokenizer(sCurrentLine,",");
	        	
	        	String sql = "INSERT INTO "+DataDAO.TABLE_DRINK_INGREDIENTS+" VALUES("+toker.nextToken()+","+toker.nextToken()+","+toker.nextToken()+","+toker.nextToken()+ ");";
	        	db.execSQL(sql);
	        	
	        }
        } catch (SQLException e) {
	        e.printStackTrace();
        } catch (IOException e) {
	        e.printStackTrace();
        }
		
	}

}