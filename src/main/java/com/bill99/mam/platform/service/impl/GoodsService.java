package com.bill99.mam.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bill99.mam.platform.persistence.dto.GoodsDto;
import com.bill99.mam.platform.persistence.repository.GoodsRepository;
import com.bill99.mam.platform.service.IGoodsService;

@Component
public class GoodsService implements IGoodsService{
	
    @Autowired
    private GoodsRepository goodsRepository;
    
	@Override
	public List<GoodsDto> findAll() {
		List<GoodsDto> goods = goodsRepository.queryAll();
		return goods;
	}
}
