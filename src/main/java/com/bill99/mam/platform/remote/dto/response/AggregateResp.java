package com.bill99.mam.platform.remote.dto.response;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseResponse;
@Getter
@Setter
@ToString(callSuper = true)
public class AggregateResp extends RemoteBaseResponse {
    private static final long serialVersionUID = 337126472378313575L;
    private String billOrderNo;//快钱内部订单号
    private String payUrl;//扫码URL
    private String status;//交易状态

    @SuppressWarnings("rawtypes")
	private Map mpayInfo;//支付信息
    private String idBiz;//交易编号
    private String merchantId;//商户编号
}
