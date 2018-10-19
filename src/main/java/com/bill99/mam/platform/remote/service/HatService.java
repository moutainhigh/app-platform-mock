package com.bill99.mam.platform.remote.service;

import com.bill99.mam.platform.remote.api.IHatService;
import com.bill99.mam.platform.remote.dto.request.*;
import com.bill99.mam.platform.remote.dto.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HatService {
    @Autowired
    private IHatService hatService;
    /**
     * 个人注册
     * @param request
     * @return
     */
    public HatPersonalRegisterResp personalRegister(HatPersonalRegisterReq request) {
        return hatService.personalRegister(request).get();
    }

    /**
     * 余额查询
     * @param request
     * @return
     */
    public HatQueryAcctBalanceResp queryAcctBalance(HatQueryAcctBalanceReq request) {
        return hatService.queryAcctBalance(request).get();
    }

    /**
     * 余额明细查询
     * @param balanceDetailQryReq
     * @return
     */
    public AcctBalanceDetailQryResp queryBalanceDetail(AcctBalanceDetailQryReq balanceDetailQryReq){
        return hatService.queryBalanceDetail(balanceDetailQryReq).get();
    }
    /**
     * 账户状态查询
     * @param accountStatusQryReq
     * @return
     */
    public AccountStatusQryResp queryAccountStatus( AccountStatusQryReq accountStatusQryReq){
        return hatService.queryAccountStatus(accountStatusQryReq).get();
    }
    /**
     * 账户状态修改
     * @param accountStatusModifyReq
     * @return
     */
    AccountStatusModifyResp modifyAccountStatus(AccountStatusModifyReq accountStatusModifyReq){
        return hatService.modifyAccountStatus(accountStatusModifyReq).get();
    }

    /**
     * 查询个人信息
     * @param personInfoReq
     * @return
     */
    public HatQryPersonInfoResp queryPersonInfo(HatQryPersonInfoReq personInfoReq){
        return hatService.queryPersonInfo(personInfoReq).get();
    }

    /**
     * 银行卡鉴权
     * @param bankCardAuthReq
     * @return
     */
    public HatBankCardAuthResp bankCardAuth(HatBankCardAuthReq bankCardAuthReq){
        return hatService.bankCardAuth(bankCardAuthReq).get();
    }
    /**
     * 个人银行卡绑定
     * @param bankCardBindReq
     * @return
     */
    public PersonBankCardBindResp bindPersonBankCard(PersonBankCardBindReq bankCardBindReq){
        return hatService.bindPersonBankCard(bankCardBindReq).get();
    }

    /**
     * 个人银行卡解绑
     * @param personBankCardUnbindReq
     * @return
     */
    public PersonBankCardUnbindResp unbindPersonBankCard(PersonBankCardUnbindReq personBankCardUnbindReq){
        return hatService.unbindPersonBankCard(personBankCardUnbindReq).get();
    }

    /**
     * 查询个人银行卡列表
     * @param bankCarListQryReq
     * @return
     */
    public PersonBankCarListQryResp qryPersonBandCardList(PersonBankCarListQryReq bankCarListQryReq){
        return hatService.qryPersonBandCardList(bankCarListQryReq).get();
    }

    /**
     * 检测是否支持绑定指定的银行卡
     * @param bankCardAcceptReq
     * @return
     */
    public BankCardAcceptResp acceptBankCard(BankCardAcceptReq bankCardAcceptReq){
        return hatService.acceptBankCard(bankCardAcceptReq).get();
    }
    /**
     * 询支持的绑卡银行列表
     * @return
     */
    public BankCardAcceptListQryResp qryBankCardAcceptList(BankCardAcceptListQryReq bankCardAcceptListQryReq){
        return hatService.qryBankCardAcceptList(bankCardAcceptListQryReq).get();
    }
    /**
     * 商户会员绑定银行卡
     * @param bankCardBindReq
     * @return
     */
    public EnterpriseBankCardBindResp bindEnterpriseBandCard(EnterpriseBankCardBindReq bankCardBindReq){
        return hatService.bindEnterpriseBandCard(bankCardBindReq).get();
    }
    /**
     * 商户会员查询已绑定的银行卡列表
     * @param bankCardListQryReq
     * @return
     */
    public EnterpriseBankCardListQryResp qryEnterpriseBankCardList(EnterpriseBankCardListQryReq bankCardListQryReq){
        return hatService.qryEnterpriseBankCardList(bankCardListQryReq).get();
    }

    /**
     * 商户银行卡解绑
     * @param bankCardUnbindReq
     * @return
     */
    public EnterpriseBankCardUnbindResp unbindEnterpriseBankCard(EnterpriseBankCardUnbindReq bankCardUnbindReq){
        return hatService.unbindEnterpriseBankCard(bankCardUnbindReq).get();
    }

    /**
     * 个人销户
     * @param cancelReq
     * @return
     */
    public PersonAcctNumberCancelResp cancelPersonAcctNumber(PersonAcctNumberCancelReq cancelReq){
        return hatService.cancelPersonAcctNumber(cancelReq).get();
    }

    /**
     * 提现
     * @param withDrawReq
     * @return
     */
    public WithDrawResp withdraw(WithDrawReq withDrawReq){
        return hatService.withdraw(withDrawReq).get();
    }
    /**
     * 单笔退货
     * @param refundReq
     * @return
     */
    public RefundResp refund(RefundReq refundReq){
        return hatService.refund(refundReq).get();
    }
    /**
     * 聚合下单
     * @param aggregateReq
     * @return
     */
    public AggregateResp aggregate (AggregateReq aggregateReq){
        return hatService.aggregate(aggregateReq).get();
    }
    /**
     * 绑卡充值
     * @param depositReq
     * @return
     */
    public DepositResp deposite (DepositReq depositReq){
        return hatService.deposite(depositReq).get();
    }
    /**
     * 支付
     * @param orderPaymentReq
     * @return
     */
    public OrderPaymentResp orderPay(OrderPaymentReq orderPaymentReq){
        return hatService.orderPay(orderPaymentReq).get();
    }
    /**
     * 支付订单详情查询
     * @param detailQryReq
     * @return
     */
    public OrderDetailQryResp queryPayDetail (OrderDetailQryReq detailQryReq){
        return hatService.queryPayDetail(detailQryReq).get();
    }

    /**
     * 退货订单详情查询
     * @param refundDetailQryReq
     * @return
     */
    public RefundDetailQryResp queryRufundDetail (RefundDetailQryReq refundDetailQryReq){
        return hatService.queryRufundDetail(refundDetailQryReq).get();
    }

    /**
     * 快捷获取动态码
     * @param dynamicCodeReq
     * @return
     */
    public QuickPaymentDynamicCodeResp getDynamicCode(QuickPaymentDynamicCodeReq dynamicCodeReq){
        return hatService.getDynamicCode(dynamicCodeReq).get();
    }

    /**
     * 快捷PCI查询
     * @param pciQryReq
     * @return
     */
    public QuickPaymentPciQryResp queryPci(QuickPaymentPciQryReq pciQryReq){
        return hatService.queryPci(pciQryReq).get();
    }

    /**
     * 快捷支付
     * @param quickPaymentReq
     * @return
     */
    public QuickPaymentResp quickPay(QuickPaymentReq quickPaymentReq){
        return hatService.quickPay(quickPaymentReq).get();
    }

    /**
     * 一键支付鉴权申请
     * @param indAuthReq
     * @return
     */
    public QuickPaymentIndAuthResp indAuth(QuickPaymentIndAuthReq indAuthReq){
        return hatService.indAuth(indAuthReq).get();
    }
    /**
     * 一键支付鉴权确认
     * @param indAuthVerifyReq
     * @return
     */
    public QuickPaymentIndAuthVerifyResp indAuthVerify(QuickPaymentIndAuthVerifyReq indAuthVerifyReq){
        return hatService.indAuthVerify(indAuthVerifyReq).get();
    }

    /**
     * 解绑平台用户银行卡
     * @param unbindCardReq
     * @return
     */
    public QuickPaymentUnbindCardResp unbindCard(QuickPaymentUnbindCardReq unbindCardReq){
        return hatService.unbindCard(unbindCardReq).get();
    }
}
