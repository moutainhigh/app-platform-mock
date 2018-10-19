package com.bill99.mam.platform.service.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.service.model.BaseRequest;
@Getter
@Setter
@ToString(callSuper = true)
public class OrderPayReq extends BaseRequest {
    private static final long serialVersionUID = 2052681111489843253L;
    private Integer payMode;
    private String memberBankAcctId;
    private Long orderId;
    private Long payeeUId;//主收款方id
}
