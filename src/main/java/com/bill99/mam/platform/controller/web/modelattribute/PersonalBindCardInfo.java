package com.bill99.mam.platform.controller.web.modelattribute;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;
@Data
@ToString(exclude = "loginPwd")
public class PersonalBindCardInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotBlank
    private String name;
    @NotBlank
    private String phone;
    @NotBlank
    private String idType;
    @NotBlank
    private String identificationCard;
    @NotBlank
    private String bankAcctId;
    @NotBlank
    private String validCode;
    /** 信用卡安全码*/
    private String cvv2;
    /** 信用卡有效期*/
    private String expiredDate;
    private String token;
}
