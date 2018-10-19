package com.bill99.mam.platform.controller.web.personal;

import com.bill99.mam.platform.common.constant.PlatformConst;
import com.bill99.mam.platform.common.enumeration.CustomerType;
import com.bill99.mam.platform.common.util.LoginIdUtil;
import com.bill99.mam.platform.common.util.WrappedBeanCopier;
import com.bill99.mam.platform.controller.web.modelattribute.CustomerVo;
import com.bill99.mam.platform.controller.web.modelattribute.LoginInfo;
import com.bill99.mam.platform.controller.web.modelattribute.PersonalBindCardInfo;
import com.bill99.mam.platform.controller.web.modelattribute.PersonalRegisterInfo;
import com.bill99.mam.platform.controller.web.modelattribute.RechargeWithdrawalsCardInfo;
import com.bill99.mam.platform.controller.web.util.SessionUtil;
import com.bill99.mam.platform.persistence.dto.CustomerDto;
import com.bill99.mam.platform.remote.dto.response.BindCardInfo;
import com.bill99.mam.platform.service.IGoodsService;
import com.bill99.mam.platform.service.ILoginPasswordService;
import com.bill99.mam.platform.service.IPersonalCustomerService;
import com.bill99.mam.platform.service.model.BaseResponse;
import com.bill99.mam.platform.service.model.request.PersonalBindCardReq;
import com.bill99.mam.platform.service.model.request.PersonalRegisterReq;
import com.bill99.mam.platform.service.model.request.PersonalUnBindCardReq;
import com.bill99.mam.platform.service.model.response.PersonalBindCardResp;
import com.bill99.mam.platform.service.model.response.PersonalRegisterResp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.util.List;

@Controller
public class PersonalCustomerController {
    @Resource
    private IPersonalCustomerService personalCustomerService;
    @Resource
    private ILoginPasswordService loginPasswordService;
    @Resource
    private IGoodsService goodsService;
    
    @RequestMapping("/personal/query")
    public String queryByid(HttpSession session, Model model){
    	LoginInfo loginInfo = SessionUtil.getLoginInfoFromSession(session);
        CustomerDto customerDto = personalCustomerService.queryCustomerById(new Long(loginInfo.getCustomerId()));
        CustomerVo copyProperties = WrappedBeanCopier.copyProperties(customerDto, CustomerVo.class);
        model.addAttribute("customer", copyProperties);
        return "personal/query";
    }
    
    @RequestMapping("/personal/bindCardPage")
    public String personalBindCardPage(HttpSession session, Model model) {
    	LoginInfo loginInfo = SessionUtil.getLoginInfoFromSession(session);
    	model.addAttribute("loginInfo", loginInfo);
    	return "personal/bindCard";
    }
    
    @RequestMapping("/personal/bankCardAuth")
    @ResponseBody
    public Object personalBankCardAuth(@RequestBody PersonalBindCardInfo bindCardInfo, HttpSession session, Model model) {
    	
    	PersonalBindCardReq req = new PersonalBindCardReq();
    	LoginInfo loginInfo = SessionUtil.getLoginInfoFromSession(session);
    	req.setUId(loginInfo.getCustomerId());
    	req.setName(bindCardInfo.getName());
    	req.setIdType(bindCardInfo.getIdType());
    	req.setIdentificationCard(bindCardInfo.getIdentificationCard());
    	req.setBankAcctId(bindCardInfo.getBankAcctId());
    	req.setCvv2(bindCardInfo.getCvv2());
//    	req.setValidCode(bindCardInfo.getValidCode());
    	req.setPhone(bindCardInfo.getPhone());
    	req.setExpiredDate(bindCardInfo.getExpiredDate());
    	
    	String token = personalCustomerService.personalBankCardAuth(req);
    	PersonalBindCardResp rsp = new PersonalBindCardResp();
    	rsp.setToken(token);
    	return rsp;
    }
    
