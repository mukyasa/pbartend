package com.drinkmixerdemo.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.StringTokenizer;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.drinkmixerdemo.utils.Constants;

/**
 * Does init of database 
 * 
 * @author Darren
 */
public class DrinkInserts {
	public static InputStream in = null;

	public static void insertDrinks(SQLiteDatabase db) {
		try {
			Reader inr = new InputStreamReader(in);
			String sCurrentLine;
			BufferedReader bin = new BufferedReader(inr);
			int i=0;

			while ((sCurrentLine = bin.readLine()) != null) {
				StringTokenizer toker = new StringTokenizer(sCurrentLine,"|");
				
				if(Constants.showAds && i%2==0)//demo version only
				{
					i++;
					continue;
				}
				
				String sql = "INSERT INTO " + DataDAO.TABLE_DRINK + " VALUES(" + toker.nextToken() + "," + toker.nextToken() + ",'" + toker.nextToken()
				                + "','" + toker.nextToken() + "'," + toker.nextToken() + "," + toker.nextToken() +  "," + toker.nextToken() + "," + toker.nextToken() + ");";
				db.execSQL(sql); 
				i++;
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

