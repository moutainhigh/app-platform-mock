package com.bill99.mam.platform.service.impl;

import com.bill99.mam.platform.common.constant.ResultCode;
import com.bill99.mam.platform.common.enumeration.CustomerStatusEnum;
import com.bill99.mam.platform.common.enumeration.CustomerType;
import com.bill99.mam.platform.common.util.CommonUtil;
import com.bill99.mam.platform.common.util.EncryUtil;
import com.bill99.mam.platform.common.util.LoginIdUtil;
import com.bill99.mam.platform.persistence.dto.CustomerDto;
import com.bill99.mam.platform.persistence.dto.LoginPasswordDto;
import com.bill99.mam.platform.persistence.repository.CustomerRepository;
import com.bill99.mam.platform.persistence.repository.LoginPasswordRepository;
import com.bill99.mam.platform.remote.dto.request.HatPersonalRegisterReq;
import com.bill99.mam.platform.remote.dto.response.HatPersonalRegisterResp;
import com.bill99.mam.platform.remote.service.HatService;
import com.bill99.mam.platform.service.IRegisterService;
import com.bill99.mam.platform.service.model.request.PersonalRegisterReq;
import com.bill99.mam.platform.service.model.response.PersonalRegisterResp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
public class RegisterService implements IRegisterService{
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private LoginPasswordRepository loginPasswordRepository;
    @Autowired
    private HatService hatService;
    @Transactional
    public PersonalRegisterResp personalRegister(PersonalRegisterReq request) {
        PersonalRegisterResp personalRegisterResp = new PersonalRegisterResp();
        // TODO: 2018/3/5 参数校验-注解
        // TODO: 2018/3/5 重复会员校验 在一个平台、个人会员、同一个登录帐号
        /** 保存会员主表 */
        CustomerDto customerDto = savePersonalCustomer(request);
        // 调hat注册快钱账号
        HatPersonalRegisterResp hatPersonalRegisterResp = hatService.personalRegister(
                convert2PersonalRegisterReq(request,customerDto.getId()));

        if (!hatPersonalRegisterResp.getRspCode().equals(ResultCode.SUCCESS)){
            personalRegisterResp.setRspCode(hatPersonalRegisterResp.getRspCode());
            personalRegisterResp.setRspMsg(hatPersonalRegisterResp.getRspMsg());
            return personalRegisterResp;
        }
        /** 保存登录密码 */
        saveLoginPwd(request,customerDto);
        // 更新状态为有效
        customerDto.setStatus(CustomerStatusEnum.VERIFIED.status());
        customerRepository.update(customerDto);
        return personalRegisterResp;
    }

    /**
     * 保存个人用户信息
     * @param request
     * @return
     */
    public CustomerDto savePersonalCustomer (PersonalRegisterReq request){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerType(CustomerType.PERSONAL.type());
        customerDto.setLoginId(request.getLoginId());
        customerDto.setLoginType(LoginIdUtil.getLoginIdType(request.getLoginId()));
        customerDto.setName(request.getName());
        customerDto.setPlatformId(Long.valueOf(request.getPlatformId()));
        customerDto.setStatus(CustomerStatusEnum.NEW.status());//创建状态
        customerDto.setIdcardNumber(request.getIdCardNumber());
        customerDto.setIdcardType(CommonUtil.toInteger(request.getIdCardType()));
        return customerRepository.addCustomer(customerDto);
    }

    /**
     * 保存登录密码
     * @param request
     * @param customerDto
     */
    public  void saveLoginPwd(PersonalRegisterReq request,CustomerDto customerDto){
        LoginPasswordDto loginPasswordDto = new LoginPasswordDto();
        loginPasswordDto.setCustomerId(customerDto.getId());
        loginPasswordDto.setLoginPassword(EncryUtil.md5Encry(request.getLoginPwd()));
        loginPasswordRepository.addLoginPwd(loginPasswordDto);
    }

    public HatPersonalRegisterReq convert2PersonalRegisterReq(PersonalRegisterReq request,long customerId){

        HatPersonalRegisterReq hatPersonalRegisterReq = new HatPersonalRegisterReq();
        String loginId = request.getLoginId();
        if (LoginIdUtil.validateEmail(loginId)){
            hatPersonalRegisterReq.setEmail(loginId);
        } else {
            hatPersonalRegisterReq.setMobile(loginId);
        }

        hatPersonalRegisterReq.setIdCardNumber(request.getIdCardNumber());
        hatPersonalRegisterReq.setIdCardType(request.getIdCardType());
        hatPersonalRegisterReq.setName(request.getName());
        hatPersonalRegisterReq.setUId(String.valueOf(customerId));

        return hatPersonalRegisterReq;
    }
}
