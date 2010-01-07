/**
 * Date: Jan 7, 2010
 * Project: FlashCard
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.flashcard.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class InstructionsActivity extends Activity {
	
	/**
	 * This will be transparent and there is a theme involved see values/style
	 * then in the manifest you need to apply this theme to this class
	 */
	  @Override
		protected void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  Window window = getWindow();
		  window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
			
		  setContentView(R.layout.instructions); 
		  
	    }
}
