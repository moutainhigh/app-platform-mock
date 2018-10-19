package com.bill99.mam.platform.remote.dto.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
@Data
@ToString(callSuper = true)
public class AccountBalance implements Serializable {
    private static final long serialVersionUID = 6995846571385619421L;
    /** */
    private String accountType;
    private String accountName;
    private String balance;
}
