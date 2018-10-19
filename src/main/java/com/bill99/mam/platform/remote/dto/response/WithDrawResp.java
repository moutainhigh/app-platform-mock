package com.bill99.mam.platform.remote.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseResponse;

@Getter
@Setter
@ToString(callSuper = true)
public class WithDrawResp extends RemoteBaseResponse {
    private static final long serialVersionUID = -8650211289380354314L;
    private String outTradeNo;//外部订单号
    private String actualAmount;//到账金额
    private String tradeFee;//提现手续费
    private String status;//交易状态
}
