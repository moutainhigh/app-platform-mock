package com.bill99.mam.platform.common.enumeration;

import com.bill99.mam.platform.common.IMessage;

public enum ResultEnum implements IMessage{

	SUCCESS("0000", "成功"),
	FAILED("9999", "失败"),
	AMOUNT_FORMAT_ERROR("1001", "金额格式错误"),
	FAILURE("8001", "服务器异常");
	
	private String code;
	
	private String msg;

	private ResultEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public String code() {
		return code;
	}

	public String message() {
		return msg;
	}
	
}
