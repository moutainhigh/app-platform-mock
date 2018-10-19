package com.bill99.mam.platform.remote.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseResponse;

@Getter
@Setter
@ToString(callSuper = true)
public class PersonBankCardUnbindResp extends RemoteBaseResponse {
    private static final long serialVersionUID = 8458789013667369749L;
}
