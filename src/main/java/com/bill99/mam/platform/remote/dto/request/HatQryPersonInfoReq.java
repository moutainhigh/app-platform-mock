package com.bill99.mam.platform.remote.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;
@Getter
@Setter
@ToString(callSuper = true)
public class HatQryPersonInfoReq extends RemoteBaseRequest {
    private static final long serialVersionUID = 1297414704861285009L;
    private String uId;
}
