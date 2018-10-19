package com.bill99.mam.platform.service.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.service.model.BaseRequest;
@Getter
@Setter
@ToString(callSuper = true)
public class EnterpriseUnBindCardReq extends BaseRequest {
    private static final long serialVersionUID = 1L;
    private String uId;
    private String memberBankAcctId;
}
