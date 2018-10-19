package com.bill99.mam.platform.service;

import com.bill99.mam.platform.model.request.OrderPayReq;
import com.bill99.mam.platform.model.request.OrderSaveReq;
import com.bill99.mam.platform.model.request.ProductItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTest {
    @Autowired
    private IOrderService orderService;

    @Test
    public void testSaveOrder() {

        OrderSaveReq orderSaveReq = new OrderSaveReq();
        orderSaveReq.setBuyerId(10012l);

        ProductItem item1 = new ProductItem();
        item1.setSellerId(50l);
        item1.setSellerName("测试");//必输项
        item1.setProductName("测试商品888");
        item1.setProductNum(2l);
        item1.setProductPrice(73l);
        item1.setProductTag(3l);

        ProductItem item2 = new ProductItem();
        item2.setSellerId(50l);
        item2.setSellerName("测试");
        item2.setProductName("测试商品565");
        item2.setProductNum(1l);
        item2.setProductPrice(56l);
        item2.setProductTag(4l);

        ProductItem item3 = new ProductItem();
        item3.setSellerId(52l);
        item3.setSellerName("邮件测试");
        item3.setProductName("测试商品1");
        item3.setProductNum(2l);
        item3.setProductPrice(23l);
        item3.setProductTag(1l);
        List orderItems = new ArrayList<ProductItem>();
        orderItems.add(item1);
//        orderItems.add(item2);
//        orderItems.add(item3);
        orderSaveReq.setOrderItems(orderItems);

        orderService.saveOrder(orderSaveReq);

    }

    @Test
    public void testPayOrder() {
        OrderPayReq orderPayReq = new OrderPayReq();
        orderPayReq.setPayMode(10);
        orderPayReq.setOrderId(21200000l);
        orderService.payOrder(orderPayReq);
    }
}
