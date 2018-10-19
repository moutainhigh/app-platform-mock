package com.bill99.mam.platform.service.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.common.constant.PlatformConst;
import com.bill99.mam.platform.service.model.BaseRequest;

@Getter
@Setter
@ToString(callSuper = true)
public class EnterpriseRegisterReq extends BaseRequest {
    private static final long serialVersionUID = 1L;
    private String name;
    private String loginId;
    private String loginIdType;
    private String loginPwd;
    private String platformId = PlatformConst.PLATFORM_MERCHENT_CODE;
    private String idCardType;
    private String idCardNumber;
}
