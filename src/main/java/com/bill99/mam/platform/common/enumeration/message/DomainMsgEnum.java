package com.bill99.mam.platform.common.enumeration.message;

import com.bill99.mam.platform.common.IMessage;
/**
 * 业务处理服务 3000-6999
 * @author jerry.xu.coc
 *
 */
public enum DomainMsgEnum implements IMessage{
	CUSTOMER_NOT_EXIST("3001", "客户不存在"),LOGIN_PWD_ILLEGAL("3002", "登录密码不正确"),
	CUSTOMER_LOGIN_ID_EXIST("3003", "登录ID已存在"),
	;

	private String code;
	private String message;

	private DomainMsgEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}
	@Override
	public String message() {
		return message;
	}

	@Override
	public String code() {
		return code;
	}

}
