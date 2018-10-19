package com.bill99.mam.platform.persistence.dto;

import com.bill99.mam.platform.common.enumeration.OrderStatusEnum;
import com.bill99.mam.platform.common.enumeration.PayStatusEnum;

import lombok.Data;

import org.apache.commons.collections.CollectionUtils;

import java.util.Date;
import java.util.List;
@Data
public class OrderDto {
    private Long id;

    private Long uid;

    private Long payeeUid;

    private Long orderAmount;

    private Long payAmount;

    private Long refundAmount;

    private Long actualAmount;

    private Long tradeFee;

    private Integer isPlatformPayFee;

    private String billOrderNo;

    private String tradeNo;

    private Integer functionCode;

    private String sharingData;

    private Integer feeMode;

    private Integer payMode;

    private Integer payStatus;

    private Integer orderStatus;

    private String settlePeriod;

    private Date txnBeginTime;

    private Date txnEndTime;

    private Date txnExpireTime;

    private String memberBankAcctId;

    private String currencyCode;

    private String authCode;

    private String orderType;

    private String payType;

    private Integer isAssured;

    private String bgUrl;

    private Date stlDate;

    private String payUrl;

    private Integer secureType;

    private String respCode;

    private String respMsg;

    private String memo;

    private String dataMap;

    private Date updateTime;

    private Date createTime;


    private List<OrderDetailDto> orderDetails;

    /**
     * 支付成功更新状态
     */
    public void setPayOrderSuccess(){
        setPayStatus(PayStatusEnum.SUCCESS.value());
        setOrderStatus(OrderStatusEnum.SUCCESS.value());
        setTxnEndTime(new Date());
        setUpdateTime(new Date());
        if (CollectionUtils.isNotEmpty(orderDetails)){
            for (OrderDetailDto orderDetailDto: orderDetails) {
                orderDetailDto.setPayOrderSuccess();
            }
        }
    }
    /**
     * 支付失败更新状态
     */
    public void setPayOrderFailed(){
        setPayStatus(PayStatusEnum.FAILED.value());
        setOrderStatus(OrderStatusEnum.FAILED.value());
        setTxnEndTime(new Date());
        setUpdateTime(new Date());
        if (CollectionUtils.isNotEmpty(orderDetails)){
            for (OrderDetailDto orderDetailDto: orderDetails) {
                orderDetailDto.setPayOrderFailed();
            }
        }
    }

    public Long countOrderAmount(){
        long orderAmount = 0;
        if (CollectionUtils.isNotEmpty(orderDetails)){
            for (OrderDetailDto orderDetailDto: orderDetails) {
                orderAmount += orderDetailDto.countOrderAmount();
            }
        }
        setOrderAmount(orderAmount);
        return orderAmount;
    }
}