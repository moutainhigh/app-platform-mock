package com.bill99.mam.platform.remote.dto.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import com.bill99.mam.platform.common.util.PrivacyUtil;
import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;
@Getter
@Setter

public class HatBankCardAuthReq extends RemoteBaseRequest implements Serializable {
    private static final long serialVersionUID = 8795369305473059563L;
    private String uId;
    private String bankAcctId;
    private String mobile;
    private String idCardNumber;
    private String idCardType;
    private String name;
    private String cvv2;
    private String expiredDate;
	@Override
	public String toString() {
		return "HatBankCardAuthReq [uId=" + uId 
				+ ", bankAcctId=" + PrivacyUtil.encryptSensitiveInfo(bankAcctId)
				+ ", mobile=" + PrivacyUtil.encryptSensitiveInfo(mobile) 
				+ ", idCardNumber=" + PrivacyUtil.encryptSensitiveInfo(idCardNumber)
				+ ", idCardType=" + idCardType + ", name=" + name + ", cvv2="
				+ cvv2 + ", expiredDate=" + expiredDate + ", toString()="
				+ super.toString() + "]";
	}
    
}
