package com.bill99.mam.platform;

import com.bill99.mam.platform.persistence.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerTest2 {
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testQueryById2() {
        System.out.println(customerRepository.queryCustomerById(1));
    }
}
