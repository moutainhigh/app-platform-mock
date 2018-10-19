package com.bill99.mam.platform.common.enumeration;

public enum HatTradeStatusEnum {
    INIT("0", "初始化"),
    SUCCESS("1", "成功"),
    FAILED("2", "失败"),
    PROCESSING("3", "进行中"),
    ACCEPTED("4", "受理成功"),
    CHANNEL_ACCEPTED("5", "渠道受理成功");

    private String code;
    private String msg;

    private HatTradeStatusEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
