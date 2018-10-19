package com.bill99.mam.platform.controller.web.post;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bill99.mam.platform.common.enumeration.ResultEnum;
import com.bill99.mam.platform.common.util.OkHttpUtil;
import com.bill99.mam.platform.config.AcmsConfig;
import com.bill99.mam.platform.controller.web.modelattribute.PostInfo;
import com.bill99.mam.platform.service.model.response.DoPostResp;

@Controller
public class DoPostController {
	
    private static final Logger logger = LoggerFactory.getLogger(DoPostController.class);
	
    @Autowired
	private AcmsConfig acmsConfig;
    
	@RequestMapping("/post/index")
    public String doPost(HttpSession session, Model model){
    	return "personal/doPost";
    }
    
    @RequestMapping("/post/doPost")
    @ResponseBody
    public DoPostResp doPost(@RequestBody PostInfo postInfo, HttpSession session, Model model){
    	DoPostResp resp = new DoPostResp();
    	try {
    		if(null == postInfo || !postInfo.getPostKey().equalsIgnoreCase(acmsConfig.getPostKey())) {
    			logger.error("请求key不合法， req={}", postInfo);
    			resp.setRspCode("5001");
    			resp.setRspMsg("请求key不合法");
    		} else {
    			String data = OkHttpUtil.getInstance().post(postInfo.getUrl(), postInfo.getRequestParam());
    			resp.setData(data);
    		}
		} catch (Exception e) {
			resp.setRspCode(ResultEnum.FAILED.code());
			resp.setRspMsg(ResultEnum.FAILED.message());
			logger.error("发送请求异常， req={}, e={}", postInfo, e);
		}
    	logger.info("发送请求， req={}, resp={}", postInfo, resp);
        return resp;
    }
    
}
