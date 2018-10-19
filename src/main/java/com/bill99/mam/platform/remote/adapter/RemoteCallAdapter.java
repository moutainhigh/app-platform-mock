package com.bill99.mam.platform.remote.adapter;

import retrofit2.Call;
import retrofit2.CallAdapter;

import java.lang.reflect.Type;

public class RemoteCallAdapter<R> implements CallAdapter<R,RemoteCall<R>> {

	private final Type responseType;

    public RemoteCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public RemoteCall<R> adapt(Call<R> call) {
        return new RemoteCall<>(call);
    }
}
