package com.bill99.mam.platform.remote.dto.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;
@Getter
@Setter
@ToString(callSuper = true)
public class QuickPaymentIndAuthReq extends RemoteBaseRequest implements Serializable{
    private static final long serialVersionUID = 2991366486864587389L;
    /* 平台用户id */
    private String uId;
    /* 外部订单号 */
    private String outTradeNo;
    /* 卡类型 */
    private String cardType;
    /* 银行卡号  首次快捷支付传全卡号获取动态码*/
    private String bankAcctId;
    /* 信用卡有效期 格式为MMYY */
    private String expiredDate;
    /* 信用卡安全码 */
    private String cvv2;
    /* 持卡人姓名*/
    private String name;
    /* 银行预留手机号 */
    private String mobile;
    /* 持卡人证件号码*/
    private String idCardNumber;
    /* 证件类型 默认：101 */
    private String idCardType;
    /* 绑定类型 0：商户绑定1：成员绑定 默认为0*/
    private String bindType;
}
