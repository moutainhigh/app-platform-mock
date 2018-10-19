package com.bill99.mam.platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.bill99.mam.platform.common.constant.PlatformConst;
import com.bill99.mam.platform.common.constant.ResultCode;
import com.bill99.mam.platform.common.enumeration.*;
import com.bill99.mam.platform.common.exception.ExceptionFactory;
import com.bill99.mam.platform.common.util.CommonUtil;
import com.bill99.mam.platform.common.util.DateUtil;
import com.bill99.mam.platform.persistence.dto.LedgerAcctConfigDto;
import com.bill99.mam.platform.persistence.dto.OrderDetailDto;
import com.bill99.mam.platform.persistence.dto.OrderDto;
import com.bill99.mam.platform.persistence.dto.OrderProductItemDto;
import com.bill99.mam.platform.persistence.repository.LedgerAcctConfigRepository;
import com.bill99.mam.platform.persistence.repository.OrderRepository;
import com.bill99.mam.platform.remote.dto.request.*;
import com.bill99.mam.platform.remote.dto.response.*;
import com.bill99.mam.platform.remote.service.HatService;
import com.bill99.mam.platform.service.IOrderService;
import com.bill99.mam.platform.service.model.BaseResponse;
import com.bill99.mam.platform.service.model.request.*;
import com.bill99.mam.platform.service.model.request.ProductItem;
import com.bill99.mam.platform.service.model.response.*;
import com.bill99.mam.platform.service.model.response.QuickPaymentResp;
import com.bill99.mam.platform.service.support.OrderSupport;
import com.google.common.base.Objects;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.bill99.mam.platform.common.util.Validator.checkArgument;
import static com.bill99.mam.platform.common.util.Validator.checkNotNull;

@Component
public class OrderService implements IOrderService {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private LedgerAcctConfigRepository ledgerRepository;
	@Autowired
	private HatService hatService;


	/**
	 * 保存订单
	 * 
	 * @param orderSaveReq
	 * @return
	 */
	@Override
	public OrderSaveResp saveOrder(OrderSaveReq orderSaveReq) {
		checkNotNull(orderSaveReq);
		OrderDto orderDto = new OrderDto();
		orderDto.setFunctionCode(FunctionCodeEnum.ACCT_PAY.value());
		// 主收款方暂定平台，支付时可以选择修改
		orderDto.setPayeeUid(Long.valueOf(PlatformConst.PLATFORM_MERCHENT_CODE));
		orderDto.setOrderType(PlatformConst.DEFAULT_ORDER_TYPE);
		orderDto.setUid(orderSaveReq.getBuyerId());// 买家用户ID
		// 记录卖家ID与商户名称
		Map<Long, String> sellerId2Name = Maps.newHashMap();
		// 卖家ID与商品订单表表
		Multimap<Long, OrderProductItemDto> sellerId2ItemMap = getOrderProductItem(orderSaveReq, sellerId2Name);
		// 获取子订单相关记录
		List<OrderDetailDto> orderDetailDtoList = OrderSupport.getOrderDetailDtos(sellerId2Name, sellerId2ItemMap);
		// 设置订单子表
		orderDto.setOrderDetails(orderDetailDtoList);
		// 计算并设置订单金额与支付金额
		orderDto.setPayAmount(orderDto.countOrderAmount());
		// 保存订单
		orderDto = orderRepository.save(orderDto);
		// 返回订单号
		OrderSaveResp resp = new OrderSaveResp();
		resp.setOrderId(orderDto.getId());
		return resp;
	}

	/**
	 * 充值
	 */
	@Override
	public OrderDto saveRechargeOrder(RechargeWithdrawalsSaveReq rechargeSaveReq) {
		OrderDto orderDto = new OrderDto();
		orderDto.setFunctionCode(FunctionCodeEnum.DEPOSIT.value());
		orderDto.setPayeeUid(Long.valueOf(PlatformConst.PLATFORM_MERCHENT_CODE));
		orderDto.setOrderType(String.valueOf(OrderTypeEnum.ACCOUNT_RECHARGE.value()));
		orderDto.setUid(rechargeSaveReq.getBuyerId());// 买家用户ID
		orderDto.setOrderAmount(rechargeSaveReq.getPayAmount());
		orderDto.setPayAmount(rechargeSaveReq.getPayAmount());
		orderDto.setOrderStatus(OrderStatusEnum.ACCEPTANCE.value());
		orderDto = orderRepository.save(orderDto);
		return orderDto;
	}

