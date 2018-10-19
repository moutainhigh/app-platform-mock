package com.bill99.mam.platform.remote.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseResponse;

@Getter
@Setter
@ToString(callSuper = true)
public class QuickPaymentIndAuthResp extends RemoteBaseResponse {
    private static final long serialVersionUID = -70634610312595983L;
    private String token;//鉴权确认token
    private String shortBankAcctId;//短卡号
}
