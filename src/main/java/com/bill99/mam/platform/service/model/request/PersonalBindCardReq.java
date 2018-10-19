package com.bill99.mam.platform.service.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.service.model.BaseRequest;
@Getter
@Setter
@ToString(callSuper = true)
public class PersonalBindCardReq extends BaseRequest {
	private static final long serialVersionUID = 1L;
    private String uId;
    private String name;
    private String phone;
    private String idType;
    private String identificationCard;
    private String bankAcctId;
    private String validCode;
    /** 信用卡安全码*/
    private String cvv2;
    /** 信用卡有效期*/
    private String expiredDate;
    private String token;
}
