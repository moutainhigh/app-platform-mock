package com.bill99.mam.platform.service;

import java.util.List;

import com.bill99.mam.platform.persistence.dto.CustomerDto;
import com.bill99.mam.platform.remote.dto.response.BindCardInfo;
import com.bill99.mam.platform.service.model.request.PersonalBindCardReq;
import com.bill99.mam.platform.service.model.request.PersonalRegisterReq;
import com.bill99.mam.platform.service.model.request.PersonalUnBindCardReq;
import com.bill99.mam.platform.service.model.response.PersonalRegisterResp;

public interface IPersonalCustomerService {
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
    PersonalRegisterResp personalRegister(PersonalRegisterReq request);
    
    List<BindCardInfo> qryPersonBandCardList(String id);
    
    /** 银行卡鉴权 */
    String personalBankCardAuth(PersonalBindCardReq req);
    
    /** 银行卡绑卡 */
    String personBindBankCard(PersonalBindCardReq req);
    
    /** 银行卡解绑卡 */
    void personUnbindBankCard(PersonalUnBindCardReq req);
}
