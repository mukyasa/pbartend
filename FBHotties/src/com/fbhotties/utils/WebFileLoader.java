package com.fbhotties.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

public class WebFileLoader {

	private String URL_STRING = "http://mypocket-technologies.com/fbhotties/";
	private final Map<String,Bitmap> drawableMap;
	private static WebFileLoader loader=null;
	
	
    public Map<String, Bitmap> getDrawableMap() {
		return drawableMap;
	}

	public WebFileLoader() {
    	drawableMap = new HashMap<String,Bitmap>();
    }
	
	/***
	 * singleton
	 * @return
	 */
	public static WebFileLoader getInstance(){
		
		if(loader==null)
			loader = new WebFileLoader();
		
		return loader;
	}
	

	public Bitmap getRemoteImage(String imageName) {
		try {
			String urlString = URL_STRING + imageName;
			
			if (drawableMap.containsKey(imageName)) {
	    		return drawableMap.get(imageName);
	    	}

			URL aURL = new URL(urlString);
			
			final URLConnection conn = aURL.openConnection();
			conn.connect();
			final BufferedInputStream bis = new BufferedInputStream(conn
					.getInputStream());
			final Bitmap bm = BitmapFactory.decodeStream(bis);
			bis.close();
			return bm;
		} catch (IOException e) {
			Log.d("DEBUGTAG", "Oh noooz an error...");
		}
		return null;
	}
	
	 public void fetchDrawableOnThread(final String imageName, final ImageView imageView) {

		 String urlString = URL_STRING + imageName;
		 if (drawableMap.containsKey(urlString)) {
	    		imageView.setImageBitmap(drawableMap.get(urlString));
	    	}

	    	final Handler handler = new Handler() {
	    		@Override
	    		public void handleMessage(Message message) {
	    			imageView.setImageBitmap((Bitmap) message.obj);
	    		}
	    	};

	    	Thread thread = new Thread() {
	    		@Override
	    		public void run() {
	    			//TODO : set imageView to a "pending" image
	    			Bitmap drawable = getRemoteImage(imageName);
	    			Message message = handler.obtainMessage(1, drawable);
	    			handler.sendMessage(message);
	    		}
	    	};
	    	thread.start();
	    }

}
