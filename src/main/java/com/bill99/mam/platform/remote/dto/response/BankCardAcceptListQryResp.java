package com.bill99.mam.platform.remote.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseResponse;

@Getter
@Setter
@ToString(callSuper = true)
public class BankCardAcceptListQryResp extends RemoteBaseResponse {
    private static final long serialVersionUID = 4611411805935195163L;
    private List<BankAcceptInfo> bankAcceptList;
}
