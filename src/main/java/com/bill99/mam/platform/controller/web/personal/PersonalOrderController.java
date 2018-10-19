package com.bill99.mam.platform.controller.web.personal;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Encoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bill99.mam.platform.common.constant.PlatformConst;
import com.bill99.mam.platform.common.enumeration.FunctionCodeEnum;
import com.bill99.mam.platform.common.enumeration.PayModeEnum;
import com.bill99.mam.platform.common.enumeration.ResultEnum;
import com.bill99.mam.platform.common.util.AmountUtil;
import com.bill99.mam.platform.common.util.CommonUtil;
import com.bill99.mam.platform.common.util.QrcodeUtil;
import com.bill99.mam.platform.common.util.WrappedBeanCopier;
import com.bill99.mam.platform.config.AcmsConfig;
import com.bill99.mam.platform.controller.web.adapter.personal.BankAcceptInfoService;
import com.bill99.mam.platform.controller.web.adapter.personal.QueryPciService;
import com.bill99.mam.platform.controller.web.adapter.personal.QuickPayService;
import com.bill99.mam.platform.controller.web.modelattribute.BankAcceptInfoDTO;
import com.bill99.mam.platform.controller.web.modelattribute.LoginInfo;
import com.bill99.mam.platform.controller.web.modelattribute.OrderPay;
import com.bill99.mam.platform.controller.web.modelattribute.OrderProductItemVo;
import com.bill99.mam.platform.controller.web.modelattribute.OrderVo;
import com.bill99.mam.platform.controller.web.modelattribute.QuickBindCardInfoDTO;
import com.bill99.mam.platform.controller.web.modelattribute.QuickPaymentDTO;
import com.bill99.mam.platform.controller.web.modelattribute.RechargeWithdrawalsCardInfo;
import com.bill99.mam.platform.controller.web.util.SessionUtil;
import com.bill99.mam.platform.persistence.dto.CustomerDto;
import com.bill99.mam.platform.persistence.dto.GoodsDto;
import com.bill99.mam.platform.persistence.dto.OrderDetailDto;
import com.bill99.mam.platform.persistence.dto.OrderDto;
import com.bill99.mam.platform.persistence.dto.OrderProductItemDto;
import com.bill99.mam.platform.persistence.repository.OrderDetailRepository;
import com.bill99.mam.platform.persistence.repository.OrderRepository;
import com.bill99.mam.platform.remote.dto.response.BindCardInfo;
import com.bill99.mam.platform.service.IGoodsService;
import com.bill99.mam.platform.service.IOrderService;
import com.bill99.mam.platform.service.IPersonalCustomerService;
import com.bill99.mam.platform.service.IPersonalOrderService;
import com.bill99.mam.platform.service.IRefundService;
import com.bill99.mam.platform.service.model.BaseResponse;
import com.bill99.mam.platform.service.model.request.AggregatePreOrderReq;
import com.bill99.mam.platform.service.model.request.DynamicCodeReq;
import com.bill99.mam.platform.service.model.request.IndAuthReq;
import com.bill99.mam.platform.service.model.request.IndAuthVerifyReq;
import com.bill99.mam.platform.service.model.request.OrderListReq;
import com.bill99.mam.platform.service.model.request.OrderPayReq;
import com.bill99.mam.platform.service.model.request.OrderSaveReq;
import com.bill99.mam.platform.service.model.request.ProductItem;
import com.bill99.mam.platform.service.model.request.RechargeWithdrawalsSaveReq;
import com.bill99.mam.platform.service.model.request.UnbindCardReq;
import com.bill99.mam.platform.service.model.response.AggregatePreOrderResp;
import com.bill99.mam.platform.service.model.response.DynamicCodeResp;
import com.bill99.mam.platform.service.model.response.IndAuthResp;
import com.bill99.mam.platform.service.model.response.IndAuthVerifyResp;
import com.bill99.mam.platform.service.model.response.OrderPayResp;
import com.bill99.mam.platform.service.model.response.OrderSaveResp;
import com.bill99.mam.platform.service.model.response.QuickPaymentResp;
import com.bill99.mam.platform.service.model.response.RefundResponse;
import com.bill99.mam.platform.service.model.response.UnbindCardResp;
import com.google.zxing.BarcodeFormat;

@Controller
public class PersonalOrderController {
    private static final Logger logger = LoggerFactory.getLogger(PersonalOrderController.class);

