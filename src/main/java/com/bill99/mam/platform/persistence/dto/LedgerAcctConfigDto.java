package com.bill99.mam.platform.persistence.dto;

import lombok.Data;

import java.util.Date;
@Data
public class LedgerAcctConfigDto {

    private Long id;

    private Integer ledgerAcctType;

    private Long goodsId;

    private Long customerId;

    private Integer rate;

    private Integer sharingType;

    private Date createTime;

    private Date updateTime;
}