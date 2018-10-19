package com.bill99.mam.platform.persistence.repository;

import static com.bill99.mam.platform.common.util.Validator.checkNotNull;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.bill99.mam.platform.common.enumeration.message.RepositoryMsgEnum;
import com.bill99.mam.platform.common.util.WrappedBeanCopier;
import com.bill99.mam.platform.persistence.dao.LoginPasswordMapper;
import com.bill99.mam.platform.persistence.dto.LoginPasswordDto;
import com.bill99.mam.platform.persistence.entity.LoginPassword;
import com.bill99.mam.platform.persistence.entity.LoginPasswordExample;

@Component
public class LoginPasswordRepository {
    @Resource
    private LoginPasswordMapper loginPasswordMapper;

    /**
     * 校验用户密码
     * 注：所以在Repository中的密码都是MD5加密处理过的
     * @param request
     * @return
     */
    public boolean validateLoginPwd(LoginPasswordDto request) {
    	checkNotNull(request,RepositoryMsgEnum.LOGIN_PWD_DTO_EMPTY);
    	String loginPwd = request.getLoginPassword();
    	Long customerId = request.getCustomerId();
    	checkNotNull(customerId,RepositoryMsgEnum.CUSTOMER_ID_EMPTY);
    	checkNotNull(loginPwd,RepositoryMsgEnum.LOGIN_PWD_EMPTY);
    	
        LoginPasswordDto loginPwdDto = queryDtoByCustomerId(customerId);
        checkNotNull(loginPwdDto,RepositoryMsgEnum.CUSTOMER_ID_NOT_EXIST);
        /** 密码已经MD5加密过 */
        if (StringUtils.equals(loginPwd, loginPwdDto.getLoginPassword())){
        	return true;
        }

        return false;
    }

    /**
     * 新增登录密码
     * @param loginPwdDto
     * @return
     */
    public LoginPasswordDto addLoginPwd(LoginPasswordDto loginPwdDto) {
    	checkNotNull(loginPwdDto,RepositoryMsgEnum.LOGIN_PWD_DTO_EMPTY);
    	checkNotNull(loginPwdDto.getCustomerId(),RepositoryMsgEnum.CUSTOMER_ID_EMPTY);
    	checkNotNull(loginPwdDto.getLoginPassword(),RepositoryMsgEnum.LOGIN_PWD_EMPTY);
        
        loginPwdDto.setCreateTime(new Date());
        loginPwdDto.setUpdateTime(new Date());
        LoginPassword loginPassword = WrappedBeanCopier.copyProperties(loginPwdDto, LoginPassword.class);
        loginPasswordMapper.insert(loginPassword);
        return WrappedBeanCopier.copyProperties(loginPassword, LoginPasswordDto.class);
    }

    /**
     * 根据客户号查询用户登录密码信息
     * @param customerId
     * @return
     */
    public  LoginPasswordDto queryDtoByCustomerId(Long customerId){
        if(customerId == null) return null;
        LoginPasswordExample passwordExample = new LoginPasswordExample();
        passwordExample.createCriteria().andCustomerIdEqualTo(customerId);
        List<LoginPassword> loginPasswordList = loginPasswordMapper.selectByExample(passwordExample);
        if (CollectionUtils.isEmpty(loginPasswordList)){return null;}
        return WrappedBeanCopier.copyProperties(loginPasswordList.get(0),LoginPasswordDto.class);
    }

    /**
     * 更新登录密码
     * @param loginPwdDto
     * @return
     */
    public LoginPasswordDto updateLoginPwd(LoginPasswordDto loginPwdDto){
    	
    	checkNotNull(loginPwdDto,RepositoryMsgEnum.LOGIN_PWD_DTO_EMPTY);
    	checkNotNull(loginPwdDto.getId(),RepositoryMsgEnum.LOGIN_PWD_DTO_ID_EMPTY);
    	checkNotNull(loginPwdDto.getLoginPassword(),RepositoryMsgEnum.LOGIN_PWD_EMPTY);

        loginPwdDto.setUpdateTime(new Date());
        LoginPassword loginPassword = WrappedBeanCopier.copyProperties(loginPwdDto,LoginPassword.class);
        loginPasswordMapper.updateByPrimaryKeySelective(loginPassword);
        return WrappedBeanCopier.copyProperties(loginPassword,LoginPasswordDto.class);
    }

}
