package com.bill99.mam.platform.remote.dto.request;

import lombok.Getter;
import lombok.Setter;

import com.bill99.mam.platform.common.util.PrivacyUtil;
import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;

@Getter
@Setter
public class PersonBankCardBindReq extends RemoteBaseRequest {
    private static final long serialVersionUID = -209855965504815669L;
    private String uId;
    private String token;
    private String bankAcctId;
    private String mobile;
    private String idCardNumber;
    private String idCardType;
    private String name;
    private String validCode;
    private String cvv2;
    private String expiredDate;
	@Override
	public String toString() {
		return "PersonBankCardBindReq [uId=" + uId + ", token=" + token
				+ ", bankAcctId=" + PrivacyUtil.encryptSensitiveInfo(bankAcctId)
				+ ", mobile=" + PrivacyUtil.encryptSensitiveInfo(mobile)
				+ ", idCardNumber=" + PrivacyUtil.encryptSensitiveInfo(idCardNumber) 
				+ ", idCardType="
				+ idCardType + ", name=" + name + ", validCode=" + validCode
				+ ", cvv2=" + cvv2 + ", expiredDate=" + expiredDate
				+ ", toString()=" + super.toString() + "]";
	}
}
