package com.bill99.mam.platform.persistence.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class RefundDto implements Serializable{
    private static final long serialVersionUID = 8840780109940987L;

    private Long refundNo;

    private Long origOrderNo;

    private Long subMerchantUid;

    private String sharingData;

    private Long refundAmount;

    private Integer refundStatus;

    private Long payeeUid;

    private String billOrderNo;

    private String tradeNo;

    private Date refundStartTime;

    private Date refundEndTime;

    private String bgUrl;

    private String memo;

    private String errorCode;

    private String errorInfo;

    private String dataMap;

    private Date createTime;

    private Date updateTime;
    List<SubRefundDto> subRefundDtoList;

}