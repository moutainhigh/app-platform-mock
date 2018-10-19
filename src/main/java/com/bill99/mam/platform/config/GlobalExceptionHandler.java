package com.bill99.mam.platform.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.bill99.mam.platform.common.exception.CommonException;
import com.bill99.mam.platform.service.model.BaseResponse;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(value = CommonException.class)
    public Object errorHandler(CommonException e) {
		BaseResponse response = new BaseResponse(e.getErrorCode(), e.getMessage());
		return response;
    }
}
