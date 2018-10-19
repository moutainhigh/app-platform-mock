package com.bill99.mam.platform.controller.web.enterprise;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bill99.mam.platform.common.enumeration.FunctionCodeEnum;
import com.bill99.mam.platform.common.util.AmountUtil;
import com.bill99.mam.platform.controller.web.modelattribute.LoginInfo;
import com.bill99.mam.platform.controller.web.modelattribute.RechargeWithdrawalsCardInfo;
import com.bill99.mam.platform.controller.web.util.SessionUtil;
import com.bill99.mam.platform.persistence.dto.OrderDto;
import com.bill99.mam.platform.persistence.repository.OrderDetailRepository;
import com.bill99.mam.platform.persistence.repository.OrderRepository;
import com.bill99.mam.platform.remote.dto.response.BindCardInfo;
import com.bill99.mam.platform.service.IEnterpriseCustomerService;
import com.bill99.mam.platform.service.IOrderService;
import com.bill99.mam.platform.service.model.BaseResponse;
import com.bill99.mam.platform.service.model.request.RechargeWithdrawalsSaveReq;
import com.bill99.mam.platform.service.model.response.OrderPayResp;

@Controller
public class EnterpriseOrderController {
	
    @Resource
    private IEnterpriseCustomerService enterpriseCustomerService;
    @Resource
    private IOrderService orderService;
    @Resource
    private OrderRepository orderRepository;
    @Resource
    private OrderDetailRepository orderDetailRepository;
    
    /**
     * 充值提现页面
     */
    @RequestMapping("/enterprise/rechargeWithdrawalsPage")
    public String rechargeWithdrawalsPage(HttpSession session, Model model){
    	LoginInfo loginInfo = SessionUtil.getLoginInfoFromSession(session);
    	
    	String balance = enterpriseCustomerService.queryAcctBalance(new Long(loginInfo.getCustomerId()));
    	model.addAttribute("balance", balance);
    	
    	List<BindCardInfo> bindCardList = enterpriseCustomerService.qryEnterpriseBandCardList(loginInfo.getCustomerId());
    	model.addAttribute("bindCardList", bindCardList);
    	
    	return "enterprise/rechargeWithdrawalsPage";
    }
    
    /**
     * 充值提现
     */
    @RequestMapping("/enterprise/rechargeWithdrawals")
    @ResponseBody
    public Object rechargeWithdrawals(@RequestBody RechargeWithdrawalsCardInfo request, HttpSession session, Model model){
    	System.out.println(request);
    	LoginInfo loginInfo = SessionUtil.getLoginInfoFromSession(session);
    	RechargeWithdrawalsSaveReq req = generateRechargeSaveReq(request);
    	req.setBuyerId(new Long(loginInfo.getCustomerId()));
    	
    	BaseResponse res = null;
    	if (FunctionCodeEnum.DEPOSIT.value().toString().equals(request.getFunctionCode())) {
    		OrderDto orderDto = orderService.saveRechargeOrder(req);
    		res = orderService.payRechargeOrder(orderDto, req.getBankAcctId());
    	} else if (FunctionCodeEnum.WITHDRAW.value().toString().equals(request.getFunctionCode())){
    		res = orderService.saveWithdrawals(req);
    	}
    	return res;
    }
    
    private RechargeWithdrawalsSaveReq generateRechargeSaveReq(RechargeWithdrawalsCardInfo request) {
    	RechargeWithdrawalsSaveReq req = new RechargeWithdrawalsSaveReq();
    	req.setBankAcctId(request.getBankAcctId());
    	req.setFunctionCode(request.getFunctionCode());
    	req.setPayAmount(AmountUtil.changeY2F(request.getPayAmount()));
    	
		return req;
	}

    
    /**
     * 订单状态
     * @return
     */
    @RequestMapping("/enterprise/orderPayStatus/{orderId}")
    @ResponseBody
    public Object orderPayStatus(@PathVariable String orderId, HttpSession session) {
    	OrderDto order = orderRepository.findById(new Long(orderId));
    	
    	OrderPayResp resp = new OrderPayResp();
    	resp.setPayStatus(String.valueOf(order.getPayStatus()));
    	return resp;
    }
    
}
