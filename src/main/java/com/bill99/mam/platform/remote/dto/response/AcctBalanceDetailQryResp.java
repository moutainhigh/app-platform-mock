package com.bill99.mam.platform.remote.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseResponse;
@Getter
@Setter
@ToString(callSuper = true)
public class AcctBalanceDetailQryResp extends RemoteBaseResponse {
    private static final long serialVersionUID = 8260598141046100250L;
    private String pageNo;//查询页号
    private String pageSize;//查询每页大小
    private String recordCount;//记录数
    private List<AccountBalanceItem> balanceItemList;//余额明细列表
}
