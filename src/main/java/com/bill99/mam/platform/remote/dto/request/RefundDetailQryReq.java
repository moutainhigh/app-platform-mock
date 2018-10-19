package com.bill99.mam.platform.remote.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;

@Getter
@Setter
@ToString(callSuper = true)
public class RefundDetailQryReq extends RemoteBaseRequest {
    private static final long serialVersionUID = -3594983323436958834L;
    private String  tradeNo;
    private String outTradeNo;
}
