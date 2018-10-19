package com.bill99.mam.platform.remote.config;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.bill99.mam.platform.common.enumeration.message.RemoteMsgEnum;
import com.bill99.mam.platform.common.exception.ExceptionFactory;
import com.bill99.mam.platform.common.util.JdkSignService;
import com.bill99.mam.platform.config.AcmsConfig;
import com.bill99.mam.platform.remote.adapter.RemoteCallAdapterFactory;
import com.bill99.mam.platform.remote.interceptor.HatHttpInterceptor;
import com.google.gson.GsonBuilder;

@Configuration
public class RestAdapterConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(RestAdapterConfig.class);
	@Autowired
	private AcmsConfig acmsConfig;
	/**
	 * 获取RestAdapter单例Bean
	 * @return
	 */
	@Bean("hatRestAdapter")
	public Retrofit getRestAdapter() {

		LOGGER.info("acmsConfig={}", acmsConfig);

		OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
				.connectTimeout(20, TimeUnit.SECONDS)
				.readTimeout(20, TimeUnit.SECONDS)
				.writeTimeout(20, TimeUnit.SECONDS)
				.addInterceptor(initHatHttpInterceptor());

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(acmsConfig.getHatGatewayUrl())
				.addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyyMMddHHmmss").create()))
				.addCallAdapterFactory(RemoteCallAdapterFactory.INSTANCE)
				.client(okHttpClient.build()).build();

		return retrofit;
	}

	private HatHttpInterceptor initHatHttpInterceptor() {
		// 初始化加验签服务
		JdkSignService signService = new JdkSignService();
		try {
			signService.initKey(acmsConfig);
		} catch (Exception e) {
			LOGGER.error("initHatHttpInterceptor signService initKey error", e);
			ExceptionFactory.throwException(RemoteMsgEnum.SIGN_SERVICE_INIT_ERROR,e);
		}
		
		HatHttpInterceptor interceptor = new HatHttpInterceptor();
		interceptor.setSignService(signService);
		
		return interceptor;
	}
}