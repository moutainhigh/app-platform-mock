package com.bill99.mam.platform.controller.web.adapter.personal;

import com.bill99.mam.platform.common.enumeration.*;
import com.bill99.mam.platform.controller.web.modelattribute.QuickBindCardInfoDTO;
import com.bill99.mam.platform.remote.dto.response.QuickBindCardInfo;
import com.bill99.mam.platform.service.IBankCardService;
import com.bill99.mam.platform.service.model.request.PciQryReq;
import com.bill99.mam.platform.service.model.response.PciQryResp;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class QueryPciService {
    private static final String ENTERPRISE = "0"; //0：商户绑定
    private static final String PERSONAL = "1"; //1：成员绑定
    private static final String CUSTOMER_PERSONAL = String.valueOf(CustomerType.PERSONAL.type()); //1:个人会员
    private static final String PAY_MODE_QUICK_PAY = "12"; //12：快捷支付 15：一键支付
    private static final String PAY_MODE_ONE_KEY = "15"; //12：快捷支付 15：一键支付


    @Autowired
    private IBankCardService bankCardService;

    /**
     * 快捷支付PCI查询
     * @param uId
     * @param customerType
     * @return
     */
    public ArrayList<QuickBindCardInfoDTO> queryQuickPay(String uId, String customerType){
        return query(uId, customerType, PAY_MODE_QUICK_PAY);
    }

    /**
     * 一键支付PCI查询
     * @param uId
     * @param customerType
     * @return
     */
    public ArrayList<QuickBindCardInfoDTO> queryOneKey(String uId, String customerType){
        return query(uId, customerType, PAY_MODE_ONE_KEY);
    }

    public ArrayList<QuickBindCardInfoDTO> query(String uId, String customerType, String payMode){
        PciQryReq pciQryReq = new PciQryReq();
        pciQryReq.setUId(uId);
//        String bindType = customerType2BindType(customerType);
        String bindType = "0";
        pciQryReq.setBindType(bindType);
        pciQryReq.setPayMode(payMode);

        PciQryResp pciQryResp = bankCardService.queryPci(pciQryReq);

        ArrayList<QuickBindCardInfoDTO> list = new ArrayList<>();
        if(pciQryResp != null && pciQryResp.getBindCardList() != null){
            for (QuickBindCardInfo cardInfo: pciQryResp.getBindCardList()) {
                list.add(transfer2VO(cardInfo, bindType));
            }
        }
//        list.add(mock(bindType)); //mock 测试
        return list;
    }

    private QuickBindCardInfoDTO transfer2VO(QuickBindCardInfo cardInfo, String bindType){
        QuickBindCardInfoDTO cardInfoVO = new QuickBindCardInfoDTO();
        BeanUtils.copyProperties(cardInfo, cardInfoVO);

        cardInfoVO.setIdCardTypeName(IdCardTypeEnum.desc(cardInfo.getIdCardType()));
        cardInfoVO.setCardTypeName(CardTypeEnum.name(cardInfo.getCardType()));
        if(StringUtils.equals(bindType, ENTERPRISE)){
            cardInfoVO.setBankIdName(EnterpriseBankEnum.name(cardInfo.getBankId()));
        }else {
            cardInfoVO.setBankIdName(PersonalBankEnum.name(cardInfo.getBankId()));
        }

        return cardInfoVO;
    }

    private String customerType2BindType(String customerType){
        String bindType = ENTERPRISE;
        if(StringUtils.equals(CUSTOMER_PERSONAL, customerType)){
            bindType = PERSONAL;
        }
        return bindType;
    }

    /**
     * 测试mock
     * @param bindType
     * @return
     */
    private QuickBindCardInfoDTO mock(String bindType){
        QuickBindCardInfo info = new QuickBindCardInfo();
        info.setName("name");
        info.setIdCardType("101");
        info.setIdCardNumber("1223472");
        info.setShortBankAcctId("124");
        info.setMobile("18739910086");
        info.setBankId("ZKCB");
        info.setCardType("0001");
        info.setCreateTime("sss");
        info.setUpdateTime("updatetime");

        return transfer2VO(info, bindType);
    }
}
