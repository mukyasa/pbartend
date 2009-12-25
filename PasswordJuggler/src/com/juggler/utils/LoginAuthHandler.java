package com.juggler.utils;

public class LoginAuthHandler {

	private static LoginAuthHandler handler=null;
	private boolean didLogin=false;

	public boolean isDidLogin() {
		return didLogin;
	}

	public void setDidLogin(boolean didLogin) {
		this.didLogin = didLogin;
	}

	public static LoginAuthHandler getInstance() {

		if(handler == null)
			handler = new LoginAuthHandler();
		
		return handler;
	}
	
	
	
}
