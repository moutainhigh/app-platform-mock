package com.bill99.mam.platform.common.enumeration;

public enum CustomerType {

    PERSONAL(1, "personal", "个人会员"),
    ENTERPRISE(2, "enterprise", "企业会员"),;

    private int type;
    private String code;
    private String desc;
    private CustomerType(int type,String code, String desc){
        this.type = type;
        this.code = code;
        this.desc = desc;
    }

    public int type(){
        return type;
    }
    public String code (){
        return code;
    }
    public String desc (){
    	return desc;
    }
    
    public static CustomerType customerType(int type) {
    	CustomerType result = null;
    	for (CustomerType e : CustomerType.values()) {
    		if (e.type == type) {
    			result = e;
    			break;
    		}
    	}
    	return result;
    }
    
    public static boolean isPersonal(int type) {
    	return PERSONAL.type() == type;
    }
}
