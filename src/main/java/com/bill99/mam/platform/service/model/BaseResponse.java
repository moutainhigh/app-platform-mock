package com.bill99.mam.platform.service.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.common.constant.ResultCode;
@Getter
@Setter
@ToString
public class BaseResponse implements Serializable{
	private static final long serialVersionUID = 1658760365320337155L;
	private String rspCode;
	private String rspMsg;
	private String traceId;
	public BaseResponse() {
		this(ResultCode.SUCCESS, null);
	}
	public BaseResponse(String rspCode, String rspMsg) {
		super();
		this.rspCode = rspCode;
		this.rspMsg = rspMsg;
	}
}
