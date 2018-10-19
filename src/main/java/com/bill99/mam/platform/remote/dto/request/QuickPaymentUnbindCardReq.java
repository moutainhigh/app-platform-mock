package com.bill99.mam.platform.remote.dto.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;
@Getter
@Setter
@ToString(callSuper = true)
public class QuickPaymentUnbindCardReq extends RemoteBaseRequest implements Serializable{
    private static final long serialVersionUID = 8539369088840570128L;
    /* 平台用户id */
    private String uId;
    /* 银行代码 */
    private String bankId;
    /* 银行短卡号*/
    private String shortBankAcctId;
    /* 支付方式 */
    private String payMode;
    /* 绑定类型 */
    private String bindType;
}
