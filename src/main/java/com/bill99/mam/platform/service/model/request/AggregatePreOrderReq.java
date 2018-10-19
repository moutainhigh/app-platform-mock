package com.bill99.mam.platform.service.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.service.model.BaseRequest;

@Getter
@Setter
@ToString(callSuper = true)
public class AggregatePreOrderReq extends BaseRequest {
    private static final long serialVersionUID = -414697588143098713L;
    private Long orderId;
    private Integer functionCode;//交易功能  10：消费；14：充值
    private Long payeeUId;//主收款方id

    private String payMode;//支付方式
    private String payType;//支付类型

    private String subAppId;

}
