package com.bill99.mam.platform.controller.web.modelattribute;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;
@Data
@ToString(exclude = "loginPwd")
public class EnterpriseBindCardInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 户名*/
    @NotBlank
    private String name;
    /** 银行编号*/
    @NotBlank
    private String bankId;
    /** 分支行名称*/
    @NotBlank
    private String branchBankName;
    /** 银行账号*/
    @NotBlank
    private String bankAcctId;
    /** 开户行省份*/
    @NotBlank
    private String province;
    /** 开户行城市*/
    @NotBlank
    private String city;
}
