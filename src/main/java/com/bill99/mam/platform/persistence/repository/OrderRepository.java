package com.bill99.mam.platform.persistence.repository;

import static com.bill99.mam.platform.common.util.Validator.checkNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bill99.mam.platform.common.constant.PlatformConst;
import com.bill99.mam.platform.common.enumeration.OrderStatusEnum;
import com.bill99.mam.platform.common.enumeration.PayStatusEnum;
import com.bill99.mam.platform.common.enumeration.message.RepositoryMsgEnum;
import com.bill99.mam.platform.common.util.CommonUtil;
import com.bill99.mam.platform.common.util.WrappedBeanCopier;
import com.bill99.mam.platform.persistence.dao.OrderExtMapper;
import com.bill99.mam.platform.persistence.dao.OrderMapper;
import com.bill99.mam.platform.persistence.dto.OrderDetailDto;
import com.bill99.mam.platform.persistence.dto.OrderDto;
import com.bill99.mam.platform.persistence.dto.OrderProductItemDto;
import com.bill99.mam.platform.persistence.entity.Order;
import com.bill99.mam.platform.persistence.entity.OrderExample;
import com.bill99.mam.platform.service.model.request.OrderListReq;
import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;

@Component
public class OrderRepository {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderExtMapper orderExtMapper;
    @Autowired
    private OrderDetailRepository detailRepository;

    public OrderDto findById(Long id) {
        if (id == null) return null;
        Order order = orderMapper.selectByPrimaryKey(id);
        if (order == null) return null;
        OrderDto orderDto = WrappedBeanCopier.copyProperties(order, OrderDto.class);
        List<OrderDetailDto> orderDetailDtos = detailRepository.findByOrderNo(order.getId());
        if (CollectionUtils.isNotEmpty(orderDetailDtos)) {
            orderDto.setOrderDetails(orderDetailDtos);
        }
        return orderDto;
    }

    public OrderDto save(OrderDto orderDto) {
        checkNotNull(orderDto,RepositoryMsgEnum.ORDER__DTO_EMPTY);
        Order order = WrappedBeanCopier.copyProperties(orderDto, Order.class);
        Date now = new Date();
        order.setUpdateTime(now);
        order.setCreateTime(now);
        order.setTxnBeginTime(now);
        order.setPayStatus(Optional.fromNullable(order.getPayStatus()).or(PayStatusEnum.INITED.value()));
        order.setOrderStatus(ObjectUtils.defaultIfNull(order.getOrderStatus(),OrderStatusEnum.WAIT_PAYMENT.value()));
        order.setCurrencyCode(StringUtils.defaultIfBlank(order.getCurrencyCode(),PlatformConst.DEFAULT_CURRENCY_CODE));
        order.setIsAssured(MoreObjects.firstNonNull(order.getIsAssured(),0));//是否担保交易
        order.setFeeMode(Optional.fromNullable(order.getFeeMode()).or(0));//0:主收款方承担手续费--暂定默认为平台方
        order.setIsPlatformPayFee(CommonUtil.isPlatformPayFee(order.getPayeeUid()));//主收款收是否平台 0否 1是
        orderMapper.insert(order);

        OrderDto newOrderDto = WrappedBeanCopier.copyProperties(order, OrderDto.class);
        List<OrderDetailDto> orderDetailDtos = orderDto.getOrderDetails();
        if (CollectionUtils.isNotEmpty(orderDetailDtos)) {
            List<OrderDetailDto> detailDtoList = new ArrayList<>();
            for (OrderDetailDto detailDto : orderDetailDtos) {
                detailDto.setOrderNo(order.getId());
                detailDtoList.add(detailRepository.save(detailDto));
            }
            newOrderDto.setOrderDetails(detailDtoList);
        }
        return newOrderDto;
    }

