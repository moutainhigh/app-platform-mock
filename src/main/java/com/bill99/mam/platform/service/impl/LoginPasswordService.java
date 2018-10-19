package com.bill99.mam.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bill99.mam.platform.persistence.dto.LoginPasswordDto;
import com.bill99.mam.platform.persistence.repository.LoginPasswordRepository;
import com.bill99.mam.platform.service.ILoginPasswordService;

@Component
public class LoginPasswordService implements ILoginPasswordService{
	
    @Autowired
    private LoginPasswordRepository loginPasswordRepository;
    
    @Override
    public LoginPasswordDto addLoginPwd(LoginPasswordDto loginPasswordDto) {
    	loginPasswordDto = loginPasswordRepository.addLoginPwd(loginPasswordDto);
    	return loginPasswordDto;
    }
    
}
