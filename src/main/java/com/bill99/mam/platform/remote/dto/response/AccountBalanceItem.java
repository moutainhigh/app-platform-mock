package com.bill99.mam.platform.remote.dto.response;

import lombok.Data;

import java.io.Serializable;
@Data
public class AccountBalanceItem implements Serializable {
    private static final long serialVersionUID = -1947191826444300427L;
    private String tradeType;//交易类型
    private String tradeTime;//交易时间
    private String amount;//发生金额
    private String direction;//方向
    private String memo;//备注
    private String tradeNo;//交易流水号
}
