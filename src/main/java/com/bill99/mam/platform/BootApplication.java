package com.bill99.mam.platform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication
@ImportResource({"classpath:spring/spring-config-common.xml"})
@MapperScan("com.bill99.mam.platform.persistence.dao")
public class BootApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BootApplication.class);
    }

    @RequestMapping("/")
    public String index(){
        return "forword:/index.html";
    }
}