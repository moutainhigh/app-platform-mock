package com.bill99.mam.platform.service;

import com.bill99.mam.platform.service.model.request.BankAcceptListQryReq;
import com.bill99.mam.platform.service.model.request.PciQryReq;
import com.bill99.mam.platform.service.model.response.BankAcceptListQryResp;
import com.bill99.mam.platform.service.model.response.PciQryResp;

public interface IBankCardService {
    /**
     * 快捷PCI查询
     * @param pciQryReq
     * @return
     */
    PciQryResp queryPci(PciQryReq pciQryReq);
    /**
    * @Description: 查询支持的绑卡银行列表 
    * @param acceptListQryReq
    * @return BankAcceptListQryResp
    * @throws
     */
    BankAcceptListQryResp queryAcceptList(BankAcceptListQryReq acceptListQryReq);
}