	@Override
	public BaseResponse payRechargeOrder(OrderDto orderDto, String bankAcctId) {
		DepositReq depositReq = new DepositReq();
		depositReq.setFunctionCode(FunctionCodeEnum.DEPOSIT.value().toString());
		depositReq.setOutTradeNo(orderDto.getId().toString());
		depositReq.setUId(String.valueOf(orderDto.getUid()));
		depositReq.setMerchantName(PlatformConst.PLATFORM_MERCHENT_NAME);
		depositReq.setOrderAmount(String.valueOf(orderDto.getOrderAmount()));
		depositReq.setPayAmount(String.valueOf(orderDto.getPayAmount()));
		depositReq.setPayMode(String.valueOf(PayModeEnum.BANK_ACCT_PAY.value()));
		depositReq.setMemberBankAcctId(bankAcctId);
		depositReq.setOrderType(String.valueOf(OrderTypeEnum.ACCOUNT_RECHARGE.value()));
		depositReq.setBgUrl(PlatformConst.BG_URL);

		BaseResponse resp = new BaseResponse();
		DepositResp deposite = hatService.deposite(depositReq);
		if (ResultCode.success(deposite.getRspCode())) {
			orderDto.setPayOrderSuccess();
		} else {
			orderDto.setPayOrderFailed();
			resp.setRspCode(deposite.getRspCode());
			resp.setRspMsg(deposite.getRspMsg());
		}
		orderRepository.update(orderDto);
		return resp;
	}

	/**
	 * 提现
	 */
	@Override
	public BaseResponse saveWithdrawals(RechargeWithdrawalsSaveReq rechargeSaveReq) {
		OrderDto orderDto = new OrderDto();
		orderDto.setFunctionCode(FunctionCodeEnum.DEPOSIT.value());
		orderDto.setPayeeUid(Long.valueOf(PlatformConst.PLATFORM_MERCHENT_CODE));
		orderDto.setOrderType(String.valueOf(OrderTypeEnum.ACCOUNT_WITHDRAWALS.value()));
		orderDto.setUid(rechargeSaveReq.getBuyerId());// 买家用户ID
		orderDto.setOrderAmount(rechargeSaveReq.getPayAmount());

		LedgerAcctConfigDto ledgerCongfig = ledgerRepository.qryLedgerCongfigByLedgerAcctType(OrderTypeEnum.ACCOUNT_WITHDRAWALS.value());
		if (null == ledgerCongfig) {
			orderDto.setTradeFee(orderDto.getOrderAmount() / 100);
		} else {
			orderDto.setTradeFee(orderDto.getOrderAmount() * ledgerCongfig.getRate() / 100);
		}

		orderDto.setOrderStatus(OrderStatusEnum.ACCEPTANCE.value());
		orderDto = orderRepository.save(orderDto);

		WithDrawReq withDrawReq = new WithDrawReq();
		withDrawReq.setFunctionCode(FunctionCodeEnum.WITHDRAW.value().toString());
		withDrawReq.setOutTradeNo(orderDto.getId().toString());
		withDrawReq.setUId(rechargeSaveReq.getBuyerId().toString());
		withDrawReq.setMerchantName(PlatformConst.PLATFORM_MERCHENT_NAME);
		withDrawReq.setAmount(rechargeSaveReq.getPayAmount().toString());
		withDrawReq.setMemberBankAcctId(rechargeSaveReq.getBankAcctId());
		withDrawReq.setPayMode(String.valueOf(PayModeEnum.BALANCE_PAY.value()));
		withDrawReq.setTradeFee(String.valueOf(orderDto.getTradeFee()));
		withDrawReq.setOrderType(String.valueOf(OrderTypeEnum.ACCOUNT_WITHDRAWALS.value()));
		withDrawReq.setBgUrl(PlatformConst.BG_URL);

		BaseResponse resp = new BaseResponse();
		WithDrawResp withdraw = hatService.withdraw(withDrawReq);
		if (ResultCode.success(withdraw.getRspCode())) {
			orderDto.setPayStatus(PayStatusEnum.IN_PAYMENT.value());
			// orderDto.setOrderStatus(OrderStatusEnum.SUCCESS.value());
			if (StringUtils.isNotBlank(withdraw.getStatus())) {
				orderDto.setOrderStatus(new Integer(withdraw.getStatus()));
			}
		} else {
			orderDto.setPayOrderFailed();
			resp.setRspCode(withdraw.getRspCode());
			resp.setRspMsg(withdraw.getRspMsg());
		}
		orderRepository.update(orderDto);
		return resp;
	}

