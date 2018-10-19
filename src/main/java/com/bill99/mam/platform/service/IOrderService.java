package com.bill99.mam.platform.service;

import com.bill99.mam.platform.persistence.dto.OrderDto;
import com.bill99.mam.platform.service.model.BaseResponse;
import com.bill99.mam.platform.service.model.request.AggregatePreOrderReq;
import com.bill99.mam.platform.service.model.request.DynamicCodeReq;
import com.bill99.mam.platform.service.model.request.IndAuthReq;
import com.bill99.mam.platform.service.model.request.IndAuthVerifyReq;
import com.bill99.mam.platform.service.model.request.OrderPayReq;
import com.bill99.mam.platform.service.model.request.OrderSaveReq;
import com.bill99.mam.platform.service.model.request.QuickPayment;
import com.bill99.mam.platform.service.model.request.RechargeWithdrawalsSaveReq;
import com.bill99.mam.platform.service.model.request.UnbindCardReq;
import com.bill99.mam.platform.service.model.response.AggregatePreOrderResp;
import com.bill99.mam.platform.service.model.response.DynamicCodeResp;
import com.bill99.mam.platform.service.model.response.IndAuthResp;
import com.bill99.mam.platform.service.model.response.IndAuthVerifyResp;
import com.bill99.mam.platform.service.model.response.OrderPayResp;
import com.bill99.mam.platform.service.model.response.OrderSaveResp;
import com.bill99.mam.platform.service.model.response.QuickPaymentResp;
import com.bill99.mam.platform.service.model.response.UnbindCardResp;

public interface IOrderService {
    /**
     * 保存商品支付订单
     * @param orderSaveReq
     * @return
     */
    OrderSaveResp saveOrder(OrderSaveReq orderSaveReq);
    
    /**
     * 保存充值订单
     */
    OrderDto saveRechargeOrder(RechargeWithdrawalsSaveReq rechargeSaveReq);
    
    /**
     * 支付充值订单(绑卡支付)
     */
    BaseResponse payRechargeOrder(OrderDto orderDto, String bankAcctId);
    
    /**
     * 保存提现订单
     */
    BaseResponse saveWithdrawals(RechargeWithdrawalsSaveReq rechargeSaveReq);

    /**
     * 支付订单（余额支付、绑卡支付）
     * @param orderPayReq
     * @return
     */
    OrderPayResp payOrder(OrderPayReq orderPayReq);

    /**
     * 聚合下单
     * @param aggregatePreOrderReq
     * @return
     */
    AggregatePreOrderResp AggregatePreOrder(AggregatePreOrderReq aggregatePreOrderReq);
    /**
    * @Description: 快捷支付-获取验证码 
    * @param dynamicCodeReq
    * @return DynamicCodeResp
    * @throws
     */
    DynamicCodeResp getDynamicCode(DynamicCodeReq dynamicCodeReq);

    /**
     * 快捷(一键)支付
     * @param quickPaymentReq
     * @return
     */
    QuickPaymentResp quickPay(QuickPayment quickPaymentReq);
    /**
     * @Description: 一键支付鉴权申请
     * @param indAuthReq
     * @return DynamicCodeResp
     * @throws
     */
    IndAuthResp indAuth(IndAuthReq indAuthReq);
    /**
     * @Description: 一键支付鉴权确认
     * @param indAuthVerifyReq
     * @return DynamicCodeResp
     * @throws
     */
    IndAuthVerifyResp indAuthVerify(IndAuthVerifyReq indAuthVerifyReq);
    /**
     * @Description: 解绑平台用户银行卡
     * @param unbindCardReq
     * @return UnbindCardResp
     * @throws
     */
    UnbindCardResp unbindCard(UnbindCardReq unbindCardReq);
}
