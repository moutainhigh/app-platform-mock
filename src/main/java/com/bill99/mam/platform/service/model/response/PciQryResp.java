package com.bill99.mam.platform.service.model.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.response.QuickBindCardInfo;
import com.bill99.mam.platform.service.model.BaseResponse;

@Getter
@Setter
@ToString(callSuper = true)
public class PciQryResp extends BaseResponse {
    private static final long serialVersionUID = -7124402421359228856L;
    private List<QuickBindCardInfo> bindCardList;
}
