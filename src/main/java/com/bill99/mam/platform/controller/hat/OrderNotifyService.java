package com.bill99.mam.platform.controller.hat;

import com.bill99.mam.platform.controller.hat.modelattribute.TradeResultNotifyReq;
import com.bill99.mam.platform.controller.hat.modelattribute.TradeResultNotifyResp;
import org.springframework.stereotype.Service;

@Service
public class OrderNotifyService extends AbstractNotifyService{


    public TradeResultNotifyResp process(TradeResultNotifyReq request){
        return super.process(request);
    }


}
