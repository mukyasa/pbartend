package com.flashcard.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.flashcard.R;
import com.flashcard.domain.CardSet;
import com.flashcard.domain.FlashCard;
import com.flashcard.domain.MessageHandler;
import com.flashcard.handler.ApplicationHandler;

public class AppUtil extends Constants {
	public static final String PREFS_NAME = "app_pref";
	public static final String PREF_SOUND = "silentMode";
	public static final String PREF_FONT_SIZE = "fontSize";
	public static final String PREF_SAW_DIRECTIONS = "directions";
	public static final String PREF_BOOKMARKS = "bookmarks";
	public static final String BOOKMARKS = "rootbookmarks";
	public static final String PREF_SAVED_CARDS = "savedcards";
	public static final String PREF_CURRENT_CARD = "currentcard";
	public static boolean isFlipped = false;
	public static String searchTerm;
	public boolean isCalled=false;

	private static AppUtil instance=null;
	
	//singleton for one reason that is the instructions
	public static AppUtil getInstance()
	{
		if(instance==null)
			instance = new AppUtil();
		
		return instance;
	}
	
	//<img src="http://i.quizlet.com/i/4400a18b1ea131770665eacf8d002de9_m.jpg" width="155" height="240" />
	/**
	 * Takes a html image tag and gets the url
	 */
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
	/**
	 * get an iamge from a given url and returns a drawable object to use
	 * Dec 12, 2009
	 * dmason
	 * @param url
	 * @param context
	 * @return
	 *
	 */
	public static void getsizedDrawableFromURL(String url,Context context,TextView tvFlashCard) {
		 BitmapDrawable drawable=null;
		try {
	        URL myImageURL = null;
	        myImageURL = new URL(url);
	        HttpURLConnection connection = (HttpURLConnection) myImageURL.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        //now size
	        int width = myBitmap.getWidth();
	        int height = myBitmap.getHeight();
	        float fwidth = width;
	        float fheight = height;
	        float newWidth=0;
	        float newHeight=0;
	       
	         
	        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
	        
			if (display.getWidth() > display.getHeight()) 
			{
				newWidth=155;
				float ratio =fheight/fwidth;
	        	newHeight = newWidth*ratio;
			}
			else
			{
				newHeight=160;
				float ratio =fwidth/fheight;
				newWidth = newHeight*ratio;
			}
	       
	        // calculate the scale - in this case = 0.4f
	        float scaleWidth = ((float) newWidth) / width;
	        float scaleHeight = (newHeight) / height;
	       
	        // create a matrix for the manipulation
	        Matrix matrix = new Matrix();
	        // resize the bit map
	        matrix.postScale(scaleWidth, scaleHeight);

	        // recreate the new Bitmap
	        Bitmap resizedBitmap = Bitmap.createBitmap(myBitmap, 0, 0,
	                          width, height, matrix, true); 
	        //set back to drawable
	        drawable = new BitmapDrawable(resizedBitmap); 
	        
	        
	        //for portrait
	        if (display.getOrientation() == Configuration.ORIENTATION_PORTRAIT) 
	        	tvFlashCard.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
	        else //for landscape
	        	tvFlashCard.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
	        
	        tvFlashCard.setGravity(Gravity.TOP);
	        tvFlashCard.setCompoundDrawablePadding(5);
	        
        } catch (MalformedURLException e) {
	        e.printStackTrace();
        } catch (IOException e) {
	        e.printStackTrace();
        }
	}
	
