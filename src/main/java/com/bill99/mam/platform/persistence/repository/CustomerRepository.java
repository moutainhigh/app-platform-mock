package com.bill99.mam.platform.persistence.repository;

import static com.bill99.mam.platform.common.util.Validator.checkNotNull;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bill99.mam.platform.common.constant.PlatformConst;
import com.bill99.mam.platform.common.enumeration.CustomerStatusEnum;
import com.bill99.mam.platform.common.enumeration.message.RepositoryMsgEnum;
import com.bill99.mam.platform.common.util.CommonUtil;
import com.bill99.mam.platform.common.util.LoginIdUtil;
import com.bill99.mam.platform.common.util.WrappedBeanCopier;
import com.bill99.mam.platform.persistence.dao.CustomerMapper;
import com.bill99.mam.platform.persistence.dto.CustomerDto;
import com.bill99.mam.platform.persistence.entity.Customer;
import com.bill99.mam.platform.persistence.entity.CustomerExample;

@Component
public class CustomerRepository {
    @Autowired
    private CustomerMapper customerMapper;

    public CustomerDto queryCustomerById(long id) {
        Customer customer = customerMapper.selectByPrimaryKey(id);
        return WrappedBeanCopier.copyProperties(customer, CustomerDto.class);
    }

    public CustomerDto queryCustomer(Integer customerType, String loginId) {
        CustomerExample customerExample = new CustomerExample();
        CustomerExample.Criteria criteria = customerExample.createCriteria();
        criteria.andPlatformIdEqualTo(CommonUtil.toLong(PlatformConst.PLATFORM_MERCHENT_CODE));

        if (customerType != null){
            criteria.andCustomerTypeEqualTo(customerType);
        }

        if (StringUtils.isNotBlank(loginId)){
            criteria.andLoginIdEqualTo(loginId);
        }

        criteria.andStatusEqualTo(CustomerStatusEnum.VERIFIED.status());
        criteria.andLoginTypeEqualTo(LoginIdUtil.getLoginIdType(loginId));
        List<Customer> customerList = customerMapper.selectByExample(customerExample);
        if (CollectionUtils.isEmpty(customerList)) return null;
        return WrappedBeanCopier.copyProperties(customerList.get(0), CustomerDto.class);
    }

    public List<CustomerDto> queryCustomer(Integer customerType, String loginId, List<Integer> statusList) {

        CustomerExample customerExample = new CustomerExample();
        CustomerExample.Criteria criteria = customerExample.createCriteria();
        criteria.andPlatformIdEqualTo(CommonUtil.toLong(PlatformConst.PLATFORM_MERCHENT_CODE));

        if (customerType != null){
            criteria.andCustomerTypeEqualTo(customerType);
        }

        if (StringUtils.isNotBlank(loginId)){
            criteria.andLoginIdEqualTo(loginId);
        }

        if (CollectionUtils.isNotEmpty(statusList)) {
            criteria.andStatusIn(statusList);
        }
        criteria.andLoginTypeEqualTo(LoginIdUtil.getLoginIdType(loginId));
        List<Customer> customerList = customerMapper.selectByExample(customerExample);
        return WrappedBeanCopier.copyPropertiesOfList(customerList, CustomerDto.class);
    }

    public CustomerDto addCustomer(CustomerDto customerDto) {
        checkNotNull(customerDto,RepositoryMsgEnum.CUSTOMER_DTO_NOT_EXIST);
        customerDto.setCreateTime(new Date());
        customerDto.setUpdateTime(new Date());
        Customer customer = WrappedBeanCopier.copyProperties(customerDto, Customer.class);
        customerMapper.insert(customer);
        return WrappedBeanCopier.copyProperties(customer, CustomerDto.class);
    }

    public CustomerDto update(CustomerDto customerDto) {
        checkNotNull(customerDto,RepositoryMsgEnum.CUSTOMER_DTO_NOT_EXIST);
        checkNotNull(customerDto.getId(),RepositoryMsgEnum.CUSTOMER_ID_EMPTY);
        customerDto.setUpdateTime(new Date());
        Customer customer = WrappedBeanCopier.copyProperties(customerDto, Customer.class);
        customerMapper.updateByPrimaryKeySelective(customer);
        return WrappedBeanCopier.copyProperties(customer, CustomerDto.class);
    }
}
