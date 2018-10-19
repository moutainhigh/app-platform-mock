package com.bill99.mam.platform.service.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.service.model.BaseResponse;
@Getter
@Setter
@ToString(callSuper = true)
public class PersonalBindCardResp extends BaseResponse {
    private static final long serialVersionUID = 1L;
    private String token;
    private String memberBankAcctId;
}
