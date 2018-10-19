package com.bill99.mam.platform.controller.web.modelattribute;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;
@Data
@ToString(exclude = "loginPwd")
public class PersonalRegisterInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotBlank
    private String name;
    @NotBlank
    private String phone;
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String idType;
    @NotBlank
    private String identificationCard;
    @NotBlank
    private String test;
}
