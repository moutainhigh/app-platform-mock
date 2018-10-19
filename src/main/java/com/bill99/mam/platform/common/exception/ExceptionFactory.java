package com.bill99.mam.platform.common.exception;


import com.bill99.mam.platform.common.IMessage;

public class ExceptionFactory {
	
	public static CommonException create(IMessage messageEnum){
		return new CommonException(messageEnum.message(),messageEnum.code());
	}
	
	public static CommonException create(IMessage messageEnum,Throwable e){
		return new CommonException(messageEnum.message(),messageEnum.code(),e);
	}
	
	public static CommonException create(String errorCode,String errorMsg){
		return new CommonException(errorMsg,errorCode);
	}
	
	public static CommonException create(String errorCode,String errorMsg,Throwable e){
		return new CommonException(errorMsg,errorCode,e);
	}
	
	public static void throwException(IMessage registryEnum) throws CommonException{
		throw create(registryEnum);
	}
	
	public static void throwException(IMessage registryEnum,Throwable e) throws CommonException{
		throw create(registryEnum,e);
	}
	
	public static void throwException(String errorCode,String errorMsg) throws CommonException{
		throw create(errorCode,errorMsg);
	}
	
	public static void throwException(String errorCode,String errorMsg,Throwable e) throws CommonException{
		throw create(errorCode,errorMsg,e);
	}

}