	public Multimap<Long, OrderProductItemDto> getOrderProductItem(
			OrderSaveReq orderSaveReq, Map<Long, String> sellerId2Seller) {
		Multimap<Long, OrderProductItemDto> sellerId2ItemMap = ArrayListMultimap.create();
		List<ProductItem> productItems = orderSaveReq.getOrderItems();
		for (ProductItem productItem : productItems) {
			Long sellerId = productItem.getSellerId();
			sellerId2Seller.put(sellerId, productItem.getSellerName());
			sellerId2ItemMap.put(sellerId, generatorItem(productItem));
		}
		return sellerId2ItemMap;
	}

	/**
	 * @Description: 生成商品订单
	 * @param productItem
	 * @return OrderProductItemDto
	 * @throws
	 */
	private OrderProductItemDto generatorItem(ProductItem productItem) {

		OrderProductItemDto productItemDto = new OrderProductItemDto();
		productItemDto.setProductNum(productItem.getProductNum());
		productItemDto.setProductPrice(productItem.getProductPrice());
		productItemDto.setProductTag(productItem.getProductTag());
		productItemDto.setProductName(productItem.getProductName());

		Long goodId = productItem.getProductTag();
		LedgerAcctConfigDto acctConfig = ledgerRepository.qryLedgerCongfigByGoodId(goodId);
		int rate = acctConfig == null ? 1 : acctConfig.getRate();// 默认为1%
		productItemDto.setLedgerRate(rate);

		boolean isPlatformPayFee = CommonUtil.isPlatform(productItem.getSellerId());
		// 商品订单金额
		Long sumPrice = productItem.getProductNum() * productItem.getProductPrice();
		// 计算平台分账金额
		productItemDto.setPlatformLedgerAmount(isPlatformPayFee ? sumPrice : OrderSupport.calculatePlatformLedgerAmount(sumPrice, rate));
		// 计算商户分账金额
		productItemDto.setMerchantLedgerAmount(isPlatformPayFee ? 0 : sumPrice - productItemDto.getPlatformLedgerAmount());
		productItemDto.setProductCategory(productItem.getProductCategory());
		productItemDto.setProductDesc(productItem.getProductDesc());
		return productItemDto;
	}

