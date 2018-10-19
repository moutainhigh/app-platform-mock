package com.bill99.mam.platform.remote.dto.request;

import lombok.Getter;
import lombok.Setter;

import com.bill99.mam.platform.common.util.PrivacyUtil;
import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;
@Getter
@Setter
public class BankCardAcceptReq extends RemoteBaseRequest {
    private static final long serialVersionUID = -7122593347683475799L;
    private String bankAcctId;
	@Override
	public String toString() {
		return "BankCardAcceptReq [bankAcctId=" + PrivacyUtil.encryptSensitiveInfo(bankAcctId) + ", toString()="
				+ super.toString() + "]";
	}
}
