package com.bill99.mam.platform.service;

import java.util.List;

import com.bill99.mam.platform.persistence.dto.CustomerDto;
import com.bill99.mam.platform.remote.dto.response.BindCardInfo;
import com.bill99.mam.platform.service.model.request.EnterpriseBindCardReq;
import com.bill99.mam.platform.service.model.request.EnterpriseUnBindCardReq;
import com.bill99.mam.platform.service.model.request.PersonalRegisterReq;
import com.bill99.mam.platform.service.model.response.PersonalRegisterResp;

public interface IEnterpriseCustomerService {
    /**
     * 根据客户ID查询客户信息
     * @param id
     * @return
     */
    CustomerDto queryCustomerById(Long id);
    
    CustomerDto addCustomer(CustomerDto dto);
    
    String queryAcctBalance(Long id);
    
    /**
     * 个人会员注册
     * @param request
     * @return
     */
    PersonalRegisterResp enterpriseRegister(PersonalRegisterReq request);
    
    /** 银行卡绑卡 */
    String bindEnterpriseBankCard(EnterpriseBindCardReq req);
    
    /** 查询银行卡列表 */
    List<BindCardInfo> qryEnterpriseBandCardList(String id);
    
    /** 银行卡解绑卡 */
    void enterpriseUnbindBankCard(EnterpriseUnBindCardReq req);
}