	/**
	 * 订单支付-同步（余额、绑卡）
	 * 
	 * @param orderPayReq
	 * @return
	 */
	@Override
	public OrderPayResp payOrder(OrderPayReq orderPayReq) {
		// TODO: 2018/3/30 参数校验
		checkNotNull(orderPayReq);
		checkNotNull(orderPayReq.getPayeeUId(), "PayeeUI is empty");

		OrderPayResp orderPayResp = new OrderPayResp();
		OrderDto orderDto = orderRepository.findById(orderPayReq.getOrderId());
		Integer payMode = orderPayReq.getPayMode();
		int payModeInt = payMode.intValue();
		int bandAcctPayMode = PayModeEnum.BANK_ACCT_PAY.value();
		if (bandAcctPayMode != payModeInt && PayModeEnum.BALANCE_PAY.value() != payModeInt) {
			ExceptionFactory.throwException("3000", "payMode illegal");
		}

		// 设置主收款方
		Long payeeUid = orderPayReq.getPayeeUId();
		orderDto.setPayeeUid(payeeUid);
		orderDto.setIsPlatformPayFee(CommonUtil.isPlatformPayFee(payeeUid));
		// 设置支付方式、银行卡
		orderDto.setPayMode(payMode);
		if (bandAcctPayMode == payMode.intValue()) {
			orderDto.setMemberBankAcctId(orderPayReq.getMemberBankAcctId());
		}
		// 生成分账数据并更新订单信息
		List<SharingData> sharingDataList = OrderSupport.getSharingData(orderDto);
		String sharingDataJson = JSON.toJSONString(sharingDataList);
		orderDto.setSharingData(sharingDataJson);
		orderDto = orderRepository.update(orderDto);
		// 调用Hat订单支付接口
		OrderPaymentReq paymentReq = OrderSupport.generatorPaymentReq(orderDto,sharingDataList);
		OrderPaymentResp paymentResp = hatService.orderPay(paymentReq);
		// 处理返回结果
		orderDto.setRespCode(paymentResp.getRspCode());
		orderDto.setRespMsg(paymentResp.getRspMsg());

		if (!ResultCode.success(paymentResp.getRspCode())) {
			orderPayResp.setRspMsg(paymentResp.getRspMsg());
			orderPayResp.setRspCode(paymentResp.getRspCode());
		} else {
			orderDto.setBillOrderNo(paymentResp.getBillOrderNo());
			orderDto.setTradeNo(paymentResp.getTradeNo());
			// 余额支付、绑卡支付是同步的,可以拿到最终的结果
			String state = ObjectUtils.defaultIfNull(paymentResp.getStatus(),"0");
			if (HatTradeStatusEnum.SUCCESS.getCode().equals(state)) {
				orderDto.setPayOrderSuccess();
			} else if (HatTradeStatusEnum.FAILED.getCode().equals(state)) {
				orderDto.setPayOrderFailed();
			} else {
				// TODO: 2018/4/8 是否有超时的状态返回？
			}
		}
		// 更新订单
		orderRepository.update(orderDto);
		return orderPayResp;
	}

	/**
	 * 聚合支付下单
	 * 
	 * @param aggregatePreOrderReq
	 * @return
	 */
	@Override
	public AggregatePreOrderResp AggregatePreOrder(AggregatePreOrderReq aggregatePreOrderReq) {
		checkNotNull(aggregatePreOrderReq, "AggregatePreOrderReq is null");
		checkNotNull(aggregatePreOrderReq.getPayeeUId(), "PayeeUI is empty");
		AggregatePreOrderResp aggregatePreOrderResp = new AggregatePreOrderResp();
		Integer functionCode = aggregatePreOrderReq.getFunctionCode();
		Long orderId = aggregatePreOrderReq.getOrderId();
		OrderDto orderDto = orderRepository.findById(orderId);
		checkNotNull(orderDto, "findById retrun empty");
		checkArgument(Objects.equal(functionCode, orderDto.getFunctionCode()),"functionCode illegal");
		// 设置主收款方
		Long payeeUid = aggregatePreOrderReq.getPayeeUId();
		orderDto.setPayeeUid(payeeUid);
		orderDto.setIsPlatformPayFee(CommonUtil.isPlatformPayFee(payeeUid));
		// 设置聚合支付
        if(aggregatePreOrderReq.getPayMode() != null){
            orderDto.setPayMode(Integer.valueOf(aggregatePreOrderReq.getPayMode()));
        }else{
            orderDto.setPayMode(PayModeEnum.AGGREGATE_PAY.value());
        }

		// 生成分账数据并更新订单信息
		List<SharingData> sharingDataList = null;
		if (Objects.equal(functionCode, FunctionCodeEnum.ACCT_PAY.value())) {
			sharingDataList = OrderSupport.getSharingData(orderDto);
			String sharingDataJson = JSON.toJSONString(sharingDataList);
			orderDto.setSharingData(sharingDataJson);
		}

		orderDto = orderRepository.update(orderDto);

		// 调用HAT聚合下单接口
		AggregateReq aggregateReq = OrderSupport.generatorAggregateReq(orderDto,sharingDataList, aggregatePreOrderReq);
		AggregateResp aggregateResp = hatService.aggregate(aggregateReq);
		aggregatePreOrderResp.setRspCode(aggregateResp.getRspCode());
		aggregatePreOrderResp.setRspMsg(aggregateResp.getRspMsg());
		// TODO: 2018/4/8 待大为整理respCode与status
		if (ResultCode.success(aggregateResp.getRspCode())) {
			orderDto.setRespCode(aggregateResp.getRspCode());
			orderDto.setRespMsg(aggregateResp.getRspMsg());
			orderDto.setBillOrderNo(aggregateResp.getBillOrderNo());
			// 聚合下单成功返回 1：成功 ，订单更新为受理成功
			orderDto.setOrderStatus(CommonUtil.toInteger(HatTradeStatusEnum.ACCEPTED.getCode()));
			orderDto.setPayUrl(aggregateResp.getPayUrl());
			aggregatePreOrderResp.setPayUrl(aggregateResp.getPayUrl());
			aggregatePreOrderResp.setStatus(aggregateResp.getStatus());

			aggregatePreOrderResp.setIdBiz(aggregateResp.getIdBiz());
			aggregatePreOrderResp.setMpayInfo(aggregateResp.getMpayInfo());
			aggregatePreOrderResp.setMerchantId(aggregateResp.getMerchantId());
		}
		orderRepository.update(orderDto);
		return aggregatePreOrderResp;
	}

