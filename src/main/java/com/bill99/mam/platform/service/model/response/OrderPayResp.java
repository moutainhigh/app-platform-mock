package com.bill99.mam.platform.service.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.service.model.BaseResponse;

@Getter
@Setter
@ToString(callSuper = true)
public class OrderPayResp extends BaseResponse {
    private static final long serialVersionUID = -7637012013871301484L;
    private String orderId;
    private String payStatus;
    private String imageBase64;
}
