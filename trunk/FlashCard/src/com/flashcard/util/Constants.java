package com.flashcard.util;


public class Constants {
	public static String VERSION = "1.0.8";
	public static String COMPANY_NAME = "mypocket-technologies";
	public static final String API_VERS ="1.0";
	public static int DEFAULT_PAGE_NUMBER = 1;
	public static int TOTAL_RESULTS=0;
	public static String SORT_TYPE_DEFALUT = "alphabetical";
	public static String SORT_TYPE_STUDIED = "most_studied";
	public static String SORT_TYPE_RECENT = "most_recent";
	public static final String DEV_KEY="f4ndi00uluokcc0c";
	public static final int CARDS_PER_PAGE=25;
	public static final String EXTRAS ="&per_page="+CARDS_PER_PAGE+"&extended=on";
	
	//keep this in here so they get reset properly
	public static int count=0;
	public static int countlabel=1;
	
	/*
	 * 1.0.6 - 1/7/2010 Added instuctions
	 * 1.0.7 - 1/7/2010 Added new info in the about menu
	 * 1.0.8 - 6/6/2010 Added error handling to fling and init methods on FlashcardTest
	 * 1.0.9 - 6/6/2010 Added error handling to fling and init methods on FlashcardTest
	 * */
}
