package com.bill99.mam.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bill99.mam.platform.common.constant.ResultCode;
import com.bill99.mam.platform.common.enumeration.CustomerStatusEnum;
import com.bill99.mam.platform.common.enumeration.CustomerType;
import com.bill99.mam.platform.common.enumeration.ResultEnum;
import com.bill99.mam.platform.common.enumeration.message.DomainMsgEnum;
import com.bill99.mam.platform.common.exception.ExceptionFactory;
import com.bill99.mam.platform.common.util.AmountUtil;
import com.bill99.mam.platform.common.util.CommonUtil;
import com.bill99.mam.platform.common.util.EncryUtil;
import com.bill99.mam.platform.common.util.LoginIdUtil;
import com.bill99.mam.platform.persistence.dto.CustomerDto;
import com.bill99.mam.platform.persistence.dto.LoginPasswordDto;
import com.bill99.mam.platform.persistence.repository.CustomerRepository;
import com.bill99.mam.platform.persistence.repository.LoginPasswordRepository;
import com.bill99.mam.platform.remote.dto.request.HatBankCardAuthReq;
import com.bill99.mam.platform.remote.dto.request.HatPersonalRegisterReq;
import com.bill99.mam.platform.remote.dto.request.HatQueryAcctBalanceReq;
import com.bill99.mam.platform.remote.dto.request.PersonBankCarListQryReq;
import com.bill99.mam.platform.remote.dto.request.PersonBankCardBindReq;
import com.bill99.mam.platform.remote.dto.request.PersonBankCardUnbindReq;
import com.bill99.mam.platform.remote.dto.response.BindCardInfo;
import com.bill99.mam.platform.remote.dto.response.HatBankCardAuthResp;
import com.bill99.mam.platform.remote.dto.response.HatPersonalRegisterResp;
import com.bill99.mam.platform.remote.dto.response.HatQueryAcctBalanceResp;
import com.bill99.mam.platform.remote.dto.response.PersonBankCarListQryResp;
import com.bill99.mam.platform.remote.dto.response.PersonBankCardBindResp;
import com.bill99.mam.platform.remote.dto.response.PersonBankCardUnbindResp;
import com.bill99.mam.platform.remote.service.HatService;
import com.bill99.mam.platform.service.IPersonalCustomerService;
import com.bill99.mam.platform.service.model.request.PersonalBindCardReq;
import com.bill99.mam.platform.service.model.request.PersonalRegisterReq;
import com.bill99.mam.platform.service.model.request.PersonalUnBindCardReq;
import com.bill99.mam.platform.service.model.response.PersonalRegisterResp;

@Component
public class PersonalCustomerService implements IPersonalCustomerService{
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private HatService hatService;
    @Autowired
    private LoginPasswordRepository loginPasswordRepository;

    public CustomerDto queryCustomerById(Long id){
    	CustomerDto customerDto = customerRepository.queryCustomerById(id);
    	
    	HatQueryAcctBalanceReq req = new HatQueryAcctBalanceReq();
    	req.setUId(String.valueOf(id));
    	req.setAccountType(new String[]{"DEF00001"});
        return customerDto;
    }
    
    @Override
    public String queryAcctBalance(Long id) {
    	String balance = null;
    	
    	HatQueryAcctBalanceReq req = new HatQueryAcctBalanceReq();
    	req.setUId(String.valueOf(id));
    	req.setAccountType(new String[]{"DEF00001"});
    	HatQueryAcctBalanceResp queryAcctBalance = hatService.queryAcctBalance(req);
    	if (ResultCode.success(queryAcctBalance.getRspCode())
    			&& CollectionUtils.isNotEmpty(queryAcctBalance.getAccountBalanceList())) {
    		try {
				balance = AmountUtil.changeF2Y(queryAcctBalance.getAccountBalanceList().get(0).getBalance());
			} catch (Exception e) {
				ExceptionFactory.create(ResultEnum.AMOUNT_FORMAT_ERROR);
			}
    	}
    	return balance;
    }
    
