package com.bill99.mam.platform.controller.hat;

import com.bill99.mam.platform.common.enumeration.ResultEnum;
import com.bill99.mam.platform.controller.hat.modelattribute.TradeResultNotifyReq;
import com.bill99.mam.platform.controller.hat.modelattribute.TradeResultNotifyResp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NotifyController {
	private static final Logger logger = LoggerFactory.getLogger(NotifyController.class);
    @Autowired
    private ConsumeNotifyService consumeNotifyService;
    @Autowired
    private OrderNotifyService orderNotifyService;
    @Autowired
    private RechargeNotifyService rechargeNotifyService;
    @Autowired
    private RefundNotifyService refundNotifyService;

    /**
     * 交易结果通知。
     * @param request
     * @return
     */
    @RequestMapping("/notifyTradeResult")
    @ResponseBody
    public TradeResultNotifyResp notifyTradeResult(@RequestBody TradeResultNotifyReq request) {
    	logger.info("receive notifyTradeResult,request={}",request);
        TradeResultNotifyResp response = null;

        String type = request.getTradeType();     //交易类型，见tradeType
        switch (type){
            case "10":{ //下单
                response = orderNotifyService.process(request);
                break;
            }
            case "11":{ //消费
                response = consumeNotifyService.process(request);
                break;
            }
            case "12":{ //退货
                response = refundNotifyService.process(request);
                break;
            }
            case "13":{ //充值
                response = rechargeNotifyService.process(request);
                break;
            }
            default:{
                response = buildFailedRes();
            }
        }
        logger.info("notifyTradeResult end:request={},response={}",request, response);
        return response;
    }

    private TradeResultNotifyResp buildFailedRes(){
        TradeResultNotifyResp response = new TradeResultNotifyResp();
        response.setRspCode(ResultEnum.FAILED.code());
        response.setRspMsg(ResultEnum.FAILED.message());
        return response;
    }

}
