package com.bill99.mam.platform.controller.web;

import com.bill99.mam.platform.persistence.dto.CustomerDto;
import com.bill99.mam.platform.service.ICustomerService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller

public class CustomerController {
    @Resource
    private ICustomerService customerAppService;
    @RequestMapping("/queryCustomerById")
    public String queryByid(@RequestParam Integer id, Model model){
        CustomerDto customerDto = customerAppService.queryCustomerById(id);
        model.addAttribute("customer", customerDto);
        return "customer";
    }
}
