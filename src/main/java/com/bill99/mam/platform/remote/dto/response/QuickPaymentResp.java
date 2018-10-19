package com.bill99.mam.platform.remote.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseResponse;

@Getter
@Setter
@ToString(callSuper = true)
public class QuickPaymentResp extends RemoteBaseResponse {
    private static final long serialVersionUID = 5817284328568999898L;
    private String payeeUId;//主收款方用户id
    private String isPlatformPayfee;
    private String billOrderNo;//快钱内部订单号
    private String outTradeNo;//外部订单号
    private String tradeNo;//快钱内部流水号
    private String uId;//平台用户id
    private String orderAmount;//订单金额
    private String payAmount;//支付金额
    private String currencyCode;//币种
    private String txnEndTime;//订单完成时间
    private String payMode;//支付方式
    private String payType;//支付类型
    private String dataMap;//扩展字段
    private String status;//交易状态
}
