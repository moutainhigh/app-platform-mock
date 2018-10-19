package com.bill99.mam.platform.controller.web.adapter.personal;

import com.bill99.mam.platform.common.enumeration.PayModeEnum;
import com.bill99.mam.platform.controller.web.modelattribute.QuickPaymentDTO;
import com.bill99.mam.platform.service.IOrderService;
import com.bill99.mam.platform.service.model.request.QuickPayment;
import com.bill99.mam.platform.service.model.response.QuickPaymentResp;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class QuickPayService {
    private static final String QUICK_PAY = "12";
    private static final String ONE_KEY = "15";
    @Autowired
    private IOrderService orderService;
    public QuickPaymentResp quickPay(QuickPaymentDTO quickPaymentDTO){
        QuickPayment quickPayment = new QuickPayment();
        quickPayment.setFunctionCode("10");
        BeanUtils.copyProperties(quickPaymentDTO, quickPayment);
        if(StringUtils.equals(quickPaymentDTO.getPayMode(), QUICK_PAY)){
            quickPayment.setPayMode(PayModeEnum.QUICK_PAY.value());
        }if(StringUtils.equals(quickPaymentDTO.getPayMode(), ONE_KEY)){
            quickPayment.setPayMode(PayModeEnum.ONE_KEY.value());
        }

        return orderService.quickPay(quickPayment);
    }
}