    @RequestMapping("/personal/bindCard")
    @ResponseBody
    public Object personalBindCard(@RequestBody PersonalBindCardInfo bindCardInfo, HttpSession session, Model model) {
    	
    	PersonalBindCardReq req = new PersonalBindCardReq();
    	LoginInfo loginInfo = SessionUtil.getLoginInfoFromSession(session);
    	req.setUId(loginInfo.getCustomerId());
    	req.setName(bindCardInfo.getName());
    	req.setIdType(bindCardInfo.getIdType());
    	req.setIdentificationCard(bindCardInfo.getIdentificationCard());
    	req.setBankAcctId(bindCardInfo.getBankAcctId());
    	req.setCvv2(bindCardInfo.getCvv2());
    	req.setExpiredDate(bindCardInfo.getExpiredDate());
    	req.setPhone(bindCardInfo.getPhone());
    	req.setValidCode(bindCardInfo.getValidCode());
    	req.setToken(bindCardInfo.getToken());
    	
    	String memberBankAcctId = personalCustomerService.personBindBankCard(req);
    	PersonalBindCardResp rsp = new PersonalBindCardResp();
    	rsp.setMemberBankAcctId(memberBankAcctId);
    	return rsp;
    }
    
    @RequestMapping("/personal/unBindbankCard/{memberBankAcctId}")
    @ResponseBody
    public Object personalUnBindbankCard(@PathVariable String memberBankAcctId, HttpSession session, Model model) {
    	
    	PersonalUnBindCardReq req = new PersonalUnBindCardReq();
    	req.setMemberBankAcctId(memberBankAcctId);
    	
    	LoginInfo loginInfo = SessionUtil.getLoginInfoFromSession(session);
    	req.setUId(loginInfo.getCustomerId());
    	
    	personalCustomerService.personUnbindBankCard(req);
    	BaseResponse rsp = new BaseResponse();
    	rsp.setRspMsg("解绑成功");
    	return rsp;
    }
    
    @RequestMapping("/personal/bindCardList")
    public String personalBindCardList(HttpSession session, Model model) {
    	LoginInfo loginInfo = SessionUtil.getLoginInfoFromSession(session);
    	model.addAttribute("loginInfo", loginInfo);
    	
    	List<BindCardInfo> bindCardList = personalCustomerService.qryPersonBandCardList(loginInfo.getCustomerId());
    	
    	model.addAttribute("bindCardList", bindCardList);
    	return "personal/bindCardList";
    }
    
    
    @RequestMapping("/personal/register")
    @ResponseBody
    public Object personalRegister(@RequestBody PersonalRegisterInfo registerInfo, HttpSession session, Model model) {
    	
    	PersonalRegisterReq registerReq = new PersonalRegisterReq();
        String loginId = registerInfo.getPhone();
        registerReq.setLoginId(loginId);
        registerReq.setLoginIdType(String.valueOf(LoginIdUtil.getLoginIdType(loginId)));
        registerReq.setLoginPwd(registerInfo.getPassword());
        registerReq.setPlatformId(PlatformConst.PLATFORM_MERCHENT_CODE);
        registerReq.setName(registerInfo.getName());
        registerReq.setIdCardNumber(registerInfo.getIdentificationCard());
        registerReq.setIdCardType(registerInfo.getIdType());
        PersonalRegisterResp registerResp = personalCustomerService.personalRegister(registerReq);
        registerResp.setUrl("register-success.html");
    	
    	LoginInfo loginInfo = new LoginInfo();
    	loginInfo.setCustomerId(registerResp.getCustomerId());
    	loginInfo.setCustomerType(String.valueOf(CustomerType.PERSONAL.type()));
    	loginInfo.setLoginId(registerInfo.getPhone());
    	loginInfo.setName(registerInfo.getName());
    	
    	SessionUtil.setLoginInfoToSession(session, loginInfo);
    	return registerResp;
    }
}
