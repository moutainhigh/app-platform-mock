package com.bill99.mam.platform.controller.web;

import com.bill99.mam.platform.common.constant.PlatformConst;
import com.bill99.mam.platform.common.constant.ResultCode;
import com.bill99.mam.platform.common.enumeration.CustomerType;
import com.bill99.mam.platform.controller.web.modelattribute.LoginInfo;
import com.bill99.mam.platform.service.ILoginService;
import com.bill99.mam.platform.service.model.request.LoginReq;
import com.bill99.mam.platform.service.model.response.LoginResp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@Controller
public class LoginController {
    @Autowired
    private ILoginService loginAppService;
    @RequestMapping("/loginView")
    public String login(Model model){
        model.addAttribute("loginInfo", new LoginInfo());
        return "loginView";
    }

    @RequestMapping(value ={ "/login" }, method = {RequestMethod.POST})
    @ResponseBody
    public Object loginValidate(@RequestBody LoginInfo loginInfo){
        LoginReq loginReq = new LoginReq();
        // TODO: 2018/3/6 暂时支持个人登录 
        loginReq.setCustomerType(loginInfo.getCustomerType());
        loginReq.setLoginId(loginInfo.getLoginId());
        loginReq.setLoginPwd(loginInfo.getLoginPwd());
        loginReq.setPlatformId(PlatformConst.PLATFORM_MERCHENT_CODE);
        LoginResp loginResp = loginAppService.login(loginReq);
        if (loginResp.getRspCode().equals(ResultCode.SUCCESS)){
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpSession session = request.getSession();
            loginInfo.setCustomerId(loginResp.getCustomerId());
            loginInfo.setName(loginResp.getName());
            session.setAttribute("loginInfo",loginInfo);
            session.setMaxInactiveInterval(30 * 60);//失效时间为30分钟
        }
        return loginResp;
    }
    
    @RequestMapping("/login-out")
    public void loginOut(HttpServletResponse response, HttpSession session) throws IOException {
    	session.removeAttribute("loginInfo");
    	response.sendRedirect("index.html");
    }
    
    @RequestMapping("/greeting")
    public String greeting(HttpSession session, Model model) {
    	LoginInfo loginInfo = (LoginInfo)session.getAttribute("loginInfo");
        model.addAttribute("idContent", loginInfo.getLoginId());
        return CustomerType.customerType(Integer.valueOf(loginInfo.getCustomerType())).code() + "/greeting";
    }
    
}
