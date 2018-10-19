package com.bill99.mam.platform.persistence.repository;

import com.bill99.mam.platform.common.util.WrappedBeanCopier;
import com.bill99.mam.platform.persistence.dao.RefundMapper;
import com.bill99.mam.platform.persistence.dto.OrderDto;
import com.bill99.mam.platform.persistence.dto.RefundDto;
import com.bill99.mam.platform.persistence.dto.SubRefundDto;
import com.bill99.mam.platform.persistence.entity.Refund;
import com.bill99.mam.platform.persistence.entity.RefundExample;
import com.google.common.base.Preconditions;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.bill99.mam.platform.common.util.Validator.checkNotNull;

@Component
public class RefundRepository {
    @Autowired
    private RefundMapper refundMapper;
    @Autowired
    private SubRefundRepository subRefundRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Transactional
    public RefundDto save(RefundDto refundDto) {
        // TODO: 2018/3/28 参数校验
        checkNotNull(refundDto,"refundDto is null");
        checkNotNull(refundDto.getOrigOrderNo(),"refundDto OrigOrderNo is null");
        Long origOrderNo = refundDto.getOrigOrderNo();
        OrderDto orderDto = orderRepository.findById(origOrderNo);
        Preconditions.checkArgument(orderDto != null);

        List<SubRefundDto> subRefundDtoList = refundDto.getSubRefundDtoList();
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(subRefundDtoList));
        // TODO: 2018/3/31 校验退货子订单是否合法

        //保存退货订单
        Refund refund = WrappedBeanCopier.copyProperties(refundDto, Refund.class);
        refund.setCreateTime(new Date());
        refund.setUpdateTime(new Date());
        refund.setRefundStartTime(new Date());
        refundMapper.insert(refund);
//        refund.setRefundNo(CommonUtil.random(refund.getRefundNo()));
//        refundMapper.updateByPrimaryKeySelective(refund);
        refundDto = WrappedBeanCopier.copyProperties(refund, RefundDto.class);
        //保存退货子订单
        Long refundNo = refundDto.getRefundNo();
        if (CollectionUtils.isNotEmpty(subRefundDtoList)) {
            List<SubRefundDto> subRefundDtos = new ArrayList<>();
            for (SubRefundDto subRefundDto : subRefundDtoList) {
                subRefundDto.setRefundNo(refundNo);
                subRefundDtos.add(subRefundRepository.save(subRefundDto));
            }
            refundDto.setSubRefundDtoList(subRefundDtos);
        }
        return refundDto;
    }

    public RefundDto findByRefundNo(Long refundNo) {
        if (refundNo == null) return null;
        Refund refund = refundMapper.selectByPrimaryKey(refundNo);
        if (refund == null) return null;
        RefundDto refundDto = WrappedBeanCopier.copyProperties(refund, RefundDto.class);
        refundDto.setSubRefundDtoList(subRefundRepository.queryByRefundNo(refundNo));
        return refundDto;
    }

    /**
     * 根据原交易订单号查询退货订单
     *
     * @param origOrderNo
     * @return
     */
    public List<RefundDto> findByOrigOrderNo(Long origOrderNo) {
        if (origOrderNo == null) return null;
        RefundExample example = new RefundExample();
        example.createCriteria().andOrigOrderNoEqualTo(origOrderNo);
        List<Refund> refundList = refundMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(refundList)) return null;
        List<RefundDto> refundDtos = WrappedBeanCopier.copyPropertiesOfList(refundList, RefundDto.class);
        for (RefundDto refundDto : refundDtos) {
            refundDto.setSubRefundDtoList(subRefundRepository.queryByRefundNo(refundDto.getRefundNo()));
        }
        return refundDtos;
    }
    public RefundDto findByOrigOrderNoAndTradeNo(Long origOrderNo, String tradeNo) {
        if (origOrderNo == null) return null;
        RefundExample example = new RefundExample();
        example.createCriteria().andOrigOrderNoEqualTo(origOrderNo).andTradeNoEqualTo(tradeNo);
        List<Refund> refundList = refundMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(refundList)) return null;
        List<RefundDto> refundDtos = WrappedBeanCopier.copyPropertiesOfList(refundList, RefundDto.class);
        for (RefundDto refundDto : refundDtos) {
            refundDto.setSubRefundDtoList(subRefundRepository.queryByRefundNo(refundDto.getRefundNo()));
        }
        RefundDto dto = null;
        if(refundDtos != null && !refundDtos.isEmpty()){
            dto = refundDtos.get(0);
        }
        return dto;
    }
    public RefundDto update(RefundDto refundDto) {
        checkNotNull(refundDto,"refundDto is null");
        refundDto.setUpdateTime(new Date());
        List<SubRefundDto> subRefundDtoList = refundDto.getSubRefundDtoList();
        Refund refund = WrappedBeanCopier.copyProperties(refundDto, Refund.class);
        refundMapper.updateByPrimaryKeySelective(refund);
        refundDto = WrappedBeanCopier.copyProperties(refund,RefundDto.class);
        if (CollectionUtils.isNotEmpty(subRefundDtoList)) {
            List<SubRefundDto> subRefundDtos = new ArrayList<>();
            for (SubRefundDto subRefundDto : subRefundDtoList) {
                subRefundDtos.add(subRefundRepository.update(subRefundDto));
            }
            refundDto.setSubRefundDtoList(subRefundDtos);
        }

        return refundDto;
    }
}
