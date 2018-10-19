package com.bill99.mam.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bill99.mam.platform.common.util.WrappedBeanCopier;
import com.bill99.mam.platform.remote.dto.request.BankCardAcceptListQryReq;
import com.bill99.mam.platform.remote.dto.request.QuickPaymentPciQryReq;
import com.bill99.mam.platform.remote.dto.response.BankCardAcceptListQryResp;
import com.bill99.mam.platform.remote.dto.response.QuickPaymentPciQryResp;
import com.bill99.mam.platform.remote.service.HatService;
import com.bill99.mam.platform.service.IBankCardService;
import com.bill99.mam.platform.service.model.request.BankAcceptListQryReq;
import com.bill99.mam.platform.service.model.request.PciQryReq;
import com.bill99.mam.platform.service.model.response.BankAcceptListQryResp;
import com.bill99.mam.platform.service.model.response.PciQryResp;

@Component
public class BankCardService implements IBankCardService {
    @Autowired
    private HatService hatService;
    /**
     * 查询PCI
     * @param pciQryReq
     * @return
     */
    @Override
    public PciQryResp queryPci(PciQryReq pciQryReq) {
        QuickPaymentPciQryReq qryReq = WrappedBeanCopier.copyProperties(pciQryReq,QuickPaymentPciQryReq.class);
        QuickPaymentPciQryResp pciQryResp = hatService.queryPci(qryReq);
        PciQryResp responsePciQryResp = WrappedBeanCopier.copyProperties(pciQryResp,PciQryResp.class);
        responsePciQryResp.setBindCardList(pciQryResp.getBindCardList());
        return responsePciQryResp;
    }
    /**
     * 查詢支持的銀行列表
     */
	@Override
	public BankAcceptListQryResp queryAcceptList(BankAcceptListQryReq acceptListQryReq) {
		BankCardAcceptListQryReq qryReq = WrappedBeanCopier.copyProperties(acceptListQryReq, BankCardAcceptListQryReq.class);
		BankCardAcceptListQryResp resp = hatService.qryBankCardAcceptList(qryReq);
		BankAcceptListQryResp response = WrappedBeanCopier.copyProperties(resp, BankAcceptListQryResp.class);
		response.setBankAcceptList(resp.getBankAcceptList());
		return response;
	}
}
