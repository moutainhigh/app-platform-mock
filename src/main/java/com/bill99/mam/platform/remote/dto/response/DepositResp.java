package com.bill99.mam.platform.remote.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseResponse;
@Getter
@Setter
@ToString(callSuper = true)
public class DepositResp extends RemoteBaseResponse {
    private static final long serialVersionUID = 6644403191599959653L;
    private String outTradeNo;//
    private String billOrderNo;//快钱内部订单号
    private String tradeNo;//快钱内部交易流水号
    private String uId;//平台用户id
    private String payAmount;//支付金额
    private String orderAmount;//订单金额
    private String currencyCode;//币别
    private String txnEndTime;//订单完成时间
    private String status;//交易状态
}