	/**
	 * 快捷支付-获取验证码
	 */
	@Override
	public DynamicCodeResp getDynamicCode(DynamicCodeReq dynamicCodeReq) {
		// TODO: 2018/4/26 参数校验：储蓄卡四要素 信用卡六要素
		checkNotNull(dynamicCodeReq, "dynamicCodeReq is null");
		checkNotNull(dynamicCodeReq.getPayeeUId(), "PayeeUI is empty");
		String orderId = dynamicCodeReq.getOrderId();
		OrderDto orderDto = orderRepository.findById(Long.valueOf(orderId));
		checkNotNull(orderDto, "order not exist,id=" + orderId);
		// 设置主收款方
		Long payeeUid = dynamicCodeReq.getPayeeUId();
		orderDto.setPayeeUid(payeeUid);
		orderDto.setIsPlatformPayFee(CommonUtil.isPlatformPayFee(payeeUid));
		orderDto = orderRepository.update(orderDto);

		QuickPaymentDynamicCodeReq codeReq = OrderSupport.getQuickPaymentDynamicCodeReq(dynamicCodeReq, orderId,orderDto);
		QuickPaymentDynamicCodeResp codeResp = hatService.getDynamicCode(codeReq);

		DynamicCodeResp dynamicCodeResp = new DynamicCodeResp();
		if (ResultCode.success(codeResp.getRspCode())) {
			dynamicCodeResp.setToken(codeResp.getToken());
			dynamicCodeResp.setValidCode(codeResp.getValidCode());
		} else {
			dynamicCodeResp.setRspCode(codeResp.getRspCode());
			dynamicCodeResp.setRspMsg(codeResp.getRspMsg());
		}

		return dynamicCodeResp;
	}

