package com.bill99.mam.platform.service;

import java.util.List;

import com.bill99.mam.platform.persistence.dto.GoodsDto;

public interface IGoodsService {
	
	List<GoodsDto> findAll();
}
