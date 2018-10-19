package com.bill99.mam.platform.remote.dto.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.bill99.mam.platform.remote.dto.RemoteBaseResponse;
@Getter
@Setter
@ToString(callSuper = true)
public class HatPersonalRegisterResp extends RemoteBaseResponse implements Serializable {
	private static final long serialVersionUID = -7935047194212610081L;
	private String openId;
}
