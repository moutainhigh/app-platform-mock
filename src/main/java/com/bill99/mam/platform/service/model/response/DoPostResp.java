package com.bill99.mam.platform.service.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.service.model.BaseResponse;

@Setter
@Getter
@ToString(callSuper = true)
public class DoPostResp extends BaseResponse {
	
	private static final long serialVersionUID = 6155398518180916509L;
	
	private String data;

}
