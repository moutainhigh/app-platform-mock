package com.bill99.mam.platform.remote.dto.response;

import lombok.Getter;
import lombok.Setter;

import com.bill99.mam.platform.common.util.PrivacyUtil;
import com.bill99.mam.platform.remote.dto.RemoteBaseResponse;
@Getter
@Setter
public class HatQryPersonInfoResp extends RemoteBaseResponse {
    private static final long serialVersionUID = 1134555659458483401L;
    private String uId;
    private String openId;
    private String idCardType;
    private String idCardNumber;
    private String name;
    private String mobile;
    private String email;
	@Override
	public String toString() {
		return "HatQryPersonInfoResp [uId=" + uId + ", openId=" + openId
				+ ", idCardType=" + idCardType + ", idCardNumber="
				+ PrivacyUtil.encryptSensitiveInfo(idCardNumber) + ", name=" + name 
				+ ", mobile=" + PrivacyUtil.encryptSensitiveInfo(mobile)
				+ ", email=" + PrivacyUtil.encryptSensitiveInfo(email) + ", toString()=" + super.toString() + "]";
	}
    
}
