package com.bill99.mam.platform.service.support;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import com.bill99.mam.platform.common.constant.PlatformConst;
import com.bill99.mam.platform.common.enumeration.FunctionCodeEnum;
import com.bill99.mam.platform.common.util.CommonUtil;
import com.bill99.mam.platform.common.util.DateUtil;
import com.bill99.mam.platform.persistence.dto.OrderDetailDto;
import com.bill99.mam.platform.persistence.dto.OrderDto;
import com.bill99.mam.platform.persistence.dto.OrderProductItemDto;
import com.bill99.mam.platform.remote.dto.request.AggregateReq;
import com.bill99.mam.platform.remote.dto.request.OrderDetail;
import com.bill99.mam.platform.remote.dto.request.OrderPaymentReq;
import com.bill99.mam.platform.remote.dto.request.QuickPaymentDynamicCodeReq;
import com.bill99.mam.platform.remote.dto.request.QuickPaymentIndAuthReq;
import com.bill99.mam.platform.remote.dto.request.QuickPaymentIndAuthVerifyReq;
import com.bill99.mam.platform.remote.dto.request.QuickPaymentReq;
import com.bill99.mam.platform.service.model.request.AggregatePreOrderReq;
import com.bill99.mam.platform.service.model.request.DynamicCodeReq;
import com.bill99.mam.platform.service.model.request.IndAuthReq;
import com.bill99.mam.platform.service.model.request.IndAuthVerifyReq;
import com.bill99.mam.platform.service.model.request.QuickPayment;
import com.bill99.mam.platform.service.model.request.SharingData;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
/**
 * 订单帮助类
 * @author jerry.xu.coc
 */
public class OrderSupport {
    /**
     * 组装HAT聚合下单请求对象
     * @param orderDto
     * @param sharingDataList
     * @return
     */
    public static AggregateReq generatorAggregateReq(OrderDto orderDto, List<SharingData> sharingDataList, AggregatePreOrderReq req){
        AggregateReq aggregateReq = new AggregateReq();
        aggregateReq.setFunctionCode(CommonUtil.toString(orderDto.getFunctionCode()));
        aggregateReq.setUId(CommonUtil.toString(orderDto.getUid()));
        aggregateReq.setPayeeUId(CommonUtil.toString(orderDto.getPayeeUid()));
        aggregateReq.setIsPlatformPayee(CommonUtil.toString(orderDto.getIsPlatformPayFee()));
        aggregateReq.setMerchantName(PlatformConst.PLATFORM_MERCHENT_NAME);
        aggregateReq.setSettlePeriod(orderDto.getSettlePeriod());
        aggregateReq.setOutTradeNo(CommonUtil.toString(orderDto.getId()));
        aggregateReq.setPayAmount(CommonUtil.toString(orderDto.getPayAmount()));
        aggregateReq.setOrderAmount(CommonUtil.toString(orderDto.getOrderAmount()));
        aggregateReq.setOrderType(CommonUtil.toString(orderDto.getOrderType()));
        if (Objects.equal(orderDto.getFunctionCode(), FunctionCodeEnum.ACCT_PAY.value())){
            aggregateReq.setSharingData(sharingDataList.toArray(new SharingData[sharingDataList.size()]));
        }
        aggregateReq.setIsAssure(CommonUtil.toString(orderDto.getIsAssured()));
        aggregateReq.setFeeMode(CommonUtil.toString(orderDto.getFeeMode()));
        aggregateReq.setOrderDetails(generatorOrderDetailsReq(orderDto.getOrderDetails()));
        aggregateReq.setTxnBeginTime(DateUtil.formatHat(orderDto.getTxnBeginTime()));
        aggregateReq.setBgUrl(PlatformConst.BG_URL);

        aggregateReq.setPayMode(req.getPayMode());
        aggregateReq.setPayType(req.getPayType());
        aggregateReq.setSubAppId(req.getSubAppId());

        return aggregateReq;
    }

