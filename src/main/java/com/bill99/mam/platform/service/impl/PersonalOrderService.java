package com.bill99.mam.platform.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bill99.mam.platform.persistence.dto.GoodsDto;
import com.bill99.mam.platform.persistence.repository.GoodsRepository;
import com.bill99.mam.platform.service.IPersonalOrderService;

@Component
public class PersonalOrderService implements IPersonalOrderService{

	@Autowired
	private GoodsRepository goodsRepository;
	
	@Override
	public Map<String, Object> confirm(String[] details) {
		Map<String, Object> map = new HashMap<String, Object>();
		//商品总数目
		int count = 0;
		//商品总类目
		int typeCount = 0;
		//订单金额
		BigDecimal orderAmount = NumberUtils.createBigDecimal("0");
		//产品id,产品名称,商家id,商家名称,单价,数量
		for (String detail : details) {
			String[] split = detail.split(",");
			GoodsDto goodsDto = goodsRepository.queryById(new Long(split[0]));
			int goodNum = new Integer(split[5]);
			count += goodNum;
			typeCount++;
			orderAmount = orderAmount.add(goodsDto.getPrice().multiply(new BigDecimal(goodNum)));
		}
		map.put("count", count);
		map.put("typeCount", typeCount);
		map.put("orderAmount", orderAmount);
		return map;
	}
	
}
