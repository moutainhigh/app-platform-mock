package com.bill99.mam.platform.persistence.repository;

import static com.bill99.mam.platform.common.util.Validator.checkNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bill99.mam.platform.common.enumeration.OrderStatusEnum;
import com.bill99.mam.platform.common.enumeration.PayStatusEnum;
import com.bill99.mam.platform.common.enumeration.message.RepositoryMsgEnum;
import com.bill99.mam.platform.common.util.CommonUtil;
import com.bill99.mam.platform.common.util.WrappedBeanCopier;
import com.bill99.mam.platform.persistence.dao.OrderDetailMapper;
import com.bill99.mam.platform.persistence.dto.OrderDetailDto;
import com.bill99.mam.platform.persistence.dto.OrderProductItemDto;
import com.bill99.mam.platform.persistence.entity.OrderDetail;
import com.bill99.mam.platform.persistence.entity.OrderDetailExample;
import com.google.common.base.Optional;

@Component
public class OrderDetailRepository {
    @Autowired
    private OrderDetailMapper detailMapper;
    @Autowired
    private OrderProductItemRepository productItemRepository;
    /**
     * 保存子订单
     * @param detailDto
     * @return
     */
    public OrderDetailDto save(OrderDetailDto detailDto) {
        checkNotNull(detailDto,RepositoryMsgEnum.ORDER_DETAIL_DTO_EMPTY);
        OrderDetail orderDetail = WrappedBeanCopier.copyProperties(detailDto, OrderDetail.class);
        Date now = new Date();
        orderDetail.setUpdateTime(now);
        orderDetail.setCreateTime(now);
        orderDetail.setOrderStatus(Optional.fromNullable(orderDetail.getOrderStatus()).or(OrderStatusEnum.WAIT_PAYMENT.value()));
        orderDetail.setPayStatus(ObjectUtils.defaultIfNull(orderDetail.getPayStatus(),PayStatusEnum.INITED.value()));
        orderDetail.setMerchantIsPlatform(CommonUtil.isPlatformPayFee(orderDetail.getSubMerchantUid()));
        //保存订单明细
        detailMapper.insert(orderDetail);
        OrderDetailDto newDetailDto = WrappedBeanCopier.copyProperties(orderDetail, OrderDetailDto.class);
        //保存OrderProductItem
        List<OrderProductItemDto> productItemDtos = detailDto.getProductList();
        if (CollectionUtils.isNotEmpty(productItemDtos)) {
            List<OrderProductItemDto> orderProductItemDtoList = new ArrayList<>();
            for (OrderProductItemDto productItemDto : productItemDtos) {
                //设置子订单号
                productItemDto.setOrderDetailNo(newDetailDto.getId());
                orderProductItemDtoList.add(productItemRepository.save(productItemDto));
            }
            newDetailDto.setProductList(orderProductItemDtoList);
        }
        return newDetailDto;
    }
    /**
     * 更新子订单
     * @param detailDto
     * @return
     */
    public OrderDetailDto update(OrderDetailDto detailDto) {
        checkNotNull(detailDto,RepositoryMsgEnum.ORDER_DETAIL_DTO_EMPTY);
        OrderDetail orderDetail = WrappedBeanCopier.copyProperties(detailDto, OrderDetail.class);
        orderDetail.setUpdateTime(new Date());
        //更新订单明细
        detailMapper.updateByPrimaryKeySelective(orderDetail);
        OrderDetailDto newDetailDto = WrappedBeanCopier.copyProperties(orderDetail, OrderDetailDto.class);
        List<OrderProductItemDto> productItemDtos = detailDto.getProductList();
        if (CollectionUtils.isNotEmpty(productItemDtos)) {
            List<OrderProductItemDto> orderProductItemDtoList = new ArrayList<>();
            for (OrderProductItemDto productItemDto : productItemDtos) {
                orderProductItemDtoList.add(productItemRepository.update(productItemDto));
            }
            newDetailDto.setProductList(orderProductItemDtoList);
        }
        return newDetailDto;
    }

    public OrderDetailDto findById(Long id) {
        if (id == null) return null;
        OrderDetailDto detailDto = null;
        OrderDetail orderDetail = detailMapper.selectByPrimaryKey(id);
        detailDto = WrappedBeanCopier.copyProperties(orderDetail, OrderDetailDto.class);
        if (detailDto == null) return null;
        List<OrderProductItemDto> productItemDtos = productItemRepository.findByDetailNo(detailDto.getId());
        detailDto.setProductList(productItemDtos);
        return detailDto;
    }

    /**
     * 根据订单号查询子订单
     * @param orderNo
     * @return
     */
    public List<OrderDetailDto> findByOrderNo(Long orderNo) {
        if (orderNo == null) return null;
        OrderDetailExample example = new OrderDetailExample();
        example.createCriteria().andOrderNoEqualTo(orderNo);
        List<OrderDetail> orderDetails = detailMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(orderDetails)) return null;

        List<OrderDetailDto> orderDetailDtos = WrappedBeanCopier.copyPropertiesOfList(orderDetails, OrderDetailDto.class);
        List<OrderProductItemDto> productItemDtos = null;
        for (OrderDetailDto orderDetailDto : orderDetailDtos) {
            productItemDtos = productItemRepository.findByDetailNo(orderDetailDto.getId());
            if (CollectionUtils.isNotEmpty(productItemDtos)) {
                orderDetailDto.setProductList(productItemDtos);
            }
        }
        return orderDetailDtos;
    }
}