    public static List<OrderDetail> generatorOrderDetailsReq(List<OrderDetailDto> orderDetailDtos) {
        if (CollectionUtils.isEmpty(orderDetailDtos)) return null;
        List<OrderDetail> orderDetailList = Lists.newArrayList();
        OrderDetail detail = null;
        for (OrderDetailDto orderDetailDto : orderDetailDtos) {
            detail = new OrderDetail();
            detail.setSubMerchantUId(CommonUtil.toString(orderDetailDto.getSubMerchantUid()));
            detail.setSubOutTradeNo(CommonUtil.toString(orderDetailDto.getId()));
            detail.setSubMerchantName(orderDetailDto.getSubMerchantName());
            detail.setIsPlatformSubMerchant(CommonUtil.toString(orderDetailDto.getMerchantIsPlatform()));
            detail.setOrderAmount(CommonUtil.toString(orderDetailDto.getOrderAmount()));
            detail.setProductList(generatorProductItemReq(orderDetailDto.getProductList()));
            orderDetailList.add(detail);
        }

        return orderDetailList;
    }

    public static List<com.bill99.mam.platform.remote.dto.request.ProductItem> generatorProductItemReq(
            List<OrderProductItemDto> orderProductItemDtoList) {
        if (CollectionUtils.isEmpty(orderProductItemDtoList)) return null;
        List<com.bill99.mam.platform.remote.dto.request.ProductItem> productItems = Lists.newArrayList();
        com.bill99.mam.platform.remote.dto.request.ProductItem productItem = null;
        for (OrderProductItemDto orderProductItemDto : orderProductItemDtoList) {
            productItem = new com.bill99.mam.platform.remote.dto.request.ProductItem();
            productItem.setProductCategory(orderProductItemDto.getProductCategory());
            productItem.setProductDesc(orderProductItemDto.getProductDesc());
            productItem.setProductName(orderProductItemDto.getProductName());
            productItem.setProductNum(CommonUtil.toString(orderProductItemDto.getProductNum()));
            productItem.setProductprice(CommonUtil.toString(orderProductItemDto.getProductPrice()));
            productItem.setProductTag(CommonUtil.toString(orderProductItemDto.getProductTag()));
            productItems.add(productItem);
        }

        return productItems;
    }

    /**
     * 获取分账数据
     * @param orderDto
     * @return
     */
    public static List<SharingData> getSharingData(OrderDto orderDto) {
        List<SharingData> sharingDataList = Lists.newArrayList();
        SharingData sharingData = null;
        List<OrderDetailDto> detailDtos = orderDto.getOrderDetails();
        for (OrderDetailDto detailDto : detailDtos) {
        	//商户分账数据
        	Long merchantLedgerAmount = detailDto.getMerchantLedgerAmount();
        	if (merchantLedgerAmount != null && merchantLedgerAmount > 0) {
        		sharingData = new SharingData();
                sharingData.setSubOutTradeNo(CommonUtil.toString(detailDto.getId()));
                sharingData.setIsPlatformSharing(0);
                sharingData.setSharingApplyAmount(merchantLedgerAmount);
                sharingData.setSharingUId(detailDto.getSubMerchantUid());
                sharingData.setSettlePeriod(PlatformConst.DEFAULT_SETTLE_PERIOD);//结算周期
                sharingData.setSharingDesc(detailDto.getSubMerchantName()+" sharingDate");
                sharingDataList.add(sharingData);
			}
            //平台分账数据
            Long platformLedgerAmount = detailDto.getPlatformLedgerAmount();
            if (platformLedgerAmount != null && platformLedgerAmount > 0) {
            	sharingData = new SharingData();
                sharingData.setSubOutTradeNo(CommonUtil.toString(detailDto.getId()));
                sharingData.setIsPlatformSharing(1);
                sharingData.setSharingApplyAmount(platformLedgerAmount);
                sharingData.setSharingUId(Long.valueOf(PlatformConst.PLATFORM_MERCHENT_CODE));
                sharingData.setSettlePeriod(PlatformConst.DEFAULT_SETTLE_PERIOD);//结算周期
                sharingData.setSharingDesc(PlatformConst.PLATFORM_MERCHENT_NAME+" sharingDate");
                sharingDataList.add(sharingData);
			}
        }
        
        return sharingDataList;
    }

