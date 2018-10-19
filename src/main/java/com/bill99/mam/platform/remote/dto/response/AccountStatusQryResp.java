package com.bill99.mam.platform.remote.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseResponse;
@Getter
@Setter
@ToString(callSuper = true)
public class AccountStatusQryResp extends RemoteBaseResponse {
    private static final long serialVersionUID = -3879033987344223173L;
    private String uId;
    private String[] accountStatus;
}
