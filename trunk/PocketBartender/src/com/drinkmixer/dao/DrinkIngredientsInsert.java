package com.drinkmixer.dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import android.database.sqlite.SQLiteDatabase;

public class DrinkIngredientsInsert {
	public static InputStream in=null;

	public static  void insertDrinkIng(SQLiteDatabase db) throws Exception {
		
		Reader inr = new InputStreamReader(in);
		
		String sCurrentLine;
		
		BufferedReader bin = new BufferedReader(inr);
		
		while ((sCurrentLine = bin.readLine()) != null) 
		{
			String [] tokes = sCurrentLine.split(",");
			
			String sql = "INSERT INTO "+DataDAO.TABLE_DRINK_INGREDIENTS+" VALUES("+tokes[0]+","+tokes[1]+","+tokes[2]+","+tokes[3]+ ");";
			db.execSQL(sql);
			
		}
		
	}

}
;