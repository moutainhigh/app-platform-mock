package com.bill99.mam.platform.controller.web.modelattribute;

public class QuickPaymentDTO {
    private String orderId;
    private String mobile;
    private String validCode;
    private String token;
    private String isOpenQuick;
    private String isQuickPayment;
    private String cardType;
    private String bankAcctId;
    private String shortBankAcctId;
    private String name;
    private String idCardType;
    private String idCardTypeName;
    private String idCardNumber;
    //isSubsidiaryCard

    private String payeeUId;
    private String expiredDate;
    private String cvv2;


    private String bankId;
    private String bankIdName;

    private String cardTypeName;
    private String createTime;
    private String updateTime;

    private String payMode;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

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

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIsOpenQuick() {
        return isOpenQuick;
    }

    public void setIsOpenQuick(String isOpenQuick) {
        this.isOpenQuick = isOpenQuick;
    }

    public String getBankAcctId() {
        return bankAcctId;
    }

    public void setBankAcctId(String bankAcctId) {
        this.bankAcctId = bankAcctId;
    }

    public String getPayeeUId() {
        return payeeUId;
    }

    public void setPayeeUId(String payeeUId) {
        this.payeeUId = payeeUId;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getIsQuickPayment() {
        return isQuickPayment;
    }

    public void setIsQuickPayment(String isQuickPayment) {
        this.isQuickPayment = isQuickPayment;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }
}
