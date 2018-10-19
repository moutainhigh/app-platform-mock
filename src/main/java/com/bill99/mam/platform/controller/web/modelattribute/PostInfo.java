package com.bill99.mam.platform.controller.web.modelattribute;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PostInfo implements Serializable {
	
	private static final long serialVersionUID = 4268369553843579129L;
	
	/** 请求的Url */
	private String url;
	
	/** 请求的json串 */
	private String requestParam;
	
	/** 请求key 控制权限*/
	private String postKey;

}