    @Override
    public String personalBankCardAuth(PersonalBindCardReq request) {
    	HatBankCardAuthReq req = new HatBankCardAuthReq();
    	req.setUId(request.getUId());
    	req.setName(request.getName());
    	req.setMobile(request.getPhone());;
    	req.setIdCardType(request.getIdType());
    	req.setIdCardNumber(request.getIdentificationCard());
    	req.setBankAcctId(request.getBankAcctId());
    	req.setCvv2(request.getCvv2());
    	req.setExpiredDate(request.getExpiredDate());
    	
    	HatBankCardAuthResp rsp = hatService.bankCardAuth(req);
    	if (!ResultCode.success(rsp.getRspCode())) {
    		ExceptionFactory.throwException(rsp.getRspCode(), rsp.getRspMsg());
    	}
    	
    	return rsp.getToken();
    }
    
    @Override
    public String personBindBankCard(PersonalBindCardReq request) {
    	
    	PersonBankCardBindReq req = new PersonBankCardBindReq();
    	req.setUId(request.getUId());
    	req.setName(request.getName());
    	req.setMobile(request.getPhone());;
    	req.setIdCardType(request.getIdType());
    	req.setIdCardNumber(request.getIdentificationCard());
    	req.setBankAcctId(request.getBankAcctId());
    	req.setCvv2(request.getCvv2());
    	req.setExpiredDate(request.getExpiredDate());
    	req.setValidCode(request.getValidCode());
    	req.setToken(request.getToken());
    	
    	PersonBankCardBindResp rsp = hatService.bindPersonBankCard(req);
    	if (!ResultCode.success(rsp.getRspCode())) {
    		ExceptionFactory.throwException(rsp.getRspCode(), rsp.getRspMsg());
    	}
    	
    	return rsp.getMemberBankAcctId();
    	
    }
    
    @Override
    public void personUnbindBankCard(PersonalUnBindCardReq request) {
    	
    	PersonBankCardUnbindReq req = new PersonBankCardUnbindReq();
    	req.setUId(request.getUId());
    	req.setMemberBankAcctId(request.getMemberBankAcctId());
    	PersonBankCardUnbindResp rsp = hatService.unbindPersonBankCard(req);
    	if (!ResultCode.success(rsp.getRspCode())) {
    		ExceptionFactory.throwException(rsp.getRspCode(), rsp.getRspMsg());
    	}
    }

    @Override
    public List<BindCardInfo> qryPersonBandCardList(String id) {
    	List<BindCardInfo> result = new ArrayList<BindCardInfo>();
    	PersonBankCarListQryReq req = new PersonBankCarListQryReq();
    	req.setUId(id);
    	PersonBankCarListQryResp rsp = hatService.qryPersonBandCardList(req);
    	
    	if (!ResultCode.success(rsp.getRspCode())) {
    		ExceptionFactory.throwException(rsp.getRspCode(), rsp.getRspMsg());
    	}
    	result = rsp.getBindCardList();
    	return result;
    }
    
    @Override
    public CustomerDto addCustomer(CustomerDto dto) {
    	dto = customerRepository.addCustomer(dto);
    	return dto;
    }
    
    @Transactional
    @Override
    public PersonalRegisterResp personalRegister(PersonalRegisterReq request) {
        // TODO: 2018/3/5 参数校验-注解
        // TODO: 2018/3/5 重复会员校验 在一个平台、个人会员、同一个登录帐号
        /** 保存会员主表 */
    	CustomerDto customerDto = customerRepository.queryCustomer(CustomerType.PERSONAL.type(), request.getLoginId());
    	if (null != customerDto) {
    		ExceptionFactory.throwException(DomainMsgEnum.CUSTOMER_LOGIN_ID_EXIST);
    	}
        customerDto = savePersonalCustomer(request);
        // 调hat注册快钱账号
        HatPersonalRegisterResp hatPersonalRegisterResp = hatService.personalRegister(
                convert2PersonalRegisterReq(request,customerDto.getId()));

        if (!hatPersonalRegisterResp.getRspCode().equals(ResultCode.SUCCESS)){
        	ExceptionFactory.throwException(hatPersonalRegisterResp.getRspCode(),
        			hatPersonalRegisterResp.getRspMsg());
        }
        /** 保存登录密码 */
        saveLoginPwd(request,customerDto);
        // 更新状态为有效
        customerDto.setStatus(CustomerStatusEnum.VERIFIED.status());
        customerRepository.update(customerDto);
        PersonalRegisterResp personalRegisterResp = new PersonalRegisterResp();
        personalRegisterResp.setCustomerId(String.valueOf(customerDto.getId()));
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
