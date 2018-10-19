package com.bill99.mam.platform.common.enumeration;

import org.apache.commons.lang3.StringUtils;

public enum EnterpriseBankEnum {
    ICBC("ICBC", "中国工商银行"),
    CMB("CMB", "招商银行"),
    ABC("ABC", "中国农业银行"),
    HZB("HZB", "杭州银行"),
    JSB("JSB", "江苏银行"),
    CCB("CCB", "中国建设银行"),
    BOC("BOC", "中国银行"),
    COMM("COMM", "交通银行"),
    CMBC("CMBC", "中国民生银行"),
    SDB("SDB", "深圳发展银行"),
    GDB("GDB", "广发银行"),
    CITIC("CITIC", "中信银行"),
    HXB("HXB", "华夏银行"),
    SPDB("SPDB", "上海浦东发展银行"),
    CIB("CIB", "兴业银行"),
    CEB("CEB", "中国光大银行"),
    GZRCC("GZRCC", "广州农村商业银行"),
    GZCB("GZCB", "广州银行"),
    SHRCC("SHRCC", "上海农商银行"),
    POST("POST", "中国邮政储蓄"),
    BOB("BOB", "北京银行"),
    CBHB("CBHB", "渤海银行"),
    RCB("RCB", "农信银中心"),
    SHB("SHB", "上海银行"),
    NCSYYH("NCSYYH", "农村商业银行"),
    CSSYYH("CSSYYH", "城市商业银行"),
    NCXYHZS("NCXYHZS", "农村信用合作社"),
    NJCB("NJCB", "南京市商业银行"),
    NBB("NBB", "宁波银行"),
    CBC("CBC", "花旗银行"),
    HSB("HSB", "徽商银行"),
    CSB("CSB", "长沙银行"),
    HSBC("HSBC", "汇丰银行"),
    SPAB("SPAB", "平安银行"),
    BEA("BEA", "东亚银行"),
    CZB("CZB", "浙商银行"),
    CNPY("CNPY", "中国银联"),
    CNPYGZ("CNPYGZ", "广州银联"),
    CNPYHN("CNPYHN", "湖南银联"),
    CNPYGD("CNPYGD", "广东银联"),
    CNPYSH("CNPYSH", "上海银联"),
    XIB("XIB", "厦门国际银行"),
    WZYH("WZYH", "外资银行");


    private String bankCode;
    private String bankName;
    EnterpriseBankEnum(String bankCode, String bankName){
        this.bankCode = bankCode;
        this.bankName = bankName;
    }

    /**
     * 获取银行名字
     * @param bankCode
     * @return
     */
    public static String name(String bankCode){
        String bankName = "";
        for (EnterpriseBankEnum bank: values()) {
            if(StringUtils.equals(bankCode, bank.getBankCode())){
                bankName = bank.getBankName();
                break;
            }
        }
        return bankName;
    }
    public String getBankCode() {
        return bankCode;
    }

    public String getBankName() {
        return bankName;
    }
}
