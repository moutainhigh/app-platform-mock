package com.bill99.mam.platform.common.enumeration;

/**
 * 支付方式（10余额支付;11绑卡支付;12 聚合支付;13 快捷支付）
 */
public enum PayModeEnum {

    //余额支付
    BALANCE_PAY(10),
    //绑卡支付
    BANK_ACCT_PAY(11),
    //快捷支付
    QUICK_PAY(12),
    //聚合支付-飞快付
    AGGREGATE_PAY(13),
    //聚合支付-SDK
    AGGREGATE_PAY_SDK(14),
    //一键支付
    ONE_KEY(15)
    ;
    private Integer status;

    private PayModeEnum(int status){
        this.status = status;
    }
    public int value(){
        return this.status;
    }
}
