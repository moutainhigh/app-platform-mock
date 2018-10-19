package com.bill99.mam.platform.service.impl;

import static com.bill99.mam.platform.common.util.Validator.checkNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bill99.mam.platform.common.enumeration.message.DomainMsgEnum;
import com.bill99.mam.platform.common.exception.ExceptionFactory;
import com.bill99.mam.platform.common.util.EncryUtil;
import com.bill99.mam.platform.persistence.dto.CustomerDto;
import com.bill99.mam.platform.persistence.dto.LoginPasswordDto;
import com.bill99.mam.platform.persistence.repository.CustomerRepository;
import com.bill99.mam.platform.persistence.repository.LoginPasswordRepository;
import com.bill99.mam.platform.service.ILoginService;
import com.bill99.mam.platform.service.model.request.LoginReq;
import com.bill99.mam.platform.service.model.response.LoginResp;


@Component
public class LoginService implements ILoginService{
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private LoginPasswordRepository loginPasswordRepository;

    public LoginResp login(LoginReq request) {

        LoginResp resp = new LoginResp();
        // TODO: 2018/3/6 参数校验-注解
        /** 1.查询客户信息，取得customerId*/
        checkNotNull(request);
        CustomerDto customerDto = customerRepository.queryCustomer(
                Integer.valueOf(request.getCustomerType()),
                request.getLoginId());
        checkNotNull(customerDto,DomainMsgEnum.CUSTOMER_NOT_EXIST);
        /**
         * 校验密码
         */
        LoginPasswordDto loginPasswordDto = new LoginPasswordDto();
        loginPasswordDto.setCustomerId(customerDto.getId());
        loginPasswordDto.setLoginPassword(EncryUtil.md5Encry(request.getLoginPwd()));
        if (!loginPasswordRepository.validateLoginPwd(loginPasswordDto)){
            ExceptionFactory.throwException(DomainMsgEnum.LOGIN_PWD_ILLEGAL);
        }

        resp.setCustomerId(String.valueOf(customerDto.getId()));
        resp.setName(customerDto.getName());
        return resp;
    }
}
