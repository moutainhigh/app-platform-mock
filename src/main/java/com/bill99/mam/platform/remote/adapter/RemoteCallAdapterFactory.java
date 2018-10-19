package com.bill99.mam.platform.remote.adapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class RemoteCallAdapterFactory extends CallAdapter.Factory {
    public static final RemoteCallAdapterFactory INSTANCE = new RemoteCallAdapterFactory();
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        // 获取原始类型
        Class<?> rawType = getRawType(returnType);
        // 返回值必须是HatCall并且带有泛型
        if (rawType == RemoteCall.class && returnType instanceof ParameterizedType) {
            Type callReturnType = getParameterUpperBound(0, (ParameterizedType) returnType);
            return new RemoteCallAdapter<>(callReturnType);
        }
        return null;
    }
}