	private static final String IMG_FORMAT = "png";
	private static final String DYNAMIC_CODE = "DynamicCode";
	private static final String DYNAMIC_CODE_ONE_KEY = "DynamicCodeOneKey"; //一键支付token
	private static final String ONE_KEY = "1"; //0:快捷支付，1：一键支付
    @Resource
    private IGoodsService goodsService;
    @Resource
    private IPersonalOrderService personalOrderService;
    @Resource
    private IPersonalCustomerService personalCustomerService;
    @Resource
    private IOrderService orderService;
    @Resource
    private IRefundService refundService;
    @Resource
    private OrderRepository orderRepository;
    @Resource
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private QueryPciService queryPciService;
	@Autowired
    private QuickPayService quickPayService;
    @Autowired
	private BankAcceptInfoService bankAcceptInfoService;
    @Autowired
    private AcmsConfig acmsConfig;

    /**
     * 商品列表
     */
    @RequestMapping("/personal/goods")
    public String goods(HttpSession session, Model model) throws IOException {
    	List<GoodsDto> goods = goodsService.findAll();
    	if (CollectionUtils.isNotEmpty(goods)) {
    		for (GoodsDto dto : goods) {
    			Long customerId = dto.getCustomerId();
    			String customerName = null;
    			if (CommonUtil.isPlatform(customerId)) {
    				customerName = PlatformConst.PLATFORM_MERCHENT_NAME;
				} else {
					CustomerDto customerDto = personalCustomerService.queryCustomerById(customerId);
					customerName = customerDto.getName();
				}
    			dto.setCustomerName(customerName);
    			dto.setPrice(dto.getPrice().multiply(new BigDecimal("0.01")));
    		}
    	}
    	model.addAttribute("goods", goods);
    	return "personal/order/goods";
    }
    
    /**
     * 订单列表查询
     */
    @RequestMapping("/personal/orderListQuery/{pageNum}")
    public String orderListQuery(@PathVariable int pageNum, HttpSession session, Model model) throws IOException {
    	LoginInfo loginInfo = SessionUtil.getLoginInfoFromSession(session);
    	
    	OrderListReq qryVo = new OrderListReq();
    	int pageSize = 10;
    	qryVo.setPageSize(pageSize);
    	qryVo.setStartRow((pageNum-1)*pageSize);
    	qryVo.setUid(new Long(loginInfo.getCustomerId()));
    	
    	List<OrderDto> orderDtoList = orderRepository.selectPageList(qryVo);
    	Long countByCustomerId = orderRepository.countByCustomerId(new Long(loginInfo.getCustomerId()));
    	
    	
    	List<OrderVo> copyPropertiesOfList = WrappedBeanCopier.copyPropertiesOfList(orderDtoList, OrderVo.class);
        for (OrderVo vo:copyPropertiesOfList) {
            if(Objects.equals(vo.getFunctionCode(), 14) || Objects.equals(vo.getFunctionCode(), 15)){
                vo.setShow(false);
            }else{
                vo.setShow(true);
            }
        }
    	model.addAttribute("orders", copyPropertiesOfList);
    	model.addAttribute("pageNum", pageNum);
    	model.addAttribute("prePage", pageNum-1);
    	model.addAttribute("nextPage", pageNum+1);
    	model.addAttribute("lastPage", (countByCustomerId-1)/pageSize + 1);
    	return "personal/order/orderListQuery";
    }
    
    /**
     * 订单详情
     */
    @RequestMapping("/personal/orderSingleQuery")
    public String orderSingleQuery(@RequestParam(value="orderId") String orderId, HttpSession session, Model model) throws IOException {
    	
    	OrderDto order = orderRepository.findById(new Long(orderId));
    	List<OrderDetailDto> orderDetailDtoList = order.getOrderDetails();
    	
    	List<OrderProductItemVo> returnList = new ArrayList<OrderProductItemVo>();
    	if (CollectionUtils.isNotEmpty(orderDetailDtoList)) {
    		OrderProductItemVo vo = null;
    		for (OrderDetailDto detailDto : orderDetailDtoList) {
    			List<OrderProductItemDto> productList = detailDto.getProductList();
    			if (CollectionUtils.isNotEmpty(productList)) {
    				for (OrderProductItemDto dto : productList) {
    					vo = WrappedBeanCopier.copyProperties(dto, OrderProductItemVo.class);
    					vo.setMerchantName(detailDto.getSubMerchantName());
    					vo.setOrderId(orderId);
    					returnList.add(vo);
    				}
    			}
    		}
    	}
    	model.addAttribute("goods", returnList);
    	model.addAttribute("order", order);
    	return "personal/order/orderSingleQuery";
    }
    
