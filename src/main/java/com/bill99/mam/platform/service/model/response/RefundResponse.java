package com.bill99.mam.platform.service.model.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.response.RefundResp;
import com.bill99.mam.platform.service.model.BaseResponse;
@Getter
@Setter
@ToString(callSuper = true)
public class RefundResponse extends BaseResponse {
	private static final long serialVersionUID = -1368963929272717712L;
	private Long orderNo;  //order表id
    private List<Long> refundItemList; //item list
    private RefundResp hatResponse;
}
