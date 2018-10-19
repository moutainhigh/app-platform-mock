package com.bill99.mam.platform.service;

import com.bill99.mam.platform.service.model.response.RefundResponse;

import java.util.List;

public interface IRefundService {
    public List<RefundResponse> sendRefundRequest(List<String> idList);
}
