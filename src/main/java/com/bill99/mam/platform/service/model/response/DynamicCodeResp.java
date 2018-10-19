package com.bill99.mam.platform.service.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.service.model.BaseResponse;

@Getter
@Setter
@ToString(callSuper = true)
public class DynamicCodeResp extends BaseResponse {
    private static final long serialVersionUID = -7085979801562702459L;
    private String token;//支付token 成功必返
    private String validCode;//短信验证码  成功必返
}
