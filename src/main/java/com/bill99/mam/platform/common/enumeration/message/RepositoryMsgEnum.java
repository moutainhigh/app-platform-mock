package com.bill99.mam.platform.common.enumeration.message;

import com.bill99.mam.platform.common.IMessage;
/**
 * 持久层提示信息 2001-2999
 * @author jerry.xu.coc
 *
 */
public enum RepositoryMsgEnum implements IMessage {
	CUSTOMER_NOT_EXIST("2001", "客户不存在"), LOGIN_PWD_ILLEGAL("2002", "登录密码不正确"), 
	CUSTOMER_DTO_NOT_EXIST("2003", "客户DTO不存在"), CUSTOMER_LOGIN_ID_EXIST("2004", "登录ID已存在"), 
	LEDGER_CONFIG_DTO_EMPTY("2005", "分账配置DTO不能为空"),LOGIN_PWD_DTO_EMPTY("2006", "登录密码DTO不能为空"),
	CUSTOMER_ID_EMPTY("2007", "用户ID不能为空"),CUSTOMER_ID_NOT_EXIST("2008", "用户ID不存在"),
	LOGIN_PWD_EMPTY("2009", "登录密码不能为空"),LOGIN_PWD_DTO_ID_EMPTY("2010", "客户DTO对象ID不能为空"),
	ORDER_DETAIL_DTO_EMPTY("2011", "子订单DTO对象不能为空"),ORDER_PRODUCTITEM_DTO_EMPTY("2012", "商品订单DTO对象不能为空"),
	ORDER__DTO_EMPTY("2013", "主订单DTO对象不能为空"),REFUND__DTO_EMPTY("2014", "退货订单DTO对象不能为空"),
	ORDER_PRODUCTITEM_ID_EMPTY("2015", "商品订单ID不能为空"),
	;
	private String code;
	private String message;

	private RepositoryMsgEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public String message() {
		return message;
	}

	@Override
	public String code() {
		return code;
	}

}
