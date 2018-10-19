package com.bill99.mam.platform.controller.hat;

import com.bill99.mam.platform.common.enumeration.HatTradeStatusEnum;
import com.bill99.mam.platform.common.enumeration.OrderStatusEnum;
import com.bill99.mam.platform.common.enumeration.RefundStatus;
import com.bill99.mam.platform.controller.hat.modelattribute.TradeResultNotifyReq;
import com.bill99.mam.platform.controller.hat.modelattribute.TradeResultNotifyResp;
import com.bill99.mam.platform.persistence.dto.OrderProductItemDto;
import com.bill99.mam.platform.persistence.dto.RefundDto;
import com.bill99.mam.platform.persistence.dto.SubRefundDto;
import com.bill99.mam.platform.persistence.repository.OrderProductItemRepository;
import com.bill99.mam.platform.persistence.repository.RefundRepository;
import com.bill99.mam.platform.persistence.repository.SubRefundRepository;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RefundNotifyService extends AbstractNotifyService{
    private static final Logger logger = LoggerFactory.getLogger(OrderNotifyService.class);
    @Autowired
    private RefundRepository refundRepository;
    @Autowired
    private SubRefundRepository subRefundRepository;
    @Autowired
    private OrderProductItemRepository orderProductItemRepository;
    public TradeResultNotifyResp process(TradeResultNotifyReq request){
        logger.info("Accept notify:request={}", request);
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

        Long origOutTradeNo = Long.valueOf(outTradeNo);
        RefundDto refundDto  = refundRepository.findByOrigOrderNoAndTradeNo(origOutTradeNo, tradeNo);
//        RefundDto refundDto = refundRepository.findByRefundNo(refundId);
        if(refundDto != null){
            Long refundId = refundDto.getRefundNo();
            Integer refundStatus = refundDto.getRefundStatus();
            Integer processing = Integer.valueOf(RefundStatus.PROCESSING.getCode());
            Integer success = Integer.valueOf(RefundStatus.SUCCESS.getCode());
            Integer failed = Integer.valueOf(RefundStatus.FAILED.getCode());

            if(refundDto != null && Objects.equals(refundStatus, processing)){
                if (StringUtils.equals(status, HatTradeStatusEnum.SUCCESS.getCode())) {
                    refundDto.setRefundStatus(success);
                    refundDto.setErrorCode(rspCode);
                    refundDto.setErrorInfo(rspMsg);
                    updateItem(refundId, OrderStatusEnum.REFUND_SUCCESS);
                } else if (StringUtils.equals(status, HatTradeStatusEnum.FAILED.getCode())) {
                    refundDto.setRefundStatus(failed);
                    refundDto.setErrorCode(rspCode);
                    refundDto.setErrorInfo(rspMsg);
                    updateItem(refundId, OrderStatusEnum.SUCCESS);//还原状态为成功
                } else {
                    response = buildFailedResponse();
                    logger.info("order status is PROCESSING do nothing!hatStatus={},outTradeNo={}", status, outTradeNo);
                }
                refundRepository.update(refundDto);
            }
        }else {
            response = buildFailedResponse();
        }
        return response;
    }

    private void updateItem(Long refundId, OrderStatusEnum status){
        List<SubRefundDto> subRefundDtoList = subRefundRepository.queryByRefundNo(refundId);
        for (SubRefundDto subRefund:subRefundDtoList) {
            if(subRefund != null){
                Long itemNo = subRefund.getOrigOrderItemNo();
                OrderProductItemDto itemDto = orderProductItemRepository.findById(itemNo);
                if(itemDto != null){
                    itemDto.setOrderStatus(status.value());
                    orderProductItemRepository.update(itemDto);
                }
            }
        }
    }
}
