package com.bill99.mam.platform.remote.dto.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;
@Getter
@Setter
@ToString(callSuper = true,exclude={"idCardNumber","mobile","email"})
public class HatPersonalRegisterReq extends RemoteBaseRequest implements Serializable {
	private static final long serialVersionUID = -7759438116258405051L;
	/** 平台用户id */
    private String uId;
    /** 证件类型 */
    private String idCardType;
    /** 证件号码 */
    private String idCardNumber;
    /** 姓名 必须与证件匹配 */
    private String name;
    /** 手机号 */
    private String mobile;
    /** 邮箱 */
    private String email;
}
