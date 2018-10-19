package com.bill99.mam.platform.service.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.service.model.BaseResponse;

@Getter
@Setter
@ToString(callSuper = true)
public class IndAuthResp extends BaseResponse {
    private static final long serialVersionUID = -7085979801562702459L;
    private String token;//支付token 成功必返
    private String shortBankAcctId;//短卡号
}
