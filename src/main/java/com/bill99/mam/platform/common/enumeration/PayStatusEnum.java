package com.bill99.mam.platform.common.enumeration;

public enum PayStatusEnum {
    //初始化
    INITED(0),
    //成功
    SUCCESS(1),
    //失败
    FAILED(2),
    //付款中
    IN_PAYMENT(3),;

    private Integer status;

    private  PayStatusEnum(int status){
        this.status = status;
    }
    public int value(){
        return this.status;
    }
}
