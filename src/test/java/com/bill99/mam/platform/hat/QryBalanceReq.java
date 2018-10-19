package com.bill99.mam.platform.hat;

import lombok.Data;

import java.io.Serializable;

@Data
public class QryBalanceReq implements Serializable{
        private static final long serialVersionUID = 1L;
        private String appId;
        private String[] accountType;
        private String uid;
}