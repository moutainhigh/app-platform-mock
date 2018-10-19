package com.bill99.mam.platform.common.enumeration;

public enum CustomerStatusEnum {
    //已注销
    CANCEL(-1),
    //新建
    NEW(1),
    //待审批（企业用户）
    APPROVING(2),
    //已激活或有效
    VERIFIED(3),
    ;
    private Integer status;

    private CustomerStatusEnum(Integer status){
     this.status = status;
    }


    public Integer status(){
        return this.status;
    }
}
