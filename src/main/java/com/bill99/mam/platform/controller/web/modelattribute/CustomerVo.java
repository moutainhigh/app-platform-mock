package com.bill99.mam.platform.controller.web.modelattribute;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bill99.mam.platform.common.enumeration.CustomerType;
import com.bill99.mam.platform.common.util.PrivacyUtil;

import lombok.Data;
@Data
public class CustomerVo implements Serializable{
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
    
    public String getIdcardNumber() {
    	if (CustomerType.isPersonal(this.customerType)) {
    		return PrivacyUtil.encryptIdCard(idcardNumber);
    	}
    	return this.idcardNumber;
    }
    
    public String getLoginId() {
    	if (CustomerType.isPersonal(this.customerType)) {
    		return PrivacyUtil.encryptPhoneNo(loginId);
    	}
    	return this.loginId;
    }
    
    public String getUpdateTimeFormat() {
    	return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.updateTime);
    }
    
    public String getCreateTimeFormat() {
    	return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.createTime);
    }
}
