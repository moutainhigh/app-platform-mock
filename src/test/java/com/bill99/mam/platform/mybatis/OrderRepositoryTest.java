package com.bill99.mam.platform.mybatis;

import com.bill99.mam.platform.persistence.dto.OrderDetailDto;
import com.bill99.mam.platform.persistence.dto.OrderDto;
import com.bill99.mam.platform.persistence.dto.OrderProductItemDto;
import com.bill99.mam.platform.persistence.repository.OrderDetailRepository;
import com.bill99.mam.platform.persistence.repository.OrderProductItemRepository;
import com.bill99.mam.platform.persistence.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository detailRepository;
    @Autowired
    private OrderProductItemRepository productItemRepository;

    @Test
    public void addOrder() {
        OrderDto orderDto = new OrderDto();

        OrderDetailDto detailDto = new OrderDetailDto();
        OrderProductItemDto productItemDto = new OrderProductItemDto();

    }

    @Test
    public void addOrderDetail() {

        OrderDetailDto detailDto = new OrderDetailDto();
        detailDto.setOrderAmount(12l);
        detailDto.setOrderNo(122l);
        detailDto.setOrderStatus(0);
        detailDto.setOrderType(1l);
        detailDto.setPayStatus(0);
//        detailDto.setSettleAmount(2l);
        detailDto.setSubMerchantUid(212132l);
        detailDto.setSubMerchantName("测试子商户名称23");

        OrderProductItemDto productItemDto = new OrderProductItemDto();
        productItemDto.setOrderStatus(1);
        productItemDto.setPayStatus(2);
        productItemDto.setProductName("测试商品34");
        productItemDto.setProductNum(2l);
        productItemDto.setProductPrice(45l);
        productItemDto.setProductTag(144l);

        OrderProductItemDto productItemDto2 = new OrderProductItemDto();
        productItemDto2.setOrderStatus(1);
        productItemDto2.setPayStatus(2);
        productItemDto2.setProductName("测试商品56");
        productItemDto2.setProductNum(2l);
        productItemDto2.setProductPrice(45l);
        productItemDto2.setProductTag(144l);
        List<OrderProductItemDto> productItemDtos = new ArrayList<OrderProductItemDto>();
        productItemDtos.add(productItemDto);
        productItemDtos.add(productItemDto2);
        detailDto.setProductList(productItemDtos);

        OrderDetailDto detailDto2 = detailRepository.save(detailDto);
        System.out.println("------>detailDto"+detailDto);
        System.out.println("------>detailDto2"+detailDto2);
    }
    @Test
    public void findDetailByOrderNo(){
        List<OrderDetailDto> orderDetailDtos = detailRepository.findByOrderNo(null);

        System.out.println(orderDetailDtos);

    }

    @Test
    public void addProductItem() {
        OrderProductItemDto productItemDto = new OrderProductItemDto();
        productItemDto.setOrderDetailNo(1000021l);
        productItemDto.setOrderStatus(1);
        productItemDto.setPayStatus(2);
        productItemDto.setProductName("测试商品2");
        productItemDto.setProductNum(2l);
        productItemDto.setProductPrice(45l);
        productItemDto.setProductTag(144l);
        productItemDto = productItemRepository.save(productItemDto);
        System.out.println(productItemDto);
    }




}
