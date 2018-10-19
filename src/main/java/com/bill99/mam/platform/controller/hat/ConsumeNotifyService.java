package com.bill99.mam.platform.controller.hat;

import com.bill99.mam.platform.controller.hat.modelattribute.TradeResultNotifyReq;
import com.bill99.mam.platform.controller.hat.modelattribute.TradeResultNotifyResp;
import org.springframework.stereotype.Service;

@Service
public class ConsumeNotifyService extends AbstractNotifyService{

    public TradeResultNotifyResp process(TradeResultNotifyReq notifyReq){

        return super.process(notifyReq);
    }
}
