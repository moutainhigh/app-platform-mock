package com.bill99.mam.platform.remote.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;

@Getter
@Setter
@ToString(callSuper = true)
public class PersonAcctNumberCancelReq extends RemoteBaseRequest{
    private static final long serialVersionUID = 5528616042501972625L;
    private String uId;
}