    /**
    * @Description: 快捷支付验证码获取请求对象 
    * @param dynamicCodeReq
    * @param orderId
    * @param orderDto
    * @return QuickPaymentDynamicCodeReq
    * @throws
     */
    public static QuickPaymentDynamicCodeReq getQuickPaymentDynamicCodeReq(DynamicCodeReq dynamicCodeReq, String orderId, OrderDto orderDto) {
        QuickPaymentDynamicCodeReq codeReq = new QuickPaymentDynamicCodeReq();
        codeReq.setOutTradeNo(orderId);
        codeReq.setUId(CommonUtil.toString(orderDto.getUid()));
        codeReq.setPayAmount(CommonUtil.toString(orderDto.getPayAmount()));
        codeReq.setBankId(dynamicCodeReq.getBankId());
        codeReq.setCardType(dynamicCodeReq.getCardType());
        codeReq.setBankAcctId(dynamicCodeReq.getBankAcctId());
        codeReq.setShortBankAcctId(dynamicCodeReq.getShortBankAcctId());
        codeReq.setExpiredDate(dynamicCodeReq.getExpiredDate());
        codeReq.setCvv2(dynamicCodeReq.getCvv2());
        codeReq.setName(dynamicCodeReq.getName());
        codeReq.setMobile(dynamicCodeReq.getMobile());
        codeReq.setIdCardNumber(dynamicCodeReq.getIdCardNumber());
        codeReq.setIdCardType(dynamicCodeReq.getIdCardType());
        codeReq.setPayMode(dynamicCodeReq.getPayMode());
        return codeReq;
    }
    /**
    * @Description: 
    * @param orderDto
    * @param sharingDataList
    * @return OrderPaymentReq
    * @throws
     */
    public static OrderPaymentReq generatorPaymentReq(OrderDto orderDto, List<SharingData> sharingDataList) {
        OrderPaymentReq paymentReq = new OrderPaymentReq();
        paymentReq.setFunctionCode(orderDto.getFunctionCode().toString());
        paymentReq.setMerchantName(PlatformConst.PLATFORM_MERCHENT_NAME);
        paymentReq.setPayeeUId(orderDto.getPayeeUid().toString());
        paymentReq.setIsPlatformPayee(CommonUtil.toString(orderDto.getIsPlatformPayFee()));
        paymentReq.setOutTradeNo(orderDto.getId().toString());
        paymentReq.setOrderAmount(CommonUtil.toString(orderDto.getOrderAmount()));
        paymentReq.setPayAmount(CommonUtil.toString(orderDto.getPayAmount()));
        if (CollectionUtils.isNotEmpty(sharingDataList)) {
            paymentReq.setSharingData(sharingDataList.toArray(new SharingData[sharingDataList.size()]));
        }
        paymentReq.setTxnBeginTime(DateUtil.formatHat(orderDto.getTxnBeginTime()));
        paymentReq.setIsAssure(CommonUtil.toString(orderDto.getIsAssured()));
        paymentReq.setFeeMode(CommonUtil.toString(orderDto.getFeeMode()));
        paymentReq.setOrderType(orderDto.getOrderType());
        paymentReq.setPayMode(CommonUtil.toString(orderDto.getPayMode()));
        paymentReq.setPayType(orderDto.getPayType());
        paymentReq.setMemberBankAcctId(orderDto.getMemberBankAcctId());
        paymentReq.setCurrencyCode(orderDto.getCurrencyCode());
        paymentReq.setAuthCode(orderDto.getAuthCode());
        paymentReq.setUId(CommonUtil.toString(orderDto.getUid()));
        paymentReq.setTxnExpireTime(DateUtil.formatHat(orderDto.getTxnExpireTime()));
        paymentReq.setOrderDetails(generatorOrderDetailsReq(orderDto.getOrderDetails()));
        paymentReq.setBgUrl(orderDto.getBgUrl());
        paymentReq.setMemo(orderDto.getMemo());
        paymentReq.setDataMap(orderDto.getDataMap());

        return paymentReq;
    }

