package com.bill99.mam.platform.remote.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;

@Getter
@Setter
@ToString(callSuper = true)
public class AccountStatusModifyReq extends RemoteBaseRequest {
    private static final long serialVersionUID = 2255461330352342722L;
    private String uId;
    private String accountType;
    private String actionType;
    private String memo;
}
