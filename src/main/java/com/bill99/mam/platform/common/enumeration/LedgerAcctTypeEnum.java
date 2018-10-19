package com.bill99.mam.platform.common.enumeration;

public enum LedgerAcctTypeEnum {

    GOODS(1),//普通商品
    WITH_DRAW(2),//提现
    DEPOSITE(3),//充值
    REFUND(4),//退货
    ;

    private int ledgerAcctType;
    private LedgerAcctTypeEnum(int ledgerAcctType){
        this.ledgerAcctType = ledgerAcctType;
    }
    public int value(){
        return this.ledgerAcctType;
    }
}
