package com.bill99.mam.platform.service.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.service.model.BaseRequest;

@Getter
@Setter
@ToString(callSuper = true)
public class QuickPayment extends BaseRequest {
    private static final long serialVersionUID = 7823074424984800304L;
    private String functionCode;
    private String orderId;//
    private String mobile;//
    private String validCode;//
    private String token;
    private String isOpenQuick;//是否开通快捷支付 0-未开通 1-已开通
    private String isQuickPayment;//是否支持快捷支付 0:不支持1:支持 默认值：0不支持
    private String cardType;
    private String bankAcctId;//isOpenQuick=0时，快捷支付传全卡号
    private String shortBankAcctId;//isOpenQuick=1时，快捷支付传短卡号
    private String expiredDate;//cardType=0001且没有传uId时必填 (格式为MMYY)
    private String cvv2;//cardType=0001且没有传uId时必填
    private String name;//没有传uId时必填
    private String idCardNumber;//没有传uId时必填
    private String idCardType;//
    private String isSubsidiaryCard;//若是附属卡支付则必填 0:否1:是 默认值：0否
    private String txnSendIp;//交易发送方IP
    private int payMode; //支付方式
}
