package com.bill99.mam.platform.remote.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseResponse;

@Getter
@Setter
@ToString(callSuper = true)
public class QuickPaymentPciQryResp extends RemoteBaseResponse {
    private static final long serialVersionUID = -7124402421359228856L;
    private String uId;
    private List<QuickBindCardInfo> bindCardList;
}
