package com.bill99.mam.platform.remote.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;

@Getter
@Setter
@ToString(callSuper = true)
public class PersonBankCarListQryReq extends RemoteBaseRequest {
    private static final long serialVersionUID = 7128113000766027436L;
    private String uId;
}
