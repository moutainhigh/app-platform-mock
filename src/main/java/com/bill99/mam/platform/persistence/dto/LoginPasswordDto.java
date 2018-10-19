package com.bill99.mam.platform.persistence.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class LoginPasswordDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long customerId;

    private String loginPassword;

    private String operator;

    private Date updateTime;

    private Date createTime;
}
