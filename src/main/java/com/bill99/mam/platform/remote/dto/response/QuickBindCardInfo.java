package com.bill99.mam.platform.remote.dto.response;

import lombok.Data;

import java.io.Serializable;
@Data
public class QuickBindCardInfo implements Serializable {
    private static final long serialVersionUID = 5091600792019294792L;
    private String name;
    private String idCardType;
    private String idCardNumber;
    private String shortBankAcctId;
    private String mobile;
    private String bankId;
    private String cardType;
    private String createTime;
    private String updateTime;
}
