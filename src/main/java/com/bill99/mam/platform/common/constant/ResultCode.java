package com.bill99.mam.platform.common.constant;

public class ResultCode {
    public static final String SUCCESS = "0000";
    
    public static boolean success(String respCode) {
    	return SUCCESS.equals(respCode);
    }
}
