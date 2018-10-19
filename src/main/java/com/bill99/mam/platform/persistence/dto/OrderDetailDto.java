package com.bill99.mam.platform.persistence.dto;

import com.bill99.mam.platform.common.enumeration.OrderStatusEnum;
import com.bill99.mam.platform.common.enumeration.PayStatusEnum;

import lombok.Data;

import org.apache.commons.collections.CollectionUtils;

import java.util.Date;
import java.util.List;
@Data
public class OrderDetailDto {

    private Long id;

    private Long orderNo;

    private Long subMerchantUid;

    private String subMerchantName;

    private Integer merchantIsPlatform;

    private Long orderAmount;

    private Long merchantLedgerAmount;

    private Long platformLedgerAmount;

    private Long refundAmount;

    private Long setttleAmount;

    private Long orderType;

    private Date updateTime;

    private Date createTime;

    private Integer orderStatus;

    private Integer payStatus;


    private List<OrderProductItemDto> productList;
    //支付成功
    public void setPayOrderSuccess(){
        setPayStatus(PayStatusEnum.SUCCESS.value());
        setOrderStatus(OrderStatusEnum.SUCCESS.value());
        setUpdateTime(new Date());
        if (CollectionUtils.isNotEmpty(productList)){
            for (OrderProductItemDto productItemDto: productList) {
                productItemDto.setPayOrderSuccess();
            }
        }
    }
   // 支付失败
    public void setPayOrderFailed() {
        setPayStatus(PayStatusEnum.FAILED.value());
        setOrderStatus(OrderStatusEnum.FAILED.value());
        setUpdateTime(new Date());
        if (CollectionUtils.isNotEmpty(productList)) {
            for (OrderProductItemDto productItemDto : productList) {
                productItemDto.setPayOrderFailed();
            }
        }
    }

    public Long countOrderAmount(){
        long orderAmount = 0;
        if (CollectionUtils.isNotEmpty(productList)){
            for (OrderProductItemDto itemDto: productList) {
                orderAmount += itemDto.getProductNum()*itemDto.getProductPrice();
            }
        }
        setOrderAmount(orderAmount);
        return orderAmount;
    }
    /**
    * @Description: 计算分账金额
    * @param 
    * @return void
    * @throws
     */
    public void countLedgerAmount(){
        long merchantLedgerAmount = 0,platformLedgerAmount = 0;
        if (CollectionUtils.isNotEmpty(productList)){
            for (OrderProductItemDto itemDto: productList) {
            	merchantLedgerAmount += itemDto.getMerchantLedgerAmount();
            	platformLedgerAmount += itemDto.getPlatformLedgerAmount();
            }
        }
        setMerchantLedgerAmount(merchantLedgerAmount);
        setPlatformLedgerAmount(platformLedgerAmount);
    }

}