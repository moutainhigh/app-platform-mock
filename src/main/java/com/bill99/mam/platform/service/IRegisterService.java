package com.bill99.mam.platform.service;

import com.bill99.mam.platform.service.model.request.PersonalRegisterReq;
import com.bill99.mam.platform.service.model.response.PersonalRegisterResp;

public interface IRegisterService {
    /**
     * 个人会员注册
     * @param request
     * @return
     */
    PersonalRegisterResp personalRegister(PersonalRegisterReq request);
}
