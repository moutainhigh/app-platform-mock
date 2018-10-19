package com.bill99.mam.platform.controller.web.modelattribute;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
@Data
public class RechargeWithdrawalsCardInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotBlank
    private String bankAcctId;
    /** 14：充值；15：提现 */
    @NotBlank
    @Pattern(regexp="[14,15]", message="functionCode: 14-充值；15-提现")
    private String functionCode;
    @NotBlank
    private String payAmount;
}
