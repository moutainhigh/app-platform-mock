package com.bill99.mam.platform.mybatis;

import com.bill99.mam.platform.persistence.repository.SubRefundRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubRefundTest {
    @Autowired
    private SubRefundRepository subRefundRepository;
    @Test
    public void testSave(){
//        SubRefund subRefund = new SubRefund();
//        subRefund.setCreateTime(new Date());
//        subRefund.setOrigOrderItemNo(23234l);
//        subRefund.setOrigOrderNo(43543l);
//        subRefund.setRefundNo(6567l);
//        subRefund.setSubMerchantUid(34535l);
//        subRefund.setUpdateTime(new Date());
//        subRefund = subRefundRepository.save(subRefund);
//        System.out.println(JSON.toJSONString(subRefund,true));
    }

}
