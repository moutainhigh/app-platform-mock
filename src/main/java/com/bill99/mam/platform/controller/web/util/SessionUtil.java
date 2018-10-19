package com.bill99.mam.platform.controller.web.util;

import javax.servlet.http.HttpSession;

import com.bill99.mam.platform.controller.web.modelattribute.LoginInfo;

public class SessionUtil {
	
	public static String LOGIN_INFO = "loginInfo";

	public static LoginInfo getLoginInfoFromSession(HttpSession session) {
    	LoginInfo loginInfo = (LoginInfo)session.getAttribute(LOGIN_INFO);
    	return loginInfo;
    }
	
	public static void setLoginInfoToSession(HttpSession session, LoginInfo loginInfo) {
    	session.setAttribute(LOGIN_INFO, loginInfo);
    }
}
