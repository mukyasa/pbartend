package com.card.util;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.content.SharedPreferences;

import com.card.R;
import com.card.domain.CardSet;
import com.card.domain.FlashCard;

public class AppUtil {

	public static final String PREFS_NAME = "app_pref";
	public static final String PREF_SOUND = "silentMode";
	public static final String PREF_FONT_SIZE = "fontSize";

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
	public static float getFontSize(Context context) {
		SharedPreferences settings = context.getSharedPreferences(AppUtil.PREFS_NAME, 0);
		String fontSize = settings.getString(PREF_FONT_SIZE, context.getString(R.string.normal));

		if (fontSize.equals(context.getString(R.string.smaller)))
			return 12;
		else if (fontSize.equals(context.getString(R.string.normal)))
			return 16;
		else
			return 24;
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
