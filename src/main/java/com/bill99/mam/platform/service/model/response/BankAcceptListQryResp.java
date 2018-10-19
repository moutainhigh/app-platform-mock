package com.bill99.mam.platform.service.model.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.response.BankAcceptInfo;
import com.bill99.mam.platform.service.model.BaseResponse;

@Getter
@Setter
@ToString(callSuper = true)
public class BankAcceptListQryResp extends BaseResponse {
    private static final long serialVersionUID = 4611411805935195163L;
    private List<BankAcceptInfo> bankAcceptList;
}