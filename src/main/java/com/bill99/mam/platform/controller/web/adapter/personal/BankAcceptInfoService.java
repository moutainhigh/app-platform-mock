package com.bill99.mam.platform.controller.web.adapter.personal;

import com.bill99.mam.platform.common.enumeration.CardTypeEnum;
import com.bill99.mam.platform.controller.web.modelattribute.BankAcceptInfoDTO;
import com.bill99.mam.platform.remote.dto.response.BankAcceptInfo;
import com.bill99.mam.platform.service.IBankCardService;
import com.bill99.mam.platform.service.model.request.BankAcceptListQryReq;
import com.bill99.mam.platform.service.model.response.BankAcceptListQryResp;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankAcceptInfoService {
    @Autowired
    private IBankCardService bankCardService;

    public List<BankAcceptInfoDTO> queryAcceptList(){
        BankAcceptListQryResp resp = bankCardService.queryAcceptList(new BankAcceptListQryReq());

        ArrayList<BankAcceptInfoDTO> list = new ArrayList<>();
        if(resp != null){
            List<BankAcceptInfo> bankAcceptInfoList = resp.getBankAcceptList();
            for (BankAcceptInfo acceptInfo: bankAcceptInfoList) {
                BankAcceptInfoDTO dto = new BankAcceptInfoDTO();
                BeanUtils.copyProperties(acceptInfo, dto);
                dto.setCardTypeName(CardTypeEnum.name(acceptInfo.getCardType()));
                list.add(dto);
            }
        }

        return list;
    }
}
