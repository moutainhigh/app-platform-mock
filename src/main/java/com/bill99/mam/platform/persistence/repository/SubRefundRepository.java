package com.bill99.mam.platform.persistence.repository;

import static com.bill99.mam.platform.common.util.Validator.checkNotNull;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bill99.mam.platform.common.util.WrappedBeanCopier;
import com.bill99.mam.platform.persistence.dao.SubRefundMapper;
import com.bill99.mam.platform.persistence.dto.SubRefundDto;
import com.bill99.mam.platform.persistence.entity.SubRefund;
import com.bill99.mam.platform.persistence.entity.SubRefundExample;

@Component
public class SubRefundRepository {
    @Autowired
    private SubRefundMapper subRefundMapper;

    public SubRefundDto save(SubRefundDto subRefundDto){
        // TODO: 2018/3/28  参数校验
        checkNotNull(subRefundDto,"subRefundDto is null");
//        List<SubRefundDto> subRefundDtoList = null;
//        subRefundDtoList = queryByOrigOrderNo(
//                subRefundDto.getOrigOrderNo(),
//                subRefundDto.getOrigSubOrderNo(),
//                subRefundDto.getOrigOrderItemNo());

//        Preconditions.checkArgument(CollectionUtils.isEmpty(subRefundDtoList));
        subRefundDto.setUpdateTime(new Date());
        subRefundDto.setCreateTime(new Date());
        SubRefund subRefund = WrappedBeanCopier.copyProperties(subRefundDto,SubRefund.class);
        subRefundMapper.insert(subRefund);
        return WrappedBeanCopier.copyProperties(subRefund,SubRefundDto.class);
    }

    public SubRefundDto update(SubRefundDto subRefundDto){
        checkNotNull(subRefundDto,"subRefundDto is null");
        subRefundDto.setUpdateTime(new Date());
        SubRefund subRefund = WrappedBeanCopier.copyProperties(subRefundDto,SubRefund.class);
        subRefundMapper.updateByPrimaryKeySelective(subRefund);
        return WrappedBeanCopier.copyProperties(subRefund,SubRefundDto.class);
    }

    /**
     * 根据退货订单号查询退货子订单列表
     * @param refundNo
     * @return
     */
    public List<SubRefundDto> queryByRefundNo(Long refundNo){
        if (refundNo == null) return null;
        SubRefundExample example = new SubRefundExample();
        example.createCriteria().andRefundNoEqualTo(refundNo);
        List<SubRefund> subRefundList = subRefundMapper.selectByExample(example);
        return WrappedBeanCopier.copyPropertiesOfList(subRefundList,SubRefundDto.class);
    }

    /**
     * 根据原订单号、子订单号及商品订单号查询退货子订单信息
     * @param origOrderNo
     * @param origSubOrderNo
     * @param OrigOrderItemNo
     * @return
     */
    public List<SubRefundDto> queryByOrigOrderNo(Long origOrderNo,Long origSubOrderNo,Long OrigOrderItemNo){
    	checkNotNull(origOrderNo,"origOrderNo is null");
        SubRefundExample example = new SubRefundExample();
        SubRefundExample.Criteria criteria = example.createCriteria();
        criteria.andOrigOrderNoEqualTo(origOrderNo);
        if (origSubOrderNo != null){
            criteria.andOrigSubOrderNoEqualTo(origSubOrderNo);
        }
        if (OrigOrderItemNo != null){
            criteria.andOrigOrderItemNoEqualTo(OrigOrderItemNo);
        }
        List<SubRefund> subRefundList = subRefundMapper.selectByExample(example);
        return WrappedBeanCopier.copyPropertiesOfList(subRefundList,SubRefundDto.class);
    }

}
