package com.bill99.mam.platform.remote.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;
@Getter
@Setter
@ToString(callSuper = true)
public class OrderDetailQryResp extends RemoteBaseRequest {
    private static final long serialVersionUID = -3172370349740182733L;
    private String payeeUId;//平主收款方用户id
    private String outTradeNo;//外部订单号
    private String billOrderNo;//快钱内部订单号
    private String orderAmount;//订单金额
    private String payAmount;//支付金额
    private String refundAmount;//支付金额
    private String payMode;
    private String currencyCode;//币种 不填，默认为CNY
    private String txnBeginTime;//订单生成时间
    private String txnExpireTime;//订单失效时间
    private String txnEndTime;//订单完成时间
    private String memo;//备注
    private String tradeNo;//快钱内部流水号
    private String status;//交易状态
}
