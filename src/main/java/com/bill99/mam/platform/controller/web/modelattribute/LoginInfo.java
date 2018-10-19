package com.bill99.mam.platform.controller.web.modelattribute;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
@Data
@ToString(exclude="loginPwd")
public class LoginInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String loginId;
    private String loginPwd;
    private String customerType;
    private String customerId;
    private String name;
}
