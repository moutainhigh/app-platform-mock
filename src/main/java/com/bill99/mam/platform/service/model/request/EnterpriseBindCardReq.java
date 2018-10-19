package com.bill99.mam.platform.service.model.request;

import lombok.Data;

@Data
public class EnterpriseBindCardReq {
	/** 平台内用户标识*/
	private String uId;
    /** 户名*/
    private String name;
    /** 银行编号*/
    private String bankId;
    /** 分支行名称*/
    private String branchBankName;
    /** 银行账号*/
    private String bankAcctId;
    /** 开户行省份*/
    private String province;
    /** 开户行城市*/
    private String city;
}