    public static List<OrderDetailDto> getOrderDetailDtos(Map<Long, String> sellerId2Name, Multimap<Long, OrderProductItemDto> sellerId2ItemMap) {
        List<OrderDetailDto> orderDetailDtoList = Lists.newArrayList();
        Collection<OrderProductItemDto> itemDtos = null;
        OrderDetailDto detailDto = null;
        for (Long sellerId : sellerId2ItemMap.keySet()) {
            itemDtos = sellerId2ItemMap.get(sellerId);
            boolean isPlatformPayFee = CommonUtil.isPlatform(sellerId);
            detailDto = new OrderDetailDto();
            detailDto.setSubMerchantName(isPlatformPayFee ? PlatformConst.PLATFORM_MERCHENT_NAME : sellerId2Name.get(sellerId));
            detailDto.setSubMerchantUid(sellerId);
            detailDto.setMerchantIsPlatform(BooleanUtils.toInteger(isPlatformPayFee));
            detailDto.setOrderType(CommonUtil.toLong(PlatformConst.DEFAULT_ORDER_TYPE));
            if (CollectionUtils.isNotEmpty(itemDtos)) {
                detailDto.setProductList(Lists.newArrayList(itemDtos));
            }
            //计算分账金额
            detailDto.countLedgerAmount();
            orderDetailDtoList.add(detailDto);
        }
        return orderDetailDtoList;
    }
    /**
    * @Description: 生成快捷支付请求对象  
    * @param quickPaymentReq
    * @param orderDto
    * @param functionCode
    * @param sharingDataList
    * @return QuickPaymentReq
    * @throws
     */
    public static QuickPaymentReq getQuickPaymentReq(QuickPayment quickPaymentReq, OrderDto orderDto, String functionCode, List<SharingData> sharingDataList) {
        QuickPaymentReq paymentReq = new QuickPaymentReq();
        paymentReq.setFunctionCode(quickPaymentReq.getFunctionCode());
        paymentReq.setMerchantName(PlatformConst.PLATFORM_MERCHENT_NAME);
        paymentReq.setPayeeUId(CommonUtil.toString(orderDto.getPayeeUid()));
        paymentReq.setIsPlatformPayee(CommonUtil.toString(orderDto.getIsPlatformPayFee()));
        paymentReq.setOutTradeNo(orderDto.getId().toString());
        paymentReq.setOrderAmount(CommonUtil.toString(orderDto.getOrderAmount()));
        paymentReq.setPayAmount(CommonUtil.toString(orderDto.getPayAmount()));
        if (StringUtils.equals(functionCode, FunctionCodeEnum.ACCT_PAY.value().toString())){
            paymentReq.setSharingData(sharingDataList.toArray(new SharingData[sharingDataList.size()]));
        }
        paymentReq.setTxnBeginTime(DateUtil.formatHat(orderDto.getTxnBeginTime()));
        paymentReq.setIsAssure(CommonUtil.toString(orderDto.getIsAssured()));
        paymentReq.setFeeMode(CommonUtil.toString(orderDto.getFeeMode()));
        paymentReq.setOrderType(orderDto.getOrderType());
        paymentReq.setUId(CommonUtil.toString(orderDto.getUid()));
        paymentReq.setPayMode(CommonUtil.toString(orderDto.getPayMode()));
        paymentReq.setMobile(quickPaymentReq.getMobile());
        paymentReq.setValidCode(quickPaymentReq.getValidCode());
        paymentReq.setToken(quickPaymentReq.getToken());
        paymentReq.setIsOpenQuick(quickPaymentReq.getIsOpenQuick());
        paymentReq.setIsQuickPayment(quickPaymentReq.getIsQuickPayment());
        paymentReq.setOrderDetails(OrderSupport.generatorOrderDetailsReq(orderDto.getOrderDetails()));
        paymentReq.setCardType(quickPaymentReq.getCardType());
        paymentReq.setBankAcctId(quickPaymentReq.getBankAcctId());
        paymentReq.setShortBankAcctId(quickPaymentReq.getShortBankAcctId());
        paymentReq.setExpiredDate(quickPaymentReq.getExpiredDate());
        paymentReq.setCvv2(quickPaymentReq.getCvv2());
        paymentReq.setName(quickPaymentReq.getName());
        paymentReq.setIdCardNumber(quickPaymentReq.getIdCardNumber());
        paymentReq.setIdCardType(quickPaymentReq.getIdCardType());
        paymentReq.setIsSubsidiaryCard(quickPaymentReq.getIsSubsidiaryCard());
        paymentReq.setTxnSendIp(CommonUtil.getLocalIp());
        return paymentReq;
    }

