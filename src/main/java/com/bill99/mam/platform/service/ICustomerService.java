package com.bill99.mam.platform.service;

import com.bill99.mam.platform.persistence.dto.CustomerDto;

public interface ICustomerService {
    /**
     * 根据客户ID查询客户信息
     * @param id
     * @return
     */
    CustomerDto queryCustomerById(int id);
    CustomerDto queryVerifiedCustomer(Integer customerType,String loginId);
}
