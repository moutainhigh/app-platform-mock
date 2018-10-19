package com.bill99.mam.platform.service.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.service.model.BaseRequest;

@Getter
@Setter
@ToString(callSuper = true)
public class PciQryReq extends BaseRequest {
    private static final long serialVersionUID = -704473395368424214L;
    private String uId;
    private String bankId;//银行代码
    private String shortBankAcctId;//短卡号
    private String bindType;//绑定类型 0：商户绑定 1：成员绑定 默认为0

    private String payMode; //支付方式 查询时必传,12：快捷支付 15：一键支付

}
