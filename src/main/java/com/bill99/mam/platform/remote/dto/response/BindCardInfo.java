package com.bill99.mam.platform.remote.dto.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import com.bill99.mam.platform.common.util.PrivacyUtil;

@Getter
@Setter
public class BindCardInfo implements Serializable {
    private static final long serialVersionUID = -368386632441747090L;
    /**
     * 银行卡主键Id信息
     */
    private String memberBankAcctId;
    /**
     * 银行账户
     */
    private String bankAcctId;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 持卡人姓名
     */
    private String name;
    /**
     * 银行编号
     */
    private String bankId;
    /**
     * 卡类型
     */
    private String cardType;
    /**
     * 银行名
     */
    private String bankName;
    /**
     * 银行卡状态 1已验证，0未验证
     */
    private String status;
    /**
     * 支行名称
     */
    private String branchBankName;
    /**
     * 开户行省份
     */
    private String province;
    /**
     * 开户行城市
     */
    private String city;
    /**
     * 绑卡时间
     * 绑卡时间格式：yyyyMMddHHmmss同一张银行卡被重新绑定后，此时间为第一次绑卡的时间。
     */
    private String bindDateTime;
	@Override
	public String toString() {
		return "BindCardInfo [memberBankAcctId=" + PrivacyUtil.encryptSensitiveInfo(memberBankAcctId)
				+ ", bankAcctId=" + bankAcctId 
				+ ", mobile=" + PrivacyUtil.encryptSensitiveInfo(mobile)
				+ ", name=" + name + ", bankId=" + bankId + ", cardType="
				+ cardType + ", bankName=" + bankName + ", status=" + status
				+ ", branchBankName=" + branchBankName + ", province="
				+ province + ", city=" + city + ", bindDateTime="
				+ bindDateTime + ", toString()=" + super.toString() + "]";
	}
    
    
}
