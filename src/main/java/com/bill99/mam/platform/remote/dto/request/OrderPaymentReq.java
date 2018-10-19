package com.bill99.mam.platform.remote.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;
import com.bill99.mam.platform.service.model.request.SharingData;

@Getter
@Setter
@ToString(callSuper = true)
public class OrderPaymentReq extends RemoteBaseRequest {
    private static final long serialVersionUID = -3172370349740182733L;
    private String functionCode;
    private String merchantName;
    private String payeeUId;//平主收款方用户id
    private String isPlatformPayee;//主收款方是否平台  0.否  1.是
    private String outTradeNo;//外部订单号
    private String orderAmount;//订单金额
    private String payAmount;//支付金额
    private SharingData[] sharingData;//分账数据
    private String txnBeginTime;//订单生成时间
    private String isAssure;//是否担保交易
    private String feeMode;
    private String orderType;
    private String payMode;
    private String payType;
    private String memberBankAcctId;
    private String currencyCode;//币种 不填，默认为CNY
    private String authCode;
    private String uId;//平台用户id
    private String txnExpireTime;//订单失效时间
    private List<OrderDetail> orderDetails;//子订单明细
    private String bgUrl;//后台通知URL
    private String memo;//备注
    private String dataMap;//扩展字段
}
