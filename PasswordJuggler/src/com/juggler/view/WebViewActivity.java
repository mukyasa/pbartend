/**
 * Date: Jan 11, 2010
 * Project: PasswordJuggler
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.juggler.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class WebViewActivity extends Activity {
	
	private WebView mWebView;
	private Handler mHandler = new Handler();

	    
	@Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        setContentView(R.layout.web_frame);
       // TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
		//tvTitle.setText("Web");
        
        mWebView = (WebView) findViewById(R.id.webview);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);

        mWebView.setWebChromeClient(new MyWebChromeClient());

        mWebView.addJavascriptInterface(new DemoJavaScriptInterface(), "demo");

        mWebView.loadUrl("http://www.google.com");
    }
	
	 final class DemoJavaScriptInterface {

	        DemoJavaScriptInterface() {
	        }

	        /**
	         * This is not called on the UI thread. Post a runnable to invoke
	         * loadUrl on the UI thread.
	         */
	        public void clickOnAndroid() {
	            mHandler.post(new Runnable() {
	                public void run() {
	                    mWebView.loadUrl("javascript:wave()");
	                }
	            });

	        }
	    }

	    /**
	     * Provides a hook for calling "alert" from javascript. Useful for
	     * debugging your javascript.
	     */
	    final class MyWebChromeClient extends WebChromeClient {
	        @Override
	        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
	            result.confirm();
	            return true;
	        }
	    }
	
}
