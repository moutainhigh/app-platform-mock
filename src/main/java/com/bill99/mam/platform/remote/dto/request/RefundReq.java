package com.bill99.mam.platform.remote.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;
import com.bill99.mam.platform.service.model.request.SharingData;

@Getter
@Setter
@ToString(callSuper = true)
public class RefundReq extends RemoteBaseRequest {
    private static final long serialVersionUID = 916996990793262911L;
    private String functionCode = "12";
    private String outRefundNo;//退货订单号
    private String origOutTradeNo;//原交易订单号
    private String billOrderNo;//快钱订单号
    private String subOutTradeNo;//原交易子订单号
    private String subMerchantUId;//平台子商户号
    private String isPlatformSubMerchant;//0.否  1.是
    private String refundAmount;//退款金额
    private SharingData[] sharingData;//分账数据
    private String memo;//备注
    private String txnBeginTime;//退货申请时间
    private String dataMap;//扩展字段
}
