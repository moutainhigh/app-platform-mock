package com.bill99.mam.platform.controller.web;

import com.bill99.mam.platform.common.constant.PlatformConst;
import com.bill99.mam.platform.common.constant.ResultCode;
import com.bill99.mam.platform.common.util.LoginIdUtil;
import com.bill99.mam.platform.controller.web.modelattribute.PersonalRegisterInfo;
import com.bill99.mam.platform.service.IRegisterService;
import com.bill99.mam.platform.service.model.request.PersonalRegisterReq;
import com.bill99.mam.platform.service.model.response.PersonalRegisterResp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class RegisterController {
    @Autowired
    private IRegisterService registerAppService;
    @RequestMapping("/personalRegisterView")
    public String personalRegisterView(Model model) {
        model.addAttribute("personalRegisterInfo", new PersonalRegisterInfo());
        return "c/personalRegister";
    }

    /**
     * 个人会员注册
     * @param personalRegisterInfo
     * @param model
     * @return
     */
    @RequestMapping("/personalRegister")
    public String personalRegister(@ModelAttribute("personalRegisterInfo") PersonalRegisterInfo personalRegisterInfo ,Model model) {

//        PersonalRegisterReq registerReq = new PersonalRegisterReq();
//        String loginId = personalRegisterInfo.getLoginId();
//        registerReq.setLoginId(loginId);
//        registerReq.setLoginIdType(String.valueOf(LoginIdUtil.getLoginIdType(loginId)));
//        registerReq.setLoginPwd(personalRegisterInfo.getLoginPwd());
//        registerReq.setPlatformId(PlatformConst.PLATFORM_ID);
//        registerReq.setName(personalRegisterInfo.getName());
//        registerReq.setIdCardNumber(personalRegisterInfo.getIdCardNumber());
//        registerReq.setIdCardType(personalRegisterInfo.getIdCardType());
//        PersonalRegisterResp registerResp = registerAppService.personalRegister(registerReq);
//        if (registerResp.getRspCode().equals(ResultCode.SUCCESS)){
//            return "redirect:/loginView";
//        }
//
//        model.addAttribute("personalRegisterResultMsg",registerResp.getRspMsg());

        return "personalRegisterResult";
    }
}
