package com.bill99.mam.platform.common.enumeration;

public enum RefundStatus {
    INIT("0", "初始化"),
    SUCCESS("1", "成功"),
    FAILED("2", "失败"),
    PROCESSING("3", "正在处理");


    private String code;
    private String msg;
    RefundStatus(String code, String msg){
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
