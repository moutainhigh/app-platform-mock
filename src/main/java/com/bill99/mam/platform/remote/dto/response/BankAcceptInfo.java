package com.bill99.mam.platform.remote.dto.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString(callSuper = true)
public class BankAcceptInfo implements Serializable{
    private static final long serialVersionUID = -1061756055781827905L;
    private String cardType;
    private String bankId;
    private String bankName;
}
