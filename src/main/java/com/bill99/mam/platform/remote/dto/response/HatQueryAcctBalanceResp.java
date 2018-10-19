package com.bill99.mam.platform.remote.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseResponse;
@Getter
@Setter
@ToString(callSuper = true)
public class HatQueryAcctBalanceResp extends RemoteBaseResponse {
    private static final long serialVersionUID = -7424474048270071333L;
    private List<AccountBalance> accountBalanceList;
}
