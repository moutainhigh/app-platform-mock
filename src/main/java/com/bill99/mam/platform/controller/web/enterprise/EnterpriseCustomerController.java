package com.bill99.mam.platform.controller.web.enterprise;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bill99.mam.platform.common.enumeration.CustomerType;
import com.bill99.mam.platform.common.util.WrappedBeanCopier;
import com.bill99.mam.platform.controller.web.modelattribute.CustomerVo;
import com.bill99.mam.platform.controller.web.modelattribute.EnterpriseBindCardInfo;
import com.bill99.mam.platform.controller.web.modelattribute.EnterpriseRegisterInfo;
import com.bill99.mam.platform.controller.web.modelattribute.LoginInfo;
import com.bill99.mam.platform.controller.web.modelattribute.RechargeWithdrawalsCardInfo;
import com.bill99.mam.platform.persistence.dto.CustomerDto;
import com.bill99.mam.platform.remote.dto.response.BindCardInfo;
import com.bill99.mam.platform.service.IEnterpriseCustomerService;
import com.bill99.mam.platform.service.model.BaseResponse;
import com.bill99.mam.platform.service.model.request.EnterpriseBindCardReq;
import com.bill99.mam.platform.service.model.request.EnterpriseUnBindCardReq;
import com.bill99.mam.platform.service.model.response.EnterpriseBindCardResp;

@Controller
public class EnterpriseCustomerController {
    @Resource
    private IEnterpriseCustomerService enterpriseCustomerService;
    
    @RequestMapping("/enterprise/register")
    @ResponseBody
    public Object enterpriseRegister(@RequestBody EnterpriseRegisterInfo registerInfo, HttpSession session, Model model) {
    	System.out.println(registerInfo);
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("status", "success");
    	map.put("url", "register-success.html");
    	
    	LoginInfo loginInfo = new LoginInfo();
    	loginInfo.setCustomerType(String.valueOf(CustomerType.ENTERPRISE.type()));
    	loginInfo.setLoginId(registerInfo.getIdContent());
    	
    	session.setAttribute("loginInfo", loginInfo);
    	return map;
    }
    
    @RequestMapping("/enterprise/query")
    public String queryByid(HttpSession session, Model model){
    	LoginInfo loginInfo = (LoginInfo)session.getAttribute("loginInfo");
        CustomerDto customerDto = enterpriseCustomerService.queryCustomerById(new Long(loginInfo.getCustomerId()));
        CustomerVo copyProperties = WrappedBeanCopier.copyProperties(customerDto, CustomerVo.class);
        model.addAttribute("customer", copyProperties);
        return "enterprise/query";
    }
    
    @RequestMapping("/enterprise/bindCardPage")
    public String enterpriseBindCardPage(HttpSession session, Model model) {
    	LoginInfo loginInfo = (LoginInfo)session.getAttribute("loginInfo");
    	model.addAttribute("loginInfo", loginInfo);
    	return "enterprise/bindCard";
    }
    
    @RequestMapping("/enterprise/bindCard")
    @ResponseBody
    public Object enterpriseBindCard(@RequestBody EnterpriseBindCardInfo bindCardInfo, HttpSession session, Model model) {
    	EnterpriseBindCardReq req = new EnterpriseBindCardReq();
    	LoginInfo loginInfo = getLoginInfoFromSession(session);
    	req.setUId(loginInfo.getCustomerId());
    	req.setBankAcctId(bindCardInfo.getBankAcctId());
    	req.setBankId(bindCardInfo.getBankId());
    	req.setBranchBankName(bindCardInfo.getBranchBankName());
    	req.setName(bindCardInfo.getName());
    	req.setProvince(bindCardInfo.getProvince());
    	req.setCity(bindCardInfo.getCity());
    	
    	
    	String memberBankAcctId = enterpriseCustomerService.bindEnterpriseBankCard(req);
    	EnterpriseBindCardResp rsp = new EnterpriseBindCardResp();
    	rsp.setMemberBankAcctId(memberBankAcctId);
    	return rsp;
    }
    
    @RequestMapping("/enterprise/unBindbankCard/{memberBankAcctId}")
    @ResponseBody
    public Object enterpriseUnBindbankCard(@PathVariable String memberBankAcctId, HttpSession session, Model model) {
    	
    	EnterpriseUnBindCardReq req = new EnterpriseUnBindCardReq();
    	req.setMemberBankAcctId(memberBankAcctId);
    	
    	LoginInfo loginInfo = getLoginInfoFromSession(session);
    	req.setUId(loginInfo.getCustomerId());
    	
    	enterpriseCustomerService.enterpriseUnbindBankCard(req);
    	BaseResponse rsp = new BaseResponse();
    	rsp.setRspMsg("解绑成功");
    	return rsp;
    }
    
    @RequestMapping("/enterprise/bindCardList")
    public String enterpriseBindCardList(HttpSession session, Model model) {
    	LoginInfo loginInfo = getLoginInfoFromSession(session);
    	model.addAttribute("loginInfo", loginInfo);
    	
    	List<BindCardInfo> bindCardList = enterpriseCustomerService.qryEnterpriseBandCardList(loginInfo.getCustomerId());
    	
    	model.addAttribute("bindCardList", bindCardList);
    	return "enterprise/bindCardList";
    }
    
    private LoginInfo getLoginInfoFromSession(HttpSession session) {
    	LoginInfo loginInfo = (LoginInfo)session.getAttribute("loginInfo");
    	return loginInfo;
    }
}
