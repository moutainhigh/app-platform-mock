package com.bill99.mam.platform.remote.dto.request;

import lombok.Data;

import java.io.Serializable;
@Data
public class ProductItem implements Serializable {
    private static final long serialVersionUID = -9086850325357768181L;
    private String productTag;//商品编码
    private String productName;//商品名称
    private String productNum;//商品数量
    private String productDesc;//商品描述
    private String productCategory;//商品类目
    private String productprice;//商品单价
}
