package com.bill99.mam.platform.common.constant;

import com.bill99.mam.platform.common.util.DateUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Component
public class PlatformConst {
    public static final String DATE_FORMAT_DEFAULT = DateUtil.FORMAT_PATTEN_HAT;
    //默认结算周期
    public static final String DEFAULT_SETTLE_PERIOD = "T+1";
    public static final String PLATFORM_MERCHENT_NAME = "MockSystem";
    public static final String DEFAULT_CURRENCY_CODE = "CNY";
    public static final String DEFAULT_ORDER_TYPE = "110002";
    /** 对应平台在快钱注册的会员号*/
    public static String PLATFORM_MERCHENT_CODE;
    /** 交易结果通知URL */
    public static String BG_URL;
    @PostConstruct
    public void initMerchentCode() {
    	PLATFORM_MERCHENT_CODE = "";
    	BG_URL = "";
	}
}
