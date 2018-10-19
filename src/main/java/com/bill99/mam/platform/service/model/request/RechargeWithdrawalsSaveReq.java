package com.bill99.mam.platform.service.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.service.model.BaseRequest;

@Getter
@Setter
@ToString(callSuper = true)
public class RechargeWithdrawalsSaveReq  extends BaseRequest {
	private static final long serialVersionUID = -7728197310373441493L;
    private String bankAcctId;
    /** 14：充值；15：提现 */
    private String functionCode;
    private Long payAmount;
	private Long buyerId;//买家客户号
}