    /**
     * 订单状态
     * @return
     */
    @RequestMapping("/personal/orderPayStatus/{orderId}")
    @ResponseBody
    public Object orderPayStatus(@PathVariable String orderId, HttpSession session) {
    	OrderDto order = orderRepository.findById(new Long(orderId));
    	
    	OrderPayResp resp = new OrderPayResp();
    	resp.setPayStatus(String.valueOf(order.getPayStatus()));
    	return resp;
    }
    
    /**
     * 退货
     * @throws IOException
     */
    @RequestMapping("/personal/returnGoods")
    @ResponseBody
    public Object returnGoods(@RequestParam(value="confirm", required=true) String confirm, Model model) throws IOException {
    	String[] details = confirm.split(";");
    	
    	List<RefundResponse> sendRefundRequest = refundService.sendRefundRequest(Arrays.asList(details));
    	BaseResponse res = new BaseResponse();
    	if (CollectionUtils.isNotEmpty(sendRefundRequest) && sendRefundRequest.size() > 0) {
    		RefundResponse result = sendRefundRequest.get(0);
    		res.setRspCode(result.getRspCode());
    		res.setRspMsg(result.getRspMsg());
    	}
    	return res;
    }
    
    /**
     * 充值提现页面
     */
    @RequestMapping("/personal/rechargeWithdrawalsPage")
    public String rechargeWithdrawalsPage(HttpSession session, Model model){
    	LoginInfo loginInfo = SessionUtil.getLoginInfoFromSession(session);
    	
    	String balance = personalCustomerService.queryAcctBalance(new Long(loginInfo.getCustomerId()));
    	model.addAttribute("balance", balance);
    	
    	List<BindCardInfo> bindCardList = personalCustomerService.qryPersonBandCardList(loginInfo.getCustomerId());
    	model.addAttribute("bindCardList", bindCardList);
    	
    	return "personal/rechargeWithdrawalsPage";
    }
    
