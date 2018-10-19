package com.bill99.mam.platform.remote.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseResponse;
@Getter
@Setter
@ToString(callSuper = true)
public class QuickPaymentDynamicCodeResp extends RemoteBaseResponse {
    private static final long serialVersionUID = 307459679681916097L;
    private String uId;
    private String token;//支付token 成功必返
    private String validCode;//短信验证码  成功必返
}
