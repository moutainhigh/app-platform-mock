package com.bill99.mam.platform.common.enumeration.message;

import com.bill99.mam.platform.common.IMessage;
/**
 * Controller处理服务 7000-7999
 * @author jerry.xu.coc
 */
public enum ControllerMsgEnum implements IMessage{
	;

	private String code;
	private String message;

	private ControllerMsgEnum(String code, String message) {
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
