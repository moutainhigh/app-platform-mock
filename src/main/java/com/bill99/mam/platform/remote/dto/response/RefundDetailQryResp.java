package com.bill99.mam.platform.remote.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseResponse;

@Getter
@Setter
@ToString(callSuper = true)
public class RefundDetailQryResp extends RemoteBaseResponse {
    private static final long serialVersionUID = 2256653152762306820L;
    private String tradeNo;//快钱内部流水号
    private String billOrderNo;//快钱内部订单号
    private String outRefundNo;//退货号
    private String origTradeNo;//原交易流水号
    private String payMode;//支付方式
    private String payType;//支付类型
    private String payAmount;//支付金额
    private String uId;//平台用户id
    private String memberBankAcctId;//银行卡绑卡ID
    private String bankAcctId;//银行账户ID
    private String bankName;//银行名
    private String status;//交易状态
    private String txnBeginTime;//订单生成时间
    private String txnEndTime;//订单完成时间
}