	/**
	 * gets the last known viewed card
	 * Dec 27, 2009
	 * dmason
	 * @param context
	 * @return
	 *
	 */
	public static int getLastVeiwedCard(Context context) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		return settings.getInt(PREF_CURRENT_CARD, 0);
	}
	
	public static void setLastVeiwedCard(Context context,int cardNumber) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(AppUtil.PREF_CURRENT_CARD, cardNumber);
		// Don't forget to commit your edits!!!
		editor.commit();
	}
	
	/**
	 * shows directions
	 * Jan 7, 2010
	 * dmason
	 * @param context
	 * @return
	 *
	 */
	public static int getSawDirections(Context context) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		return settings.getInt(PREF_SAW_DIRECTIONS, 0);
	}
	
	public static void setSawDirections(Context context,int wizardInt) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(AppUtil.PREF_SAW_DIRECTIONS, wizardInt);
		// Don't forget to commit your edits!!!
		editor.commit();
	}
	
	
	/**
	 * Gets the sound preferences
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getSound(Context context) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		return settings.getBoolean(PREF_SOUND, true);
	}
	/**
	 * Gets bookmarks out of pref Nov 24, 2009 dmason
	 * 
	 * @param context
	 * @return
	 */
	public static String getBookmarks(Context context) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		String bookmarks = settings.getString(PREF_BOOKMARKS, "");
		return bookmarks;
	}
	/**
	 * gets saved cards Dec 3, 2009 dmason
	 * 
	 * @param context
	 * @return
	 */
	public static String getSavedCards(Context context) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		String savedCards = settings.getString(PREF_SAVED_CARDS, "");
		return savedCards;
	}
	/**
	 * saves the cards to pref Dec 3, 2009 dmason
	 * 
	 * @param context
	 * @param cardset
	 * @throws JSONException
	 */
	public static MessageHandler setSavedCards(Context context, CardSet cardset) throws JSONException {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		String oldCards = getSavedCards(context);
		MessageHandler msg = new MessageHandler();
		// Log.v("", "SAVED_CARDS from pref="+oldCards);
		JSONArray sets = null;
		// look for older cards
		if (!"".equals(oldCards)) {
			JSONTokener toke = new JSONTokener(oldCards);
			JSONObject jsonObj = new JSONObject(toke);
			if (((String) jsonObj.get("response_type")).equals("ok")) {
				sets = (JSONArray) jsonObj.get("sets");
			}
		} else {
			sets = new JSONArray();
		}
		// no need to put ones in that exist
		if (!sets.toString().contains(cardset.id + "")) {
			JSONObject root = new JSONObject();
			JSONObject set = new JSONObject();
			JSONArray terms = new JSONArray();
			ArrayList<FlashCard> flashcards = cardset.flashcards;
			Iterator<FlashCard> iter = flashcards.iterator();
			while (iter.hasNext()) {
				FlashCard flashCard = (FlashCard) iter.next();
				JSONArray termdata = new JSONArray();
				termdata.put(flashCard.question);
				termdata.put(flashCard.answer);
				termdata.put(flashCard.imageURL);
				terms.put(termdata);
			}
			// Log.v("","There is one in there" + sets.toString());
			set.put("id", cardset.id);
			set.put("title", cardset.title);
			set.put("term_count", cardset.cardCount);
			set.put("terms", terms);
			sets.put(set);
			root.put("response_type", "ok");
			root.put("total_results", sets.length());
			root.put("sets", sets);
			// get existing cards first
			SharedPreferences.Editor editor = settings.edit();
			editor.putString(PREF_SAVED_CARDS, root.toString());
			// Don't forget to commit your edits!!!
			// check to see if the cards exists if so dont do it
			if (sets.length() <= 10) editor.commit();
		} else {
			msg.didSave = false;
			msg.message = cardset.title + " already saved.";
			return msg;
		}
		// Log.v("", "LENGTH="+sets.length());
		if (sets.length() <= 10) msg.didSave = true;
		else {
			msg.didSave = false;
			msg.message = "Maximum cards saved reached.";
		}
		return msg;
	}
	public static void deleteBookmarks(Context context, Integer id) throws JSONException {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		String oldbookmarks = getBookmarks(context);
		JSONObject newbookmark = new JSONObject();
		JSONArray newbookmarkwrapper = new JSONArray();
		// look for older bookmarks
		JSONTokener toke = new JSONTokener(oldbookmarks);
		JSONObject bookmarks = new JSONObject(toke);
		JSONArray bookmarkwrapper = new JSONArray(bookmarks.getString(BOOKMARKS));
		// loop old bookmarks and look for the one we are deleting
		for (int i = 0; i < bookmarkwrapper.length(); i++) {
			JSONArray b = (JSONArray) bookmarkwrapper.get(i);
			// Log.v("",id+"=="+b.get(0));
			if (((Integer) b.get(0)).intValue() != id.intValue()) newbookmarkwrapper.put(b);
		}
		newbookmark.put(BOOKMARKS, newbookmarkwrapper);
		// get existing bookmarks first
		// Log.v("", "bookmarks="+bookmarks.toString());
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(PREF_BOOKMARKS, newbookmark.toString());
		// Don't forget to commit your edits!!!
		// check to see if the bookmark exists if so dont do it
		editor.commit();
	}
	public static void deleteCard(Context context, Integer id) throws JSONException {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		String oldCards = getSavedCards(context);
		// Log.v("", "SAVED_CARDS from pref="+oldCards);
		JSONArray sets = null;
		JSONTokener toke = new JSONTokener(oldCards);
		JSONObject jsonObj = new JSONObject(toke);
		JSONObject root = new JSONObject();
		JSONArray newsets = new JSONArray();
		TOTAL_RESULTS = (Integer) jsonObj.get("total_results");
		sets = (JSONArray) jsonObj.get("sets");
		for (int i = 0; i < sets.length(); i++) {
			JSONObject set = (JSONObject) sets.get(i);
			if (((Integer) set.get("id")).intValue() != id.intValue()) {
				newsets.put(set);
			}
		}
		root.put("response_type", "ok");
		root.put("total_results", sets.length());
		root.put("sets", newsets);
		// get existing cards first
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(PREF_SAVED_CARDS, root.toString());
		// Don't forget to commit your edits!!!
		// check to see if the cards exists if so dont do it
		editor.commit();
	}
	/**
	 * Reverses the queston and the answer. Nov 27, 2009 dmason
	 * 
	 * @param context
	 * @param cardset
	 */
	public static void switchQuestionAnswer(Context context, CardSet cardset) {
		ArrayList<FlashCard> flashCards = cardset.flashcards;
		Iterator<FlashCard> iter = flashCards.iterator();
		if(!isFlipped)
			isFlipped=true;
		else
			isFlipped=false;
		
		while (iter.hasNext()) {
			FlashCard flashCard = (FlashCard) iter.next();
			String answer = flashCard.answer;
			String question = flashCard.question;
			// swap them.
			flashCard.question = answer;
			flashCard.answer = question;
		}
	}
	public static void setBookmarks(Context context, CardSet cardset) throws JSONException {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		String oldbookmarks = getBookmarks(context);
		// Log.v("", "bookmarks from pref="+oldbookmarks);
		JSONObject bookmarks = null;
		JSONArray bookmarkwrapper = null;
		// look for older bookmarks
		if (!"".equals(oldbookmarks)) {
			JSONTokener toke = new JSONTokener(oldbookmarks);
			// Log.v("", "TOKE="+toke.toString());
			bookmarks = new JSONObject(toke);
			// Log.v("", "ROOT="+bookmarks.toString());
			bookmarkwrapper = new JSONArray(bookmarks.getString(BOOKMARKS));
			// Log.v("", "BOOKMARKS="+bookmarkwrapper.toString());
		} else {
			bookmarks = new JSONObject();
			bookmarkwrapper = new JSONArray();
		}
		JSONArray bookmarkdetails = new JSONArray();
		// put in new ones
		bookmarkdetails.put(cardset.id);
		bookmarkdetails.put(cardset.title);
		// Log.v("", "BOOKMARK DETAILS="+bookmarkdetails.toString());
		bookmarkwrapper.put(bookmarkdetails);
		bookmarks.put(BOOKMARKS, bookmarkwrapper);
		// get existing bookmarks first
		// Log.v("", "bookmarks="+bookmarks.toString());
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(PREF_BOOKMARKS, bookmarks.toString());
		// Don't forget to commit your edits!!!
		// check to see if the bookmark exists if so dont do it
		editor.commit();
	}
	/**
	 * Gets the URL Nov 23, 2009 dmason
	 * 
	 * @param value
	 * @param sortType
	 * @param pageNumber
	 * @return
	 */
	public static JSONArray getQuizletData(String value, String sortType, int pageNumber) {
		JSONArray sets = null;
		try {
			// Log.v("", "sort type=" + sortType + " value="+ value +
			// " pageNumber="+ pageNumber);
			URL url =
			        new URL("http://quizlet.com/api/" + API_VERS + "/sets?dev_key=" + DEV_KEY + value + EXTRAS
			                + "&sort=" + sortType + "&page=" + pageNumber);
			// Log.v("", url.toString());
			InputStream is = url.openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			JSONTokener toke = new JSONTokener(sb.toString());
			JSONObject jsonObj = new JSONObject(toke);
			if (((String) jsonObj.get("response_type")).equals("ok")) {
				TOTAL_RESULTS = (Integer) jsonObj.get("total_results");
				sets = (JSONArray) jsonObj.get("sets");
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
	 * builds out a new arraylist of cardsets Nov 23, 2009 dmason
	 * 
	 * @param cardsets
	 * @param sets
	 * @return
	 * @throws JSONException
	 */
	public static ArrayList<CardSet> createNewCardSetArrayList(ArrayList<CardSet> cardsets, JSONArray sets) {
		try {
			for (int i = 0; i < sets.length(); i++) {
				JSONObject set = (JSONObject) sets.get(i);
				CardSet cardset =
				        new CardSet((String) set.get("title"), (JSONArray) set.get("terms"), (Integer) set
				                .get("term_count"), (Integer) set.get("id"));
				cardsets.add(cardset);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return cardsets;
	}
	/**
	 * 
	 * Dec 13, 2009
	 * dmason
	 * @param value
	 * @param sortType
	 * @param pageNumber
	 * @return
	 *
	 */
	public static boolean initCardSets(String value, String sortType, int pageNumber) {
		return initCardSets(value, sortType, pageNumber, false, null);
	}
	/**
	 * Call the API and get the results back based on a set of values puts it on
	 * the application handler Nov 23, 2009 dmason
	 * 
	 * @param value
	 * @return
	 */
	public static boolean initCardSets(String value, String sortType, int pageNumber, boolean isFromSaved,
	        Context context) {
		boolean didGetResults = false;
		try {
			JSONArray sets = null;
			if (!isFromSaved) sets = getQuizletData(value, sortType, pageNumber);
			else {
				String json = getSavedCards(context);
				JSONTokener toke = new JSONTokener(json);
				JSONObject jsonObj = new JSONObject(toke);
				if (((String) jsonObj.get("response_type")).equals("ok")) {
					Constants.TOTAL_RESULTS = (Integer) jsonObj.get("total_results");
					sets = (JSONArray) jsonObj.get("sets");
				}
			}
			if (sets != null) {
				ApplicationHandler.clearHandlerInstance();
				ApplicationHandler handler = ApplicationHandler.instance();
				ArrayList<CardSet> cardsets = handler.cardsets;
				// gets the titles
				for (int i = 0; i < sets.length(); i++) {
					JSONObject set = (JSONObject) sets.get(i);
					if (value.equals(((Integer) set.get("id")).toString()) && isFromSaved) {
						CardSet cardset =
						        new CardSet((String) set.get("title"), (JSONArray) set.get("terms"), (Integer) set
						                .get("term_count"), (Integer) set.getInt("id"));
						cardsets.add(cardset);
						break;
					} else if (!isFromSaved) {
						CardSet cardset =
						        new CardSet((String) set.get("title"), (JSONArray) set.get("terms"), (Integer) set
						                .get("term_count"), (Integer) set.getInt("id"));
						cardsets.add(cardset);
					}
				}
				didGetResults = true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return didGetResults;
	}
	
	/**
	 * removes the ascii encoded chars
	 * Dec 12, 2009
	 * dmason
	 * @param title
	 * @return
	 *
	 */
	public static String defringe(String title){
		
        // Create a pattern to match cat
        Pattern p = Pattern.compile("&quot;");
        // Create a matcher with an input string
        Matcher m = p.matcher(title);
        StringBuffer sb = new StringBuffer();
        boolean result = m.find();
        
        while(result) {
    		m.appendReplacement(sb, "\"");
    		result = m.find();
        }
        m.appendTail(sb);
        
        
        
        title = sb.toString();
        sb = new StringBuffer();
        // Create a pattern to match cat
       // p = Pattern.compile("&quot;|&#039;|&amp;");
        p = Pattern.compile("&#039;");
        // Create a matcher with an input string
        m = p.matcher(title);
        result = m.find();
        
        while(result) {
    		m.appendReplacement(sb, "'");
    		//m.appendReplacement(sb, "&");
    	
    		result = m.find();
        }
        m.appendTail(sb);
        
        
        
        title = sb.toString();
        sb = new StringBuffer();
        // Create a pattern to match cat
        p = Pattern.compile("&amp;");
        // Create a matcher with an input string
        m = p.matcher(title);
        result = m.find();
        
        while(result) {
    		m.appendReplacement(sb, "&");
    		result = m.find();
        }
        m.appendTail(sb);
        
        
        return sb.toString();
	        
      
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
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		String fontSize = settings.getString(PREF_FONT_SIZE, context.getString(R.string.autosize));
		if (fontSize.equals(radioText)) return true;
		else return false;
	}
	/**
	 * gets the font size 12sp 16sp or 22sp
	 * 
	 * @return
	 */
	public static float getFontSize(Context context, String cardText,FlashCard flashcard) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		String fontSize = settings.getString(PREF_FONT_SIZE, context.getString(R.string.autosize));
		if (fontSize.equals(context.getString(R.string.smaller))) return 12;
		else if (fontSize.equals(context.getString(R.string.normal))) return 16;
		else if (fontSize.equals(context.getString(R.string.bigger))) return 24;
		else { 
			int len = cardText.length();
			
			//if there is an image this all goes out the window
			if(!flashcard.imageURL.equals(""))
			{
				if (len <= 30) return 24;
				else if (len > 30 && len <= 80) return 18;
				else if (len > 80 && len <= 200) return 16;
				else if (len > 200 && len <= 384) return 14;
				else if (len > 384 && len <= 500) 
					return 13;
				else
					return 11;
				
			}else
			{
				// Log.v("", "LENGTH="+ len);
				Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
				if (display.getWidth() > display.getHeight()) {
					// landscape
					if (len <= 30) return 36;
					else if (len > 30 && len <= 100) return 30;
					else if (len > 100 && len <= 276) return 24;
					else if (len > 276 && len <= 500) return 16;
					else return 12;
				} else {
					// portrait
					if (len <= 30) return 36;
					else if (len > 30 && len <= 80) return 30;
					else if (len > 80 && len <= 200) return 24;
					else if (len > 200 && len <= 400) return 16;
					else return 14;
				}
			}
		}
	}
	/**
	 * takes a cardset and shuffles the cards out of order Nov 25, 2009 dmason
	 * 
	 * @param set
	 */
	public static void shuffleCard(CardSet set) {
		ArrayList<FlashCard> shuffled = set.flashcards;
		Collections.shuffle(shuffled);
		set.flashcards = shuffled;
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
