package com.bill99.mam.platform.remote.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;

@Getter
@Setter
@ToString(callSuper = true)
public class WithDrawReq extends RemoteBaseRequest {
    private static final long serialVersionUID = 58117828022136013L;
    private String functionCode;//交易功能
    private String outTradeNo;//外部订单号
    private String uId;//平台用户id
    private String merchantName;//平台商户名称
    private String amount;//提现金额(单位：分)
    private String memberBankAcctId;//快钱返回用户绑定的银行卡号ID
    private String payMode;//支付方式
    private String tradeFee;//分提现手续费--平台收取
    private String orderType;
    private String bgUrl;//通知地址
}
