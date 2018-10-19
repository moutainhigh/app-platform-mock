package com.bill99.mam.platform.remote.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;

@Getter
@Setter
@ToString(callSuper = true)
public class BankCardAcceptListQryReq extends RemoteBaseRequest {
    private static final long serialVersionUID = -2077616025792648196L;
}
