package com.bill99.mam.platform.persistence.repository;

import com.bill99.mam.platform.common.enumeration.OrderStatusEnum;
import com.bill99.mam.platform.common.enumeration.PayStatusEnum;
import com.bill99.mam.platform.common.enumeration.message.RepositoryMsgEnum;
import com.bill99.mam.platform.common.util.WrappedBeanCopier;
import com.bill99.mam.platform.persistence.dao.OrderProductItemMapper;
import com.bill99.mam.platform.persistence.dto.OrderProductItemDto;
import com.bill99.mam.platform.persistence.entity.OrderProductItem;
import com.bill99.mam.platform.persistence.entity.OrderProductItemExample;
import com.google.common.base.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static com.bill99.mam.platform.common.util.Validator.checkNotNull;

@Component
public class OrderProductItemRepository {
    @Autowired
    private OrderProductItemMapper productItemMapper;

    public OrderProductItemDto save(OrderProductItemDto dto){
        checkNotNull(dto,RepositoryMsgEnum.ORDER_PRODUCTITEM_DTO_EMPTY);
        OrderProductItem productItem = WrappedBeanCopier.copyProperties(dto,OrderProductItem.class);
        Date now = new Date();
        productItem.setCreateTime(now);
        productItem.setUpdateTime(now);
        productItem.setOrderStatus(Optional.fromNullable(productItem.getOrderStatus()).or(OrderStatusEnum.WAIT_PAYMENT.value()));
        productItem.setPayStatus(ObjectUtils.defaultIfNull(productItem.getPayStatus(),PayStatusEnum.INITED.value()));
        productItemMapper.insert(productItem);
        return WrappedBeanCopier.copyProperties(productItem,OrderProductItemDto.class);
    }

    public OrderProductItemDto update(OrderProductItemDto dto){
        checkNotNull(dto,RepositoryMsgEnum.ORDER_PRODUCTITEM_DTO_EMPTY);
        checkNotNull(dto.getId(),RepositoryMsgEnum.ORDER_PRODUCTITEM_ID_EMPTY);
        OrderProductItem productItem = WrappedBeanCopier.copyProperties(dto,OrderProductItem.class);
        productItem.setUpdateTime(new Date());
        productItemMapper.updateByPrimaryKeySelective(productItem);
        return WrappedBeanCopier.copyProperties(productItem,OrderProductItemDto.class);
    }

    public OrderProductItemDto findById(Long id){
        if (id == null) return null;
        return WrappedBeanCopier.copyProperties(productItemMapper.selectByPrimaryKey(id),OrderProductItemDto.class);
    }

    public List<OrderProductItemDto> findByDetailNo(Long orderDetailNo){
        if (orderDetailNo == null) return null;
        OrderProductItemExample productItemExample = new OrderProductItemExample();
        productItemExample.createCriteria().andOrderDetailNoEqualTo(orderDetailNo);
        List<OrderProductItem> productItems = productItemMapper.selectByExample(productItemExample);
        return WrappedBeanCopier.copyPropertiesOfList(productItems,OrderProductItemDto.class);
    }
}
