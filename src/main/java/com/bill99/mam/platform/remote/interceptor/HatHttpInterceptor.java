package com.bill99.mam.platform.remote.interceptor;

import com.bill99.mam.platform.common.constant.PlatformConst;
import com.bill99.mam.platform.common.enumeration.message.RemoteMsgEnum;
import com.bill99.mam.platform.common.util.JdkSignService;

import static com.bill99.mam.platform.common.util.JsonDesensitiveUtil.*;

import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;

import okhttp3.*;
import okio.Buffer;
import okio.BufferedSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.bill99.mam.platform.common.util.Validator.checkNotNull;

/**
 * hathttp拦截器：加签
 * @author jerry.xu.coc
 */
public class HatHttpInterceptor implements Interceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(HatHttpInterceptor.class);
    private final Charset UTF8 = Charset.forName("UTF-8");
    private JdkSignService signService;
    private static final String X_99BILL_TRACEID = "X-99Bill-TraceId";
    private static final String X_99BILL_PLATFORMCODE = "X-99Bill-PlatformCode";
    private static final String X_99BILL_SIGNATURE = "X-99Bill-Signature";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody requestBody = request.body();
        //取得请求体
        String body =  getRequestBody(requestBody);
        //增加头部信息
        Request.Builder requestBuilder = addHeads(request, body);
        Request newRequest = requestBuilder.build();
        LOGGER.info("发送请求,method={},url={},headers={},body={}", newRequest.method(), newRequest.url(), getHeads(newRequest),sensitiveFilter(body));
        long startNs = System.nanoTime();
        Response response = chain.proceed(newRequest);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        //取得返回消息体
        String rBody = getResponseBody(response);
        LOGGER.info("收到响应,code={},message={},cost={},requestUrl={},request={},respose={}", response.code(), response.message(), tookMs
        		,response.request().url(),sensitiveFilter(body),sensitiveFilter(rBody));
        return response;
    }

    private Request.Builder addHeads(Request request, String body) {
        return request.newBuilder()
                    .addHeader(X_99BILL_TRACEID, String.valueOf(System.currentTimeMillis()))
                    .addHeader(X_99BILL_PLATFORMCODE, PlatformConst.PLATFORM_MERCHENT_CODE)
                    .addHeader(X_99BILL_SIGNATURE, signService.sign(body.getBytes(UTF8)));
    }

    /**
     * 打印请求头
     * @param request
     * @return
     */
    private String getHeads(Request request) {

        Headers heads = request.headers();
        Map<String, String> map = Splitter.on("\n")
                .omitEmptyStrings()
                .trimResults()
                .withKeyValueSeparator(": ")
                .split(heads.toString());

        map = Maps.filterKeys(map,new Predicate<String>(){
            @Override
            public boolean apply(String input) {
                return !input.startsWith(X_99BILL_SIGNATURE);
            }
        });
        return map.toString();
    }

    private String getResponseBody(Response response) throws IOException {
        checkNotNull(response, RemoteMsgEnum.RESPONSE_EMPTY);
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        checkNotNull(source, RemoteMsgEnum.RESPONSE_SOURCE_EMPTY);
        //取得返回消息体
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
                LOGGER.error("UnsupportedCharsetException", e);
            }
        }
        return buffer.clone().readString(charset);
    }

    private String getRequestBody(RequestBody requestBody) throws IOException {
    	checkNotNull(requestBody, RemoteMsgEnum.REQUEST_BODY_EMPTY);
    	Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        Charset charset = UTF8;
        MediaType contentType = requestBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }
        return buffer.readString(charset);
    }
	
	public void setSignService(JdkSignService signService) {
		this.signService = signService;
	}
}

