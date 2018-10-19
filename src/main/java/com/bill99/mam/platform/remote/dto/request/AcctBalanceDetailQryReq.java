package com.bill99.mam.platform.remote.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;

@Getter
@Setter
@ToString(callSuper = true)
public class AcctBalanceDetailQryReq extends RemoteBaseRequest {
    private static final long serialVersionUID = -75126232282995919L;
    private String uId;//平台用户id
    private String accountType;//账户类型
    private String startTime;//查询起始时间
    private String endTime;//查询截止时间
    private String pageNo;//查询页号
    private String pageSize;//查询每页大小
}
