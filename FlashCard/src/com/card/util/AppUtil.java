package com.card.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.card.R;
import com.card.domain.CardSet;
import com.card.domain.FlashCard;
import com.card.handler.ApplicationHandler;

public class AppUtil {

	public static final String PREFS_NAME = "app_pref";
	public static final String PREF_SOUND = "silentMode";
	public static final String PREF_FONT_SIZE = "fontSize";
	public static final String PREF_BOOKMARKS= "bookmarks";
	public static final String BOOKMARKS = "rootbookmarks";
	public static String searchTerm;

	/**
	 * Gets the sound preferences
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getSound(Context context) {
		SharedPreferences settings = context.getSharedPreferences(AppUtil.PREFS_NAME, 0);
		return settings.getBoolean(PREF_SOUND, false);
	}
	
	/**
	 * Gets bookmarks out of pref
	 * Nov 24, 2009
	 * dmason
	 * @param context
	 * @return
	 *
	 */
	public static String getBookmarks(Context context)
	{
		SharedPreferences settings = context.getSharedPreferences(AppUtil.PREFS_NAME, 0);
		String bookmarks = settings.getString(PREF_BOOKMARKS, "");
		
		return bookmarks;
	}
	
	public static void setBookmarks(Context context,CardSet cardset) throws JSONException
	{
		
			
		SharedPreferences settings = context.getSharedPreferences(AppUtil.PREFS_NAME, 0);
		String oldbookmarks=getBookmarks(context);
		//Log.v("", "bookmarks from pref="+oldbookmarks);
		
		JSONObject bookmarks=null;
		JSONArray bookmarkwrapper=null;
		//look for older bookmarks
		if(!"".equals(oldbookmarks))
		{
			JSONTokener toke = new JSONTokener(oldbookmarks);
			//Log.v("", "TOKE="+toke.toString());
			bookmarks = new JSONObject(toke);
			//Log.v("", "ROOT="+bookmarks.toString());
			bookmarkwrapper = new JSONArray(bookmarks.getString(BOOKMARKS));
			//Log.v("", "BOOKMARKS="+bookmarkwrapper.toString());
		}
		else
		{
			bookmarks = new JSONObject();
			bookmarkwrapper = new JSONArray();
		}

		
		JSONArray bookmarkdetails = new JSONArray();
		
        //put in new ones
		bookmarkdetails.put(cardset.id);
		bookmarkdetails.put(cardset.title);
		//Log.v("", "BOOKMARK DETAILS="+bookmarkdetails.toString());
		
		bookmarkwrapper.put(bookmarkdetails);
		bookmarks.put(BOOKMARKS, bookmarkwrapper);
		
    	//get existing bookmarks first
    	//Log.v("", "bookmarks="+bookmarks.toString());
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putString(AppUtil.PREF_BOOKMARKS, bookmarks.toString());
	    
	    // Don't forget to commit your edits!!!
	    editor.commit();
	    
	}
	
	/**
	 * Gets the URL
	 * Nov 23, 2009
	 * dmason
	 * @param value
	 * @param sortType
	 * @param pageNumber
	 * @return
	 *
	 */
	public static JSONArray getQuizletData(String value,String sortType,int pageNumber)
	{
		JSONArray sets=null;
		try {
	        //Log.v("", "sort type=" + sortType + " value="+ value + " pageNumber="+ pageNumber);
	        URL url = new URL("http://quizlet.com/api/"+ Constants.API_VERS +"/sets?dev_key="+Constants.DEV_KEY + value + Constants.EXTRAS + "&sort="+sortType+"&page="+pageNumber);
	        Log.v("", url.toString());
	        InputStream is = url.openStream();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	        StringBuilder sb = new StringBuilder();
	        
	        String line;
	        while ((line = reader.readLine()) != null) 
	        {
	        	sb.append(line + "\n");
	        }
	        
	        JSONTokener toke = new JSONTokener(sb.toString());
	        JSONObject jsonObj = new JSONObject(toke);
	        
	        
	        if(((String)jsonObj.get("response_type")).equals("ok"))
	        {
	        	sets = (JSONArray)jsonObj.get("sets");
	        }
	        
        } catch (MalformedURLException e) {
	        e.printStackTrace();
        } catch (IOException e) {
	        e.printStackTrace();
        } catch (JSONException e) {
	        e.printStackTrace();
        }
        
        return sets;
	}
	
