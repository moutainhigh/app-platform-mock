package com.bill99.mam.platform.controller.web.modelattribute;

import lombok.Data;

@Data
public class OrderPay {
	private String orderId;
	private String bankAcctId;
	/**
	 * 10-账户余额;11-银行卡支付;12-快捷支付;13-聚合支付
	 * 
	 */
	private String functionCode;
	/**
	 * 主收款方
	 */
	private String payeeUId;
}
