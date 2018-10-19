package com.bill99.mam.platform.remote.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseResponse;

@Getter
@Setter
@ToString(callSuper = true)
public class QuickPaymentUnbindCardResp extends RemoteBaseResponse {
    private static final long serialVersionUID = 1097581391605167240L;
}