    public OrderDto update(OrderDto orderDto) {
        checkNotNull(orderDto,RepositoryMsgEnum.ORDER__DTO_EMPTY);
        Order order = WrappedBeanCopier.copyProperties(orderDto, Order.class);
        order.setUpdateTime(new Date());
        orderMapper.updateByPrimaryKeySelective(order);
        OrderDto newOrderDto = WrappedBeanCopier.copyProperties(order, OrderDto.class);
        List<OrderDetailDto> orderDetailDtos = orderDto.getOrderDetails();
        if (CollectionUtils.isNotEmpty(orderDetailDtos)) {
            List<OrderDetailDto> detailDtoList = new ArrayList<>();
            for (OrderDetailDto detailDto : orderDetailDtos) {
                detailDtoList.add(detailRepository.update(detailDto));
            }
            newOrderDto.setOrderDetails(detailDtoList);
        }
        return newOrderDto;
    }

    public List<OrderDto> findByCustomerId(Long customerId) {
        if (customerId == null) return null;
        OrderExample example = new OrderExample();
        example.createCriteria().andUidEqualTo(customerId);
        return queryOrderByExample(example);
    }
    
    public List<OrderDto> selectPageList(OrderListReq qryVo) {
    	List<Order> orders = orderExtMapper.selectPageList(qryVo);
    	List<OrderDto> orderDtos = WrappedBeanCopier.copyPropertiesOfList(orders, OrderDto.class);
    	return orderDtos;
    }

    public Long countByCustomerId(Long customerId) {
    	OrderExample example = new OrderExample();
        OrderExample.Criteria criteria = example.createCriteria();
        if (customerId != null){
            criteria.andUidEqualTo(customerId);
        }
        return orderExtMapper.countByExample(example);
    }
    
    public List<OrderDto> findByCustomerId(Long customerId, int payState) {
        OrderExample example = new OrderExample();
        OrderExample.Criteria criteria = example.createCriteria();
        if (customerId != null){
            criteria.andUidEqualTo(customerId);
        }
        criteria.andPayStatusEqualTo(payState);
        return queryOrderByExample(example);
    }

    private List<OrderDto> queryOrderByExample(OrderExample orderExample) {
        if (orderExample == null) return null;
        List<Order> orders = orderMapper.selectByExample(orderExample);
        if (CollectionUtils.isEmpty(orders)) return null;
        List<OrderDto> orderDtos = WrappedBeanCopier.copyPropertiesOfList(orders, OrderDto.class);
        for (OrderDto orderDto : orderDtos) {
            Long orderNo = orderDto.getId();
            List<OrderDetailDto> detailDtos = detailRepository.findByOrderNo(orderNo);
            orderDto.setOrderDetails(detailDtos);
        }
        return orderDtos;
    }

    /**
     * 取消订单 必须满足以下两个条件：
     * 1. 订单状态为待付款
     * 2.支付状态为初始化或支付失败
     * @param orderId
     * @return
     */

    public boolean cancelOrder(Long orderId) {

        if (orderId == null) return false;

        OrderDto orderDto = findById(orderId);
        int orderStatus = orderDto.getOrderStatus().intValue();
        int payStatus = orderDto.getPayStatus().intValue();

        if (orderStatus != OrderStatusEnum.WAIT_PAYMENT.value() ||
                payStatus != PayStatusEnum.INITED.value() ||
                payStatus != PayStatusEnum.FAILED.value()) {
            return false;
        }
        orderDto.setOrderStatus(OrderStatusEnum.CALCEL_ORDER.value());
        for (OrderDetailDto detailDto : orderDto.getOrderDetails()) {
            detailDto.setOrderStatus(OrderStatusEnum.CALCEL_ORDER.value());
            for (OrderProductItemDto productItemDto : detailDto.getProductList()) {
                productItemDto.setOrderStatus(OrderStatusEnum.CALCEL_ORDER.value());
            }
        }
        //更新订单状态
        update(orderDto);

        return true;
    }
}