	/**
	 * builds out a new arraylist of cardsets
	 * Nov 23, 2009
	 * dmason
	 * @param cardsets
	 * @param sets
	 * @return
	 * @throws JSONException
	 *
	 */
	public static ArrayList<CardSet> createNewCardSetArrayList(ArrayList<CardSet> cardsets,JSONArray sets) throws JSONException
	{
		 for(int i=0;i<sets.length();i++)
         {
         	JSONObject set = (JSONObject)sets.get(i);
         	CardSet cardset = new CardSet((String)set.get("title"),(JSONArray)set.get("terms"),(Integer)set.get("term_count"),(Integer)set.get("id"));
         	cardsets.add(cardset);
         }
		 
		 return cardsets;
	}
	
	/**
	 * Call the API and get the results back based on a set of values puts it on the application handler
	 * Nov 23, 2009
	 * dmason
	 * @param value
	 * @return
	 *
	 */
	public static boolean initCardSets(String value,String sortType,int pageNumber)
	{
		boolean didGetResults= false;
        	try {
	            
        		JSONArray sets = getQuizletData(value,sortType,pageNumber);
	            
	            if(sets!=null)
	            {
	                ApplicationHandler.clearHandlerInstance();
	                ApplicationHandler handler = ApplicationHandler.instance();
	                ArrayList<CardSet> cardsets = handler.cardsets;
	                //gets the titles
	                for(int i=0;i<sets.length();i++)
	                {
	                	JSONObject set = (JSONObject)sets.get(i);
	                	CardSet cardset = new CardSet((String)set.get("title"),(JSONArray)set.get("terms"),(Integer)set.get("term_count"),(Integer)set.getInt("id"));
	                	cardsets.add(cardset);
	                }
	                
	                didGetResults=true;
	            }
            } catch (JSONException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
            }
	        
        return didGetResults;
        
        
	}

	/**
	 * test to see id supplied radio text is the same that is in preferences
	 * 
	 * @param context
	 * @param radioText 
	 * @param defaultValue
	 * @return
	 */
	public static boolean chooseFontSize(Context context, String radioText) {
		SharedPreferences settings = context.getSharedPreferences(AppUtil.PREFS_NAME, 0);
		String fontSize = settings.getString(PREF_FONT_SIZE, context.getString(R.string.normal));
		
		if (fontSize.equals(radioText))
			return true;
		else
			return false;
	}

	/**
	 * gets the font size 12sp 16sp or 22sp
	 * 
	 * @return
	 */
	public static float getFontSize(Context context,String cardText) {
		SharedPreferences settings = context.getSharedPreferences(AppUtil.PREFS_NAME, 0);
		String fontSize = settings.getString(PREF_FONT_SIZE, context.getString(R.string.autosize));

		if (fontSize.equals(context.getString(R.string.smaller)))
			return 12;
		else if (fontSize.equals(context.getString(R.string.normal)))
			return 16;
		else if(fontSize.equals(context.getString(R.string.bigger)))
			return 24;
		else
		{
			
			int len = cardText.length();
			//Log.v("", "LENGTH="+ len);
			if(len <= 100)
				return 30;
			else if(len > 100 && len <= 276)
				return 24;
			else if(len > 276 && len <=500)
				return 16;
			else
				return 12;
		}
	}

	/**
	 * takes a cardset and shuffles the cards out of order
	 * 
	 * @param set
	 * @return
	 */
	public static CardSet shuffleCard(CardSet set) {
		return set;
	}

	/**
	 * Takes the current set and filters out the wrong answers for a new set of
	 * wrong cards
	 * 
	 * @param set
	 * @return
	 */
	public static CardSet getWrongCardsOnly(CardSet set) {
		CardSet newSet = new CardSet();

		newSet.title = set.title;
		ArrayList<FlashCard> flashCards = set.flashcards;
		Iterator<FlashCard> iter = flashCards.iterator();
		int newCount = 0;
		// loop cards to get wrong cards
		while (iter.hasNext()) {
			FlashCard card = iter.next();
			// only add the wrong cards to the new set
			if (!card.isCorrect) {
				newCount++;
				// reset card values
				card.wasSeen = false;
				card.isCorrect = true;
				newSet.flashcards.add(card);
			}
		}
		// get the new count
		newSet.cardCount = newCount;

		return newSet;
	}
}
