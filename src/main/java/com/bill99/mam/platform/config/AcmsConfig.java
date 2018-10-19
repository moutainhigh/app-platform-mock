package com.bill99.mam.platform.config;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class AcmsConfig {
	@Value("${platform.mock.keyStorePath}")
	private String keyStorePath;
	@Value("${platform.mock.certPath}")
	private String certPath;
	@Value("${platform.mock.hatGatewayUrl}")
	private String hatGatewayUrl;
	@Value("${platform.mock.keyStorePwd}")
	private String keyStorePwd;
	@Value("${platform.mock.keyPwd}")
	private String keyPwd;
	@Value("${platform.mock.keyAlias}")
	private String keyAlias;
	@Value("${platform.mock.wechatAppId}")
	private String wechatAppId ;
	@Value("${platform.mock.postKey}")
	private String postKey;
    @Value("${platform.mock.switch.sdk}")
    private String switchSDK;
}
