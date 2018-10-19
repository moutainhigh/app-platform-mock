package com.bill99.mam.platform.persistence.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class GoodsDto {

    private Long id;

    private String name;

    private BigDecimal price;

    private Long customerId;
    private String customerName;

    private Date updateTime;

    private Date createTime;

}
