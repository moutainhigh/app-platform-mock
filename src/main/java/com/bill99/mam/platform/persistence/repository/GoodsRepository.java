package com.bill99.mam.platform.persistence.repository;

import com.bill99.mam.platform.common.util.WrappedBeanCopier;
import com.bill99.mam.platform.persistence.dao.GoodsMapper;
import com.bill99.mam.platform.persistence.dto.GoodsDto;
import com.bill99.mam.platform.persistence.entity.Goods;
import com.bill99.mam.platform.persistence.entity.GoodsExample;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoodsRepository {
    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 根据客户号查询商品
     * 如果没有传客户号，则查询全部记录
     * @param customerId
     * @return
     */
    public List<GoodsDto> query(Long customerId) {
        if (customerId == null) return null;
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        if (customerId != null) {
            criteria.andCustomerIdEqualTo(customerId);
        }
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
        return WrappedBeanCopier.copyPropertiesOfList(goodsList,GoodsDto.class);
    }
    
    
    public GoodsDto queryById(Long id) {
        if (id == null) return null;
    	GoodsExample goodsExample = new GoodsExample();
    	GoodsExample.Criteria criteria = goodsExample.createCriteria();
    	criteria.andIdEqualTo(id);
    	List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
    	if (CollectionUtils.isEmpty(goodsList)) return null;
    	return WrappedBeanCopier.copyProperties(goodsList.get(0), GoodsDto.class);
    }

    public List<GoodsDto> queryAll() {
    	
    	GoodsExample goodsExample = new GoodsExample();
    	List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
    	
    	return WrappedBeanCopier.copyPropertiesOfList(goodsList,GoodsDto.class);
    }
}
