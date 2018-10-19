package com.bill99.mam.platform.common.util;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OkHttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);

	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private static OkHttpClient client = new OkHttpClient();
	
	private static class InstanceHolder {
		private static final OkHttpUtil INSTANCE = new OkHttpUtil();
	}
	
	private OkHttpUtil() {}
	
	public static OkHttpUtil getInstance() {
		return InstanceHolder.INSTANCE;
	}

	public String post(String url, String json) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder().url(url).post(body).build();
		Response response = client.newCall(request).execute();
		logger.info("请求url={},返回信息：{}", url, response);
		if (response.isSuccessful()) {
			return response.body().string();
		} else {
			throw new IOException("Unexpected code " + response);
		}
	}
}
