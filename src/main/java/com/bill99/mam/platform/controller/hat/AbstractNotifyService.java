package com.bill99.mam.platform.controller.hat;

import com.bill99.mam.platform.common.constant.PlatformConst;
import com.bill99.mam.platform.common.enumeration.HatTradeStatusEnum;
import com.bill99.mam.platform.common.enumeration.ResultEnum;
import com.bill99.mam.platform.controller.hat.modelattribute.TradeResultNotifyReq;
import com.bill99.mam.platform.controller.hat.modelattribute.TradeResultNotifyResp;
import com.bill99.mam.platform.persistence.dto.OrderDetailDto;
import com.bill99.mam.platform.persistence.dto.OrderDto;
import com.bill99.mam.platform.persistence.dto.OrderProductItemDto;
import com.bill99.mam.platform.persistence.repository.OrderDetailRepository;
import com.bill99.mam.platform.persistence.repository.OrderProductItemRepository;
import com.bill99.mam.platform.persistence.repository.OrderRepository;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AbstractNotifyService {
    private static final Logger logger = LoggerFactory.getLogger(AbstractNotifyService.class);
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderProductItemRepository orderProductItemRepository;

    public TradeResultNotifyResp process(TradeResultNotifyReq request) {
//        logger.info("Accept notify:request={}", request);
        TradeResultNotifyResp response = buildSuccessResponse();

        String platformCode = request.getPlatformCode();
        String outTradeNo = request.getOutTradeNo();
        String billOrderNo = request.getBillOrderNo(); //
        String tradeType = request.getTradeType();
        String orderAmount = request.getOrderAmount();
        String amount = request.getAmount();
        String tradeNo = request.getTradeNo();  //
        String txnEndTime = request.getTxnEndTime(); //
        String status = request.getStatus();
        String rspCode = request.getRspCode();
        String rspMsg = request.getRspMsg();


        long id = Long.valueOf(outTradeNo);
        OrderDto order = orderRepository.findById(id);

        if (StringUtils.equals(platformCode, PlatformConst.PLATFORM_MERCHENT_CODE) && order != null) {
            if (StringUtils.equals(status, HatTradeStatusEnum.SUCCESS.getCode())) {
                order.setPayOrderSuccess();
            } else if (StringUtils.equals(status, HatTradeStatusEnum.FAILED.getCode())) {
                order.setPayOrderFailed();
            } else {
                response = buildFailedResponse();
                logger.info("order status is PROCESSING do nothing!" +
                        "hatStatus={},outTradeNo={},request={}", status, outTradeNo,request);
            }
            orderRepository.update(order);
        }else {
            response = buildFailedResponse();
            logger.info("ignored request order record not exist or platformCode error:" +
                            "platformCode={},PLATFORM_MERCHENT_CODE={}",platformCode,PlatformConst.PLATFORM_MERCHENT_CODE);
        }

        return response;
    }

    public void getAffectRecords(Long orderId, List<Long> detailList, List<Long> itemList){
        List<OrderDetailDto> detailDtoList = orderDetailRepository.findByOrderNo(orderId);
        if(detailDtoList != null && !detailDtoList.isEmpty()){
            for (OrderDetailDto detail:detailDtoList) {
                Long detailId = detail.getId();
                if(detailId != null){
                    detailList.add(detailId);
                    List<OrderProductItemDto> itemDtoList = orderProductItemRepository.findByDetailNo(detailId);
                    if(itemDtoList != null && !itemDtoList.isEmpty()){
                        for (OrderProductItemDto item:itemDtoList) {
                            Long itemId = item.getId();
                            if (itemId != null){
                                itemList.add(itemId);
                            }
                        }
                    }
                }
            }
        }
    }

    public TradeResultNotifyResp buildSuccessResponse() {
        TradeResultNotifyResp response = new TradeResultNotifyResp();
        response.setRspCode(ResultEnum.SUCCESS.code());
        response.setRspMsg(ResultEnum.SUCCESS.message());
        return response;
    }
    public TradeResultNotifyResp buildFailedResponse() {
        TradeResultNotifyResp response = new TradeResultNotifyResp();
        response.setRspCode(ResultEnum.FAILED.code());
        response.setRspMsg(ResultEnum.FAILED.message());
        return response;
    }
}
