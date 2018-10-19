package com.bill99.mam.platform.common.enumeration;

import org.apache.commons.lang3.StringUtils;

public enum CardTypeEnum {
    CREDIT_CARD("0001", "贷记卡"),
    DEBIT_CARD("0002", "借记卡");

    private String cardType;
    private String cardName;
    CardTypeEnum(String cardType, String cardName){
        this.cardType = cardType;
        this.cardName = cardName;
    }
    public String getCardType() {
        return cardType;
    }

    public String getCardName() {
        return cardName;
    }

    /**
     * 获取银行卡名字
     * @param cardType
     * @return
     */
    public static String name(String cardType){
        String cardName = "";
        for (CardTypeEnum card: values()) {
            if(StringUtils.equals(cardType, card.getCardType())){
                cardName = card.getCardName();
                break;
            }
        }
        return cardName;
    }
}
