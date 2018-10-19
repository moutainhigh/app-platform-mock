package com.bill99.mam.platform.remote.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseResponse;

@Getter
@Setter
@ToString(callSuper = true)
public class RefundResp extends RemoteBaseResponse {
    private static final long serialVersionUID = 5906030598394346300L;
    private String outTradeNo;//退货订单号
    private String origOutTradeNo;//原交易订单号
    private String billOrderNo;//快钱交易订单号
    private String tradeNo;//快钱内部交易流水号
    private String refundAmount;//退货金额
    private String status;//交易状态
}
