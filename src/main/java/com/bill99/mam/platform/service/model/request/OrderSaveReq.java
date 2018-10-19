package com.bill99.mam.platform.service.model.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.service.model.BaseRequest;

@Getter
@Setter
@ToString(callSuper = true)
public class OrderSaveReq  extends BaseRequest{
    private static final long serialVersionUID = 5791546426541774568L;
    private Long buyerId;//买家客户号
    List<ProductItem> orderItems;
}
