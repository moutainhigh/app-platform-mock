package com.bill99.mam.platform.remote.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;

@Getter
@Setter
@ToString(callSuper = true)
public class DepositReq extends RemoteBaseRequest {
    private static final long serialVersionUID = 7466351709698627214L;
    private String functionCode;//交易功能
    private String merchantName;//平台商户名称
    private String outTradeNo;//外部订单号
    private String uId;//平台用户id
    private String orderAmount;//订单总金额
    private String payAmount;//支付金额
    private String payMode;//支付方式
    private String memberBankAcctId;//银行卡绑卡ID
    private String orderType;//订单类型
    private String bgUrl;//通知地址
    private String txnExpireTime;//订单失效时间
    private String currencyCode;//币种
    private String memo;//备注
}