    /**
     * 充值提现
     */
    @RequestMapping("/personal/rechargeWithdrawals")
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
   	 * 生成二维码
   	 * @param
   	 * @return
   	 * @throws Exception 
   	 */
   	@RequestMapping(value="/aggregate/qrcode")
   	@ResponseBody
	public Object generateQrcode(@RequestParam("params") String params, /*HttpServletResponse response, */HttpSession session)
			throws Exception {
		System.out.println(params);
		JSONObject json = JSON.parseObject(params);
		AggregatePreOrderReq req = new AggregatePreOrderReq();
		req.setAppId("mock");
		if (FunctionCodeEnum.DEPOSIT.value() == json.getIntValue("functionCode")) {
			RechargeWithdrawalsSaveReq saveReq = new RechargeWithdrawalsSaveReq();
			saveReq.setBankAcctId(json.getString("bankAcctId"));
			saveReq.setFunctionCode(json.getString("functionCode"));
			saveReq.setPayAmount(AmountUtil.changeY2F(json.getString("payAmount")));
			LoginInfo loginInfo = SessionUtil.getLoginInfoFromSession(session);
			saveReq.setBuyerId(new Long(loginInfo.getCustomerId()));

			OrderDto orderDto = orderService.saveRechargeOrder(saveReq);

			req.setOrderId(orderDto.getId());
			req.setFunctionCode(FunctionCodeEnum.DEPOSIT.value());
			req.setPayeeUId(new Long(PlatformConst.PLATFORM_MERCHENT_CODE));
		} else {
			req.setFunctionCode(json.getIntValue("functionCode"));
			req.setOrderId(json.getLong("orderId"));
			req.setPayeeUId(json.getLongValue("payeeUId"));
		}

		AggregatePreOrderResp resp = orderService.AggregatePreOrder(req);
		BufferedImage bufferImage = null;
		if (resp.getRspCode().equals("0000")) {
			bufferImage = QrcodeUtil.encode(resp.getPayUrl(), BarcodeFormat.QR_CODE, 300, 300);
		} else {
			bufferImage = QrcodeUtil.encode(resp.getRspMsg(), BarcodeFormat.QR_CODE, 300, 300);
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferImage, IMG_FORMAT, out);
		} catch (IOException e) {
			throw new IOException("Could not write an image of format " + IMG_FORMAT);
		} finally {
			//			if (response.getOutputStream() != null) {
			//				response.getOutputStream().flush();
			//				response.getOutputStream().close();
			//			}
		}
		BASE64Encoder encoder = new BASE64Encoder();
		String imgStr = encoder.encode(out.toByteArray());
		OrderPayResp res = new OrderPayResp();
		res.setImageBase64(imgStr);
		res.setOrderId(String.valueOf(req.getOrderId()));
		return res;
	}
	/**
     * 确认订单
     */
    @RequestMapping("/personal/orderConfirm")
    public String orderConfirm(@RequestParam(value="confirm", required=true) String confirm, HttpSession session, Model model) throws IOException {
    	//产品id,产品名称,商家id,商家名称,单价,数量
    	String[] details = confirm.split(";");
    	
    	LoginInfo loginInfo = SessionUtil.getLoginInfoFromSession(session);
    	
    	//生成订单
    	OrderSaveReq orderSaveReq = generateOrderSaveReq(details);
    	orderSaveReq.setBuyerId(new Long(loginInfo.getCustomerId()));
    	
    	OrderSaveResp orderSaveRsp = orderService.saveOrder(orderSaveReq);
    	
    	return orderPayPage(orderSaveRsp.getOrderId(), session, model);
    }
    
    /** 通过orderId 跳转到订单支付页面*/
    @RequestMapping("/personal/orderPayPage/{orderId}")
    public String orderPayPage(@PathVariable Long orderId, HttpSession session, Model model) throws IOException {
    	LoginInfo loginInfo = SessionUtil.getLoginInfoFromSession(session);
    	
    	OrderDto orderDto = orderRepository.findById(orderId);
    	model.addAttribute("orderId", orderId);
    	try {
			model.addAttribute("orderAmount", AmountUtil.changeF2Y(orderDto.getPayAmount()));
		} catch (Exception e) {
			//nothing to do
		}
    	
    	String balance = personalCustomerService.queryAcctBalance(new Long(loginInfo.getCustomerId()));
    	model.addAttribute("balance", balance);
    	
    	List<BindCardInfo> bindCardList = personalCustomerService.qryPersonBandCardList(loginInfo.getCustomerId());
    	model.addAttribute("bindCardList", bindCardList);
    	
    	List<OrderDetailDto> detailList = new ArrayList<OrderDetailDto>();
    	OrderDetailDto platformDetailDto = new OrderDetailDto();
    	platformDetailDto.setSubMerchantUid(new Long(PlatformConst.PLATFORM_MERCHENT_CODE));
    	platformDetailDto.setSubMerchantName(PlatformConst.PLATFORM_MERCHENT_NAME);
    	detailList.add(platformDetailDto);
    	for (OrderDetailDto orderDetail : orderDto.getOrderDetails()) {
    		if (!new Long(PlatformConst.PLATFORM_MERCHENT_CODE).equals(orderDetail.getSubMerchantUid())) {
    			detailList.add(orderDetail);
    		}
    	}
    	model.addAttribute("details", detailList);
    	if(StringUtils.equals("on", acmsConfig.getSwitchSDK())){
    	    model.addAttribute("switchSDK", true);
        }else{
            model.addAttribute("switchSDK", false);
        }

    	return "personal/order/pay";
    	
    }
    
    private OrderSaveReq generateOrderSaveReq(String[] details) {
    	OrderSaveReq req = new OrderSaveReq();
    	List<ProductItem> list = new ArrayList<ProductItem>();
    	ProductItem items = null;
    	//产品id,产品名称,商家id,商家名称,单价,数量
    	for (String detail : details) {
    		String[] split = detail.split(",");
    		items = new ProductItem();
    		items.setProductTag(new Long(split[0]));
    		items.setProductName(split[1]);
    		items.setSellerId(new Long(split[2]));
    		items.setSellerName(split[3]);
    		items.setProductPrice(new Long(AmountUtil.changeY2F(split[4])));
    		items.setProductNum(new Long(split[5]));
    		list.add(items);
    	}
    	req.setOrderItems(list);
		return req;
	}

    /**
     * 订单支付
     */
    @RequestMapping("/personal/orderPay")
    @ResponseBody
    public Object orderPay(@RequestBody OrderPay orderPay, Model model) {
    	
    	OrderPayReq orderPayReq = new OrderPayReq();
    	orderPayReq.setOrderId(new Long(orderPay.getOrderId()));
    	orderPayReq.setMemberBankAcctId(orderPay.getBankAcctId());
    	orderPayReq.setPayMode(new Integer(orderPay.getFunctionCode()));
    	orderPayReq.setPayeeUId(new Long(orderPay.getPayeeUId()));
    	
		OrderPayResp payOrder = orderService.payOrder(orderPayReq);
		
    	BaseResponse res = new BaseResponse();
    	res.setRspCode(payOrder.getRspCode());
    	res.setRspMsg(payOrder.getRspMsg());
    	return res;
    }
    /**
     * 解绑银行卡入口页面
     * @param
     * @return
     */
    @RequestMapping("/personal/unbindCardPage")
    public String unbindCardPage(@RequestParam int payMode,  Model model, HttpSession session){
        model.addAttribute("payMode", payMode);
        LoginInfo loginInfo = SessionUtil.getLoginInfoFromSession(session);
        String uId = loginInfo.getCustomerId();
        String customerType = loginInfo.getCustomerType();
        ArrayList<QuickBindCardInfoDTO> bindCardList = null;
        if(Objects.equals(payMode, PayModeEnum.QUICK_PAY.value())){
            bindCardList = queryPciService.queryQuickPay(uId, customerType);
        }else if (Objects.equals(payMode, PayModeEnum.ONE_KEY.value())){
            bindCardList = queryPciService.queryOneKey(uId, customerType);
        }
        model.addAttribute("bindCardList", bindCardList);
        model.addAttribute("bindCardListJson", JSON.toJSON(bindCardList));

        return "personal/unbindCard";
    }
    /**
     * 快捷支付入口页面
     * @param orderId
     * @return
     */
    @RequestMapping("/personal/quickpay/{orderId}/{payamount:.+}")
    public String showQuickPay(@PathVariable Long orderId, @PathVariable String payamount,
							   @RequestParam String payeeUId, Model model, HttpSession session){
        LoginInfo loginInfo = SessionUtil.getLoginInfoFromSession(session);
        String uId = loginInfo.getCustomerId();
        String type = loginInfo.getCustomerType();
        //查询绑卡列表
        ArrayList<QuickBindCardInfoDTO> bindCardList = queryPciService.queryQuickPay(uId, type);

		model.addAttribute("orderId", orderId);
		model.addAttribute("payamount", payamount);
		model.addAttribute("payeeUId", payeeUId);
        model.addAttribute("bindCardList", bindCardList);
        model.addAttribute("bindCardListJson", JSON.toJSON(bindCardList));
    	return "personal/order/quickPay";
	}

	/**
	 * 一键支付入口页面
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/personal/quickpay/onekey/{orderId}/{payamount:.+}")
	public String showQuickPayOneKey(@PathVariable Long orderId, @PathVariable String payamount,
							   @RequestParam String payeeUId, Model model, HttpSession session){
		LoginInfo loginInfo = SessionUtil.getLoginInfoFromSession(session);
		String uId = loginInfo.getCustomerId();
		String type = loginInfo.getCustomerType();
		//查询绑卡列表
		ArrayList<QuickBindCardInfoDTO> bindCardList = queryPciService.queryOneKey(uId, type);

		model.addAttribute("orderId", orderId);
		model.addAttribute("payamount", payamount);
		model.addAttribute("payeeUId", payeeUId);
		model.addAttribute("bindCardList", bindCardList);
		model.addAttribute("bindCardListJson", JSON.toJSON(bindCardList));
		return "personal/order/quickPayOneKey";
	}
    /**
     * 快捷(一键)支付绑卡入口页面
     * @param orderId
     * @return
     */
    @RequestMapping("/personal/quickpayBindCard/{orderId}")
    public String showQuickPayBindCard(@PathVariable Long orderId, @RequestParam String payeeUId,
                                       @RequestParam String type, @RequestParam String payAmount, Model model){
        model.addAttribute("orderId", orderId);
        model.addAttribute("payAmount", payAmount);

        List<BankAcceptInfoDTO> bankAcceptInfoList = bankAcceptInfoService.queryAcceptList();
        model.addAttribute("bankAcceptInfoList", bankAcceptInfoList);
        model.addAttribute("payeeUId", payeeUId);
        model.addAttribute("bankAcceptInfoListJson", JSON.toJSON(bankAcceptInfoList));

        String path = "personal/order/quickPayBindCard";
        if(StringUtils.equals(type, ONE_KEY)){ //判断是快捷绑卡还是一键支付绑卡
            path = "personal/order/quickPayBindCardOneKey";
        }
        return path;
    }
    /**
     * 快捷支付获取短信验证码
     * @param dynamicCodeReq
     * @return
     */
	@ResponseBody
	@RequestMapping("/personal/quickpay/getdynamicnum")
	public DynamicCodeResp getDynamicNum(@RequestBody DynamicCodeReq dynamicCodeReq, HttpSession session){
        DynamicCodeResp response = orderService.getDynamicCode(dynamicCodeReq);
        if(response != null){
            if (StringUtils.equals(response.getRspCode(), ResultEnum.SUCCESS.code())){
                session.setAttribute(DYNAMIC_CODE, response);
            }
        }
        return response;
    }

    /**
     * 快捷(一键)支付提交
     * @return
     */
    @ResponseBody
    @RequestMapping("/personal/quickpay/pay")
    public QuickPaymentResp quickPay(@RequestBody QuickPaymentDTO quickPaymentDTO, HttpSession session){
		DynamicCodeResp dynamicCode = (DynamicCodeResp) session.getAttribute(DYNAMIC_CODE);
        if(dynamicCode != null && dynamicCode.getToken() != null){
            quickPaymentDTO.setToken(dynamicCode.getToken());
        }
		QuickPaymentResp response = quickPayService.quickPay(quickPaymentDTO);
	    return response;
    }

    /**
     * 一键支付鉴权申请
     * @param indAuthReq
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/personal/quickpay/indauth")
    public IndAuthResp indAuth(@RequestBody IndAuthReq indAuthReq, HttpSession session){
        IndAuthResp response = orderService.indAuth(indAuthReq);
        if(response != null){
            if (StringUtils.equals(response.getRspCode(), ResultEnum.SUCCESS.code())){
                session.setAttribute(DYNAMIC_CODE_ONE_KEY, response);
            }
        }
        return response;
    }

    /**
     * 一键支付鉴权确认
     * @param indAuthVerifyReq
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/personal/quickpay/indauthverify")
    public IndAuthVerifyResp indAuthVerify(@RequestBody IndAuthVerifyReq indAuthVerifyReq, HttpSession session){
        IndAuthResp dynamicCode = (IndAuthResp) session.getAttribute(DYNAMIC_CODE_ONE_KEY);
        if(dynamicCode != null && dynamicCode.getToken() != null){
            indAuthVerifyReq.setToken(dynamicCode.getToken());
            indAuthVerifyReq.setShortBankAcctId(dynamicCode.getShortBankAcctId());
        }

        IndAuthVerifyResp response = orderService.indAuthVerify(indAuthVerifyReq);

        return response;
    }

    /**
     * 解绑平台用户银行卡
     * @param unbindCardReq
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/personal/quickpay/unbindcard")
    public UnbindCardResp unbindCard(@RequestBody UnbindCardReq unbindCardReq, HttpSession session){
        LoginInfo loginInfo = SessionUtil.getLoginInfoFromSession(session);
        String uId = loginInfo.getCustomerId();
        if(unbindCardReq != null){
            unbindCardReq.setUId(uId);
        }
        UnbindCardResp response = orderService.unbindCard(unbindCardReq);
        return response;
    }

    /**
     * SDK支付下单
     * @param params
     * @return
     */
	@RequestMapping(value="/aggregate/qrcode/sdk", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public Object createSDKOrder(@RequestParam("params") String params){
		JSONObject json = JSON.parseObject(params);

		AggregatePreOrderReq req = new AggregatePreOrderReq();
		req.setAppId("mock");

		req.setFunctionCode(json.getIntValue("functionCode"));
		req.setOrderId(json.getLong("orderId"));
		req.setPayeeUId(json.getLongValue("payeeUId"));
		//SDK 下单
		req.setPayMode(json.getString("payMode"));
		req.setPayType(json.getString("payType"));
        //微信加传WECHATAPP
		if(StringUtils.equals("WECHATAPP", req.getPayType()) && acmsConfig.getWechatAppId() != null){
		    req.setSubAppId(acmsConfig.getWechatAppId());
        }
		AggregatePreOrderResp resp = orderService.AggregatePreOrder(req);
//		return resp.toString();
        return JSON.toJSONString(resp);
	}
}
