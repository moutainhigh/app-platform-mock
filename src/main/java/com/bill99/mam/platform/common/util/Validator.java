package com.bill99.mam.platform.common.util;

import com.bill99.mam.platform.common.IMessage;
import com.bill99.mam.platform.common.exception.ExceptionFactory;
import com.bill99.mam.platform.remote.dto.RemoteBaseRequest;

import java.util.Optional;

public class Validator {

    public static void checkArgument(boolean expression) {
        if (!expression){
            ExceptionFactory.throwException("1000","argument illegal");
        }
    }
    
    public static void checkArgument(boolean expression,String message) {
        if (!expression){
            ExceptionFactory.throwException("1000",message);
        }
    }

    public static void checkArgument(boolean expression, IMessage message) {
        if (!expression){
            ExceptionFactory.throwException(message);
        }
    }

    public static <T> T checkNotNull(T reference) {
        return Optional.ofNullable(reference).orElseThrow(()->ExceptionFactory.create("2000","object is null"));
    }
    
    public static <T> T checkNotNull(T reference,String message) {
        return Optional.ofNullable(reference).orElseThrow(()->ExceptionFactory.create("2000",message));
    }

    public static <T> T checkNotNull(T reference,IMessage message) {
        return Optional.ofNullable(reference).orElseThrow(()->ExceptionFactory.create(message));
    }

    public static void main(String[] args) {
        RemoteBaseRequest remoteBaseRequest = null;
        System.out.println(checkNotNull(remoteBaseRequest));
    }
}
