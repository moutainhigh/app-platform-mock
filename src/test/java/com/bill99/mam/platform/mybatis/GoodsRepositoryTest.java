package com.bill99.mam.platform.mybatis;

import com.bill99.mam.platform.persistence.dto.GoodsDto;
import com.bill99.mam.platform.persistence.repository.GoodsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsRepositoryTest {
    @Autowired
    private GoodsRepository goodsRepository;


    @Test
    public void testQuery(){
        List<GoodsDto> goodsDtoList = goodsRepository.query(Long.valueOf(1));
        System.out.println(goodsDtoList.size());
        System.out.println(goodsDtoList);
    }
}
