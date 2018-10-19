package com.bill99.mam.platform.service.model.request;

import lombok.Data;

@Data
public class ProductItem {
	private Long sellerId;//商品卖家ID
    private String sellerName;//卖家名称
    private Long productTag;//商吕编码
    private String productName;//商品名称
    private Long productNum;//商品数目
    private Long productPrice;//商品单价
    private String productCategory;//商品类目
    private String productDesc;//商品描述
}