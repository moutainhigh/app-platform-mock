package com.bill99.mam.platform.remote.dto.response;

import lombok.Getter;
import lombok.Setter;

import com.bill99.mam.platform.common.util.PrivacyUtil;
import com.bill99.mam.platform.remote.dto.RemoteBaseResponse;

@Getter
@Setter
public class PersonBankCardBindResp extends RemoteBaseResponse {
    private static final long serialVersionUID = 4105525892001833212L;
    private String memberBankAcctId;
	@Override
	public String toString() {
		return "PersonBankCardBindResp [memberBankAcctId=" + 
				PrivacyUtil.encryptSensitiveInfo(memberBankAcctId)
				+ ", toString()=" + super.toString() + "]";
	}
    
    
}
