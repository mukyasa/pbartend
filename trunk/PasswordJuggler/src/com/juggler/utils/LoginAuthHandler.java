package com.juggler.utils;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;

import com.juggler.view.LoginView;

public class LoginAuthHandler extends CountDownTimer {
	private static Context context; 
	private  boolean loginRequired=false;
	private boolean didLogin=false;
	private static LoginAuthHandler handler=null;
	private static long timeout=5000; // 5 minutes
	private String appPwd;
	private boolean loginScreenShowing=false;

	
	
	public boolean isLoginScreenShowing() {
    	return loginScreenShowing;
    }
	public void setLoginScreenShowing(boolean loginScreenShowing) {
    	this.loginScreenShowing = loginScreenShowing;
    }
	public LoginAuthHandler(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
		// TODO Auto-generated constructor stub
	}
	public boolean isDidLogin() {
		return didLogin;
	}

	public void setDidLogin(boolean didLogin) {
		this.didLogin = didLogin;
	}

	public static LoginAuthHandler getInstance(Context c) {

		if(handler == null)
		{
			handler = new LoginAuthHandler(timeout,1000);
			context = c;
		}
		return handler;
	}

	public static long getTimeout() {
		return timeout;
	}

	public static void setTimeout(long timeout) {
		LoginAuthHandler.timeout = timeout;
	}

	@Override
	public void onFinish() {
		Intent intent = new Intent(context,LoginView.class);
    	context.startActivity(intent);
		
	}
	
	public String getAppPwd() {
    	return appPwd;
    }
	public void setAppPwd(String appPwd) {
    	this.appPwd = appPwd;
    }
	
	@Override
	public void onTick(long millisUntilFinished) {
		// TODO Auto-generated method stub
		
	}
	public boolean isLoginRequired() {
    	return loginRequired;
    }
	public void setLoginRequired(boolean loginRequired) {
    	this.loginRequired = loginRequired;
    }
	
	
	
	
	
}
