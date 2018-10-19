package com.bill99.mam.platform.remote.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseResponse;
@Getter
@Setter
@ToString(callSuper = true)
public class PersonBankCarListQryResp extends RemoteBaseResponse {
    private static final long serialVersionUID = 8151095255866177535L;
    private List<BindCardInfo> bindCardList;
}
