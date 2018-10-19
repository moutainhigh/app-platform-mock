package com.bill99.mam.platform.service.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.service.model.BaseRequest;

@Getter
@Setter
@ToString(callSuper = true)
public class IndAuthVerifyReq extends BaseRequest {
    private static final long serialVersionUID = 8654275896709312845L;
    /* 订单号 */
    private String orderId;
    /* 短卡号 */
    private String shortBankAcctId;
    /* 鉴权确认token */
    private String token;
    /* 验证码 */
    private String validCode;
    /* 卡类型 */
    private String cardType;
    /* 银行卡号 */
    private String bankAcctId;
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
    /* 绑定类型 0：商户绑定1：成员绑定 默认为0*/
    private String bindType;
}
