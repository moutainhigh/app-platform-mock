package com.bill99.mam.platform.service.model.response;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import com.bill99.mam.platform.service.model.BaseResponse;

@Getter
@Setter
public class AggregatePreOrderResp extends BaseResponse {
    private static final long serialVersionUID = 2848559023649382242L;
    private String payUrl;
    private String status;

    @SuppressWarnings("rawtypes")
	private Map mpayInfo;//支付信息
    private String idBiz;//交易编号
    private String merchantId;//商户编号

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("{");
        sb.append("payUrl='").append(payUrl).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", mpayInfo='").append(mpayInfo).append('\'');
        sb.append(", idBiz='").append(idBiz).append('\'');
        sb.append(", merchantId='").append(merchantId).append('\'');
        sb.append(", rspCode='").append(this.getRspCode()).append('\'');
        sb.append(", rspMsg='").append(this.getRspMsg()).append('\'');
        sb.append(", traceId='").append(this.getTraceId()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
