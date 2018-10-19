package com.bill99.mam.platform.service.impl;

import com.bill99.mam.platform.common.enumeration.CustomerType;
import com.bill99.mam.platform.persistence.dto.CustomerDto;
import com.bill99.mam.platform.persistence.repository.CustomerRepository;
import com.bill99.mam.platform.service.ICustomerService;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerService implements ICustomerService{
    @Autowired
    private CustomerRepository customerRepository;

    public CustomerDto queryCustomerById(int id){
        return customerRepository.queryCustomerById(id);
    }

    @Override
    public CustomerDto queryVerifiedCustomer(Integer customerType, String loginId) {
        return customerRepository.queryCustomer(customerType,loginId);
    }

    /**
     * 注册时校验账号是否存在
     * @param customerType
     * @param loginId
     * @return
     */
    public boolean verifyCustomerExists(Integer customerType, String loginId) {
        // TODO: 2018/3/18 参数校验
        if (customerType != null && CustomerType.PERSONAL.type() == customerType.intValue()){
            CustomerDto customerDto = customerRepository.queryCustomer(customerType,loginId);
            if (customerDto != null){
                return true;
            }
        } else {
            //企业用户
            List<Integer> statusList = new ArrayList<>();
            statusList.add(2);//待审批
            statusList.add(3);//有效
            List<CustomerDto> customerDtos = customerRepository.queryCustomer(customerType,loginId,statusList);
            if (CollectionUtils.isNotEmpty(customerDtos)) return true;
        }

        return false;

    }
}