    /**
     * @Description: 获取一键支付鉴权申请请求对象
     * @param req
     * @param orderId
     * @param orderDto
     * @return QuickPaymentDynamicCodeReq
     * @throws
     */
    public static QuickPaymentIndAuthReq getQuickPaymentIndAuthReq(IndAuthReq req, String orderId, OrderDto orderDto) {
        QuickPaymentIndAuthReq codeReq = new QuickPaymentIndAuthReq();
        codeReq.setOutTradeNo(orderId);
        codeReq.setUId(CommonUtil.toString(orderDto.getUid()));
        codeReq.setCardType(req.getCardType());
        codeReq.setBankAcctId(req.getBankAcctId());
        codeReq.setExpiredDate(req.getExpiredDate());
        codeReq.setCvv2(req.getCvv2());
        codeReq.setName(req.getName());
        codeReq.setMobile(req.getMobile());
        codeReq.setIdCardNumber(req.getIdCardNumber());
        codeReq.setIdCardType(req.getIdCardType());
        codeReq.setBindType(req.getBindType());
        return codeReq;
    }
    /**
     * @Description: 获取一键支付鉴权确认请求对象
     * @param req
     * @param orderId
     * @param orderDto
     * @return QuickPaymentDynamicCodeReq
     * @throws
     */
    public static QuickPaymentIndAuthVerifyReq getQuickPaymentIndAuthVerifyReq(IndAuthVerifyReq req, String orderId, OrderDto orderDto) {
        QuickPaymentIndAuthVerifyReq codeReq = new QuickPaymentIndAuthVerifyReq();
        codeReq.setOutTradeNo(orderId);
        codeReq.setUId(CommonUtil.toString(orderDto.getUid()));
        codeReq.setShortBankAcctId(req.getShortBankAcctId());
        codeReq.setToken(req.getToken());
        codeReq.setValidCode(req.getValidCode());
        codeReq.setCardType(req.getCardType());
        codeReq.setBankAcctId(req.getBankAcctId());
        codeReq.setExpiredDate(req.getExpiredDate());
        codeReq.setCvv2(req.getCvv2());
        codeReq.setName(req.getName());
        codeReq.setMobile(req.getMobile());
        codeReq.setIdCardNumber(req.getIdCardNumber());
        codeReq.setIdCardType(req.getIdCardType());
        codeReq.setBindType(req.getBindType());
        return codeReq;
    }
    
    public static Long calculatePlatformLedgerAmount(long sumPrice,int rate){
    	 BigDecimal ledgerRate = new BigDecimal(rate).divide(new BigDecimal(100) , 2, BigDecimal.ROUND_UP);
    	 return new BigDecimal(sumPrice).multiply(ledgerRate).longValue();
    }
    
}
