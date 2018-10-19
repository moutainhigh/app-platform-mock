package com.bill99.mam.platform.remote.dto.response;

import lombok.Getter;
import lombok.Setter;

import com.bill99.mam.platform.common.util.PrivacyUtil;
import com.bill99.mam.platform.remote.dto.RemoteBaseResponse;
@Getter
@Setter
public class EnterpriseBankCardBindResp extends RemoteBaseResponse {
    private static final long serialVersionUID = 5167950812328207669L;
    private String memberBankAcctId;
	@Override
	public String toString() {
		return "EnterpriseBankCardBindResp [memberBankAcctId="
				+ PrivacyUtil.encryptSensitiveInfo(memberBankAcctId) 
				+ ", toString()=" + super.toString() + "]";
	}
    
}
