package com.bill99.mam.platform.common.enumeration;

/**
 * 10：支付；11：撤销；12：退货；14：支付账户充值；15：支付账户提现；20：下单；22：订单详情；29：冲正；31：支付结果轮询
 */
public enum FunctionCodeEnum {
    //消费
    ACCT_PAY(10),
    //撤销
    CANCEL(11),
    //退货
    REFUND(12),
    //支付账户充值
    DEPOSIT(14),
    //提现
    WITHDRAW(15),
    //下单
    AGGREGATE(20),
    ;
    private Integer status;

    private FunctionCodeEnum(Integer status){
     this.status = status;
    }


    public Integer value(){
        return this.status;
    }
}
