package com.bill99.mam.platform.remote.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseResponse;
@Getter
@Setter
@ToString(callSuper = true)
public class EnterpriseBankCardListQryResp extends RemoteBaseResponse {
    private static final long serialVersionUID = 3256676854501336470L;
    private List<BindCardInfo> bindCardList;
}