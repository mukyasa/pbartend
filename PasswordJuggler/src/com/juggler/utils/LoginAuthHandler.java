package com.juggler.utils;

import android.content.Intent;
import android.os.CountDownTimer;

import com.juggler.view.LoginView;

public class LoginAuthHandler extends CountDownTimer {

	public LoginAuthHandler(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
		// TODO Auto-generated constructor stub
	}

	private static LoginAuthHandler handler=null;
	private boolean didLogin=false;
	
	private static long timeout=300000; // 5 minutes

	public boolean isDidLogin() {
		return didLogin;
	}

	public void setDidLogin(boolean didLogin) {
		this.didLogin = didLogin;
	}

	public static LoginAuthHandler getInstance() {

		if(handler == null)
		{
			handler = new LoginAuthHandler(timeout,1000);
			handler.start();
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
		//Intent intent = new Intent(Intent.ACTION_VIEW,LoginView.class);
    	//startActivity(intent);
		
	}

	@Override
	public void onTick(long millisUntilFinished) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
}
