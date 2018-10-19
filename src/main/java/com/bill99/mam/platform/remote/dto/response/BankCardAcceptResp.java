package com.bill99.mam.platform.remote.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseResponse;
@Getter
@Setter
@ToString(callSuper = true)
public class BankCardAcceptResp extends RemoteBaseResponse {
    private static final long serialVersionUID = 7452287047160227709L;
    private String cardType;
    private String bankId;
    private String bankName;
}