	/**
	 * 快捷支付
	 * 
	 * @param quickPaymentReq
	 * @return
	 */
	@Override
	public QuickPaymentResp quickPay(QuickPayment quickPaymentReq) {
		checkNotNull(quickPaymentReq, "quickPaymentReq is null");
		String orderId = quickPaymentReq.getOrderId();
		OrderDto orderDto = orderRepository.findById(Long.valueOf(orderId));
		checkNotNull(orderDto, "order not exist,id=" + orderId);
		String functionCode = quickPaymentReq.getFunctionCode();
		// 设置支付方式
		orderDto.setPayMode(quickPaymentReq.getPayMode());
		// 生成分账数据并更新订单信息
		List<SharingData> sharingDataList = null;
		if (StringUtils.equals(functionCode, FunctionCodeEnum.ACCT_PAY.value().toString())) {
			sharingDataList = OrderSupport.getSharingData(orderDto);
			String sharingDataJson = JSON.toJSONString(sharingDataList);
			orderDto.setSharingData(sharingDataJson);
		}
		orderDto = orderRepository.update(orderDto);

		QuickPaymentReq paymentReq = OrderSupport.getQuickPaymentReq(quickPaymentReq, orderDto, functionCode, sharingDataList);
		// 调用Hat快捷支付接口
		com.bill99.mam.platform.remote.dto.response.QuickPaymentResp resp = hatService.quickPay(paymentReq);
		// 处理快捷支付返回结果
		QuickPaymentResp paymentResp = new QuickPaymentResp();
		paymentResp.setRspCode(resp.getRspCode());
		paymentResp.setRspMsg(resp.getRspMsg());
		orderDto.setRespCode(resp.getRspCode());
		orderDto.setRespMsg(resp.getRspMsg());

		if (ResultCode.success(resp.getRspCode())) {
			orderDto.setPayOrderSuccess();
			orderDto.setBillOrderNo(resp.getBillOrderNo());
			orderDto.setTradeNo(resp.getTradeNo());
			orderDto.setTxnEndTime(DateUtil.parseHat(resp.getTxnEndTime()));
		}

		orderRepository.update(orderDto);
		return paymentResp;
	}

	/**
	 * 一键支付鉴权申请
	 * @param indAuthReq
	 * @return
	 */
	@Override
	public IndAuthResp indAuth(IndAuthReq indAuthReq) {
        checkNotNull(indAuthReq, "dynamicCodeReq is null");

        String orderId = indAuthReq.getOrderId();
        OrderDto orderDto = orderRepository.findById(Long.valueOf(orderId));
        checkNotNull(orderDto, "order not exist,id=" + orderId);
        // 设置主收款方
        Long payeeUid = indAuthReq.getPayeeUId();
        orderDto.setPayeeUid(payeeUid);
        orderDto.setIsPlatformPayFee(CommonUtil.isPlatformPayFee(payeeUid));
        orderDto = orderRepository.update(orderDto);

        QuickPaymentIndAuthReq codeReq = OrderSupport.getQuickPaymentIndAuthReq(indAuthReq, orderId,orderDto);
        QuickPaymentIndAuthResp codeResp = hatService.indAuth(codeReq);

        IndAuthResp indAuthResp = new IndAuthResp();
        if (ResultCode.success(codeResp.getRspCode())) {
            indAuthResp.setToken(codeResp.getToken());
            indAuthResp.setShortBankAcctId(codeResp.getShortBankAcctId());
        } else {
            indAuthResp.setRspCode(codeResp.getRspCode());
            indAuthResp.setRspMsg(codeResp.getRspMsg());
        }

        return indAuthResp;
	}

    /**
     * 一键支付鉴权确认
     * @param indAuthVerifyReq
     * @return
     */
	@Override
	public IndAuthVerifyResp indAuthVerify(IndAuthVerifyReq indAuthVerifyReq) {
        checkNotNull(indAuthVerifyReq, "dynamicCodeReq is null");

        String orderId = indAuthVerifyReq.getOrderId();
        OrderDto orderDto = orderRepository.findById(Long.valueOf(orderId));
        checkNotNull(orderDto, "order not exist,id=" + orderId);

        QuickPaymentIndAuthVerifyReq codeReq = OrderSupport.getQuickPaymentIndAuthVerifyReq(indAuthVerifyReq, orderId,orderDto);
        QuickPaymentIndAuthVerifyResp codeResp = hatService.indAuthVerify(codeReq);

        IndAuthVerifyResp indAuthResp = new IndAuthVerifyResp();
        indAuthResp.setRspCode(codeResp.getRspCode());
        indAuthResp.setRspMsg(codeResp.getRspMsg());

        return indAuthResp;
	}

	@Override
	public UnbindCardResp unbindCard(UnbindCardReq unbindCardReq) {
        QuickPaymentUnbindCardReq req = new QuickPaymentUnbindCardReq();
        BeanUtils.copyProperties(unbindCardReq, req);
        QuickPaymentUnbindCardResp resp = hatService.unbindCard(req);
        UnbindCardResp response = new UnbindCardResp();
        BeanUtils.copyProperties(resp, response);
		return response;
	}
}
