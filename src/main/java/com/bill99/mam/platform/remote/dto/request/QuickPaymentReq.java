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
public class QuickPaymentReq extends RemoteBaseRequest {
    private static final long serialVersionUID = 6616651206009933158L;
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
    private String uId;//平台用户id
    private String payMode;//支付方式
    private String mobile;//银行预留手机号
    private String validCode;//手机验证码
    private String token;//手机验证码令牌
    private String isOpenQuick;//是否开通快捷支付 0-未开通 1-已开通
    private String isQuickPayment;//是否开通快捷支付 0:不支持1:支持 默认值：0不支持
    private List<OrderDetail> orderDetails;//子订单明细
    private String cardType;//卡类型
    private String bankAcctId;//银行卡号
    private String shortBankAcctId;//短卡号
    private String expiredDate;//信用卡有效期
    private String cvv2;//信用卡安全码
    private String name;//
    private String idCardNumber;//持卡人证件号码
    private String idCardType;//证件类型
    private String isSubsidiaryCard;//是否附属卡
    private String txnSendIp;//
    private String currencyCode;//币种 不填，默认为CNY
    private String txnExpireTime;//订单失效时间
    private String bgUrl;//后台通知URL
    private String memo;//备注
    private String dataMap;//扩展字段
}
