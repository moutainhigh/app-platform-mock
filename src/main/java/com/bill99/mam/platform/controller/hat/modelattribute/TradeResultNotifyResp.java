package com.bill99.mam.platform.controller.hat.modelattribute;

import lombok.Data;

import java.io.Serializable;
@Data
public class TradeResultNotifyResp implements Serializable {
    private static final long serialVersionUID = -5944602827804663059L;
    private String rspCode;
    private String rspMsg;
}
