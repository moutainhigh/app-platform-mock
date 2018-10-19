package com.bill99.mam.platform.remote.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;

@Getter
@Setter
@ToString(callSuper = true)
public class QuickPaymentPciQryReq extends RemoteBaseRequest {
    private static final long serialVersionUID = -704473395368424214L;
    private String uId;
    private String bankId;//银行代码
    private String shortBankAcctId;//短卡号
    private String bindType;//绑定类型 0：商户绑定 1：成员绑定 默认为0

    private String payMode; //支付方式
}
