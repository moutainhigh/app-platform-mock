package com.bill99.mam.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bill99.mam.platform.common.constant.ResultCode;
import com.bill99.mam.platform.common.exception.ExceptionFactory;
import com.bill99.mam.platform.persistence.dto.CustomerDto;
import com.bill99.mam.platform.persistence.repository.CustomerRepository;
import com.bill99.mam.platform.persistence.repository.LoginPasswordRepository;
import com.bill99.mam.platform.remote.dto.request.EnterpriseBankCardBindReq;
import com.bill99.mam.platform.remote.dto.request.EnterpriseBankCardListQryReq;
import com.bill99.mam.platform.remote.dto.request.EnterpriseBankCardUnbindReq;
import com.bill99.mam.platform.remote.dto.request.HatQueryAcctBalanceReq;
import com.bill99.mam.platform.remote.dto.response.BindCardInfo;
import com.bill99.mam.platform.remote.dto.response.EnterpriseBankCardBindResp;
import com.bill99.mam.platform.remote.dto.response.EnterpriseBankCardListQryResp;
import com.bill99.mam.platform.remote.dto.response.EnterpriseBankCardUnbindResp;
import com.bill99.mam.platform.remote.dto.response.HatQueryAcctBalanceResp;
import com.bill99.mam.platform.remote.service.HatService;
import com.bill99.mam.platform.service.IEnterpriseCustomerService;
import com.bill99.mam.platform.service.model.request.EnterpriseBindCardReq;
import com.bill99.mam.platform.service.model.request.EnterpriseUnBindCardReq;
import com.bill99.mam.platform.service.model.request.PersonalRegisterReq;
import com.bill99.mam.platform.service.model.response.PersonalRegisterResp;

@Component
public class EnterpriseCustomerService implements IEnterpriseCustomerService{
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private HatService hatService;
    @Autowired
    private LoginPasswordRepository loginPasswordRepository;

    @Override
    public CustomerDto queryCustomerById(Long id){
    	CustomerDto customerDto = customerRepository.queryCustomerById(id);
        return customerDto;
    }
    
    @Override
    public String queryAcctBalance(Long id) {
    	String balance = null;
    	
    	HatQueryAcctBalanceReq req = new HatQueryAcctBalanceReq();
    	req.setUId(String.valueOf(id));
    	req.setAccountType(new String[]{"ACTP0001"});
    	HatQueryAcctBalanceResp queryAcctBalance = hatService.queryAcctBalance(req);
    	if (ResultCode.success(queryAcctBalance.getRspCode())
    			&& CollectionUtils.isNotEmpty(queryAcctBalance.getAccountBalanceList())) {
    		balance = queryAcctBalance.getAccountBalanceList().get(0).getBalance();
    	}
    	return balance;
    }
    
    @Override
    public String bindEnterpriseBankCard(EnterpriseBindCardReq request) {
    	EnterpriseBankCardBindReq req = new EnterpriseBankCardBindReq();
    	req.setUId(request.getUId());
    	req.setBankAcctId(request.getBankAcctId());
    	req.setBankId(request.getBankId());
    	req.setBranchBankName(request.getBranchBankName());
    	req.setName(request.getName());
    	req.setProvince(request.getProvince());
    	req.setCity(request.getCity());
    	
    	EnterpriseBankCardBindResp rsp = hatService.bindEnterpriseBandCard(req);
    	if (!ResultCode.success(rsp.getRspCode())) {
    		ExceptionFactory.throwException(rsp.getRspCode(), rsp.getRspMsg());
    	}
    	
    	return rsp.getMemberBankAcctId();
    }
    
    @Override
    public List<BindCardInfo> qryEnterpriseBandCardList(String id) {
    	List<BindCardInfo> result = new ArrayList<BindCardInfo>();
    	EnterpriseBankCardListQryReq req = new EnterpriseBankCardListQryReq();
    	
    	req.setUId(id);
    	EnterpriseBankCardListQryResp rsp = hatService.qryEnterpriseBankCardList(req);
    	
    	if (!ResultCode.success(rsp.getRspCode())) {
    		ExceptionFactory.throwException(rsp.getRspCode(), rsp.getRspMsg());
    	}
    	result = rsp.getBindCardList();
    	return result;
    }
    
    @Override
    public void enterpriseUnbindBankCard(EnterpriseUnBindCardReq request) {
    	
    	EnterpriseBankCardUnbindReq req = new EnterpriseBankCardUnbindReq();
    	req.setUId(request.getUId());
    	req.setMemberBankAcctId(request.getMemberBankAcctId());
    	
    	EnterpriseBankCardUnbindResp rsp = hatService.unbindEnterpriseBankCard(req);
    	if (!ResultCode.success(rsp.getRspCode())) {
    		ExceptionFactory.throwException(rsp.getRspCode(), rsp.getRspMsg());
    	}
    }

    @Override
    public CustomerDto addCustomer(CustomerDto dto) {
    	return null;
    }
    
    @Transactional
    @Override
    public PersonalRegisterResp enterpriseRegister(PersonalRegisterReq request) {
    	return null;
    }

}
