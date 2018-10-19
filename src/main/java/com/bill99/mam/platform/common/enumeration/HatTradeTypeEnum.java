package com.bill99.mam.platform.common.enumeration;

public enum HatTradeTypeEnum {
    ORDER("10", "下单"),
    CONSUME("11", "消费"),
    REFUND("12", "退货"),
    RECHARGE("13", "充值");


    private String code;
    private String msg;
    private HatTradeTypeEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
