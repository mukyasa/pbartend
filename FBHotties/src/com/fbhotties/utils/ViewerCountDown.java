/**
 * Date: May 24, 2010
 * Project: FBHotties
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.fbhotties.utils;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Gallery;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class ViewerCountDown extends CountDownTimer {

	private static ViewerCountDown handler=null;
	private static long timeout=3000; // 3 seconds
	private static Gallery gallery;
	private static View view;
	
	/**
     * May 24, 2010
     * @param millisInFuture
     * @param countDownInterval
     * 
     */
    public ViewerCountDown(long millisInFuture, long countDownInterval) {
	    super(millisInFuture, countDownInterval);
	    // TODO Auto-generated constructor stub
    }
    
    public static ViewerCountDown getInstance(Gallery g,View v) {

    	gallery =g;
    	view=v;
		if(handler == null)
		{
			handler = new ViewerCountDown(timeout,1000);
		}
		return handler;
	}

	/* (non-Javadoc)
     * @see android.os.CountDownTimer#onFinish()
     */
    @Override
    public void onFinish() {
	    // TODO Auto-generated method stub
    	gallery.setVisibility(Gallery.INVISIBLE);
    	view.setPadding(0, 0, 0, 0);
    	
    }

	/* (non-Javadoc)
     * @see android.os.CountDownTimer#onTick(long)
     */
    @Override
    public void onTick(long millisUntilFinished) {
	    // TODO Auto-generated method stub
	    
    }
	
	
}
