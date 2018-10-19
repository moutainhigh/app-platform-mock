package com.bill99.mam.platform.hat;

import com.bill99.mam.platform.common.util.WrappedBeanCopier;
import com.bill99.mam.platform.remote.dto.request.HatPersonalRegisterReq;
import com.bill99.mam.platform.remote.dto.request.HatQueryAcctBalanceReq;
import com.bill99.mam.platform.remote.dto.response.HatQueryAcctBalanceResp;
import com.bill99.mam.platform.remote.service.HatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 与Hat联调测试使用
 */
@Controller
public class RetrofitController {
    @Autowired
    private HatService hatService;

    @RequestMapping("/test")
    @ResponseBody
    public Object login() {
        HatPersonalRegisterReq request = new HatPersonalRegisterReq();
        return hatService.personalRegister(request);
    }

    @RequestMapping("/account/balance")
    @ResponseBody
    public HatQueryAcctBalanceResp queryAcctBalance(@RequestBody QryBalanceReq request) {
        HatQueryAcctBalanceReq balanceReq = WrappedBeanCopier.copyProperties(request, HatQueryAcctBalanceReq.class);
        balanceReq.setUId(request.getUid());
        return hatService.queryAcctBalance(balanceReq);
    }
}
