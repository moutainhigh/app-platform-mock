package com.bill99.mam.platform.controller.web.modelattribute;

public class QuickBindCardInfoDTO {
    private String name;
    private String idCardType;
    private String idCardTypeName;
    private String idCardNumber;
    private String shortBankAcctId;
    private String mobile;
    private String bankId;
    private String bankIdName;
    private String cardType;
    private String cardTypeName;
    private String createTime;
    private String updateTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdCardTypeName() {
        return idCardTypeName;
    }

    public void setIdCardTypeName(String idCardTypeName) {
        this.idCardTypeName = idCardTypeName;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getShortBankAcctId() {
        return shortBankAcctId;
    }

    public void setShortBankAcctId(String shortBankAcctId) {
        this.shortBankAcctId = shortBankAcctId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankIdName() {
        return bankIdName;
    }

    public void setBankIdName(String bankIdName) {
        this.bankIdName = bankIdName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("QuickBindCardInfoDTO{");
        sb.append("name='").append(name).append('\'');
        sb.append(", idCardType='").append(idCardType).append('\'');
        sb.append(", idCardTypeName='").append(idCardTypeName).append('\'');
        sb.append(", idCardNumber='").append(idCardNumber).append('\'');
        sb.append(", shortBankAcctId='").append(shortBankAcctId).append('\'');
        sb.append(", mobile='").append(mobile).append('\'');
        sb.append(", bankId='").append(bankId).append('\'');
        sb.append(", bankIdName='").append(bankIdName).append('\'');
        sb.append(", cardType='").append(cardType).append('\'');
        sb.append(", cardTypeName='").append(cardTypeName).append('\'');
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append(", updateTime='").append(updateTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
