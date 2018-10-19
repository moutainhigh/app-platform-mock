package com.bill99.mam.platform.remote.dto.request;

import lombok.Getter;
import lombok.Setter;

import com.bill99.mam.platform.common.util.PrivacyUtil;
import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;
@Getter
@Setter
public class EnterpriseBankCardBindReq extends RemoteBaseRequest {
    private static final long serialVersionUID = -1208957876516673648L;
    private String uId;
    private String bankId;
    private String bankAcctId;
    private String branchBankName;
    private String province;
    private String city;
    private String name;
	@Override
	public String toString() {
		return "EnterpriseBankCardBindReq [uId=" + uId + ", bankId=" + bankId
				+ ", bankAcctId=" + PrivacyUtil.encryptSensitiveInfo(bankAcctId) + ", branchBankName="
				+ branchBankName + ", province=" + province + ", city=" + city
				+ ", name=" + name + ", toString()=" + super.toString() + "]";
	}
    
}
