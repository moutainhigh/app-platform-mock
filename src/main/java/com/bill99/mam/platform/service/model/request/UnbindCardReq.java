package com.bill99.mam.platform.service.model.request;

import com.bill99.mam.platform.service.model.BaseRequest;
import com.google.common.base.MoreObjects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnbindCardReq  extends BaseRequest {
    private static final long serialVersionUID = 2156869161157996963L;
    /* 平台用户id */
    private String uId;
    /* 银行代码 */
    private String bankId;
    /* 银行短卡号*/
    private String shortBankAcctId;
    /* 支付方式 */
    private String payMode;
    /* 绑定类型 */
    private String bindType;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("uId", uId)
                .add("bankId", bankId)
                .add("shortBankAcctId", shortBankAcctId)
                .add("payMode", payMode)
                .add("bindType", bindType)
                .toString();
    }
}
