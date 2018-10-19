package com.bill99.mam.platform.common.enumeration.message;

import com.bill99.mam.platform.common.IMessage;

/**
 * 外部服务提示信息 8001-8999
 * 
 * @author jerry.xu.coc
 */
public enum RemoteMsgEnum implements IMessage {
	CALL_RESPONSE_ERROR("8001", "获取返回对象发生错误"), CALL_IS_NULL("8002", "CALL对象为空"), 
	SIGN_SERVICE_INIT_ERROR("8003", "加验签服务初始化失败"),RESPONSE_EMPTY("8004", "http返回信息体为空"),
	RESPONSE_SOURCE_EMPTY("8005", "responseBody.source为空"),REQUEST_BODY_EMPTY("8006", "http请求信息体为空"),
	;
	private String code;
	private String message;

	private RemoteMsgEnum(String code, String message) {
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
