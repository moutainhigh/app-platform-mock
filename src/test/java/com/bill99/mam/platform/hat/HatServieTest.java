package com.bill99.mam.platform.hat;

import com.alibaba.fastjson.JSON;
import com.bill99.mam.platform.remote.dto.request.*;
import com.bill99.mam.platform.remote.dto.response.HatPersonalRegisterResp;
import com.bill99.mam.platform.remote.service.HatService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HatServieTest {
    @Autowired
    private HatService hatService;
    @Test
    public void testPersonalRegister() {
        /**
         * 个人注册
         */
        HatPersonalRegisterReq registerReq = new HatPersonalRegisterReq();
        registerReq.setMobile("13200001111");
        registerReq.setUId("2132324344");
        registerReq.setName("mock平台个人注册1");
        registerReq.setIdCardType("101");
        registerReq.setIdCardNumber("341221198711282998");
        registerReq.setEmail("3423424@qq.com");
        HatPersonalRegisterResp registerResp = hatService.personalRegister(registerReq);
        System.out.println(registerResp);
    }

    @Test
    public void testQueryAcctBalance() {//查询余额  --完成
        HatQueryAcctBalanceReq req = new HatQueryAcctBalanceReq();
        req.setUId("1");
        req.setAccountType(new String[]{"DEF00001"});
        System.out.println(JSON.toJSONString(hatService.queryAcctBalance(req),true));
    }

    @Test
    public void testBankCardAuth() {//银行卡签权
        HatBankCardAuthReq req = new HatBankCardAuthReq();
        req.setUId("1");
        req.setBankAcctId("");
        req.setMobile("");
        req.setIdCardNumber("");
        req.setIdCardType("");
        req.setName("");
        System.out.println(hatService.bankCardAuth(req));
    }

    @Test
    public void testPersonBankCardBind() {//个人银行卡绑定
        PersonBankCardBindReq req = new PersonBankCardBindReq();
        req.setUId("8");
        System.out.println(hatService.bindPersonBankCard(req));
    }

    @Test
    public void testQryPersonBandCardList() {//查询个人银行卡列表
        PersonBankCarListQryReq req = new PersonBankCarListQryReq();
        req.setUId("10006");
        System.out.println(hatService.qryPersonBandCardList(req));
    }

    @Test
    public void testPersonBankCardUnbind() {//个人银行卡解绑
        PersonBankCardUnbindReq req = new PersonBankCardUnbindReq();
        req.setUId("8");
        System.out.println(hatService.unbindPersonBankCard(req));
    }

    @Test
    public void testQryBankCardAcceptList() {//查询支持的绑卡银行列表 --已完成
        BankCardAcceptListQryReq req = new BankCardAcceptListQryReq();
        System.out.println(JSON.toJSONString(hatService.qryBankCardAcceptList(req),true));
    }

    @Test
    public void testBindEnterpriseBandCard() {//商户银行卡绑定
        EnterpriseBankCardBindReq req = new EnterpriseBankCardBindReq();
        req.setUId("8");
        System.out.println(hatService.bindEnterpriseBandCard(req));
    }
    @Test
    public void queryPersonInfoTest(){//查询个人会员信息 -已完成
        HatQryPersonInfoReq request = new HatQryPersonInfoReq();
        request.setUId("10006");
        System.out.println(JSON.toJSONString(hatService.queryPersonInfo(request),true));
    }


}
