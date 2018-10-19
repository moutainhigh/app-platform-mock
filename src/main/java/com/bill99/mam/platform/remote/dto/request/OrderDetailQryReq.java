package com.bill99.mam.platform.remote.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;

@Getter
@Setter
@ToString(callSuper = true)
public class OrderDetailQryReq extends RemoteBaseRequest {
    private static final long serialVersionUID = -3172370349740182733L;
    private String tradeNo;//快钱内部流水
    private String billOrderNo;//快钱内部订单号
    private String outTradeNo;//外部订单号
}
