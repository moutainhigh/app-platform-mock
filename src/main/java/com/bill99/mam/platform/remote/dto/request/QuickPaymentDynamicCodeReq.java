package com.bill99.mam.platform.remote.dto.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;
@Getter
@Setter
@ToString(callSuper = true)
public class QuickPaymentDynamicCodeReq extends RemoteBaseRequest implements Serializable{
    private static final long serialVersionUID = 6376733267166412597L;
    /* 平台用户id */
    private String uId;
    /* 外部订单号 */
    private String outTradeNo;
    /* 支付金额 */
    private String payAmount;
    /* 银行编号 */
    private String bankId;
    /* 卡类型 */
    private String cardType;
    /* 银行卡号  首次快捷支付传全卡号获取动态码*/
    private String bankAcctId;
    /* 短卡号 */
    private String shortBankAcctId;
    /* 信用卡有效期 格式为MMYY */
    private String expiredDate;
    /* 信用卡安全码 */
    private String cvv2;
    /* 持卡人姓名 首次快捷必填 */
    private String name;
    /* 银行预留手机号 */
    private String mobile;
    /* 持卡人证件号码 首次快捷必填 */
    private String idCardNumber;
    /* 证件类型 默认：101 */
    private String idCardType;
    /* 支付方式 12：快捷支付获取 15：一键支付(白名单)必须输入验证码*/
    private String payMode;
}
