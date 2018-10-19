package com.bill99.mam.platform.common.enumeration;

public enum OrderStatusEnum {
    //待付款
    WAIT_PAYMENT(0, "待付款"),
    //成功
    SUCCESS(1, "成功"),
    //失败
    FAILED(2, "失败"),
    //受理中
    ACCEPTANCE(3, "受理中"),
	//受理成功
	ACCEPTED(4, "受理成功"),
	//渠道受理成功
    CHANNEL_ACCEPTED(5, "渠道受理成功"),
    //取消订单
    CALCEL_ORDER(6, "取消订单"),
    //退货--detail 表下有退货使用
    REFUND(8, "退货"),
    //退货受理成功
    REFUND_PROCESSIONG(9, "退货受理成功"),
    //退货受理成功
    REFUND_SUCCESS(10, "退货成功");

    private Integer status;
    private String desc;

    private OrderStatusEnum(int status, String desc){
        this.status = status;
        this.desc = desc;
    }
    public int value(){
        return this.status;
    }
    public String desc(){
    	return this.desc;
    }
    
    public static OrderStatusEnum get(int status) {
    	OrderStatusEnum result = null;
    	for (OrderStatusEnum e : OrderStatusEnum.values()) {
    		if (e.value() == status) {
    			result = e;
    			break;
    		}
    	}
    	return result;
    }
}
