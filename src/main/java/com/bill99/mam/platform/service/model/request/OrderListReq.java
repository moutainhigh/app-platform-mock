package com.bill99.mam.platform.service.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.service.model.BaseRequest;
@Getter
@Setter
@ToString(callSuper = true)
public class OrderListReq extends BaseRequest{
	
	private static final long serialVersionUID = -3399880457634614674L;
	private Long uid;
	private Integer startRow;
	private Integer pageSize;
}
