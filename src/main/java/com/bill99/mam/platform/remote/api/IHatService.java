package com.bill99.mam.platform.remote.api;

import com.bill99.mam.platform.remote.adapter.RemoteCall;
import com.bill99.mam.platform.remote.dto.request.*;
import com.bill99.mam.platform.remote.dto.response.*;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IHatService {
    /**
     * 个人用户注册
     * @param registerReq
     * @return
     */
    @POST("person/register")
    RemoteCall<HatPersonalRegisterResp> personalRegister(@Body HatPersonalRegisterReq registerReq);
    /**
     * 余额查询
     * @param balanceReq
     * @return
     */
    @POST("account/balance")
    RemoteCall<HatQueryAcctBalanceResp> queryAcctBalance(@Body HatQueryAcctBalanceReq balanceReq);
    /**
     * 余额明细查询
     * @param balanceDetailQryReq
     * @return
     */
    @POST("account/detail/list")
    RemoteCall<AcctBalanceDetailQryResp> queryBalanceDetail(@Body AcctBalanceDetailQryReq balanceDetailQryReq);
    /**
     * 个人信息查询
     * @param personInfoReq
     * @return
     */
    @POST("person/info")
    RemoteCall<HatQryPersonInfoResp> queryPersonInfo(@Body HatQryPersonInfoReq personInfoReq);
    /**
     * 银行卡鉴权
     * @param bankCardAuthReq
     * @return
     */
    @POST("person/bankcard/auth")
    RemoteCall<HatBankCardAuthResp> bankCardAuth(@Body HatBankCardAuthReq bankCardAuthReq);
    /**
     * 个人银行卡绑定
     * @param bankCardBindReq
     * @return
     */
    @POST("person/bankcard/bind")
    RemoteCall<PersonBankCardBindResp> bindPersonBankCard(@Body PersonBankCardBindReq bankCardBindReq);
    /**
     * 个人银行卡解绑
     * @param bankCardUnbindReq
     * @return
     */
    @POST("person/bankcard/unbind")
    RemoteCall<PersonBankCardUnbindResp> unbindPersonBankCard(@Body PersonBankCardUnbindReq bankCardUnbindReq);
    /**
     * 查询个人银行卡列表
     * @param bankCarListQryReq
     * @return
     */
    @POST("person/bankcard/list")
    RemoteCall<PersonBankCarListQryResp> qryPersonBandCardList(@Body PersonBankCarListQryReq bankCarListQryReq);
    /**
     * 检测是否支持绑定指定的银行卡
     * @param bankCardAcceptReq
     * @return
     */
    @POST("person/bankcard/accept")
    RemoteCall<BankCardAcceptResp> acceptBankCard(@Body BankCardAcceptReq bankCardAcceptReq);
    /**
     * 询支持的绑卡银行列表
     * @param
     * @return
     */
    @POST("person/bankcard/acceptlist")
    RemoteCall<BankCardAcceptListQryResp> qryBankCardAcceptList(@Body BankCardAcceptListQryReq bankCardAcceptListQryReq);
    /**
     * 商户会员绑定银行卡
     * @param bankCardBindReq
     * @return
     */
    @POST("merchant/bankcard/bind")
    RemoteCall<EnterpriseBankCardBindResp> bindEnterpriseBandCard(@Body EnterpriseBankCardBindReq bankCardBindReq);
    /**
     * 商户会员查询已绑定的银行卡列表
     * @param bankCardListQryReq
     * @return
     */
    @POST("merchant/bankcard/list")
    RemoteCall<EnterpriseBankCardListQryResp> qryEnterpriseBankCardList(@Body EnterpriseBankCardListQryReq bankCardListQryReq);
    /**
     * 商户银行卡解绑
     * @param bankCardUnbindReq
     * @return
     */
    @POST("merchant/bankcard/unbind")
    RemoteCall<EnterpriseBankCardUnbindResp> unbindEnterpriseBankCard(@Body EnterpriseBankCardUnbindReq bankCardUnbindReq);
    /**
     * 个人销户
     * @param accountCancelReq
     * @return
     */
    @POST("person/cancel")
    RemoteCall<PersonAcctNumberCancelResp> cancelPersonAcctNumber(@Body PersonAcctNumberCancelReq accountCancelReq);

    /**
     * 账户状态查询
     * @param accountStatusQryReq
     * @return
     */
    @POST("account/status")
    RemoteCall<AccountStatusQryResp> queryAccountStatus(@Body AccountStatusQryReq accountStatusQryReq);
    /**
     * 账户状态修改
     * @param accountStatusModifyReq
     * @return
     */
    @POST("account/status/setting")
    RemoteCall<AccountStatusModifyResp> modifyAccountStatus(@Body AccountStatusModifyReq accountStatusModifyReq);

    /**
     * 提现
     * @param withDrawReq
     * @return
     */
    @POST("account/withdraw")
    RemoteCall<WithDrawResp> withdraw(@Body WithDrawReq withDrawReq);
    /**
     * 单笔退货
     * @param refundReq
     * @return
     */
    @POST("order/refund")
    RemoteCall<RefundResp> refund(@Body RefundReq refundReq);
    /**
     * 聚合下单
     * @param aggregateReq
     * @return
     */
    @POST("order/apply/aggregate")
    RemoteCall<AggregateResp> aggregate (@Body AggregateReq aggregateReq);
    /**
     * 绑卡充值
     * @param depositReq
     * @return
     */
    @POST("account/deposit")
    RemoteCall<DepositResp> deposite (@Body DepositReq depositReq);
    /**
     * 支付
     * @param orderPaymentReq
     * @return
     */
    @POST("order/pay")
    RemoteCall<OrderPaymentResp> orderPay (@Body OrderPaymentReq orderPaymentReq);
    /**
     * 支付订单详情查询
     * @param detailQryReq
     * @return
     */
    @POST("order/detail")
    RemoteCall<OrderDetailQryResp> queryPayDetail (@Body OrderDetailQryReq detailQryReq);

    /**
     * 退货订单详情查询
     * @param refundDetailQryReq
     * @return
     */
    @POST("order/refundDetail")
    RemoteCall<RefundDetailQryResp> queryRufundDetail (@Body RefundDetailQryReq refundDetailQryReq);

    /**
     * 快捷获取动态码
     * @param dynamicCodeReq
     * @return
     */
    @POST("quickPay/getDynamicNum")
    RemoteCall<QuickPaymentDynamicCodeResp> getDynamicCode(@Body QuickPaymentDynamicCodeReq dynamicCodeReq);

    /**
     * 快捷PCI查询
     * @param pciQryReq
     * @return
     */
    @POST("quickPay/pciQuery")
    RemoteCall<QuickPaymentPciQryResp> queryPci(@Body QuickPaymentPciQryReq pciQryReq);

    /**
     * 快捷支付
     * @param quickPaymentReq
     * @return
     */
    @POST("order/quickPay")
    RemoteCall<QuickPaymentResp> quickPay(@Body QuickPaymentReq quickPaymentReq);
    /**
     * 一键支付鉴权申请
     * @param indAuthReq
     * @return
     */
    @POST("quickPay/indAuth")
    RemoteCall<QuickPaymentIndAuthResp> indAuth(@Body QuickPaymentIndAuthReq indAuthReq);
    /**
     * 一键支付鉴权确认
     * @param indAuthVerifyReq
     * @return
     */
    @POST("quickPay/indAuthVerify")
    RemoteCall<QuickPaymentIndAuthVerifyResp> indAuthVerify(@Body QuickPaymentIndAuthVerifyReq indAuthVerifyReq);
    /**
     * 解绑平台用户银行卡
     * @param unbindCardReq
     * @return
     */
    @POST("quickPay/unbind")
    RemoteCall<QuickPaymentUnbindCardResp> unbindCard(@Body QuickPaymentUnbindCardReq unbindCardReq);
}
