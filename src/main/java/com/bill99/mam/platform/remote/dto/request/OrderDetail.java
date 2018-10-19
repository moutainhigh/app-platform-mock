package com.bill99.mam.platform.remote.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;

@Getter
@Setter
@ToString(callSuper = true)
public class OrderDetail extends RemoteBaseRequest {
    private static final long serialVersionUID = -5194309877397861077L;
    private String subMerchantUId;//平台子商户id
    private String isPlatformSubMerchant;//子商户是否平台 0.否  1.是
    private String subOutTradeNo;//子订单编码
    private String subMerchantName;//平台子商户名称
    private String orderAmount;//子订单金额
//    private String settleAmount;//子订单结算金额
    private String orderType;//子订单类型
    private List<ProductItem> productList;//商品列表
}
