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
public class AggregateReq extends RemoteBaseRequest {
    private static final long serialVersionUID = -3172370349740182733L;
    private String functionCode;//交易功能 10：消费；14：充值
    private String uId;//平台用户id
    private String payeeUId;//平主收款方用户id
    private String isPlatformPayee;//主收款方是否平台 0.	否  1.是 functionCode=10时，必填
    private String merchantName;//平台商户名称
    private String settlePeriod;//结算周期  格式：T+n
    private String outTradeNo;//外部订单号
    private String orderAmount;//订单金额
    private String payAmount;//支付金额
    private String orderType;//订单类型
    private SharingData[] sharingData;//分账数据
    private String currencyCode;//币种 不填，默认为CNY
    private String stlDate;//结算日期
    private String isAssure;//是否担保交易
    private String feeMode;//手续费模式  0:主收款方承担手续费；1:子商户按订单金额分摊手续费
    private List<OrderDetail> orderDetails;//子订单明细
    private String txnBeginTime;//订单生成时间
    private String txnExpireTime;//订单失效时间
    private String bgUrl;//后台通知URL
    private String memo;//备注
    private String dataMap;//扩展字段

    private String payMode;//支付方式
    private String payType;//支付类型

    private String subAppId; //商户appId payType=WECHATAPP 时必填
}
