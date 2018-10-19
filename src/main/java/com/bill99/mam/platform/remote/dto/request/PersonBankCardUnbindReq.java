package com.bill99.mam.platform.remote.dto.request;

import lombok.Getter;
import lombok.Setter;

import com.bill99.mam.platform.common.util.PrivacyUtil;
import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;

@Getter
@Setter
public class PersonBankCardUnbindReq extends RemoteBaseRequest{
    private static final long serialVersionUID = -510836122178997902L;
    private String uId;
    private String memberBankAcctId;
	@Override
	public String toString() {
		return "PersonBankCardUnbindReq [uId=" + uId 
				+ ", memberBankAcctId="+ PrivacyUtil.encryptSensitiveInfo(memberBankAcctId) 
				+ ", toString()=" + super.toString() + "]";
	}
    
}
