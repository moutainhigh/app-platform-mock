package com.bill99.mam.platform.remote.adapter;

import static com.bill99.mam.platform.common.util.Validator.checkNotNull;
import retrofit2.Call;
import retrofit2.Response;

import com.bill99.mam.platform.common.enumeration.message.RemoteMsgEnum;
import com.bill99.mam.platform.common.exception.ExceptionFactory;

public class RemoteCall<R> {

    public final Call<R> call;

    public RemoteCall(Call<R> call) {
        this.call = call;
    }

    public R get(){
        checkNotNull(call,RemoteMsgEnum.CALL_IS_NULL);
        try {
            Response<R> response = call.execute();
            return response.body();
        } catch (Exception e) {
            ExceptionFactory.throwException(RemoteMsgEnum.CALL_RESPONSE_ERROR,e);
        }
        return null;
    }

}
