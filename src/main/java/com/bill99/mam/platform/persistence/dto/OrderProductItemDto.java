package com.bill99.mam.platform.persistence.dto;

import com.bill99.mam.platform.common.enumeration.OrderStatusEnum;
import com.bill99.mam.platform.common.enumeration.PayStatusEnum;
import lombok.Data;

import java.util.Date;
@Data
public class OrderProductItemDto {

    private Long id;

    private Long orderDetailNo;

    private Long productTag;

    private String productName;

    private Long productNum;

    private Long productPrice;

    private Integer ledgerRate;
    private Long merchantLedgerAmount;
    private Long platformLedgerAmount;

    private String productCategory;

    private String productDesc;

    private Date updateTime;

    private Date createTime;

    private Integer orderStatus;

    private Integer payStatus;

    public void setPayOrderSuccess(){
        setPayStatus(PayStatusEnum.SUCCESS.value());
        setOrderStatus(OrderStatusEnum.SUCCESS.value());
        setUpdateTime(new Date());
    }
    public void setPayOrderFailed() {
        setPayStatus(PayStatusEnum.FAILED.value());
        setOrderStatus(OrderStatusEnum.FAILED.value());
        setUpdateTime(new Date());
    }

}