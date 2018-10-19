package com.bill99.mam.platform.common.enumeration;

public enum OrderTypeEnum {
    //手机充值
	PHONE_RECHARGE(110001, "手机充值"),
    //买电影票
	PURCHASE_MOVIE_TICKET(110002, "买电影票"),
    //支付账户充值
	ACCOUNT_RECHARGE(250001, "支付账户充值"),
    //支付账户提现
	ACCOUNT_WITHDRAWALS(260001, "支付账户提现");

    private Integer status;
    private String desc;

    private OrderTypeEnum(int status, String desc){
        this.status = status;
        this.desc = desc;
    }
    public int value(){
        return this.status;
    }
    public String desc() {
    	return this.desc;
    }
    public static OrderTypeEnum getOrderTypeEnum(int status) {
    	OrderTypeEnum result = null;
    	for (OrderTypeEnum e :OrderTypeEnum.values()) {
    		if (e.status.equals(status)) {
    			result = e;
    			break;
    		}
    	}
    	return result;
    }
}
