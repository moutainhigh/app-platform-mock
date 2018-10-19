package com.bill99.mam.platform.persistence.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class SubRefundDto implements Serializable{
    private static final long serialVersionUID = 6870822183830505771L;

    private Long subRefundNo;

    private Long refundNo;

    private Long subMerchantUid;

    private Long origOrderNo;

    private Long origSubOrderNo;

    private Long origOrderItemNo;

    private Date createTime;

    private Date updateTime;

}