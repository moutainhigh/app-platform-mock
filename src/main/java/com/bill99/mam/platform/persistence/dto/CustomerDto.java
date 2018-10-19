package com.bill99.mam.platform.persistence.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class CustomerDto implements Serializable{
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long platformId;

    private Integer customerType;

    private String loginId;

    private Integer loginType;

    private String name;

    private String idcardNumber;

    private Integer idcardType;

    private Integer status;

    private Date updateTime;

    private Date createTime;

}
