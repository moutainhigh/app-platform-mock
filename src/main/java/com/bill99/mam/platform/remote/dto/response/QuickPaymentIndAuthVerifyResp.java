package com.bill99.mam.platform.remote.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseResponse;
@Getter
@Setter
@ToString(callSuper = true)
public class QuickPaymentIndAuthVerifyResp extends RemoteBaseResponse {
    private static final long serialVersionUID = 4951015994263925428L;
}
