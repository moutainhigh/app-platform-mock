package com.bill99.mam.platform.service.model.request;

import lombok.Data;

import java.io.Serializable;
@Data
public class SharingData implements Serializable{
    private static final long serialVersionUID = 5619419736701296770L;
    private Long sharingUId;//sPlatformSharing =0 时，平台用户id；isPlatformSharing =1 时，商户平台代码（和X-99Bill-PlatformCode相同）
    private Integer isPlatformSharing;//0.否  1.是
    private Long sharingApplyAmount;//填写应该分账的金额
    private String sharingDesc;//分账备注说明
    private String settlePeriod;//结算周期
    private String subOutTradeNo;//平台子订单编号
}
