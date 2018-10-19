package com.bill99.mam.platform.controller.hat.modelattribute;

import lombok.Data;

import java.io.Serializable;
@Data
public class TradeResultNotifyReq implements Serializable {
    private static final long serialVersionUID = 8142355938638582034L;
    private String platformCode;//平台在快钱的编号
    private String outTradeNo;//订单号
    private String billOrderNo;//快钱交易订单号
    private String tradeType;//交易类型
    private String orderAmount;//订单金额
    private String amount;//金额
    private String tradeNo;//快钱交易流水号
    private String txnEndTime;//订单完成时间
    private String status;//状态
    private String rspCode;//结果码
    private String rspMsg;//结果码描述
}
