package com.bill99.mam.platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.bill99.mam.platform.common.constant.PlatformConst;
import com.bill99.mam.platform.common.enumeration.HatTradeStatusEnum;
import com.bill99.mam.platform.common.enumeration.OrderStatusEnum;
import com.bill99.mam.platform.common.enumeration.RefundStatus;
import com.bill99.mam.platform.common.enumeration.ResultEnum;
import com.bill99.mam.platform.common.util.CommonUtil;
import com.bill99.mam.platform.common.util.DateUtil;
import com.bill99.mam.platform.persistence.dto.OrderDetailDto;
import com.bill99.mam.platform.persistence.dto.OrderProductItemDto;
import com.bill99.mam.platform.persistence.dto.RefundDto;
import com.bill99.mam.platform.persistence.dto.SubRefundDto;
import com.bill99.mam.platform.persistence.repository.OrderDetailRepository;
import com.bill99.mam.platform.persistence.repository.OrderProductItemRepository;
import com.bill99.mam.platform.persistence.repository.OrderRepository;
import com.bill99.mam.platform.persistence.repository.RefundRepository;
import com.bill99.mam.platform.remote.dto.request.RefundReq;
import com.bill99.mam.platform.remote.dto.response.RefundResp;
import com.bill99.mam.platform.remote.service.HatService;
import com.bill99.mam.platform.service.IRefundService;
import com.bill99.mam.platform.service.model.request.SharingData;
import com.bill99.mam.platform.service.model.response.RefundResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class RefundServiceImpl implements IRefundService{
    private static final String MEMO = "退货";

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductItemRepository orderProductItemRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private RefundRepository refundRepository;
    @Autowired
    private HatService hatService;

    @Override
    public List<RefundResponse> sendRefundRequest(List<String> idList) {
        Map<String, OrderProductItemDto> productItems = new HashMap<>();
        Map<Long, Object> orderDetail = new HashMap<>();
        Set<Long> order = new HashSet();
        for (String id:idList) {
            OrderProductItemDto item = orderProductItemRepository.findById(Long.valueOf(id));
            if(Objects.equals(item.getOrderStatus(), OrderStatusEnum.SUCCESS.value())){
                productItems.put(id, item);
                orderDetail.put(item.getOrderDetailNo(), "");
            }
        }
        for (long detailNO:orderDetail.keySet()) {
            OrderDetailDto detail = orderDetailRepository.findById(detailNO);
            orderDetail.put(detailNO, detail);
            if(detail != null && detail.getOrderNo() != null){
                order.add(detail.getOrderNo());
            }
        }

        List<RefundResponse> response = new ArrayList<>();

        if(productItems.isEmpty()){
            RefundResponse res = new RefundResponse();
            res.setRspCode(ResultEnum.FAILED.code());
            res.setRspMsg(ResultEnum.FAILED.message());
            response.add(res);
        }else {
            response = handleRefund (order, orderDetail, productItems);
        }

        return response;
    }

    private List<RefundResponse> handleRefund(Set<Long> order, Map<Long, Object> orderDetail, Map<String, OrderProductItemDto> productItems){
        List<RefundResponse> result = new ArrayList<>();
        //遍历order
        for (Long orderId:order) {
            //遍历detail
            for (long detailId:orderDetail.keySet()) {
                //原始交易订单号
                List<Long> aItemIdList = new ArrayList();    //itemId 列表
                List<Long> detailIdList = new ArrayList<>();  //detailId 列表

                List<SharingData> sharingDataList = new ArrayList<>();  //sharingData 列表
                BigDecimal totalAmount = BigDecimal.ZERO;   //order 退款总金额
                BigDecimal platformAmount = BigDecimal.ZERO;  //平台金额

                List<SubRefundDto> subRefundList = new ArrayList();

                Long subMerchant = null;//平台子商户号
                OrderDetailDto detail = (OrderDetailDto) orderDetail.get(detailId);
                //内部交易号相等 orderNo = orderId
                if (detail != null && Objects.equals(orderId, detail.getOrderNo())){
                    subMerchant = detail.getSubMerchantUid();//平台子商户号
                    BigDecimal refundAmount = BigDecimal.ZERO;  //退款金额
                    List<Long> items = new ArrayList<>();
                    //遍历item
                    for (String itemId:productItems.keySet()) {
                        OrderProductItemDto item = productItems.get(itemId);
                        if(item != null
                                && item.getOrderDetailNo() != null
                                && Objects.equals(detailId, item.getOrderDetailNo())){
                            long num = item.getProductNum();
                            long price = item.getProductPrice();
                            long rate = item.getLedgerRate();

                            BigDecimal n = new BigDecimal(num);
                            BigDecimal p = new BigDecimal(price);
                            BigDecimal r = new BigDecimal(rate).divide(new BigDecimal(100));
                            BigDecimal amount = n.multiply(p);

//                            BigDecimal pAmount = amount.multiply(r);
//                            BigDecimal subAmount = amount.subtract(pAmount);
                            //修改为数据库记录金额
                            BigDecimal pAmount = new BigDecimal(item.getPlatformLedgerAmount());
                            BigDecimal subAmount = new BigDecimal(item.getMerchantLedgerAmount());
                            //退款金额
                            refundAmount = refundAmount.add(subAmount);
                            platformAmount = platformAmount.add(pAmount);
                            totalAmount = totalAmount.add(amount);
                            items.add(item.getId());

                            //构建subRefundDto
                            SubRefundDto subRefundDto = buildSubRefundDto(subMerchant, orderId, detailId, itemId);
                            subRefundList.add(subRefundDto);
                        }
                    }
                    buildSharingData(sharingDataList, subMerchant, refundAmount, items);

                    aItemIdList.addAll(items);
                    detailIdList.add(detailId);
                }

                String platformMerchentCode = PlatformConst.PLATFORM_MERCHENT_CODE;

                //构建平台sharingData
                buildSharingData(sharingDataList, platformMerchentCode, platformAmount, aItemIdList);


                SharingData[] sharingData = toSharingDataArray(sharingDataList);
                //保存
                RefundDto refundDto = saveRefund(orderId, platformMerchentCode, sharingData, totalAmount, MEMO, subRefundList);

                String outTradeNo = String.valueOf(refundDto.getRefundNo());
                String origOutTradeNo = String.valueOf(orderId);
                String subOutTradeNo = String.valueOf(detailId);
                String subMerchantUId = String.valueOf(subMerchant);
                String refundAmount = totalAmount.toString();

                RefundReq request = buildRequest(outTradeNo, origOutTradeNo, subOutTradeNo, subMerchantUId, refundAmount, sharingData);

                RefundResp response = hatService.refund(request);


                RefundResponse refundResponse = postHandle(response, orderId, aItemIdList, detailIdList, refundDto, totalAmount);

                result.add(refundResponse);

            }
        }



        return result;
    }

    private RefundReq buildRequest(String outTradeNo, String origOutTradeNo, String subOutTradeNo,
                                   String subMerchantUID, String rAmount, SharingData[] sData){

        RefundReq request = new RefundReq();
        request.setOutRefundNo(outTradeNo);//退货订单号
        request.setOrigOutTradeNo(origOutTradeNo);//原交易订单号
        request.setSubOutTradeNo(subOutTradeNo);
        request.setSubMerchantUId(subMerchantUID);//平台子商户号
        request.setRefundAmount(rAmount);//退款金额
        request.setSharingData(sData); //分账数据
//        request.setBgUrl(PlatformConst.BG_URL); //通知地址
        request.setMemo("hat demo 退货");  ////备注
        request.setTxnBeginTime(DateUtil.formatHat(new Date())); //退货申请时间

        int isPlatForm = CommonUtil.isPlatformPayFee(Long.valueOf(subMerchantUID));
        request.setIsPlatformSubMerchant(String.valueOf(isPlatForm));
        return request;
    }
    private SubRefundDto buildSubRefundDto(long sUID, long orderId, long detailId, String itemId){
        SubRefundDto sub = new SubRefundDto();
        sub.setSubMerchantUid(sUID);
        sub.setOrigOrderNo(orderId);
        sub.setOrigSubOrderNo(detailId);
        sub.setOrigOrderItemNo(Long.valueOf(itemId));
        sub.setCreateTime(new Date());
        sub.setUpdateTime(new Date());
        return sub;
    }
    private RefundDto saveRefund(long orderNo,String sUID, SharingData[] sharingData, BigDecimal rAmount
            ,String memo, List<SubRefundDto> subList){
        long subUID = Long.valueOf(sUID);

        RefundDto refundDto = new RefundDto();
        refundDto.setOrigOrderNo(orderNo);
        refundDto.setSubMerchantUid(subUID);
        refundDto.setSharingData(JSON.toJSONString(sharingData));
        refundDto.setRefundAmount(rAmount.longValue());
        refundDto.setRefundStatus(Integer.valueOf(RefundStatus.INIT.getCode()));
        refundDto.setPayeeUid(subUID);
        refundDto.setRefundStartTime(new Date());
        refundDto.setMemo(memo);
        refundDto.setCreateTime(new Date());
        refundDto.setUpdateTime(new Date());
        refundDto.setSubRefundDtoList(subList);
        return refundRepository.save(refundDto);
    }
    private SharingData[] toSharingDataArray(List<SharingData> sList){
        int len = sList.size();
        SharingData[] sData = new SharingData[len];
        for (int i = 0; i < len; i++) {
            sData[i] = sList.get(i);
        }
        return sData;
    }

    private void buildSharingData(List<SharingData> sharingDataList, String subMerchan, BigDecimal amount, List<Long> items){
        buildSharingData(sharingDataList, Long.valueOf(subMerchan), amount, items);
    }
    private void buildSharingData(List<SharingData> sharingDataList, long subUID, BigDecimal amount, List<Long> items){
        long applyAmount = amount.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
        if(applyAmount > 0){
            SharingData sData = new SharingData();
            sData.setSharingUId(subUID);
            if(StringUtils.equals(PlatformConst.PLATFORM_MERCHENT_CODE, String.valueOf(subUID))){
                sData.setIsPlatformSharing(1);
            }else {
                sData.setIsPlatformSharing(0);
            }
            sData.setSettlePeriod("T+1");
            sData.setSharingApplyAmount(applyAmount);
            sData.setSharingDesc(JSON.toJSONString(items));
            sharingDataList.add(sData);
        }
    }
    private RefundResponse postHandle(RefundResp res, Long orderId, List<Long> aItemIdList, List<Long> detailIdList, RefundDto refundDto, BigDecimal totalAmount){
        String status = res.getStatus();
        RefundResponse response = new RefundResponse();

        OrderProductItemDto item = new OrderProductItemDto();

        if(status != null &&
                !StringUtils.equals(status, HatTradeStatusEnum.FAILED.getCode())){
            response.setRspCode(ResultEnum.SUCCESS.code());

            item.setOrderStatus(OrderStatusEnum.REFUND_PROCESSIONG.value());
            refundDto.setRefundStatus(Integer.valueOf(RefundStatus.PROCESSING.getCode()));

            //更新detail 表
            updateDetail(detailIdList, totalAmount);
            //更新item表
            for (Long itemNo:aItemIdList) {
                item.setId(itemNo);
                orderProductItemRepository.update(item);
            }
        }else {
            response.setRspCode(ResultEnum.FAILED.code());
            refundDto.setRefundStatus(Integer.valueOf(RefundStatus.FAILED.getCode()));
        }
        response.setRspMsg(res.getRspMsg());

        refundDto.setBillOrderNo(res.getBillOrderNo());
        refundDto.setTradeNo(res.getTradeNo());
        refundDto.setErrorCode(res.getRspCode());
        refundDto.setErrorInfo(res.getRspMsg());
        //更新refund表
        refundRepository.update(refundDto);


        response.setOrderNo(orderId);
        response.setRefundItemList(aItemIdList);
        response.setHatResponse(res);

        return response;
    }

    private void updateDetail(List<Long> detailIdList, BigDecimal totalAmount){
//        OrderDetailDto detail = new OrderDetailDto();
        //仅一条记录
        for (Long detailId:detailIdList) {
            OrderDetailDto detail = orderDetailRepository.findById(detailId);
            if(detail != null){
                if(detail.getRefundAmount() != null){
                    detail.setRefundAmount(detail.getRefundAmount() + totalAmount.longValue());
                }else {
                    detail.setRefundAmount(totalAmount.longValue());
                }
                detail.setOrderStatus(OrderStatusEnum.REFUND.value());
                orderDetailRepository.update(detail);
            }
        }
    }
}
