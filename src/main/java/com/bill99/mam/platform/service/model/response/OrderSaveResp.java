package com.bill99.mam.platform.service.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.service.model.BaseResponse;

@Getter
@Setter
@ToString(callSuper = true)
public class OrderSaveResp extends BaseResponse {
    private static final long serialVersionUID = -1804731160972650097L;
    private Long orderId;
}
