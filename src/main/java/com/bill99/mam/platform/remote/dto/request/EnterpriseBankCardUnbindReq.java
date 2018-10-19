package com.bill99.mam.platform.remote.dto.request;

import lombok.Getter;
import lombok.Setter;

import com.bill99.mam.platform.common.util.PrivacyUtil;
import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;

@Getter
@Setter
public class EnterpriseBankCardUnbindReq extends RemoteBaseRequest{
    private static final long serialVersionUID = 6012577933759928005L;
    private String uId;
    private String memberBankAcctId;
	@Override
	public String toString() {
		return "EnterpriseBankCardUnbindReq [uId=" + uId
				+ ", memberBankAcctId=" + PrivacyUtil.encryptSensitiveInfo(memberBankAcctId) + ", toString()="
				+ super.toString() + "]";
	}
    
}
