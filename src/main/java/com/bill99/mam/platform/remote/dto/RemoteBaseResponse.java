package com.bill99.mam.platform.remote.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RemoteBaseResponse implements Serializable{
	private static final long serialVersionUID = 6631632102552375987L;
	private String rspCode;
    private String rspMsg;
}
