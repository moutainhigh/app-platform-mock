package com.bill99.mam.platform.service;

import com.bill99.mam.platform.service.model.request.LoginReq;
import com.bill99.mam.platform.service.model.response.LoginResp;

public interface ILoginService {

    /**
     * 登录
     * @param request
     * @return
     */
    LoginResp login(LoginReq request);
}
