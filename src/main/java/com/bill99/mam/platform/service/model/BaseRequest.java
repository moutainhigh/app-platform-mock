package com.bill99.mam.platform.service.model;

import com.bill99.mam.platform.common.constant.PlatformConst;
import com.google.common.base.MoreObjects;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;
@Getter
@Setter
public class BaseRequest implements Serializable {
	private static final long serialVersionUID = 6729895962013872468L;
	@NotNull
	private String appId;
	private String requestTime = DateTime.now().toString(PlatformConst.DATE_FORMAT_DEFAULT);
	private String traceId = UUID.randomUUID().toString();
	private String accessToken;
	private String bizCode;
	private String extraData;

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).omitNullValues()
				.add("appId", appId)
				.add("requestTime", requestTime)
				.add("traceId", traceId)
				.add("accessToken", accessToken)
				.add("bizCode", bizCode)
				.add("extraData", extraData)
				.toString();
	}
}
