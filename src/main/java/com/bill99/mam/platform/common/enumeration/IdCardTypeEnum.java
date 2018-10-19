package com.bill99.mam.platform.common.enumeration;

import org.apache.commons.lang3.StringUtils;

public enum IdCardTypeEnum {
    A("101", "身份证"),
    B("102", "护照"),
    C("103", "军官证"),
    D("104", "士兵证"),
    E("105", "港澳台通行证"),
    F("106", "临时身份证"),
    G("107", "户口本"),
    H("108", "警官证"),
    I("109", "外国人居留证"),
    J("110", "回乡证"),
    K("111", "台胞证"),
    L("112", "其他类型证件 ");

    private String code;
    private String desc;
    IdCardTypeEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }
    public static String desc(String code){
        String desc = "";
        for (IdCardTypeEnum idCard: values()) {
            if(StringUtils.equals(code, idCard.getCode())){
                desc = idCard.getDesc();
            }
        }
        return desc;
    }
    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
